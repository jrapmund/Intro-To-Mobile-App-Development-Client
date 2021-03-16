package com.jrapmund.cpsc411.client

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import com.github.kittinunf.fuel.Fuel
import com.jrapmund.cpsc411.client.views.PairEntry
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "storeID"

/**
 * A simple [Fragment] subclass.
 * Use the [DepartmentsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DepartmentsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_departments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        println("The parameter is : $param1")
        val storeIDJsonString = Json.encodeToString(param1?:"0")

        val address = view.resources.getString(R.string.address)
        Fuel.get("$address/stores/departments/$param1").body(storeIDJsonString).response {
                request, response, result ->
            val (data, error) = result

            if(data != null) {
                println(String(data))

                val departments = Json.decodeFromString<MutableList<Pair<String, Int>>>(String(data))
                val departmentsTable = view.findViewById<TableLayout>(R.id.departmentsTable)

                for(department in departments){
                    val entry = PairEntry(view.context, department.first, department.second.toString())
                    entry.setOnClickListener { v ->
                        entry.first
                        println("Department: ${entry.first} was selected.")
                        //v.findNavController().navigate(DSLViewerFragmentDirections.actionDSLViewerFragmentToDepartmentsFragment(entry.storeId))
                    }
                    departmentsTable.addView(entry)
                }
            } else {
                Log.d("Web Service Log", "$error")
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment DepartmentsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            DepartmentsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}