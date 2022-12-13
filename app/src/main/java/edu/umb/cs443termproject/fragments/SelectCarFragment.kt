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

        // car data
        val carList = arrayListOf(
            CarItems(R.drawable.car1, "aston martin" , "DB11 V8", 12, 78, 8, 12, 503),
            CarItems(R.drawable.car2, "aston martin" , "Vantage V8", 12, 73, 8, 12, 370),
            CarItems(R.drawable.car3, "BMW" , "228i Gran Coupe", 12, 50, 12, 12, 228),
            CarItems(R.drawable.car4, "BMW" , "230i Coupe", 12, 50, 12, 12, 255),
            CarItems(R.drawable.car5, "FCA US LLC" , "Giulia", 12, 58, 10, 12, 505),
            CarItems(R.drawable.car6, "FCA US LLC" , "Giulia AWD", 12, 58, 10, 12, 280),
            CarItems(R.drawable.car7, "Ferrari" , "296 GTB", 6, 65, 48, 18, 654),
            CarItems(R.drawable.car8, "Ferrari" , "812 Competizione", 6, 92, 48, 18, 789),
            CarItems(R.drawable.car9, "FOMOCO" , "Bronco", 6, 64, 60, 12, 300),
            CarItems(R.drawable.car10, "FOMOCO" , "Bronco Sport", 6, 61, 60, 12, 418),
            CarItems(R.drawable.car11, "FOMOCO" , "EcoSport", 12, 61, 60, 12, 275),
            CarItems(R.drawable.car12, "GM" , "ENCORE AWD", 12, 53, 24, 18, 155),
            CarItems(R.drawable.car13, "GM" , "ENCORE GX AWD", 12, 53, 24, 18, 155),
            CarItems(R.drawable.car14, "GM" , "CT4", 12, 64, 24, 18, 237),
            CarItems(R.drawable.car15, "Honda" , "ILX", 12, 50, 24, 12, 201),
            CarItems(R.drawable.car16, "Honda" , "MDX AWD", 12, 70, 24, 12, 290),
            CarItems(R.drawable.car17, "Hyundai" , "G70", 12, 60, 18, 12, 252),
            CarItems(R.drawable.car18, "Hyundai" , "Accent", 12, 45, 18, 12, 120),
            CarItems(R.drawable.car19, "Hyundai" , "Elantra", 12, 47, 18, 12, 147),
            CarItems(R.drawable.car20, "Jaguar Land Rover L" , "E-PACE", 3, 67, 36, 24, 246),
            CarItems(R.drawable.car21, "Jaguar Land Rover L" , "E-PACE MHEV", 12, 104, 36, 24, 250),
            CarItems(R.drawable.car22, "Kia" , "EV6", 12, 45, 18, 12, 320),
            CarItems(R.drawable.car23, "Kia" , "Forte", 12, 45, 18, 12, 201),
            CarItems(R.drawable.car24, "Lucid USA, Inc" , "Lucid Air Dream P", 3, 67, 36, 24, 246),
            CarItems(R.drawable.car25, "Lucid USA, Inc" , "Lucid Air Grand Touring", 12, 104, 36, 24, 250),
            CarItems(R.drawable.car26, "MAZDA" , "MAZDA3", 12, 45, 18, 12, 320),
            CarItems(R.drawable.car27, "MAZDA" , "MX-30", 12, 45, 18, 12, 201),
            CarItems(R.drawable.car28, "McLaren Automotive" , "720S Coupe", 3, 67, 36, 24, 246),
            CarItems(R.drawable.car29, "McLaren Automotive" , "Artura", 12, 104, 36, 24, 250),
            CarItems(R.drawable.car30, "Mercedes-Benz" , "CLA 250", 12, 45, 18, 12, 320),
            CarItems(R.drawable.car31, "Mercedes-Benz" , "E 350", 12, 45, 18, 12, 201),
            CarItems(R.drawable.car32, "Nissan" , "Q60 RED SPORT", 3, 67, 36, 24, 246),
            CarItems(R.drawable.car33, "Nissan" , "ALTIMA S", 12, 104, 36, 24, 250),
            CarItems(R.drawable.car34, "Porsche" , "718 Cayman GTS 4.0", 12, 45, 18, 12, 320),
            CarItems(R.drawable.car35, "Porsche" , "718 Spyder", 12, 45, 18, 12, 201),
            CarItems(R.drawable.car36, "Rivian Automotive L" , "R1S", 3, 67, 36, 24, 246),
            CarItems(R.drawable.car37, "Rivian Automotive L" , "R1T", 12, 104, 36, 24, 250),
            CarItems(R.drawable.car38, "Rolls-Royce" , "Cullinan", 12, 45, 18, 12, 320),
            CarItems(R.drawable.car39, "Rolls-Royce" , "Ghost", 12, 45, 18, 12, 201),
            CarItems(R.drawable.car40, "Subaru" , "BRZ", 3, 67, 36, 24, 246),
            CarItems(R.drawable.car41, "Subaru" , "CROSSTREK", 12, 104, 36, 24, 250),
            CarItems(R.drawable.car42, "Tesla" , "Model 3 Long Range AWD", 12, 45, 18, 12, 320),
            CarItems(R.drawable.car43, "Tesla" , "Model S Long Range", 12, 45, 18, 12, 201),
            CarItems(R.drawable.car44, "Toyota" , "ES 300h", 3, 67, 36, 24, 246),
            CarItems(R.drawable.car45, "Toyota" , "ES 350 F SPORT", 12, 104, 36, 24, 250),
            CarItems(R.drawable.car46, "Volkswagen Group of" , "A3", 12, 50, 18, 18, 201),
            CarItems(R.drawable.car47, "Volkswagen Group of" , "e-tron GT", 12, 93, 18, 18, 469),
            CarItems(R.drawable.car48, "Volvo" , "Polestar2 Single Motor", 12, 78, 24, 24, 231),
            CarItems(R.drawable.car49, "Volvo" , "C40 Recharge twin", 12, 78, 24, 24, 402),
            CarItems(R.drawable.car50, "Volvo" , "XC90 T8 AWD", 12, 50, 24, 24, 312),
        )

        val recyclerView: RecyclerView = view.findViewById(R.id.rv_select_car)
        recyclerView.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        recyclerView.setHasFixedSize(true) // it makes good performance
        recyclerView.adapter = SelectCarItemAdapter(carList)
    }
}