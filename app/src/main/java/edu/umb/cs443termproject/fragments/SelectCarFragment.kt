package edu.umb.cs443termproject.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.umb.cs443termproject.R
import edu.umb.cs443termproject.adapter.HistoryItemAdapter
import edu.umb.cs443termproject.adapter.SelectCarItemAdapter
import edu.umb.cs443termproject.data.HistoryItems
import edu.umb.cs443termproject.data.SelectCarItems

class SelectCarFragment : Fragment() {

    companion object {
        const val TAG : String = "CS443"

        fun newInstance() : SelectCarFragment {
            return SelectCarFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d(TAG, "SelectCarFragment - onCreateView() called")
        return inflater.inflate(R.layout.fragment_select_car, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // need to implement on a server
        val selectCarList = arrayListOf(
            SelectCarItems(R.drawable.subaru_outback, "aston martin" , "DB11 V8"),
            SelectCarItems(R.drawable.subaru_outback, "aston martin" , "Vantage V8"),
            SelectCarItems(R.drawable.subaru_outback, "BMW" , "228i Gran Coupe"),
            SelectCarItems(R.drawable.subaru_outback, "BMW" , "230i Coupe"),
            SelectCarItems(R.drawable.subaru_outback, "FCA US LLC" , "Giulia"),
            SelectCarItems(R.drawable.subaru_outback, "FCA US LLC" , "Giulia AWD"),
            SelectCarItems(R.drawable.subaru_outback, "Ferrari" , "296 GTB"),
            SelectCarItems(R.drawable.subaru_outback, "Ferrari" , "812 Competizione"),
            SelectCarItems(R.drawable.subaru_outback, "FOMOCO" , "Bronco"),
            SelectCarItems(R.drawable.subaru_outback, "FOMOCO" , "Bronco Sport"),
            SelectCarItems(R.drawable.subaru_outback, "FOMOCO" , "EcoSport"),
            SelectCarItems(R.drawable.subaru_outback, "GM" , "ENCORE AWD"),
            SelectCarItems(R.drawable.subaru_outback, "GM" , "ENCORE GX AWD"),
            SelectCarItems(R.drawable.subaru_outback, "GM" , "CT4"),
            SelectCarItems(R.drawable.subaru_outback, "Honda" , "ILX"),
            SelectCarItems(R.drawable.subaru_outback, "Honda" , "MDX AWD"),
            SelectCarItems(R.drawable.subaru_outback, "Hyundai" , "G70"),
            SelectCarItems(R.drawable.subaru_outback, "Hyundai" , "Accent"),
            SelectCarItems(R.drawable.subaru_outback, "Hyundai" , "Elantra"),
            SelectCarItems(R.drawable.subaru_outback, "Jaguar Land Rover L" , "E-PACE"),
            SelectCarItems(R.drawable.subaru_outback, "Jaguar Land Rover L" , "E-PACE MHEV"),
            SelectCarItems(R.drawable.subaru_outback, "Kia" , "EV6"),
            SelectCarItems(R.drawable.subaru_outback, "Kia" , "Forte"),
            SelectCarItems(R.drawable.subaru_outback, "Lucid USA, Inc" , "Lucid Air Dream P"),
            SelectCarItems(R.drawable.subaru_outback, "Lucid USA, Inc" , "Lucid Air Grand Touring"),
            SelectCarItems(R.drawable.subaru_outback, "MAZDA" , "MAZDA3"),
            SelectCarItems(R.drawable.subaru_outback, "MAZDA" , "MX-30"),
            SelectCarItems(R.drawable.subaru_outback, "McLaren Automotive" , "720S Coupe"),
            SelectCarItems(R.drawable.subaru_outback, "McLaren Automotive" , "Artura"),
            SelectCarItems(R.drawable.subaru_outback, "Mercedes-Benz" , "CLA 250"),
            SelectCarItems(R.drawable.subaru_outback, "Mercedes-Benz" , "E 350"),
            SelectCarItems(R.drawable.subaru_outback, "Nissan" , "Q60 RED SPORT"),
            SelectCarItems(R.drawable.subaru_outback, "Nissan" , "ALTIMA S"),
            SelectCarItems(R.drawable.subaru_outback, "Porsche" , "718 Cayman GTS 4.0"),
            SelectCarItems(R.drawable.subaru_outback, "Porsche" , "718 Spyder"),
            SelectCarItems(R.drawable.subaru_outback, "Rivian Automotive L" , "R1S"),
            SelectCarItems(R.drawable.subaru_outback, "Rivian Automotive L" , "R1T"),
            SelectCarItems(R.drawable.subaru_outback, "Rolls-Royce" , "Cullinan"),
            SelectCarItems(R.drawable.subaru_outback, "Rolls-Royce" , "Ghost"),
            SelectCarItems(R.drawable.subaru_outback, "Subaru" , "BRZ"),
            SelectCarItems(R.drawable.subaru_outback, "Subaru" , "CROSSTREK"),
            SelectCarItems(R.drawable.subaru_outback, "Tesla" , "Model 3 Long Range AWD"),
            SelectCarItems(R.drawable.subaru_outback, "Tesla" , "Model S Long Range"),
            SelectCarItems(R.drawable.subaru_outback, "Toyota" , "ES 300h"),
            SelectCarItems(R.drawable.subaru_outback, "Toyota" , "ES 350 F SPORT"),
            SelectCarItems(R.drawable.subaru_outback, "Volkswagen Group of" , "A3"),
            SelectCarItems(R.drawable.subaru_outback, "Volkswagen Group of" , "e-tron GT"),
            SelectCarItems(R.drawable.subaru_outback, "Volvo" , "Polestar2 Single Motor"),
            SelectCarItems(R.drawable.subaru_outback, "Volvo" , "C40 Recharge twin"),
            SelectCarItems(R.drawable.subaru_outback, "Volvo" , "XC90 T8 AWD"),
        )

        val recyclerView: RecyclerView = view.findViewById(R.id.rv_select_car)
        recyclerView.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        recyclerView.setHasFixedSize(true) // it makes good performance
        recyclerView.adapter = SelectCarItemAdapter(selectCarList)
    }
}