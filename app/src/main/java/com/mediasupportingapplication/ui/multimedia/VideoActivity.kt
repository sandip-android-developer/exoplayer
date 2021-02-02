package com.mediasupportingapplication.ui.multimedia

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mediasupportingapplication.R
import com.mediasupportingapplication.VidepPlayerActivity
import kotlinx.android.synthetic.main.activity_video.*

class VideoActivity : AppCompatActivity() {
    var media: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        initView()
        actionListner()
    }

    private fun initView() {

    }

    private fun actionListner() {
        btnonClickMp4.setOnClickListener {
            var intent = Intent(this, VidepPlayerActivity::class.java)
            media =
                " https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4"
            intent.putExtra("media", media)
            intent.putExtra("mediaType", "mp4")
            startActivity(intent)
        }
        btnonClickAVI.setOnClickListener {
            var intent = Intent(this, VidepPlayerActivity::class.java)
            media =
                " https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-avi-file.avi"
            intent.putExtra("mediaType", "avi")
            intent.putExtra("media", media)
            startActivity(intent)
        }
        btnonClickMOV.setOnClickListener {
            var intent = Intent(this, VidepPlayerActivity::class.java)
            media =
                "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mov-file.mov"
            intent.putExtra("media", media)
            intent.putExtra("mediaType", "mov")
            startActivity(intent)
        }
        btnonClickMPG.setOnClickListener {
            var intent = Intent(this, VidepPlayerActivity::class.java)
            media =
                "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mpg-file.mpg"
            intent.putExtra("mediaType", "mpg")
            intent.putExtra("media", media)
            startActivity(intent)
        }

        btnonClickWMV.setOnClickListener {
            var intent = Intent(this, VidepPlayerActivity::class.java)
            media =
                "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-wmv-file.wmv"
            intent.putExtra("mediaType", "wmv")
            intent.putExtra("media", media)
            startActivity(intent)
        }
        btnonClickFLV.setOnClickListener {
            var intent = Intent(this, VidepPlayerActivity::class.java)
            media =
                "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-flv-file.flv"
            intent.putExtra("mediaType", "flv")
            intent.putExtra("media", media)
            startActivity(intent)
        }
        btnonClickM4A.setOnClickListener {
            var intent = Intent(this, VidepPlayerActivity::class.java)
            media =
                "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mov-file.mov"
            intent.putExtra("mediaType", "mov")
            intent.putExtra("media", media)
            startActivity(intent)
        }
        btnonClickWEBM.setOnClickListener {
            var intent = Intent(this, VidepPlayerActivity::class.java)
            media =
                "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-webm-file.webm"
            intent.putExtra("mediaType", "webm")
            intent.putExtra("media", media)
            startActivity(intent)
        }

        btnonClickMKV.setOnClickListener {
            var intent = Intent(this, VidepPlayerActivity::class.java)
            media =
                "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mkv-file.mkv"
            intent.putExtra("mediaType", "mkv")
            intent.putExtra("media", media)
            startActivity(intent)
        }
        btnonClickOGV.setOnClickListener {
            var intent = Intent(this, VidepPlayerActivity::class.java)
            media =
                "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-ogv-file.ogv"
            intent.putExtra("mediaType", "ogv")
            intent.putExtra("media", media)
            startActivity(intent)
        }
        btnonClick3GP.setOnClickListener {
            var intent = Intent(this, VidepPlayerActivity::class.java)
            media =
                "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-3gp-file.3gp"
            intent.putExtra("mediaType", "3gp")
            intent.putExtra("media", media)
            startActivity(intent)
        }
        btnonClick3G2.setOnClickListener {
            var intent = Intent(this, VidepPlayerActivity::class.java)
            media =
                "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-3g2-file.3g2"
            intent.putExtra("mediaType", "3gp")
            intent.putExtra("media", media)
            startActivity(intent)
        }
    }
}