package com.reign.mobilenews.modules.news_feed.ui.activity

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.reign.mobilenews.NewsFeedApp
import com.reign.mobilenews.R
import com.reign.mobilenews.di.viewmodel.DaggerViewModelFactory
import com.reign.mobilenews.modules.common.receiver.ConnectionReceiver
import com.reign.mobilenews.modules.common.receiver.ConnectionReceiverListener
import com.reign.mobilenews.modules.news_feed.ui.adapter.NewsAdapter
import com.reign.mobilenews.modules.news_feed.viewmodel.MainActivityViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
class MainActivity : AppCompatActivity(), ConnectionReceiverListener,
    NewsAdapter.OnItemClickListener {

    @Inject
    lateinit var newsFeedApp: NewsFeedApp

    @Inject
    lateinit var viewModelFactory: DaggerViewModelFactory

    private lateinit var viewModel: MainActivityViewModel

    private lateinit var adapter: NewsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        newsFeedApp.setConnectionListener(this)
        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java)

        viewModel.connectionChange(ConnectionReceiver.isConnected(this))


        viewModel =
            ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java)

        setupRecyclerView()
        setupSwipeToRefresh()

        with(adapter) {
            with(viewModel) {
                lifecycleScope.launchWhenCreated {
                    newsListFlow.collectLatest { submitData(it) }
                }
                lifecycleScope.launchWhenCreated {
                    loadStateFlow.collectLatest {
                        swipeRefreshSearch.isRefreshing = it.refresh is LoadState.Loading
                    }
                }
                lifecycleScope.launchWhenCreated {
                    loadStateFlow.distinctUntilChangedBy { it.refresh }
                        .filter { it.refresh is LoadState.NotLoading }
                        .collect { recyclerViewSearchList.scrollToPosition(0) }
                }
            }
        }

        viewModel.connectivityLiveData.observe(this, Observer {
            connectivityInclude.visibility = if (it) View.GONE else View.VISIBLE
        })

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val connectivityManager =
                getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            connectivityManager.registerDefaultNetworkCallback(networkCallback)
        }
    }


    private fun setupRecyclerView() {
        adapter = NewsAdapter(this, this)
        recyclerViewSearchList.layoutManager = LinearLayoutManager(this)
        recyclerViewSearchList.adapter = adapter
    }

    private fun setupSwipeToRefresh() {
        //swipeRefreshSearch.isRefreshing = true
        swipeRefreshSearch.setOnRefreshListener {
            adapter.refresh()
        }
    }

    private val networkCallback = @RequiresApi(Build.VERSION_CODES.N)
    object : ConnectivityManager.NetworkCallback() {
        override fun onLost(network: Network) {
            viewModel.connectionChange(false)
        }

        override fun onAvailable(network: Network) {
            viewModel.connectionChange(true)
        }
    }

    override fun onNetworkConnectionChanged(connected: Boolean) {
        viewModel.connectionChange(connected)
    }


    override fun onItemClick(view: View?, position: Int) {
        if (view?.id == R.id.itemContainer) {
            startActivity(WebViewActivityIntent(adapter.getItemAt(position)!!.storyUrl))
        } else
            if (view?.id == R.id.deleteIB) {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.hideNews(adapter.getItemAt(position))
                }
            }
    }

}