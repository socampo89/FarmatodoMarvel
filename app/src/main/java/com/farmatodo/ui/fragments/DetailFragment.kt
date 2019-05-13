package com.farmatodo.ui.fragments

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.farmatodo.R
import com.farmatodo.data.BaseData
import com.farmatodo.data.Character
import com.farmatodo.data.Creator
import com.farmatodo.databinding.DetailFragmentBinding
import com.farmatodo.ui.adapters.CardAdapter

class DetailFragment : Fragment() {

    private lateinit var binding: DetailFragmentBinding
    private lateinit var detail: BaseData

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.detail_fragment, container, false)
        detail = DetailFragmentArgs.fromBundle(arguments!!).detail
        initViews()
        return binding.root
    }

    private fun initViews() {
        val detail = this.detail
        when (detail) {
            is Creator -> {
                binding.tvDescription.visibility = View.GONE
                detail.fullName?.let {
                    binding.tvTitle.text = it
                }
            }
            is Character -> detail.name?.let {
                binding.tvTitle.text = it
            }
            else -> detail.title?.let {
                binding.tvTitle.text = it
            }
        }

        detail.description?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.tvDescription.text = Html.fromHtml(it, Html.FROM_HTML_MODE_COMPACT)
            } else {
                binding.tvDescription.text = Html.fromHtml(it)
            }
        }

        if(detail.thumbnail != null && !detail.thumbnail.path.contains(CardAdapter.IMAGE_NOT_AVAILABLE)){
            Glide
                .with(requireContext())
                .load(detail.thumbnail.path + "." +detail.thumbnail.extension)
                .centerCrop()
                .into(binding.ivThumbnail)
        } else {
            Glide
                .with(requireContext())
                .load(R.drawable.marvel_default_image)
                .centerInside()
                .into(binding.ivThumbnail)
        }
    }

}
