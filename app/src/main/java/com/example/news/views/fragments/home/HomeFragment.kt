package com.example.news.views.fragments.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.news.R
import com.example.news.databinding.FragmentHomeBinding
import com.example.news.models.Article
import com.example.news.utilities.gone
import com.example.news.utilities.visible
import com.example.news.viewmodels.HomeViewModel
import com.example.news.views.adapters.NewsAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()

    private val newsAdapter =NewsAdapter()

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.rvHome.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(context)
        }

        viewModel.updateRandom()

        observeData()

        swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(requireContext(), R.color.crayolaBlue))

        swipeRefreshLayout.setOnRefreshListener {

            viewModel.updateRandom()
            swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun observeData() {

        viewModel.random.observe(viewLifecycleOwner) { random ->

            random?.let {

                binding.progressBar.gone()
                viewModel.getdata(random)
            }
        }


        viewModel.items.observe(viewLifecycleOwner) {
            newsAdapter.updateList(it)
        }


        viewModel.loading.observe(viewLifecycleOwner) {
            if (it) binding.progressBar.visible() else binding.progressBar.gone()
        }


        viewModel.error.observe(viewLifecycleOwner) {
            it?.let {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()

                Log.e("Error",it)
            }
        }

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}