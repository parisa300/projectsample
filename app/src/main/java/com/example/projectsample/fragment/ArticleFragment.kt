package com.example.projectsample.fragment

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment


import com.google.android.material.snackbar.Snackbar

import com.example.projectsample.R
import com.example.projectsample.databinding.FragmentArticleBinding
import com.example.projectsample.ui.NewsActivity
import com.example.projectsample.ui.NewsViewModel
import kotlinx.android.synthetic.main.fragment_article.*

 class ArticleFragment :Fragment(R.layout.fragment_article) {
  private lateinit var binding: FragmentArticleBinding
    lateinit var viewModel: NewsViewModel

  //  val args : ArticleFragmentArgs by navArgs()


     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
binding = FragmentArticleBinding.bind(view)
        viewModel = (activity as NewsActivity).viewModel
      //  val article = args.article
        webView1.apply {
            webViewClient = WebViewClient()
      //      article.url?.let { loadUrl(it) }
        }

     binding.fab.setOnClickListener {
         //   viewModel.saveArticle(article)
            Snackbar.make(view,"خبر با موفقیت ذخیره شد",Snackbar.LENGTH_SHORT).show()
        }
    }
}