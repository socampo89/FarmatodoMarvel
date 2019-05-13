package com.farmatodo.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.farmatodo.R
import com.farmatodo.data.BaseData
import com.farmatodo.databinding.HomeFragmentBinding
import com.farmatodo.ui.activities.MainActivity
import com.farmatodo.ui.adapters.CardAdapter
import com.farmatodo.utils.OnAdapterItemClick
import com.farmatodo.viewmodels.HomeViewModel

class HomeFragment : Fragment(), OnAdapterItemClick {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding

    companion object{
        const val PARAM_DETAIL = "detail"
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment, container, false)
        initViews()
        return binding.root
    }

    private fun initViews() {
        binding.btCalc.setOnClickListener { viewModel.makeRequest(binding.etExp.text.toString()) }
        setupAdapter(mutableListOf())
    }

    private fun setupAdapter(data : MutableList<BaseData>){
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = CardAdapter(data,requireContext(), this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        viewModel.onDataLoaded.observe(this, Observer {
            setupAdapter(it)
        })
        viewModel.onDataDetailLoaded.observe(this, Observer {data ->
            data?.let {
                val bundle = Bundle()
                bundle.putSerializable(PARAM_DETAIL, it)
                findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
                viewModel.clearDetail()
            }
        })
        viewModel.onLoading.observe(this, Observer {
            if(it){
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        })
        viewModel.onError.observe(this, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
        viewModel.onTitleChanged.observe(this, Observer {
            updateToolbar(it)
        })
    }

    override fun onItemClick(item: Any) {
        viewModel.getDetail(item as BaseData)
    }

    private fun updateToolbar(title : String){
        (activity as MainActivity).supportActionBar!!.title = title
    }
}
