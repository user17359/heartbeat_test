package agh.ryszard.blazej.heartbeat

import agh.ryszard.blazej.heartbeat.bluetooth.BluetoothUtils
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var bluetoothUtils: BluetoothUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bluetoothUtils = BluetoothUtils();
        bluetoothUtils.bluetoothStartup(this)
        findViewById<Button>(R.id.search).setOnClickListener {
            bluetoothUtils.bluetoothScan(this)
        }
    }
}