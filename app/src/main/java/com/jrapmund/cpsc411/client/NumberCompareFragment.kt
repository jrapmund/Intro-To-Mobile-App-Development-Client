package com.jrapmund.cpsc411.client

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import com.github.kittinunf.fuel.Fuel

class NumberCompareFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_number_compare, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        println("View Created")
        Fuel.get("http://192.168.0.176:4444/newnumber").response {
                request, response, result ->
            val (data, error) = result

            if(data != null) {
                Log.d("Web Service Log", "Data returned from REST server : ${String(data)}.")

                val number = String(data)
                view.findViewById<TextView>(R.id.txtActiveNumber).text = number
            }
            else Log.d("Web Service Log", "$error")
        }

        view.findViewById<Button>(R.id.btnSubmit).setOnClickListener{ v ->
            val input = view.findViewById<TextView>(R.id.editTxtNumber).text?.toString()
            println("Input is $input")
            v.findNavController().navigate(NumberCompareFragmentDirections.actionNumberCompareFragmentToNumberCompareFeedbackFragment(input))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            NumberCompareFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}