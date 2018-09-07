package com.my.test.testapp.ui.feed.util

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.my.test.testapp.R
import com.my.test.testapp.entity.RedditPostModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.view_item_feed.*

class RedditFeedAdapter(private var items: MutableList<RedditPostModel>) : RecyclerView.Adapter<RedditFeedAdapter.FeedItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): RedditFeedAdapter.FeedItemViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.view_item_feed, parent, false)
        return FeedItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FeedItemViewHolder, position: Int) {
        val postModel = items[position]
        holder.bind(postModel)
    }

    override fun getItemCount() = items.size

    fun addItems(items: List<RedditPostModel>?) {
        items?.let {
            val position = this.items.size
            this.items.addAll(items)
            notifyItemRangeInserted(position, items.size)
        }
    }

    fun getItemByPosition(position: Int): RedditPostModel {
        return items[position]
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    class FeedItemViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(model: RedditPostModel) {
            postThumbnailDraweeView.setImageURI(model.postThumbnail)
            postTitleTextView.text = model.postTitle
            postAuthorTextView.text = model.postAuthor
        }
    }
}
