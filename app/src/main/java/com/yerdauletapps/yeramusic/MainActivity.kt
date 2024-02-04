package com.yerdauletapps.yeramusic

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.yerdauletapps.yeramusic.databinding.ActivityMainBinding
import java.io.File
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var musicAdapter: MusicAdapter

    companion object{
       lateinit var MusicListMA:ArrayList<Music>
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.coolBlueNav)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toggle = ActionBarDrawerToggle(this, binding.root, R.string.open, R.string.close)
        binding.root.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if(requestRuntimePermission())
        initialLayout()

        binding.shuffleBtn.setOnClickListener {
            val intent = Intent(this, PlayerActivity::class.java)
            intent.putExtra("index", 0)
            intent.putExtra("class", "MainActivity")
            startActivity(intent)
        }

        binding.favoritesBtn.setOnClickListener {
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }

        binding.playlistBtn.setOnClickListener {
            val intent = Intent(this, PlaylistActivity::class.java)
            startActivity(intent)
        }

        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navFeedback->{
                    Toast.makeText(this, "Feedback", Toast.LENGTH_SHORT).show()
                }
                R.id.navSettings->{
                    Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
                }
                R.id.navAbout->{
                    Toast.makeText(this, "About", Toast.LENGTH_SHORT).show()
                }
                R.id.navExit->{
                    Toast.makeText(this, "Exit", Toast.LENGTH_SHORT).show()
                    exitProcess(1)
                }
            }
            true
        }
    }

    private fun requestRuntimePermission():Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                13
            )
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 13){
            if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show()
                initialLayout()       }
            else{
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    13
                )
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)) return true
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("SetTextI18n")
    private fun initialLayout(){
        MusicListMA = getAllAudio()

        binding.recyclerMusic.setHasFixedSize(true)
        binding.recyclerMusic.setItemViewCacheSize(13)

        binding.recyclerMusic.layoutManager = LinearLayoutManager(this)
        musicAdapter = MusicAdapter(this, MusicListMA)
        binding.recyclerMusic.adapter = musicAdapter
        binding.totalSongs.text = "Total songs : "+musicAdapter.itemCount

    }

    @SuppressLint("Recycle", "Range")
    private fun getAllAudio():ArrayList<Music>{
        val tempList = ArrayList<Music>()
        val selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.DATE_ADDED,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.ALBUM_ID
        )
        val cursor = this.contentResolver.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            null,
            MediaStore.Audio.Media.DATE_ADDED + " DESC",
            null
        )

        if(cursor!=null){
            if(cursor.moveToFirst()){
                do{
                    val titleX = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                    val idX = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID))
                    val albumX = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM))
                    val artistX = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                    val timeX = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION))
                    val pathX = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                    val albumidX = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)).toString()
                    val uri = Uri.parse("content://media/external/audio/albumart")
                    val artUriX = Uri.withAppendedPath(uri, albumidX).toString()
                    val music = Music(idX, titleX, albumX, artistX, timeX, pathX, artUriX)
                    // Check if the file extension is mp3
                    if (pathX.endsWith(".mp3", ignoreCase = true)) {
                        val file = File(music.path)
                        if (file.exists()) {
                            tempList.add(music)
                        }
                    }
                }while (cursor.moveToNext())
                cursor.close()
            }
        }

        return tempList
    }

}