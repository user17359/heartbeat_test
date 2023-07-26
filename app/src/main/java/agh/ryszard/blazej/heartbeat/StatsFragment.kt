package agh.ryszard.blazej.heartbeat

import agh.ryszard.blazej.heartbeat.viewmodel.ConnectionViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels

class StatsFragment : Fragment() {

    private val sharedViewModel : ConnectionViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val toast = Toast.makeText(context, "Connected with: " + sharedViewModel.deviceName, Toast.LENGTH_LONG) // in Activity
        toast.show()
    }
}