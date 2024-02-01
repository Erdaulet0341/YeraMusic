package com.yerdauletapps.yeramusic

import java.util.concurrent.TimeUnit

data class Music(
    val id: String,
    val title: String,
    val album: String,
    val artist: String,
    val time: Long = 0,
    val path: String,
    val artUri: String
)

fun formatTime(time:Long):String{
    val munites = TimeUnit.MINUTES.convert(time, TimeUnit.MILLISECONDS)
    val seconds =
        (TimeUnit.SECONDS.convert(time, TimeUnit.MILLISECONDS) -
                munites * TimeUnit.SECONDS.convert(
            1,
            TimeUnit.MINUTES
        ))

    return String.format("%02d:%02d", munites, seconds)
}