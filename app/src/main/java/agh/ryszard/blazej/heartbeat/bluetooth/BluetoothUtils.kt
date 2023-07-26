package agh.ryszard.blazej.heartbeat.bluetooth

import agh.ryszard.blazej.heartbeat.viewmodel.ConnectionViewModel
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.polidea.rxandroidble2.RxBleClient
import com.polidea.rxandroidble2.scan.ScanFilter
import com.polidea.rxandroidble2.scan.ScanSettings
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable


class BluetoothUtils
{
    private lateinit var rxBleClient: RxBleClient
    var found: Boolean = false
    var onDeviceFound = {}

    fun bluetoothStartup(context: Context){
        rxBleClient = RxBleClient.create(context)
    }

    fun bluetoothScan(activity: Activity, viewModel: ConnectionViewModel) {
        val scanSettings = ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
            .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
            .build()

        val scanFilter = ScanFilter.Builder()
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.ACCESS_COARSE_LOCATION), 123)
        }
        if(ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            val subscribe = rxBleClient.scanBleDevices(scanSettings, scanFilter)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        if (it!=null &&
                            it.bleDevice.name != null &&
                            it.bleDevice.name.toString().startsWith("Movesense")) {
                            Log.d("Device", "Movesense device: " + it.bleDevice.name)
                            viewModel.setConnectedDevice(it.bleDevice.macAddress, it.bleDevice.name!!, activity)
                            onDeviceFound()
                        }
                    },
                    { onScanFailure(it) })
        }
        else{
            Log.d("Device", "something went wrong")
        }
    }

     fun onScanFailure(throwable: Throwable) {
        Log.w("ScanActivity", "Scan failed", throwable)
    }
}