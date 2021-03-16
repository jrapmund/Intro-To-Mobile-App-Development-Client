package com.jrapmund.cpsc411.client

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import androidx.navigation.findNavController
import com.github.kittinunf.fuel.Fuel
import com.jrapmund.cpsc411.client.views.PairEntry
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


class DSLViewerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_d_s_l_viewer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val address = view.resources.getString(R.string.address)

        Fuel.get("$address/stores").response {
            request, response, result ->
            val (data, error) = result

            if(data != null) {
                println(String(data))

                val stores = Json.decodeFromString<MutableList<Pair<Int, String>>>(String(data))
                val storesTable = view.findViewById<TableLayout>(R.id.storesTable)

                for(store in stores){
                    val entry = PairEntry(view.context, store.second, store.first.toString())
                    entry.setOnClickListener { v ->
                        println("Store: ${entry.second} was selected.")
                        v.findNavController().navigate(DSLViewerFragmentDirections.actionDSLViewerFragmentToDepartmentsFragment(entry.second))
                    }
                    storesTable.addView(entry)
                }
            } else {
                Log.d("Web Service Log", "$error")
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            DSLViewerFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}