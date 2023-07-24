package agh.ryszard.blazej.heartbeat.bluetooth

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
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

    fun bluetoothStartup(context: Context){
        rxBleClient = RxBleClient.create(context)
    }

    fun bluetoothScan(activity: Activity) {
        val scanSettings = ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
            .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
            .build()

        val scanFilter = ScanFilter.Builder()
            .build()

        ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.ACCESS_COARSE_LOCATION), 123)
        if(ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            rxBleClient.scanBleDevices(scanSettings, scanFilter)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ Log.d("Device", it.bleDevice.name ?: "No-Name")}, { onScanFailure(it) })
        }
        else{
            Log.d("Device", "something went wrong")
        }
    }

    private fun onScanFailure(throwable: Throwable) {
        Log.w("ScanActivity", "Scan failed", throwable)
    }
}