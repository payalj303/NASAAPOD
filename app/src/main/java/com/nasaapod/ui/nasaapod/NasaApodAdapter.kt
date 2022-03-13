package com.nasaapod.ui.nasaapod

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nasaapod.data.model.ApodResponse
import com.nasaapod.databinding.NasaApodViewholderBinding

class NasaApodAdapter(private val onItemClick: (ApodResponse) -> Unit) :
    ListAdapter<ApodResponse, NasaApodViewHolder>(NasaApodAdapterComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NasaApodViewHolder =
        NasaApodViewHolder(
            NasaApodViewholderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), onItemClick
        )

    override fun onBindViewHolder(holder: NasaApodViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }
}

class NasaApodViewHolder(
    private val binding: NasaApodViewholderBinding,
    private val onItemClick: (ApodResponse) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(entity: ApodResponse) {
        binding.apply {
            root.setOnClickListener { onItemClick(entity) }
            Glide.with(itemView)
                .load(entity.hdurl)
                .into(binding.apodImageView)
            binding.copyrightTextView.text = entity.copyright ?: ""
            binding.titleTextView.text = entity.title ?: ""
            binding.dateTextView.text = entity.date ?: ""
        }
    }
}

class NasaApodAdapterComparator : DiffUtil.ItemCallback<ApodResponse>() {
    override fun areItemsTheSame(oldItem: ApodResponse, newItem: ApodResponse): Boolean =
        oldItem.url == newItem.url

    override fun areContentsTheSame(oldItem: ApodResponse, newItem: ApodResponse): Boolean =
        oldItem == newItem
}