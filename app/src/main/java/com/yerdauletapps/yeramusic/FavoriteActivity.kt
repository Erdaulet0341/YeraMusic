package com.yerdauletapps.yeramusic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yerdauletapps.yeramusic.databinding.ActivityFavotireBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavotireBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.coolBlue)
        binding = ActivityFavotireBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}