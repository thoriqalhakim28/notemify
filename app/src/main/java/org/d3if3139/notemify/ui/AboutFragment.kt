package org.d3if3139.notemify.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import org.d3if3139.notemify.R
import org.d3if3139.notemify.databinding.FragmentAboutBinding

class AboutFragment : Fragment(R.layout.fragment_about) {

    private lateinit var binding: FragmentAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutBinding.inflate(layoutInflater, container, false)

        binding.backBtn.setOnClickListener {
            it.findNavController().navigate(R.id.action_aboutFragment_to_dashboardFragment)
        }


        binding.followBtn.setOnClickListener {
            val url = "https://www.instagram.com/thoriqalhakim28"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

            if(intent.resolveActivity(
                    requireActivity().packageManager
            ) != null ) {
                startActivity(intent)
            }

        }
        return binding.root
    }
}