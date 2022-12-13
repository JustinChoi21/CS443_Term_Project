package edu.umb.cs443termproject.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.internal.ViewUtils.hideKeyboard
import edu.umb.cs443termproject.MainActivity
import edu.umb.cs443termproject.R
import edu.umb.cs443termproject.data.EventType
import edu.umb.cs443termproject.databinding.FragmentEditBinding
import edu.umb.cs443termproject.extentions.hideKeyboard
import edu.umb.cs443termproject.extentions.toastMessage
import edu.umb.cs443termproject.room.History
import edu.umb.cs443termproject.room.RoomHelper
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditFragment : Fragment() {

    companion object {
        const val TAG : String = "CS443"

        fun newInstance() : EditFragment {
            return EditFragment()
        }
    }

    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!

    private lateinit var history: History


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "EditFragment - onCreateView() called")

        _binding = FragmentEditBinding.inflate(inflater, container, false)

        // when click event type editText, toast message
        binding.textInputLayoutEditEventType.setOnClickListener {
            Toast.makeText(context,
                "You can not change event type. Please delete this event and create a new one.",
                Toast.LENGTH_LONG).show()
        }

        // get arguments from HistoryFragment
        val historyId = arguments?.getInt("historyId")


        // retrieve data from database by history id
        lifecycleScope.launch {
            history = withContext(Dispatchers.IO) {
                RoomHelper.getDatabase(requireContext()).getHistoryDao().getHistoryById(historyId!!)
            }

            withContext(Dispatchers.Main) {
                // set data to edit text
                binding.etEventTypeEdit.setText(history.eventType)
                binding.etDateEdit.setText(history.eventDate)
                binding.etNoteEdit.setText(history.eventDescription)
                binding.etFuelAmountEdit.setText(history.fuelAmount.toString())
                binding.etFuelPriceEdit.setText(history.fuelPrice.toString())
            }
        }


        // When click etDateInput show date picker
        binding.etDateEdit.setOnClickListener {
            val datePickerFragment = DatePickerFragment()
            val supportFragmentManager = requireActivity().supportFragmentManager

            // we have to implement setFragmentResultListener() in the activity that contains this fragment
            supportFragmentManager.setFragmentResultListener(
                "REQUEST_KEY",
                viewLifecycleOwner
            ) {
                resultKey, bundle -> if(resultKey == "REQUEST_KEY") {
                    val selectedDate = bundle.getString("SELECTED_DATE")
                    binding.etDateEdit.setText(selectedDate)
                }
            }

            // show the date picker dialog
            datePickerFragment.show(supportFragmentManager, "DatePickerFragment")
        }

        // when fuel price input field not focus, hide keyboard
        binding.etFuelPriceEdit.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                (activity as AppCompatActivity).hideKeyboard()
            }
        }

        // when save button focused, hide keyboard
        binding.btnEditEdit.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                (activity as AppCompatActivity).hideKeyboard()
            }
        }

        // When click button btn_edit_edit, edit data in database
        binding.btnEditEdit.setOnClickListener {

            // hide keyboard
            (activity as AppCompatActivity).hideKeyboard()

            // get input data
            val eventType = binding.etEventTypeEdit.text.toString()
            val eventDate = binding.etDateEdit.text.toString()
            val eventDescription = binding.etNoteEdit.text.toString()
            var fuelAmount = 0
            var fuelPrice = 0

            // empty input field check when event type is refuel
            if (binding.etEventTypeEdit.text.toString() == EventType.Refuel.value) {
                if(binding.etFuelAmountEdit.text.toString().isEmpty() || binding.etFuelPriceEdit.text.toString().isEmpty()) {
                    (activity as AppCompatActivity).toastMessage("Please fill in all the fields")
                    return@setOnClickListener
                }

                fuelAmount = binding.etFuelAmountEdit.text.toString().toInt()
                fuelPrice = binding.etFuelPriceEdit.text.toString().toInt()
            }

            // store to history table of Room database
            lifecycleScope.launch {
                (activity as AppCompatActivity).application.let {
                    RoomHelper.getDatabase(it).getHistoryDao().updateHistoryById(
                        history.id,
                        eventType,
                        eventDate,
                        eventDescription,
                        fuelAmount,
                        fuelPrice
                    )

                    // move to history fragment
                    withContext(Dispatchers.Main) {
                        val historyFragment = HistoryFragment.newInstance()
                        (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
                            .replace(R.id.fragments_frame, historyFragment)
                            .addToBackStack(null)
                            .commit()
                        (activity as AppCompatActivity).supportActionBar?.title = "History"
                        (activity as AppCompatActivity).bottom_nav.menu.getItem(1).isChecked = true

                        // show floating action button
                        val fab = (activity as MainActivity).findViewById<View>(R.id.floatingActionButton)
                        fab.visibility = View.VISIBLE
                    }
                }
            }
        }


        // When click button btn_delete_edit, delete data in database
        binding.btnDeleteEdit.setOnClickListener {
            // hide keyboard
            (activity as AppCompatActivity).hideKeyboard()

            lifecycleScope.launch {
                (activity as AppCompatActivity).application.let {
                    RoomHelper.getDatabase(it).getHistoryDao().deleteHistoryById(history.id)

                    // move to history fragment
                    withContext(Dispatchers.Main) {
                        val historyFragment = HistoryFragment.newInstance()
                        (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
                            .replace(R.id.fragments_frame, historyFragment)
                            .addToBackStack(null)
                            .commit()
                        (activity as AppCompatActivity).supportActionBar?.title = "History"
                        (activity as AppCompatActivity).bottom_nav.menu.getItem(1).isChecked = true

                        // show floating action button
                        val fab = (activity as MainActivity).findViewById<View>(R.id.floatingActionButton)
                        fab.visibility = View.VISIBLE

                        // show Toast message
                        Toast.makeText(context,
                            "The history is deleted.",
                            Toast.LENGTH_LONG).show()
                    }
                }
            }

        }


        return binding.root
    }
}