package com.example.projectsample.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.projectsample.model.Article

@Dao
interface ArticlesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: Article):Long

    @Delete
    suspend fun deleteArticle(article: Article)

    @Query("SELECT * FROM articles")
    fun getAllArticle(): LiveData<List<Article>>
}