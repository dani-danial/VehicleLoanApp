package com.example.vehicleloanapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

//To display about screen and provide click to github
class AboutFragment : Fragment() {
//To create UI
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_about, container, false)
// to create github
        val tvGithub: TextView = view.findViewById(R.id.tvGithub)

        tvGithub.setOnClickListener {
            // This link points to your specific account and the repo name we decided on
            val url = "https://github.com/dani-danial/VehicleLoanApp"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        return view
    }
}