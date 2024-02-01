package com.yerdauletapps.yeramusic

data class Music(
    val id: String,
    val title: String,
    val album: String,
    val artist: String,
    val time: Long = 0,
    val path: String
)