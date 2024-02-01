package com.yerdauletapps.yeramusic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yerdauletapps.yeramusic.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.coolBlue)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}