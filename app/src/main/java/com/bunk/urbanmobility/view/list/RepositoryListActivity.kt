package com.bunk.urbanmobility.view.list

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bunk.urbanmobility.R
import com.bunk.urbanmobility.api.entity.RepositoryItem
import com.bunk.urbanmobility.util.VerticalSpaceItemDecoration
import com.bunk.urbanmobility.view.Info
import com.bunk.urbanmobility.view.detail.RepositoryDetailActivity
import kotlinx.android.synthetic.main.repository_list_activity.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.recyclerview.widget.DividerItemDecoration

private val TAG = RepositoryListActivity::class.java.simpleName

private const val VERTICAL_SPACE_HEIGHT = 50

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.repository_list_activity)

        with(recyclerView) {
            adapter = repositoryAdapter
            layoutManager = LinearLayoutManager(this@RepositoryListActivity)
            addItemDecoration(VerticalSpaceItemDecoration(VERTICAL_SPACE_HEIGHT))
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
//            addOnScrollListener(paginationScrollListener)
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

        repositoryListViewModel.fetchRepositories()
    }
}