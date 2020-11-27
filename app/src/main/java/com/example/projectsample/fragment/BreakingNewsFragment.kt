package com.example.projectsample.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.projectsample.adapter.NewsAdapter

import com.example.projectsample.R
import com.example.projectsample.Utils.Constants.Companion.QUERY_PAGE_SIZE
import com.example.projectsample.Utils.Resource
import com.example.projectsample.databinding.FragmentBreakingNewsBinding
import com.example.projectsample.ui.NewsActivity
import com.example.projectsample.ui.NewsViewModel
import kotlinx.android.synthetic.main.fragment_breaking_news.*

class BreakingNewsFragment :Fragment(R.layout.fragment_breaking_news) {
    private lateinit var binding: FragmentBreakingNewsBinding
    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    val TAG ="BreakingNewsFragment"


     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
binding = FragmentBreakingNewsBinding.bind(view)
        viewModel = (activity as NewsActivity).viewModel
        setupRecyclerView()

        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(
                R.id.action_breakingNewsFragment_to_articleFragment,
            bundle
            )
        }
        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { responce ->

            when(responce){
                is Resource.Succcess ->{
                    hideProgressBar()
                    responce.data?.let {newsResponce ->
                        newsAdapter.differ.submitList(newsResponce.articles.toList())
                        val totalPages=newsResponce.totalResults / QUERY_PAGE_SIZE+2
                        isLastPage=viewModel.breakingNewsPage ==totalPages

                       if(isLastPage){
                       binding.rvBreakingNews.setPadding(0,0,0,0)
                       }


                    }
                }
                is Resource.Error ->{
                    hideProgressBar()
                    responce.message?.let {message ->
                        Log.e(TAG,"error on $message")

                    }
                }

                is Resource.Loading ->{
                    showProgressBar()
                }

            }
        })
    }


    private fun hideProgressBar(){
     binding.paginationProgressBar.visibility=View.INVISIBLE
        isLoading =false
    }

    private fun showProgressBar(){
       binding.paginationProgressBar.visibility=View.VISIBLE
        isLoading =true
    }


    var isLoading =false
    var isLastPage =false
    var isScrolling =false

    val scrollListener =object : RecyclerView.OnScrollListener(){
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager =recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition =layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount =layoutManager.childCount
            val totalItemCount =layoutManager.itemCount

            val isNotLoadingAndNotLastPage =!isLoading &&  !isLastPage
            val isAtLastItem =firstVisibleItemPosition +visibleItemCount >=totalItemCount
            val isNotAtBeginning =firstVisibleItemPosition>=0
            val isTotalMoreThanVisible =totalItemCount >=QUERY_PAGE_SIZE
            val shouldPaginate =isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&isTotalMoreThanVisible
                    &&isScrolling

            if(shouldPaginate){
                viewModel.getBreakingNews("us")
                isScrolling =false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState ==AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling=true
            }
        }
    }
    private fun setupRecyclerView(){
        newsAdapter= NewsAdapter()
      binding. rvBreakingNews.apply {
            adapter=newsAdapter
            layoutManager=LinearLayoutManager(activity)
            addOnScrollListener(this@BreakingNewsFragment.scrollListener)
        }
    }
}