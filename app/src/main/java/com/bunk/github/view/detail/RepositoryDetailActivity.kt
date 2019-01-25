package com.bunk.github.view.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bunk.github.R
import com.bunk.github.data.entity.RepositoryItem
import com.bunk.github.util.GlideProvider
import com.bunk.github.view.Info
import kotlinx.android.synthetic.main.repository_detail_activity.*
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val KEY_ID = "key_id"

class RepositoryDetailActivity : AppCompatActivity() {

    private val detailsListViewModel: DetailsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.repository_detail_activity)

        detailsListViewModel.liveData.observe(this,
            Observer<RepositoryItem> { repositoryItem ->
                detailsNameTextView.text = repositoryItem.name
                starsTextView.text = String.format(getString(R.string.stars), repositoryItem.stars)
                urlTextView.text = repositoryItem.url
                descriptionTextView.text = repositoryItem.description
                languageTextView.text = repositoryItem.language
                forksTextView.text = String.format(getString(R.string.forks), repositoryItem.forks)
                openIssuesTextView.text = String.format(getString(R.string.open_issues), repositoryItem.openIssues)
                GlideProvider.Builder().url(repositoryItem.owner.avatarUrl).into(avatarImageView)
            })

        detailsListViewModel.infoLiveData.observe(this,
            Observer<Info> {
                Toast.makeText(this, it.resId, Toast.LENGTH_SHORT).show()
            }
        )

        detailsListViewModel.fetchDetails(intent.getIntExtra(KEY_ID, -1))
    }

    companion object {
        fun createIntent(context: Context, id: Int) =
            Intent(context, RepositoryDetailActivity::class.java).apply {
                putExtra(KEY_ID, id)
            }
    }
}