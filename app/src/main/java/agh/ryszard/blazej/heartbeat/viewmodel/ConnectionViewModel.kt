package agh.ryszard.blazej.heartbeat.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.movesense.mds.Mds

class ConnectionViewModel : ViewModel() {

    private lateinit var _macAddress: String
    lateinit var deviceName: String
    lateinit var mds: Mds

    fun setConnectedDevice(macAddress: String, deviceName: String, context: Context) {
        _macAddress = macAddress
        this.deviceName = deviceName
        //setup MDS
        mds = Mds.builder().build(context)
    }
}