package com.yerdauletapps.yeramusic

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.yerdauletapps.yeramusic.databinding.MusicviewBinding

class MusicAdapter(private val context: Context, private val musicList: ArrayList<Music>): RecyclerView.Adapter<MusicAdapter.MusicHolder>(){
    class MusicHolder(binding: MusicviewBinding) : RecyclerView.ViewHolder(binding.root){
        val title = binding.songNameVM
        val album = binding.authorAlbum
        val img = binding.imageVM
        val time = binding.songTime
        val root = binding.root

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicAdapter.MusicHolder {
        return MusicHolder(MusicviewBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: MusicAdapter.MusicHolder, position: Int) {
        holder.title.text = musicList[position].title
        holder.album.text = musicList[position].album
        holder.time.text = formatTime(musicList[position].time)
        Glide.with(context).load(musicList[position].artUri)
            .apply(RequestOptions().placeholder(R.drawable.ic_launcher_foreground).centerCrop())
            .into(holder.img)
        holder.root.setOnClickListener {
            val intent  = Intent(context, PlayerActivity::class.java)
            intent.putExtra("index", position)
            intent.putExtra("class", "MusicAdapter")

            ContextCompat.startActivity(context, intent, null)
        }
    }

    override fun getItemCount(): Int {
       return  musicList.size
    }


}