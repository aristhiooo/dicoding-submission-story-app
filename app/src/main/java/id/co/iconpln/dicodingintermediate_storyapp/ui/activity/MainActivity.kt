package id.co.iconpln.dicodingintermediate_storyapp.ui.activity

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import id.co.iconpln.dicodingintermediate_storyapp.R
import id.co.iconpln.dicodingintermediate_storyapp.databinding.ActivityMainBinding
import id.co.iconpln.dicodingintermediate_storyapp.repository.Result
import id.co.iconpln.dicodingintermediate_storyapp.repository.local.UserPreference
import id.co.iconpln.dicodingintermediate_storyapp.ui.adapter.StoryListAdapter
import id.co.iconpln.dicodingintermediate_storyapp.ui.customview.CustomSnackbar.snackbarError
import id.co.iconpln.dicodingintermediate_storyapp.ui.fragment.CreateStoryFragment
import id.co.iconpln.dicodingintermediate_storyapp.viewmodels.MainViewModel
import id.co.iconpln.dicodingintermediate_storyapp.viewmodels.ViewModelsFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val rootView: View = binding.root
        setContentView(rootView)

        val storyListAdapter = StoryListAdapter()

        val factory: ViewModelsFactory = ViewModelsFactory.getInstance()
        val viewModel: MainViewModel by viewModels { factory }

        viewModel.getAllStories().observe(this) { result ->
            if (result != null) {
                when(result) {
                    is Result.Loading -> {
                        isLoading(true)
                    }
                    is Result.Success -> {
                        isLoading(false)
                        if (result.data.isEmpty()) {
                            isNoData(true)
                        } else {
                            isNoData(false)
                            storyListAdapter.submitList(result.data)
                        }
                    }
                    is Result.Error -> {
                        isLoading(false)
                        isNoData(true)
                        binding.root.snackbarError(result.error)
                    }
                }
            }
        }

        binding.rvListStory.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = storyListAdapter
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getAllStories()
        }
        binding.swipeRefresh.setColorSchemeColors(
            ContextCompat.getColor(this, android.R.color.holo_blue_bright),
            ContextCompat.getColor(this, android.R.color.holo_green_light),
            ContextCompat.getColor(this, android.R.color.holo_orange_light),
            ContextCompat.getColor(this, android.R.color.holo_red_light),
        )

        binding.fabCreateNewStory.setOnClickListener {
            CreateStoryFragment.display(supportFragmentManager)
        }

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.logout -> {
                    UserPreference().clearAndLogout()
                    true
                }
                else -> false
            }

        }
    }

    private fun isLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.swipeRefresh.isRefreshing = true
            binding.rvListStory.visibility = View.GONE
        } else {
            binding.swipeRefresh.isRefreshing = false
            binding.rvListStory.visibility = View.VISIBLE
        }
    }

    private fun isNoData(isNoData: Boolean) {
        if (isNoData) {
            binding.noData.visibility = View.VISIBLE
            binding.rvListStory.visibility = View.GONE
        } else {
            binding.noData.visibility = View.GONE
            binding.rvListStory.visibility = View.VISIBLE
        }
    }
}