package com.example.projectsample.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectsample.Utils.Resource
import com.example.projectsample.model.Article
import com.example.projectsample.model.NewsResponce
import com.example.projectsample.repository.NewsRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    val newsRepository: NewsRepository
) :ViewModel (){

    val breakingNews:MutableLiveData<Resource<NewsResponce>> = MutableLiveData()
    var breakingNewsPage=1
    var breakingNewsResponce : NewsResponce? =null

    val searchNews:MutableLiveData<Resource<NewsResponce>> = MutableLiveData()
    var searchNewsPage=1
    var searchNewsResponce : NewsResponce? =null
    init {
        getBreakingNews("us")
    }

    fun getBreakingNews(countryCode:String)=viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val responce=newsRepository.getBreakingNews(countryCode,breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponce(responce))
    }

    fun searchNews(searchQuery:String)=viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val responce=newsRepository.searchNews(searchQuery,searchNewsPage)
        searchNews.postValue(handleSearchNewsResponcee(responce))
    }

    private fun handleBreakingNewsResponce(responce:Response<NewsResponce>):Resource<NewsResponce>{
        if(responce.isSuccessful){
            responce.body()?.let {resultResponce ->
                return Resource.Succcess(resultResponce)
                breakingNewsPage++
                if(breakingNewsResponce ==null){

                    breakingNewsResponce =resultResponce
                }else{
                    val oldArticle =breakingNewsResponce?.articles
                    val newArticle =resultResponce.articles
                    oldArticle?.addAll(newArticle)
                }
             return Resource.Succcess(breakingNewsResponce?:resultResponce)
            }
        }
        return Resource.Error(responce.message())
    }

    private fun handleSearchNewsResponcee(responce:Response<NewsResponce>):Resource<NewsResponce>{
        if(responce.isSuccessful){
            responce.body()?.let {resultResponce ->
                return Resource.Succcess(resultResponce)
                searchNewsPage++
                if(searchNewsResponce ==null){

                    searchNewsResponce =resultResponce
                }else{
                    val oldArticle =searchNewsResponce?.articles
                    val newArticle =resultResponce.articles
                    oldArticle?.addAll(newArticle)
                }
                return Resource.Succcess(searchNewsResponce?:resultResponce)
            }
        }
        return Resource.Error(responce.message())
    }


    fun saveArticle(article: Article) =viewModelScope.launch {
        newsRepository.upsert(article)
    }

    fun getSavedNews()=newsRepository.getSavedNews()

    fun deleteAticle(article: Article) =viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }
}