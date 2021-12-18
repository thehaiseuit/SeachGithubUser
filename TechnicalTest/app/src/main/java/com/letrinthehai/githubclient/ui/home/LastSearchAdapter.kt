package com.letrinthehai.githubclient.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.letrinthehai.githubclient.R
import com.letrinthehai.githubclient.databinding.ItemLastSearchBinding
import com.letrinthehai.githubclient.model.SearchQuery

class LastSearchAdapter()
    : RecyclerView.Adapter<LastSearchAdapter.LastSearchViewHolder>() {

    var queryStrings: List<SearchQuery>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = queryStrings?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LastSearchViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_last_search,
                parent,
                false
            ))

    override fun onBindViewHolder(holder: LastSearchViewHolder, position: Int) {
        queryStrings?.get(position)?.let {
            holder.bind(it)
        }
        holder.binding.queryItem.setOnClickListener {
            queryStrings?.get(position)?.let { query ->
                val action = HomeFragmentDirections.actionSearch(query.queryString)
                Navigation.findNavController(it).navigate(action)
            }

        }
    }

    class LastSearchViewHolder(val binding: ItemLastSearchBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(data: SearchQuery){
            binding.data = data
        }
    }


}