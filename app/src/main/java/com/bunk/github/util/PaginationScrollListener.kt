package com.bunk.github.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private val TAG = PaginationScrollListener::class.java.simpleName

class PaginationScrollListener(
    private val thresholdToEndWhenToRequestNextPage: Int,
    private val listener: () -> Unit
) : RecyclerView.OnScrollListener() {
    private var maxLastVisiblePosition = 0

    fun reset() {
        maxLastVisiblePosition = 0
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

        if (lastVisibleItemPosition < maxLastVisiblePosition) {
            return
        }

        maxLastVisiblePosition = lastVisibleItemPosition

        val itemCount = recyclerView.adapter?.itemCount
        if (itemCount != null && lastVisibleItemPosition > itemCount - thresholdToEndWhenToRequestNextPage) {
            maxLastVisiblePosition = itemCount
            listener.invoke()
        }
    }
}