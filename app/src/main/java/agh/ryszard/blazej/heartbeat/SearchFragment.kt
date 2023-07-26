package agh.ryszard.blazej.heartbeat

import agh.ryszard.blazej.heartbeat.bluetooth.BluetoothUtils
import agh.ryszard.blazej.heartbeat.viewmodel.ConnectionViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController

class SearchFragment : Fragment() {

    lateinit var bluetoothUtils: BluetoothUtils
    private val sharedViewModel : ConnectionViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        bluetoothUtils = BluetoothUtils();
        bluetoothUtils.onDeviceFound = ::onDeviceFound
        bluetoothUtils.bluetoothStartup(requireContext())
        view.findViewById<Button>(R.id.search).setOnClickListener {
            bluetoothUtils.bluetoothScan(requireActivity(), sharedViewModel)
        }
    }

    fun onDeviceFound() {
        findNavController().navigate(R.id.action_searchFragment_to_statsFragment)
    }
}