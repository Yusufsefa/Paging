package com.yyusufsefa.scorpcase.ui.main

import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scorpcase.databinding.ActivityMainBinding
import com.yyusufsefa.scorpcase.common.BaseActivity
import com.yyusufsefa.scorpcase.ui.adapter.PersonAdapter
import com.yyusufsefa.scorpcase.util.Constants
import com.yyusufsefa.scorpcase.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val adapter by lazy { PersonAdapter() }

    private val viewModel: MainViewModel by viewModels()

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)

    override fun init() {
        binding.personRV.adapter = adapter

        viewModel.person.observe(this) {
            adapter.submitData(lifecycle, it)
        }
        bindEvents()
        initAdapter()
    }

    private fun initAdapter() {

        adapter.addLoadStateListener { loadState ->
            val isListEmpty =
                loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0
            binding.txtError.isVisible = isListEmpty

            binding.personRV.isVisible = loadState.source.refresh is LoadState.NotLoading

            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading

            handleLoading(loadState.source.refresh is LoadState.Loading)

            val errorState = when {
                loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                else -> null
            }

            errorState?.let {
                binding.txtError.text = it.error.toString()
                toast(it.toString())
            }

        }

    }

    private fun bindEvents() {
        binding.personRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val scrollPosition =
                    (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                binding.refreshLayout.isEnabled = scrollPosition == Constants.INITIAL_LOAD_SIZE
            }
        })

        binding.refreshLayout.setOnRefreshListener {
            adapter.refresh()
        }
    }

    private fun handleLoading(loading: Boolean) {
        with(binding) {
            refreshLayout.isRefreshing = loading == true
        }
    }

}