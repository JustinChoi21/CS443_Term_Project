package edu.umb.cs443termproject.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.umb.cs443termproject.R

class InputFragment : Fragment() {

    companion object {
        const val TAG : String = "CS443"

        fun newInstance() : InputFragment {
            return InputFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "HomeFragment - onCreateView() called")
        return inflater.inflate(R.layout.fragment_input, container, false)
    }
}