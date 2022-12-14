package edu.umb.cs443termproject.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.internal.ViewUtils.hideKeyboard
import edu.umb.cs443termproject.MainActivity
import edu.umb.cs443termproject.R
import edu.umb.cs443termproject.data.EventType
import edu.umb.cs443termproject.databinding.FragmentInputBinding
import edu.umb.cs443termproject.extentions.hideKeyboard
import edu.umb.cs443termproject.extentions.toastMessage
import edu.umb.cs443termproject.room.History
import edu.umb.cs443termproject.room.RoomHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        Log.d(TAG, "InputFragment - onCreateView() called")

        _binding = FragmentInputBinding.inflate(inflater, container, false)

        // drop-down menu
        val eventTypes = resources.getStringArray(R.array.stringArray_eventType)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_event_type_item, eventTypes)
        binding.autoTvDropdownEventType.setAdapter(arrayAdapter)

        // when select event type, set input field
        binding.autoTvDropdownEventType.setOnItemClickListener { parent, view, position, id ->
            val eventType = parent.getItemAtPosition(position).toString()
            when (eventType) {
                EventType.Refuel.value -> {
                    // show fuel amount & price input field
                    binding.textInputLayoutInputFuelAmount.visibility = View.VISIBLE
                    binding.etFuelAmountInput.visibility = View.VISIBLE
                    binding.textInputLayoutInputFuelPrice.visibility = View.VISIBLE
                    binding.etFuelPriceInput.visibility = View.VISIBLE
                }

                EventType.EngineOil.value -> {
                    // hidden fuel amount & price input field
                    binding.textInputLayoutInputFuelAmount.visibility = View.GONE
                    binding.etFuelAmountInput.visibility = View.GONE
                    binding.textInputLayoutInputFuelPrice.visibility = View.GONE
                    binding.etFuelPriceInput.visibility = View.GONE
                }

                EventType.Tire.value -> {
                    // hidden fuel amount & price input field
                    binding.textInputLayoutInputFuelAmount.visibility = View.GONE
                    binding.etFuelAmountInput.visibility = View.GONE
                    binding.textInputLayoutInputFuelPrice.visibility = View.GONE
                    binding.etFuelPriceInput.visibility = View.GONE
                }

                EventType.RegularService.value -> {
                    // hidden fuel amount & price input field
                    binding.textInputLayoutInputFuelAmount.visibility = View.GONE
                    binding.etFuelAmountInput.visibility = View.GONE
                    binding.textInputLayoutInputFuelPrice.visibility = View.GONE
                    binding.etFuelPriceInput.visibility = View.GONE
                }
            }
        }

        // set etDateInput to today's date
        val today = java.util.Calendar.getInstance().time
        val dateFormat = java.text.SimpleDateFormat("MM/dd/yyyy", java.util.Locale.US)
        binding.etDateInput.setText(dateFormat.format(today))

        // When click etDateInput show date picker
        binding.etDateInput.setOnClickListener {
            val datePickerFragment = DatePickerFragment()
            val supportFragmentManager = requireActivity().supportFragmentManager

            // we have to implement setFragmentResultListener() in the activity that contains this fragment
            supportFragmentManager.setFragmentResultListener(
                "REQUEST_KEY",
                viewLifecycleOwner
            ) {
                resultKey, bundle -> if(resultKey == "REQUEST_KEY") {
                    val selectedDate = bundle.getString("SELECTED_DATE")
                    binding.etDateInput.setText(selectedDate)
                }
            }

            // show the date picker dialog
            datePickerFragment.show(supportFragmentManager, "DatePickerFragment")
        }

        // when fuel price input field not focus, hide keyboard
        binding.etFuelPriceInput.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                (activity as AppCompatActivity).hideKeyboard()
            }
        }

        // when save button focused, hide keyboard
        binding.btnSaveInput.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                (activity as AppCompatActivity).hideKeyboard()
            }
        }

        // When click button btn_save_input, show the result
        binding.btnSaveInput.setOnClickListener {

            // hide keyboard
            (activity as AppCompatActivity).hideKeyboard()

            // get input data
            val eventType = binding.autoTvDropdownEventType.text.toString()
            val date = binding.etDateInput.text.toString()
            val description = binding.etNoteInput.text.toString()
            var fuelAmount = 0
            var fuelPrice = 0

            // empty input field check when event type is refuel
            if (binding.autoTvDropdownEventType.text.toString() == EventType.Refuel.value) {
                if(binding.etFuelAmountInput.text.toString().isEmpty() || binding.etFuelPriceInput.text.toString().isEmpty()) {
                    (activity as AppCompatActivity).toastMessage("Please fill in all the fields")
                    return@setOnClickListener
                }

                fuelAmount = binding.etFuelAmountInput.text.toString().toInt()
                fuelPrice = binding.etFuelPriceInput.text.toString().toInt()
            }

            // store to history table of Room database
            val history = History(eventType, date, description, fuelAmount, fuelPrice)
            lifecycleScope.launch {
                (activity as AppCompatActivity).application.let {
                    RoomHelper.getDatabase(it).getHistoryDao().addHistory(history)

                    // move to history fragment
                    withContext(Dispatchers.Main) {
                        val historyFragment = HistoryFragment.newInstance()
                        (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
                            .replace(R.id.fragments_frame, historyFragment)
                            .addToBackStack(null)
                            .commit()
                        (activity as AppCompatActivity).title = "History"
                        (activity as AppCompatActivity).bottom_nav.menu.getItem(1).isChecked = true

                        // show floating action button
                        val fab = (activity as MainActivity).findViewById<View>(R.id.floatingActionButton)
                        fab.visibility = View.VISIBLE
                    }
                }
            }
        }


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