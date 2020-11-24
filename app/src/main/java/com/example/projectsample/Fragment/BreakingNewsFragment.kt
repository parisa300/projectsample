package com.example.projectsample.Fragment

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AbsListView
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContract
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.projectsample.Adapter.NewsAdapter

import com.example.projectsample.R
import com.example.projectsample.Utils.Constants.Companion.QUERY_PAGE_SIZE
import com.example.projectsample.Utils.Resource
import com.example.projectsample.ui.NewsActivity
import com.example.projectsample.ui.NewsViewModel
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import retrofit2.Response

 class BreakingNewsFragment :Fragment(R.layout.fragment_breaking_news) {
    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    val TAG ="BreakingNewsFragment"
     override fun <I : Any?, O : Any?> prepareCall(
         contract: ActivityResultContract<I, O>,
         callback: ActivityResultCallback<O>
     ): ActivityResultLauncher<I> {
         TODO("Not yet implemented")
     }

     override fun <I : Any?, O : Any?> prepareCall(
         contract: ActivityResultContract<I, O>,
         registry: ActivityResultRegistry,
         callback: ActivityResultCallback<O>
     ): ActivityResultLauncher<I> {
         TODO("Not yet implemented")
     }

     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                           rvBreakingNews.setPadding(0,0,0,0)
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
        paginationProgressBar.visibility=View.INVISIBLE
        isLoading =false
    }

    private fun showProgressBar(){
        paginationProgressBar.visibility=View.VISIBLE
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
        rvBreakingNews.apply {
            adapter=newsAdapter
            layoutManager=LinearLayoutManager(activity)
            addOnScrollListener(this@BreakingNewsFragment.scrollListener)
        }
    }
}