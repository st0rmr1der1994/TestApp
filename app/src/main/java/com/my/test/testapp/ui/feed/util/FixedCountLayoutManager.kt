package com.my.test.testapp.ui.feed.util

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

class FixedCountLayoutManager(
        context: Context?,
        private val itemsPerPage: Int
) : LinearLayoutManager(context, VERTICAL, false) {

    override fun checkLayoutParams(params: RecyclerView.LayoutParams): Boolean {
        return super.checkLayoutParams(params) && params.height == calculateItemHeight()
    }

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return modifyParamsHeight(super.generateDefaultLayoutParams())
    }

    override fun generateLayoutParams(params: ViewGroup.LayoutParams): RecyclerView.LayoutParams {
        return modifyParamsHeight(super.generateLayoutParams(params))
    }

    private fun calculateItemHeight() = Math.round(height.toFloat() / itemsPerPage)

    private fun modifyParamsHeight(initialParams: RecyclerView.LayoutParams): RecyclerView.LayoutParams {
        initialParams.height = calculateItemHeight()
        return initialParams
    }
}
