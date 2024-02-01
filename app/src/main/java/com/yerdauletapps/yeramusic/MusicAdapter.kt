package com.yerdauletapps.yeramusic

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yerdauletapps.yeramusic.databinding.MusicviewBinding

class MusicAdapter(private val context: Context, private val musicList: ArrayList<Music>): RecyclerView.Adapter<MusicAdapter.MusicHolder>(){
    class MusicHolder(binding: MusicviewBinding) : RecyclerView.ViewHolder(binding.root){
        val title = binding.songNameVM
        val album = binding.authorAlbum
        val img = binding.imageVM
        val time = binding.songTime


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicAdapter.MusicHolder {
        return MusicHolder(MusicviewBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: MusicAdapter.MusicHolder, position: Int) {
        holder.title.text = musicList[position].title
        holder.album.text = musicList[position].album
        holder.time.text = musicList[position].time.toString()

    }

    override fun getItemCount(): Int {
       return  musicList.size
    }


}