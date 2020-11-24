package com.example.projectsample.Fragment

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContract
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs



import com.google.android.material.snackbar.Snackbar

import com.example.projectsample.R
import com.example.projectsample.ui.NewsActivity
import com.example.projectsample.ui.NewsViewModel
import kotlinx.android.synthetic.main.fragment_article.*

 class ArticleFragment :Fragment(R.layout.fragment_article) {
    lateinit var viewModel: NewsViewModel
    val args : ArticleFragmentArgs by navArgs()
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
        val article = args.article
        webView1.apply {
            webViewClient = WebViewClient()
            article.url?.let { loadUrl(it) }
        }

        fab.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(view,"Article save Successfully",Snackbar.LENGTH_SHORT).show()
        }
    }
}