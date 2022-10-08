package id.co.iconpln.dicodingintermediate_storyapp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import id.co.iconpln.dicodingintermediate_storyapp.databinding.ActivityDetailStoryBinding
import id.co.iconpln.dicodingintermediate_storyapp.repository.remote.responsemodel.ListStoryItem

@Suppress("DEPRECATION")
class DetailStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        val dataStory = intent.getParcelableExtra<ListStoryItem>("STORY_DETAIL") as ListStoryItem
        binding.toolbar.title = dataStory.name
        Glide.with(this).load(dataStory.photoUrl).into(binding.ivDetailPhoto)
        binding.tvDetailDescription.text = dataStory.description
    }
}