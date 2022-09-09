package id.co.iconpln.dicodingintermediate_storyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.co.iconpln.dicodingintermediate_storyapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}