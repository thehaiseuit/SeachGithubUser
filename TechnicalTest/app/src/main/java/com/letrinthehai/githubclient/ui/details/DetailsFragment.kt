package com.letrinthehai.githubclient.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.letrinthehai.githubclient.R
import com.letrinthehai.githubclient.databinding.FragmentDetailsBinding
import com.letrinthehai.githubclient.model.User
import com.letrinthehai.githubclient.ui.interfaces.IProgressBarActivity
import com.letrinthehai.githubclient.ui.interfaces.IProgressBarFragment
import com.letrinthehai.githubclient.ui.interfaces.ISearchListenerActivity
import kotlinx.android.synthetic.main.fragment_details.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class DetailsFragment : Fragment(), KodeinAware,
    IProgressBarFragment {

    override val kodein by kodein()
    private val factory: DetailsViewModelFactory by instance()

    private val args: DetailsFragmentArgs by navArgs()

    private lateinit var viewModel: DetailsViewModel
    private lateinit var binding: FragmentDetailsBinding
    private var userId: Long = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userId = args.userId
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, factory).get(DetailsViewModel::class.java)

        binding.apply {
            lifecycleOwner = this@DetailsFragment
            vm = viewModel
        }

        viewModel.getUserById(userId)
        initObserver()
    }

    private fun initObserver() {
        viewModel.user.observe(viewLifecycleOwner, Observer {
            binding.user = it
            hideProgressBar()
            bindProjectLink(it)
        })
    }

    override fun showProgressBar() = (requireActivity() as IProgressBarActivity).show()
    override fun hideProgressBar() = (requireActivity() as IProgressBarActivity).hide()

    override fun onResume() {
        super.onResume()
        (requireActivity() as ISearchListenerActivity).showSearchView(false)
    }

    private fun bindProjectLink(user: User) {

        project_link_container?.setOnClickListener {
            val defaultBrowser = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_BROWSER)
            defaultBrowser.data = Uri.parse(user.url)
            startActivity(defaultBrowser)
        }
        tv_repo?.setOnClickListener {
            val defaultBrowser = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_BROWSER)
            defaultBrowser.data = Uri.parse(user.reposUrl)
            startActivity(defaultBrowser)
        }
        tv_follower?.setOnClickListener {
            val defaultBrowser = Intent.makeMainSelectorActivity(Intent.ACTION_MAIN, Intent.CATEGORY_APP_BROWSER)
            defaultBrowser.data = Uri.parse(user.followersUrl)
            startActivity(defaultBrowser)
        }
    }

}