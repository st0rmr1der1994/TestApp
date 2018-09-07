package com.my.test.testapp.ui.feed.util

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.MotionEvent

class RecyclerItemClickListener(
        context: Context,
        private val clickListener: OnItemClickListener?
) : RecyclerView.OnItemTouchListener {

    private val mGestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent) = true
    })

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onInterceptTouchEvent(view: RecyclerView, e: MotionEvent): Boolean {
        val childView = view.findChildViewUnder(e.x, e.y)
        if (childView != null && clickListener != null && mGestureDetector.onTouchEvent(e)) {
            clickListener.onItemClick(view.getChildAdapterPosition(childView))
            return true
        }
        return false
    }

    override fun onTouchEvent(view: RecyclerView, motionEvent: MotionEvent) {}

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
}
