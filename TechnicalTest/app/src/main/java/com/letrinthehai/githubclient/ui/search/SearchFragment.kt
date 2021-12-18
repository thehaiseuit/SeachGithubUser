package com.letrinthehai.githubclient.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.letrinthehai.githubclient.R
import com.letrinthehai.githubclient.repository.Status
import com.letrinthehai.githubclient.ui.interfaces.IProgressBarActivity
import com.letrinthehai.githubclient.ui.interfaces.IProgressBarFragment
import com.letrinthehai.githubclient.ui.interfaces.ISearchListenerActivity
import com.letrinthehai.githubclient.ui.interfaces.ISearchListenerFragment
import com.letrinthehai.githubclient.widgets.EndlessRecyclerOnScrollListener
import kotlinx.android.synthetic.main.fragment_search.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class SearchFragment : Fragment(), KodeinAware,
    IProgressBarFragment,
    ISearchListenerFragment {

    override val kodein by kodein()
    private val factory: SearchViewModelFactory by instance()

    private val args: SearchFragmentArgs by navArgs()

    private lateinit var viewModel: SearchViewModel

    private lateinit var searchQuery: String
    private lateinit var searchAdapter: SearchAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchQuery = args.searchQuery

        (requireActivity() as ISearchListenerActivity).registerSearchFragment(this)
        (requireActivity() as ISearchListenerActivity).setSearchText(searchQuery)

        viewModel = ViewModelProvider(this, factory).get(SearchViewModel::class.java)
        getData()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindUI()
        initReposObserver()
    }

    private fun initReposObserver() {
        viewModel.users.observe(viewLifecycleOwner, Observer { status ->
            when (status) {
                is Status.Loading -> showProgressBar()
                is Status.Success -> {
                    hideProgressBar()
                    if (status.data.isNotEmpty() && status.data != searchAdapter.users) {
                        searchAdapter.apply {
                            this.users = status.data
                        }
                    }
                }
                is Status.Error -> {
                    hideProgressBar()
                }
            }
        })
    }

    private fun getData() = viewModel.getRepos(searchQuery)

    override fun showProgressBar() = (requireActivity() as IProgressBarActivity).show()
    override fun hideProgressBar() = (requireActivity() as IProgressBarActivity).hide()

    private fun bindUI() {
        searchAdapter = SearchAdapter()
        recycler_view.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = searchAdapter
        }
        recycler_view.addOnScrollListener(object : EndlessRecyclerOnScrollListener(recycler_view?.layoutManager){
            override fun onLoadMore(currentPage: Int) {
                viewModel.page++
                viewModel.getRepos(searchQuery)
            }

        })

    }

    override fun doSearch(searchQuery: String) {
        this.searchQuery = searchQuery
        getData()
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as ISearchListenerActivity).showSearchView(true)
    }
}