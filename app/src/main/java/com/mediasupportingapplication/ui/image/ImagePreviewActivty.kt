package com.mediasupportingapplication.ui.image

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.mediasupportingapplication.R
import kotlinx.android.synthetic.main.activity_media_preview.*

class ImagePreviewActivty : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_preview)
        initView()
        actionListner()
    }

    private fun initView() {

    }

    private fun actionListner() {
      //  println("Gallary Image-- 1--" + intent.getStringExtra("imageInUri")!!)
      //  textPath.text=intent.getStringExtra("imageInUri")!!
        Glide.with(this)
            .load(intent.getStringExtra("imageInUri")!!.toUri())
            .into(imagePrev)

    }
}