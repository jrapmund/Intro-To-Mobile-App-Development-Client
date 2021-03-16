package com.jrapmund.cpsc411.client

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.kittinunf.fuel.Fuel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "arg1"

/**
 * A simple [Fragment] subclass.
 * Use the [NumberCompareFeedbackFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NumberCompareFeedbackFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            println("Got arguments $param1")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_number_compare_feedback, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val address = view.resources.getString(R.string.address)

        val inputJsonString = Json.encodeToString(param1?:"0")
        Fuel.post("$address/numbercompare").body(inputJsonString).response {
                request, response, result ->
            val (data, error) = result

            if(data != null) {
                Log.d("Web Service Log", "Data returned from REST server : ${String(data)}.")
                view.findViewById<TextView>(R.id.txtResponse).text = String(data)
            }
            else Log.d("Web Service Log", "$error")
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment NumberCompareFeedbackFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            NumberCompareFeedbackFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}