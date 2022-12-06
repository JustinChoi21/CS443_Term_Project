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
            CarItems(R.drawable.subaru_outback, "aston martin" , "DB11 V8", 12, 78, 8, 12, 503),
            CarItems(R.drawable.subaru_outback, "aston martin" , "Vantage V8", 12, 73, 8, 12, 370),
            CarItems(R.drawable.subaru_outback, "BMW" , "228i Gran Coupe", 12, 50, 12, 12, 228),
            CarItems(R.drawable.subaru_outback, "BMW" , "230i Coupe", 12, 50, 12, 12, 255),
            CarItems(R.drawable.subaru_outback, "FCA US LLC" , "Giulia", 12, 58, 10, 12, 505),
            CarItems(R.drawable.subaru_outback, "FCA US LLC" , "Giulia AWD", 12, 58, 10, 12, 280),
            CarItems(R.drawable.subaru_outback, "Ferrari" , "296 GTB", 6, 65, 48, 18, 654),
            CarItems(R.drawable.subaru_outback, "Ferrari" , "812 Competizione", 6, 92, 48, 18, 789),
            CarItems(R.drawable.subaru_outback, "FOMOCO" , "Bronco", 6, 64, 60, 12, 300),
            CarItems(R.drawable.subaru_outback, "FOMOCO" , "Bronco Sport", 6, 61, 60, 12, 418),
            CarItems(R.drawable.subaru_outback, "FOMOCO" , "EcoSport", 12, 61, 60, 12, 275),
            CarItems(R.drawable.subaru_outback, "GM" , "ENCORE AWD", 12, 53, 24, 18, 155),
            CarItems(R.drawable.subaru_outback, "GM" , "ENCORE GX AWD", 12, 53, 24, 18, 155),
            CarItems(R.drawable.subaru_outback, "GM" , "CT4", 12, 64, 24, 18, 237),
            CarItems(R.drawable.subaru_outback, "Honda" , "ILX", 12, 50, 24, 12, 201),
            CarItems(R.drawable.subaru_outback, "Honda" , "MDX AWD", 12, 70, 24, 12, 290),
            CarItems(R.drawable.subaru_outback, "Hyundai" , "G70", 12, 60, 18, 12, 252),
            CarItems(R.drawable.subaru_outback, "Hyundai" , "Accent", 12, 45, 18, 12, 120),
            CarItems(R.drawable.subaru_outback, "Hyundai" , "Elantra", 12, 47, 18, 12, 147),
            CarItems(R.drawable.subaru_outback, "Jaguar Land Rover L" , "E-PACE", 3, 67, 36, 24, 246),
            CarItems(R.drawable.subaru_outback, "Jaguar Land Rover L" , "E-PACE MHEV", 12, 104, 36, 24, 250),
            CarItems(R.drawable.subaru_outback, "Kia" , "EV6", 12, 45, 18, 12, 320),
            CarItems(R.drawable.subaru_outback, "Kia" , "Forte", 12, 45, 18, 12, 201),
            CarItems(R.drawable.subaru_outback, "Lucid USA, Inc" , "Lucid Air Dream P", 3, 67, 36, 24, 246),
            CarItems(R.drawable.subaru_outback, "Lucid USA, Inc" , "Lucid Air Grand Touring", 12, 104, 36, 24, 250),
            CarItems(R.drawable.subaru_outback, "MAZDA" , "MAZDA3", 12, 45, 18, 12, 320),
            CarItems(R.drawable.subaru_outback, "MAZDA" , "MX-30", 12, 45, 18, 12, 201),
            CarItems(R.drawable.subaru_outback, "McLaren Automotive" , "720S Coupe", 3, 67, 36, 24, 246),
            CarItems(R.drawable.subaru_outback, "McLaren Automotive" , "Artura", 12, 104, 36, 24, 250),
            CarItems(R.drawable.subaru_outback, "Mercedes-Benz" , "CLA 250", 12, 45, 18, 12, 320),
            CarItems(R.drawable.subaru_outback, "Mercedes-Benz" , "E 350", 12, 45, 18, 12, 201),
            CarItems(R.drawable.subaru_outback, "Nissan" , "Q60 RED SPORT", 3, 67, 36, 24, 246),
            CarItems(R.drawable.subaru_outback, "Nissan" , "ALTIMA S", 12, 104, 36, 24, 250),
            CarItems(R.drawable.subaru_outback, "Porsche" , "718 Cayman GTS 4.0", 12, 45, 18, 12, 320),
            CarItems(R.drawable.subaru_outback, "Porsche" , "718 Spyder", 12, 45, 18, 12, 201),
            CarItems(R.drawable.subaru_outback, "Rivian Automotive L" , "R1S", 3, 67, 36, 24, 246),
            CarItems(R.drawable.subaru_outback, "Rivian Automotive L" , "R1T", 12, 104, 36, 24, 250),
            CarItems(R.drawable.subaru_outback, "Rolls-Royce" , "Cullinan", 12, 45, 18, 12, 320),
            CarItems(R.drawable.subaru_outback, "Rolls-Royce" , "Ghost", 12, 45, 18, 12, 201),
            CarItems(R.drawable.subaru_outback, "Subaru" , "BRZ", 3, 67, 36, 24, 246),
            CarItems(R.drawable.subaru_outback, "Subaru" , "CROSSTREK", 12, 104, 36, 24, 250),
            CarItems(R.drawable.subaru_outback, "Tesla" , "Model 3 Long Range AWD", 12, 45, 18, 12, 320),
            CarItems(R.drawable.subaru_outback, "Tesla" , "Model S Long Range", 12, 45, 18, 12, 201),
            CarItems(R.drawable.subaru_outback, "Toyota" , "ES 300h", 3, 67, 36, 24, 246),
            CarItems(R.drawable.subaru_outback, "Toyota" , "ES 350 F SPORT", 12, 104, 36, 24, 250),
            CarItems(R.drawable.subaru_outback, "Volkswagen Group of" , "A3", 12, 50, 18, 18, 201),
            CarItems(R.drawable.subaru_outback, "Volkswagen Group of" , "e-tron GT", 12, 93, 18, 18, 469),
            CarItems(R.drawable.subaru_outback, "Volvo" , "Polestar2 Single Motor", 12, 78, 24, 24, 231),
            CarItems(R.drawable.subaru_outback, "Volvo" , "C40 Recharge twin", 12, 78, 24, 24, 402),
            CarItems(R.drawable.subaru_outback, "Volvo" , "XC90 T8 AWD", 12, 50, 24, 24, 312),
        )

        val recyclerView: RecyclerView = view.findViewById(R.id.rv_select_car)
        recyclerView.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        recyclerView.setHasFixedSize(true) // it makes good performance
        recyclerView.adapter = SelectCarItemAdapter(carList)
    }
}