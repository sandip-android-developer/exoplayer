package com.mediasupportingapplication.ui.multimedia

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mediasupportingapplication.R
import com.mediasupportingapplication.VidepPlayerActivity
import kotlinx.android.synthetic.main.activity_audio.*

class AudioActivity : AppCompatActivity() {
    var media: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio)
        initView()
    }

    private fun initView() {
        btnonClickMp3.setOnClickListener {
            var intent = Intent(this, VidepPlayerActivity::class.java)
            media =
                "https://www.learningcontainer.com/wp-content/uploads/2020/02/Kalimba.mp3"
            intent.putExtra("mediaType", "mp3")
            intent.putExtra("media", media)
            startActivity(intent)
        }
        btnonClickwav.setOnClickListener {
            var intent = Intent(this, VidepPlayerActivity::class.java)
            media =
                "https://www.learningcontainer.com/wp-content/uploads/2020/02/Kalimba-online-audio-converter.com_-1.wav"
            intent.putExtra("media", media)
            intent.putExtra("mediaType", "wav")
            startActivity(intent)
        }
        btnonClickflac.setOnClickListener {
            var intent = Intent(this, VidepPlayerActivity::class.java)
            media =
                "https://www.learningcontainer.com/wp-content/uploads/2020/02/Sample-FLAC-File.flac"
            intent.putExtra("mediaType", "flac")
            intent.putExtra("media", media)
            startActivity(intent)
        }

        btnonClickogg.setOnClickListener {
            var intent = Intent(this, VidepPlayerActivity::class.java)
            media =
                "https://www.learningcontainer.com/wp-content/uploads/2020/02/Sample-OGG-File.ogg"
            intent.putExtra("mediaType", "ogg")
            intent.putExtra("media", media)
            startActivity(intent)
        }
    }
}