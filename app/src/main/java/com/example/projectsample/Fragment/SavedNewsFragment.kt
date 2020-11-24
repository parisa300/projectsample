package com.example.projectsample.Fragment

import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContract

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


import com.google.android.material.snackbar.Snackbar
import com.example.projectsample.Adapter.NewsAdapter

import com.example.projectsample.R
import com.example.projectsample.ui.NewsActivity
import com.example.projectsample.ui.NewsViewModel
import kotlinx.android.synthetic.main.fragment_saved_news.*


 class SavedNewsFragment :Fragment(R.layout.fragment_saved_news) {
    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
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
            val bundle=Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(
                R.id.action_savedNewsFragment_to_articleFragment,
                bundle
            )
        }

        val itemTouchHelperCallback =object :ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position =viewHolder.adapterPosition
                val article =newsAdapter.differ.currentList[position]
                viewModel.deleteAticle(article)
                Snackbar.make(view,"Successfuly deleted", Snackbar.LENGTH_SHORT).apply {
                    setAction("Undo"){
                        viewModel.saveArticle(article)
                    }
                    show()
                }
            }

        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(rvSavedNews)
        }

        viewModel.getSavedNews().observe(viewLifecycleOwner, Observer {article ->
            newsAdapter.differ.submitList(article)

        })
    }

    private fun setupRecyclerView(){
        newsAdapter= NewsAdapter()
        rvSavedNews.apply {
            adapter=newsAdapter
            layoutManager= LinearLayoutManager(activity)
        }
    }
}