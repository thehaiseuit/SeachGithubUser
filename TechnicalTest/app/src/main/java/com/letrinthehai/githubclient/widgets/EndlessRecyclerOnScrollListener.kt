package com.letrinthehai.githubclient.widgets

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class EndlessRecyclerOnScrollListener(
    private val mLayoutManager: RecyclerView.LayoutManager?) : RecyclerView.OnScrollListener() {
  var firstVisibleItem = 0
  var visibleItemCount = 0
  var totalItemCount = 0
  private var previousTotal = 0
  private var loading = true
  private var currentPage = 0
  private var isUseLinearLayoutManager = false
  private var isUseGridLayoutManager = false
  override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
    super.onScrolled(recyclerView, dx, dy)
    visibleItemCount = recyclerView.childCount
    totalItemCount = mLayoutManager?.itemCount ?:0
    if (isUseLinearLayoutManager && mLayoutManager is LinearLayoutManager) {
      firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition()
    }
    if (isUseGridLayoutManager && mLayoutManager is GridLayoutManager) {
      firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition()
    }
    if (loading) {
      if (totalItemCount > previousTotal) {
        loading = false
        previousTotal = totalItemCount
      }
    }
    val visibleThreshold = 5
    if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {

      currentPage++
      onLoadMore(currentPage)
      loading = true
    }
  }

  abstract fun onLoadMore(currentPage: Int)

  init {
    if (mLayoutManager is LinearLayoutManager) {
      isUseLinearLayoutManager = true
      if (mLayoutManager is GridLayoutManager) {
        isUseGridLayoutManager = true
      }
    }
  }
}