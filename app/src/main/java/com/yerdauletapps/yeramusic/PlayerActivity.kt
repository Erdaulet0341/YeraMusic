package com.yerdauletapps.yeramusic

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yerdauletapps.yeramusic.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity(), ServiceConnection {

    private lateinit var binding: ActivityPlayerBinding

    companion object{
        private lateinit var listMusicPA: ArrayList<Music>
        var musicPosition: Int = 0
        var isPlay:Boolean = false
        var musicService:MusicService? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.coolBlue)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = Intent(this, MusicService::class.java)
        bindService(intent, this, BIND_AUTO_CREATE)
        startService(intent)
        initializeLayout()

        binding.pauseBtnPA.setOnClickListener {
            if(isPlay){
                pauseMusic()
            }
            else{
                playMusic()
            }
        }

        binding.previousBtnPA.setOnClickListener {
            prevMusic(false)
        }

        binding.nextBtnPA.setOnClickListener {
            prevMusic(true)

        }
    }

    private fun setLayout(){
        Glide.with(this).load(listMusicPA[musicPosition].artUri)
            .apply(RequestOptions().placeholder(R.drawable.ic_launcher_foreground).centerCrop())
            .into(binding.imagePA)
        binding.musicNamePA.text = listMusicPA[musicPosition].title
    }
    private fun createMediaPlayer(){
        try {
            if(musicService!!.mediaPlayer == null) musicService!!.mediaPlayer = MediaPlayer()
            musicService!!.mediaPlayer!!.reset()
            musicService!!.mediaPlayer!!.setDataSource(listMusicPA[musicPosition].path)
            musicService!!.mediaPlayer!!.prepare()
            musicService!!.mediaPlayer!!.start()
            isPlay = true
            binding.pauseBtnPA.setIconResource(R.drawable.pause_icon)
        }catch (e:Exception){return}
    }

    private fun initializeLayout(){
        musicPosition = intent.getIntExtra("index", 0)
        when(intent.getStringExtra("class")){
            "MusicAdapter" ->{
                listMusicPA = ArrayList()
                listMusicPA.addAll(MainActivity.MusicListMA)
                setLayout()
            }
            "MainActivity"->{
                listMusicPA = ArrayList()
                listMusicPA.addAll(MainActivity.MusicListMA)
                listMusicPA.shuffle()
                setLayout()
            }
        }
    }

    private fun playMusic(){
        binding.pauseBtnPA.setIconResource(R.drawable.pause_icon)
        isPlay = true
        musicService!!.mediaPlayer!!.start()
    }
    private fun pauseMusic(){
        binding.pauseBtnPA.setIconResource(R.drawable.play_icon)
        isPlay = false
        musicService!!.mediaPlayer!!.pause()

    }

    private fun prevMusic(incrment:Boolean){
        if(incrment){
            setSongPosition(true)
            setLayout()
            createMediaPlayer()
        }
        else{
            setSongPosition(false)
            setLayout()
            createMediaPlayer()
        }
    }

    private fun setSongPosition(incrment: Boolean){
        if(incrment){
            if(listMusicPA.size - 1== musicPosition) musicPosition = 0
            else ++musicPosition
        }
        else{
            if(0 == musicPosition) musicPosition = listMusicPA.size -1
            else --musicPosition
        }
    }

    override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
        val binder = p1 as MusicService.MyBinder
        musicService = binder.currentService()
        createMediaPlayer()
    }

    override fun onServiceDisconnected(p0: ComponentName?) {
        musicService = null
    }
}