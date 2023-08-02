package agh.ryszard.blazej.heartbeat.viewmodel

import agh.ryszard.blazej.heartbeat.data.EcgDataResponse
import agh.ryszard.blazej.heartbeat.data.HrDataResponse
import agh.ryszard.blazej.heartbeat.data.IMU9DataResponse
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.movesense.mds.Mds
import com.movesense.mds.MdsConnectionListener
import com.movesense.mds.MdsException
import com.movesense.mds.MdsNotificationListener
import com.movesense.mds.MdsSubscription


const val URI_MEAS_IMU = "Meas/IMU9/13"
const val URI_MEAS_ECG = "Meas/ECG/125"
const val URI_MEAS_HR = "Meas/HR"
const val URI_EVENTLISTENER = "suunto://MDS/EventListener"

class ConnectionViewModel : ViewModel() {

    val connectionListener = ConnectionListener()
    private lateinit var _macAddress: String
    lateinit var deviceName: String
    lateinit var mds: Mds
    lateinit var mdsSubscription: MdsSubscription
    var onConnectionComplete = {}

    //accelerometer
    private val _accX = MutableLiveData<Double>(0.0)
    val aacX: LiveData<Double> = _accX
    private val _accY = MutableLiveData<Double>(0.0)
    val aacY: LiveData<Double> = _accY
    private val _accZ = MutableLiveData<Double>(0.0)
    val aacZ: LiveData<Double> = _accZ

    //gyroscope
    private val _gyroX = MutableLiveData<Double>(0.0)
    val gyroX: LiveData<Double> = _gyroX
    private val _gyroY = MutableLiveData<Double>(0.0)
    val gyroY: LiveData<Double> = _gyroY
    private val _gyroZ = MutableLiveData<Double>(0.0)
    val gyroZ: LiveData<Double> = _gyroZ

    //magnetometer
    private val _magnX = MutableLiveData<Double>(0.0)
    val magnX: LiveData<Double> = _magnX
    private val _magnY = MutableLiveData<Double>(0.0)
    val magnY: LiveData<Double> = _magnY
    private val _magnZ = MutableLiveData<Double>(0.0)
    val magnZ: LiveData<Double> = _magnZ

    //other parameters
    private val _ecg = MutableLiveData<Int>(0)
    val ecg: LiveData<Int> = _ecg
    private val _hr = MutableLiveData<Int>(0)
    val hr: LiveData<Int> = _hr

    fun setConnectedDevice(macAddress: String, deviceName: String, context: Context) {
        _macAddress = macAddress
        this.deviceName = deviceName
    }

    fun connectDevice(context: Context) {
        //setup MDS
        mds = Mds.builder().build(context)
        connectionListener.onConnectionComplete = onConnectionComplete
        mds.connect(_macAddress, connectionListener)
    }

    fun disconnectDevice() {
        mds.disconnect(_macAddress)
        mdsSubscription.unsubscribe()
    }

    fun startListening(context: Context) {

        // first we connect with imu (accelerometer + gyroscope + magnetometer)
        val imuContract: String = FormatHelper.formatContractToJson(connectionListener.serial, URI_MEAS_IMU)
        Log.d("Listening", imuContract)

        mdsSubscription = mds.subscribe(
            URI_EVENTLISTENER, imuContract, object : MdsNotificationListener {
                override fun onNotification(data: String) {
                    Log.d("Listening", "onNotification(): $data")

                    val imuResponse = Gson().fromJson(data, IMU9DataResponse::class.java)
                    if (imuResponse != null) {
                        if(!imuResponse.body.arrayAcc.isNullOrEmpty()) {
                            _accX.value = imuResponse.body.arrayAcc[0].x
                            _accY.value = imuResponse.body.arrayAcc[0].y
                            _accZ.value = imuResponse.body.arrayAcc[0].z
                        }
                        if(!imuResponse.body.arrayGyro.isNullOrEmpty()) {
                            _gyroX.value = imuResponse.body.arrayGyro[0].x
                            _gyroY.value = imuResponse.body.arrayGyro[0].y
                            _gyroZ.value = imuResponse.body.arrayGyro[0].z
                        }
                        if(!imuResponse.body.arrayMagn.isNullOrEmpty()) {
                            _magnX.value = imuResponse.body.arrayMagn[0].x
                            _magnY.value = imuResponse.body.arrayMagn[0].y
                            _magnZ.value = imuResponse.body.arrayMagn[0].z
                        }
                    }
                }

                override fun onError(error: MdsException) {
                    Log.e("Listening", "subscription onError(): ", error)
                }
            })

        // second connection is with the ECG sensor

        val ecgContract = FormatHelper.formatContractToJson(connectionListener.serial, URI_MEAS_ECG)
        Log.d("ListeningECG", ecgContract)

        mdsSubscription = Mds.builder().build(context).subscribe(
            URI_EVENTLISTENER, ecgContract, object : MdsNotificationListener {
                override fun onNotification(data: String) {
                    Log.d("ListeningECG", "onNotification(): $data")

                    val ecgResponse = Gson().fromJson(data, EcgDataResponse::class.java)
                    if (ecgResponse?.body != null) {
                        if(!ecgResponse.body!!.data.isNullOrEmpty()) {
                            _ecg.value = ecgResponse.body!!.data!![0]
                        }
                    }
                }

                override fun onError(error: MdsException) {
                    Log.e("ListeningECG", "subscription onError(): ", error)
                }
            })

        // lastly we connect with heart rate measurement
        val hrContract = FormatHelper.formatContractToJson(connectionListener.serial, URI_MEAS_HR)
        Log.d("ListeningHR", hrContract)

        mdsSubscription = Mds.builder().build(context).subscribe(
            URI_EVENTLISTENER, hrContract, object : MdsNotificationListener {
                override fun onNotification(data: String) {
                    Log.d("ListeningHR", "onNotification(): $data")

                    val hrResponse = Gson().fromJson(data, HrDataResponse::class.java)
                    if (hrResponse?.body != null) {
                        if(hrResponse.body.average != null) {
                            _hr.value = hrResponse.body.average.toInt()
                        }
                    }
                }

                override fun onError(error: MdsException) {
                    Log.e("ListeningECG", "subscription onError(): ", error)
                }
            })
    }
}

class ConnectionListener : MdsConnectionListener {

    var onConnectionComplete = {}
    lateinit var serial: String

    override fun onConnect(p0: String?) {
        Log.d("ViewModel", "onConnect:$p0");
    }

    override fun onConnectionComplete(p0: String?, p1: String?) {
        Log.d("ViewModel", "onConnectionComplete: $p0, $p1");
        serial = p1!!
        onConnectionComplete()
    }

    override fun onError(p0: MdsException?) {
        Log.e("ViewModel", "onError:$p0");
    }

    override fun onDisconnect(p0: String?) {
        Log.d("ViewModel", "onDisconnect: $p0");
    }

}

object FormatHelper {
    fun formatContractToJson(serial: String?, uri: String?): String {
        val sb = java.lang.StringBuilder()
        return sb.append("{\"Uri\": \"").append(serial).append("/").append(uri).append("\"}")
            .toString()
    }
}
