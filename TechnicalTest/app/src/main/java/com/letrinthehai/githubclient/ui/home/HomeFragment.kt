package com.letrinthehai.githubclient.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.letrinthehai.githubclient.R
import com.letrinthehai.githubclient.ui.interfaces.IProgressBarActivity
import com.letrinthehai.githubclient.ui.interfaces.IProgressBarFragment
import com.letrinthehai.githubclient.ui.interfaces.ISearchListenerActivity
import com.letrinthehai.githubclient.utils.NetworkUtils
import com.letrinthehai.githubclient.utils.hide
import com.letrinthehai.githubclient.utils.hideKeyboard
import com.letrinthehai.githubclient.utils.show
import kotlinx.android.synthetic.main.fragment_home.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class HomeFragment : Fragment(), KodeinAware ,
    IProgressBarFragment {

    private lateinit var searchQuery: String

    override val kodein by kodein()
    private val factory: HomeViewModelFactory by instance()

    private lateinit var viewModel: HomeViewModel
    private lateinit var lastSearchAdapter: LastSearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initReposObserver()
        initSearchListener()
        initNetworkChangesListener()
        bindUI()
    }

    private fun bindUI() {
        lastSearchAdapter = LastSearchAdapter()
        recycler_view.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = lastSearchAdapter
        }

    }

    private fun initReposObserver() {
        viewModel.queryStrings.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it.isEmpty()){
                    last_search_title.hide()
                    recycler_view.hide()
                } else {
                    last_search_title.show()
                    recycler_view.show()
                    lastSearchAdapter.apply {
                        queryStrings = it
                    }
                }
            }
        })
    }

    private fun initSearchListener() {
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null && query.isNotEmpty()) {
                    val action = HomeFragmentDirections.actionSearch(query.trim())
                    Navigation.findNavController(search_view).navigate(action)
                    hideKeyboard()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun initNetworkChangesListener() {
        NetworkUtils.getNetworkLiveData(requireContext()).observe(viewLifecycleOwner, Observer { isConnected ->
            if (isConnected) {
                search_view.show()
                tv_error.hide()
            } else {
                search_view.hide()
                tv_error.show()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        hideProgressBar()
        (requireActivity() as ISearchListenerActivity).showSearchView(false)
        viewModel.getSearchQuery()
    }


    override fun showProgressBar() = (requireActivity() as IProgressBarActivity).show()
    override fun hideProgressBar() = (requireActivity() as IProgressBarActivity).hide()
}