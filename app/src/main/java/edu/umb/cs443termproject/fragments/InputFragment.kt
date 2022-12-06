package edu.umb.cs443termproject.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import edu.umb.cs443termproject.R
import edu.umb.cs443termproject.databinding.FragmentInputBinding

class InputFragment : Fragment() {

    companion object {
        const val TAG : String = "CS443"

        fun newInstance() : InputFragment {
            return InputFragment()
        }
    }

    private var _binding: FragmentInputBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "HomeFragment - onCreateView() called")

        _binding = FragmentInputBinding.inflate(inflater, container, false)

        val eventTypes = resources.getStringArray(R.array.stringArray_eventType)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_event_type_item, eventTypes)
        binding.autoTvDropdownEventType.setAdapter(arrayAdapter)

        return binding.root
    }

    // recreate drop-down when comeback to app
    override fun onResume() {
        super.onResume()

        val eventTypes = resources.getStringArray(R.array.stringArray_eventType)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_event_type_item, eventTypes)
        binding.autoTvDropdownEventType.setAdapter(arrayAdapter)
    }
}