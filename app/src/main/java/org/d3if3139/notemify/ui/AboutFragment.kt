package org.d3if3139.notemify.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import org.d3if3139.notemify.MainActivity
import org.d3if3139.notemify.R
import org.d3if3139.notemify.databinding.FragmentAboutBinding
import org.d3if3139.notemify.model.About
import org.d3if3139.notemify.network.AboutApi
import org.d3if3139.notemify.network.ApiStatus
import org.d3if3139.notemify.viewmodel.AboutViewModel

class AboutFragment : Fragment(R.layout.fragment_about) {

    private lateinit var binding: FragmentAboutBinding
    private lateinit var aboutViewModel: AboutViewModel
    private val viewModel: AboutViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        aboutViewModel = ViewModelProvider(this).get(AboutViewModel::class.java)
        aboutViewModel.getData().observe(viewLifecycleOwner) { list ->
            if(list.isNotEmpty()) {
                val result = list[0]
                bind(result)
            }
        }
        viewModel.getStatus().observe(viewLifecycleOwner) {
            updateUiAndProgress(it)
        }
    }

    private fun updateUiAndProgress(it: ApiStatus) {
        when(it) {
            ApiStatus.LOADING -> {
                binding.progressBar.visibility = View.VISIBLE
                binding.img.visibility = View.GONE
                binding.appName.visibility = View.GONE
                binding.appDesc.visibility = View.GONE
                binding.visit.visibility = View.GONE
            }
            ApiStatus.SUCCESS -> {
                binding.progressBar.visibility = View.GONE
                binding.img.visibility = View.VISIBLE
                binding.appName.visibility = View.VISIBLE
                binding.appDesc.visibility = View.VISIBLE
                binding.visit.visibility = View.VISIBLE

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requestNotificationPermission()
                }
            }
            ApiStatus.FAILED -> {
                binding.progressBar.visibility = View.GONE
                binding.networkError.visibility = View.VISIBLE
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun requestNotificationPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                MainActivity.PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun bind(about: About) {
        binding.apply {
            binding.appName.text = about.appName
            binding.appDesc.text = about.desc
            binding.visit.text = about.visitURL
            Glide.with(img.context)
                .load(AboutApi.getAboutImg(about.imageId))
                .error(R.drawable.baseline_broken_image_24)
                .into(img)
        }
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