package com.reign.mobilenews.modules.news_feed.ui.adapter

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ccmgolf.kitsune.data.converters.DateTimeConverter
import com.chauthai.swipereveallayout.SwipeRevealLayout
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.reign.mobilenews.R
import com.reign.mobilenews.data.entities.NewsEntity


class NewsAdapter(var context: Context, var onItemClickListener: OnItemClickListener? = null) :
    PagingDataAdapter<NewsEntity, NewsAdapter.ViewHolder>(diffCallback) {

    private val viewBinderHelper = ViewBinderHelper()

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<NewsEntity>() {
            override fun areItemsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
                val oldId = getIdentifier(oldItem)
                val newId = getIdentifier(newItem)
                return oldId == newId
            }

            override fun areContentsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
                val oldId = getIdentifier(oldItem)
                val newId = getIdentifier(newItem)
                return oldId == newId
            }
        }

        private fun getIdentifier(newsEntity: NewsEntity): Int {
            return newsEntity.id
        }
    }

    fun getItemAt(position: Int): NewsEntity? {
        return getItem(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): NewsAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_news, parent, false))
    }

    override fun onBindViewHolder(holder: NewsAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        item?.let {
            viewBinderHelper.bind(holder.swipeRevealLayout, it.id.toString())
        }


        if (item?.storyTitle != null)
            holder.newsTitleTV.text = item.storyTitle
        else {
            holder.newsTitleTV.text = item?.title
        }

        val authorDateText = item?.author + " - " + getTimeAgo(item?.createdAt)

        holder.newsAuthorDateTV.text = authorDateText
    }

    private fun getTimeAgo(createdAt: String?): String {
        var timeAgo = ""
        createdAt?.let {
            val date = DateTimeConverter.fromString(it)
            timeAgo = DateUtils.getRelativeTimeSpanString(date.time).toString()
        }

        return timeAgo
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private var mainLayout: LinearLayout = itemView.findViewById(R.id.itemContainer)
        var newsTitleTV: TextView = itemView.findViewById(R.id.newsTitleTV)
        var newsAuthorDateTV: TextView = itemView.findViewById(R.id.newsAuthorDateTV)
        var swipeRevealLayout: SwipeRevealLayout = itemView.findViewById(R.id.swipeRevealLayout)
        var deleteIB: ImageButton = itemView.findViewById(R.id.deleteIB)

        init {
            mainLayout.setOnClickListener(this)
            deleteIB.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onItemClickListener.let {
                it?.onItemClick(v, bindingAdapterPosition)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

}