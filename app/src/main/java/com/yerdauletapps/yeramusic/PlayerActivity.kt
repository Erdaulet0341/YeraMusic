package com.yerdauletapps.yeramusic

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yerdauletapps.yeramusic.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerBinding

    companion object{
        private lateinit var listMusicPA: ArrayList<Music>
        var musicPosition: Int = 0
        var medisPlayer:MediaPlayer? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.coolBlue)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        musicPosition = intent.getIntExtra("index", 0)
        when(intent.getStringExtra("class")){
            "MusicAdapter" ->{
                listMusicPA = ArrayList()
                listMusicPA.addAll(MainActivity.MusicListMA)
                if(medisPlayer == null) medisPlayer = MediaPlayer()
                medisPlayer!!.reset()
                medisPlayer!!.setDataSource(listMusicPA[musicPosition].path)
                medisPlayer!!.prepare()
                medisPlayer!!.start()
            }
        }

    }
}