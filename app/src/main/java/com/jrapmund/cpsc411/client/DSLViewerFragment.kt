package com.jrapmund.cpsc411.client

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.kittinunf.fuel.Fuel
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromJsonElement
import java.util.*



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
                view.findViewById<TextView>(R.id.jsonOutput).text = String(data)
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