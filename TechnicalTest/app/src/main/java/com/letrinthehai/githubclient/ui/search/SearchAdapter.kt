package com.letrinthehai.githubclient.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.letrinthehai.githubclient.R
import com.letrinthehai.githubclient.databinding.ItemSearchRepositoryBinding
import com.letrinthehai.githubclient.model.User

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

  var users: List<User>? = null
    set(value) {
      field = value
      notifyDataSetChanged()
    }

  override fun getItemCount() = users?.size ?: 0

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
      SearchViewHolder(
          DataBindingUtil.inflate(
              LayoutInflater.from(parent.context),
              R.layout.item_search_repository,
              parent,
              false
          )
      )

  override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
    users?.get(position)?.let {
      holder.bind(it)
    }
    holder.binding.searchItem.setOnClickListener {
      users?.get(position)?.let { user ->
        val action =
            SearchFragmentDirections.actionDetails(
                user.id
            )
        Navigation.findNavController(it).navigate(action)
      }
    }
  }

  class SearchViewHolder(val binding: ItemSearchRepositoryBinding) :
      RecyclerView.ViewHolder(binding.root){
        fun bind(data: User){
          binding.user = data
        }
      }
}