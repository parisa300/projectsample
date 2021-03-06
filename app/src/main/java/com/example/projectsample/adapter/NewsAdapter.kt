package com.example.projectsample.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

import com.bumptech.glide.Glide
import com.example.projectsample.R
import com.example.projectsample.databinding.ItemArticlePreviewBinding
import com.example.projectsample.model.Article
import kotlinx.android.synthetic.main.item_article_preview.view.*

class NewsAdapter :RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    inner class ArticleViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    private val differCallback =object :DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url==newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
           return oldItem==newItem
        }


    }

    val differ=AsyncListDiffer(this,differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
      return  ArticleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_article_preview,parent,false))

       /* val itemBinding = ItemArticlePreviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return */
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article=differ.currentList[position]
        holder.itemView.apply {

            Glide.with(this).load(article.urlToImage).into(ivArticleImage)
            holder.itemView.tvSource.text=article.source?.name
            holder.itemView.tvDescription.text=article.description
            holder.itemView. tvTitle.text=article.title
            holder.itemView. tvPublishedAt.text=article.publishedAt

            setOnClickListener() {
                onItemClickListener?.let { it(article) }
            }
        }

    }

  private var onItemClickListener : ((Article)->Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit){

        onItemClickListener = listener
    }
}