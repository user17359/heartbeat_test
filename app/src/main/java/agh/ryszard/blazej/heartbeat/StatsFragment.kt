package agh.ryszard.blazej.heartbeat

import agh.ryszard.blazej.heartbeat.databinding.FragmentStatsBinding
import agh.ryszard.blazej.heartbeat.viewmodel.ConnectionViewModel
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.activityViewModels

class StatsFragment : Fragment() {

    private var binding: FragmentStatsBinding? = null
    private val sharedViewModel : ConnectionViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmentBinding = FragmentStatsBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.listening).setOnClickListener{onListeningStart()}
        sharedViewModel.onConnectionComplete = ::onConnectionComplete
        val toast = Toast.makeText(context, "Connecting to: " + sharedViewModel.deviceName, Toast.LENGTH_SHORT) // in Activity
        toast.show()
        sharedViewModel.connectDevice(requireContext())
        binding?.apply {
            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        sharedViewModel.disconnectDevice()
        binding = null
    }

    private fun onConnectionComplete() {
        val toast = Toast.makeText(context, "Connection successful", Toast.LENGTH_SHORT) // in Activity
        toast.show()
        requireView().findViewById<Button>(R.id.listening).isEnabled = true
    }

    private fun onListeningStart() {
        sharedViewModel.startListening(requireContext())
    }

}