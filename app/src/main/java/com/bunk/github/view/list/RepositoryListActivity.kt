package com.bunk.github.view.list

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bunk.github.R
import com.bunk.github.data.entity.RepositoryItem
import com.bunk.github.util.PaginationScrollListener
import com.bunk.github.util.VerticalSpaceItemDecoration
import com.bunk.github.view.Info
import com.bunk.github.view.ShowProgressBar
import com.bunk.github.view.detail.RepositoryDetailActivity
import kotlinx.android.synthetic.main.repository_list_activity.*
import org.koin.androidx.viewmodel.ext.android.viewModel

private val TAG = RepositoryListActivity::class.java.simpleName

private const val VERTICAL_SPACE_HEIGHT = 50
private const val POSITION_TO_END_TO_REQUEST_NEW_ITEMS = 10

class RepositoryListActivity : AppCompatActivity() {

    private val repositoryAdapter = RepositoryAdapter().apply {
        repositoryItemClickListener = object : RepositoryItemClickListener {
            override fun onItemClick(repositoryItem: RepositoryItem) {
                val context = this@RepositoryListActivity
                val intent = RepositoryDetailActivity.createIntent(context, repositoryItem.id)
                context.startActivity(intent)
            }
        }
    }

    private val repositoryListViewModel: RepositoryListViewModel by viewModel()

    private val paginationScrollListener =
        PaginationScrollListener(POSITION_TO_END_TO_REQUEST_NEW_ITEMS) { repositoryListViewModel.loadNextPage() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.repository_list_activity)

        with(recyclerView) {
            adapter = repositoryAdapter
            layoutManager = LinearLayoutManager(this@RepositoryListActivity)
            addItemDecoration(VerticalSpaceItemDecoration(VERTICAL_SPACE_HEIGHT))
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            addOnScrollListener(paginationScrollListener)
        }

        repositoryListViewModel.liveData.observe(this,
            Observer<List<RepositoryItem>> {
                repositoryAdapter.submitList(it)
            }
        )

        repositoryListViewModel.infoLiveData.observe(this,
            Observer<Info> {
                Toast.makeText(this, it.resId, Toast.LENGTH_SHORT).show()
            }
        )

        repositoryListViewModel.progressBarLiveData.observe(this,
            Observer<ShowProgressBar> {
                progressBar.visibility = if (it.visibile) View.VISIBLE else View.INVISIBLE
            }
        )

        repositoryListViewModel.fetchRepositories()
    }
}