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
import edu.umb.cs443termproject.adapter.SelectCarItemAdapter
import edu.umb.cs443termproject.data.CarItems
import edu.umb.cs443termproject.data.SelectedCarItems

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
        val carList = arrayListOf(
            CarItems(R.drawable.subaru_outback, "aston martin" , "DB11 V8"),
            CarItems(R.drawable.subaru_outback_big, "aston martin" , "Vantage V8"),
            CarItems(R.drawable.subaru_outback, "BMW" , "228i Gran Coupe"),
            CarItems(R.drawable.subaru_outback, "BMW" , "230i Coupe"),
            CarItems(R.drawable.subaru_outback, "FCA US LLC" , "Giulia"),
            CarItems(R.drawable.subaru_outback, "FCA US LLC" , "Giulia AWD"),
            CarItems(R.drawable.subaru_outback, "Ferrari" , "296 GTB"),
            CarItems(R.drawable.subaru_outback, "Ferrari" , "812 Competizione"),
            CarItems(R.drawable.subaru_outback, "FOMOCO" , "Bronco"),
            CarItems(R.drawable.subaru_outback, "FOMOCO" , "Bronco Sport"),
            CarItems(R.drawable.subaru_outback, "FOMOCO" , "EcoSport"),
            CarItems(R.drawable.subaru_outback, "GM" , "ENCORE AWD"),
            CarItems(R.drawable.subaru_outback, "GM" , "ENCORE GX AWD"),
            CarItems(R.drawable.subaru_outback, "GM" , "CT4"),
            CarItems(R.drawable.subaru_outback, "Honda" , "ILX"),
            CarItems(R.drawable.subaru_outback, "Honda" , "MDX AWD"),
            CarItems(R.drawable.subaru_outback, "Hyundai" , "G70"),
            CarItems(R.drawable.subaru_outback, "Hyundai" , "Accent"),
            CarItems(R.drawable.subaru_outback, "Hyundai" , "Elantra"),
            CarItems(R.drawable.subaru_outback, "Jaguar Land Rover L" , "E-PACE"),
            CarItems(R.drawable.subaru_outback, "Jaguar Land Rover L" , "E-PACE MHEV"),
            CarItems(R.drawable.subaru_outback, "Kia" , "EV6"),
            CarItems(R.drawable.subaru_outback, "Kia" , "Forte"),
            CarItems(R.drawable.subaru_outback, "Lucid USA, Inc" , "Lucid Air Dream P"),
            CarItems(R.drawable.subaru_outback, "Lucid USA, Inc" , "Lucid Air Grand Touring"),
            CarItems(R.drawable.subaru_outback, "MAZDA" , "MAZDA3"),
            CarItems(R.drawable.subaru_outback, "MAZDA" , "MX-30"),
            CarItems(R.drawable.subaru_outback, "McLaren Automotive" , "720S Coupe"),
            CarItems(R.drawable.subaru_outback, "McLaren Automotive" , "Artura"),
            CarItems(R.drawable.subaru_outback, "Mercedes-Benz" , "CLA 250"),
            CarItems(R.drawable.subaru_outback, "Mercedes-Benz" , "E 350"),
            CarItems(R.drawable.subaru_outback, "Nissan" , "Q60 RED SPORT"),
            CarItems(R.drawable.subaru_outback, "Nissan" , "ALTIMA S"),
            CarItems(R.drawable.subaru_outback, "Porsche" , "718 Cayman GTS 4.0"),
            CarItems(R.drawable.subaru_outback, "Porsche" , "718 Spyder"),
            CarItems(R.drawable.subaru_outback, "Rivian Automotive L" , "R1S"),
            CarItems(R.drawable.subaru_outback, "Rivian Automotive L" , "R1T"),
            CarItems(R.drawable.subaru_outback, "Rolls-Royce" , "Cullinan"),
            CarItems(R.drawable.subaru_outback, "Rolls-Royce" , "Ghost"),
            CarItems(R.drawable.subaru_outback, "Subaru" , "BRZ"),
            CarItems(R.drawable.subaru_outback, "Subaru" , "CROSSTREK"),
            CarItems(R.drawable.subaru_outback, "Tesla" , "Model 3 Long Range AWD"),
            CarItems(R.drawable.subaru_outback, "Tesla" , "Model S Long Range"),
            CarItems(R.drawable.subaru_outback, "Toyota" , "ES 300h"),
            CarItems(R.drawable.subaru_outback, "Toyota" , "ES 350 F SPORT"),
            CarItems(R.drawable.subaru_outback, "Volkswagen Group of" , "A3"),
            CarItems(R.drawable.subaru_outback, "Volkswagen Group of" , "e-tron GT"),
            CarItems(R.drawable.subaru_outback, "Volvo" , "Polestar2 Single Motor"),
            CarItems(R.drawable.subaru_outback, "Volvo" , "C40 Recharge twin"),
            CarItems(R.drawable.subaru_outback, "Volvo" , "XC90 T8 AWD"),
        )

        val recyclerView: RecyclerView = view.findViewById(R.id.rv_select_car)
        recyclerView.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        recyclerView.setHasFixedSize(true) // it makes good performance
        recyclerView.adapter = SelectCarItemAdapter(carList)
    }
}