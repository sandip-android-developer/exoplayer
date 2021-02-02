package com.mediasupportingapplication

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import kotlinx.android.synthetic.main.activity_videoplayer.*
import java.util.*


class VidepPlayerActivity : AppCompatActivity() {
    var media: String = ""
    var mediaType: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_videoplayer)
        initView()
    }

    private fun initView() {
        media = intent.getStringExtra("media")!!
        textMediaType.text = "Media Type:" + intent.getStringExtra("mediaType")!!
        val player = SimpleExoPlayer.Builder(this).build()
        PlayerViewSData.setPlayer(player)
        // Build the media items.
        val rnd = Random()
        val color: Int = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
        clBackground.setBackgroundColor(color)
        val firstItem: MediaItem = MediaItem.fromUri(media)
        player.apply {
            addMediaItem(firstItem)
            prepare()
            play()

        }
    }
}