package com.example.projectsample.repository

import com.example.projectsample.api.RetrofitInstance
import com.example.projectsample.db.ArticleDatabase
import com.example.projectsample.model.Article

class NewsRepository(
    val db: ArticleDatabase
) {

    suspend fun getBreakingNews(countryCode:String,pageNumber:Int)=
        RetrofitInstance.api.getBreakingNews(countryCode,pageNumber)


    suspend fun searchNews(searchQuery: String,pageNumber: Int)=
        RetrofitInstance.api.searchForNews(searchQuery,pageNumber)


    suspend fun upsert(article: Article) =db.getArticleDao().upsert(article)
    fun getSavedNews()=db.getArticleDao().getAllArticle()
    suspend fun deleteArticle(article: Article)=db.getArticleDao().deleteArticle(article)

}