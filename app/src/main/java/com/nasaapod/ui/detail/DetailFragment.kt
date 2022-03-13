package com.nasaapod.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.nasaapod.R
import com.nasaapod.data.model.ApodResponse
import com.nasaapod.databinding.DetailFragmentBinding

class DetailFragment : Fragment(R.layout.detail_fragment) {

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = DetailFragmentBinding.bind(view)
        setupBinding()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupBinding() {
        arguments?.getParcelable<ApodResponse>("apod")?.let {
            binding.titleTextView.text = it.title ?: getString(R.string.something_went_wrong)
            binding.copyrightTextView.text = it.copyright ?: getString(R.string.something_went_wrong)
            binding.dateTextView.text = it.date ?: getString(R.string.something_went_wrong)
            binding.explanationTextView.text = it.explanation ?: getString(R.string.something_went_wrong)
            Glide.with(binding.root)
                .load(it.hdurl)
                .into(binding.adopFullImageView)
        }
    }
}