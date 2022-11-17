package com.shevy.thetestapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.shevy.thetestapp.databinding.FragmentHotSalesBinding

const val ARG_OBJECT = "object"

class HotSalesFragment : Fragment() {

    lateinit var binding: FragmentHotSalesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHotSalesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            val textView: TextView = view.findViewById(R.id.text_title)
            textView.text = getInt(ARG_OBJECT).toString()
        }
    }
}