package com.mediasupportingapplication

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.mediasupportingapplication.ui.image.ImagePreviewActivty
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var media: String = ""
    val PERMISSION_REQUEST_CODE=101
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun initView() {

        btnonClickMpGallary.setOnClickListener {

         if ( ContextCompat.checkSelfPermission(
                 this,
                Manifest.permission.READ_EXTERNAL_STORAGE
             ) == PackageManager.PERMISSION_GRANTED) {
             // You can use the API that requires the permission.
             val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
             startActivityForResult(gallery, PERMISSION_REQUEST_CODE)


         } else {
                    // You can directly ask for the permission.
             requestCameraPermission()
                  /*  requestPermissions(
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA),
                        PERMISSION_REQUEST_CODE)*/
                }

        }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == PERMISSION_REQUEST_CODE) {
            var intent = Intent(this, ImagePreviewActivty::class.java)
            println("Gallary Image--" + data?.data)
            println("Gallary Image--" + data?.data!!.path)
            var imageInUri =data?.data.toString()
            intent.putExtra("imageInUri",imageInUri!!)
            startActivity(intent)
        }
    }

    /**
     * Requests the [android.Manifest.permission.CAMERA] permission.
     * If an additional rationale should be displayed, the user has to launch the request from
     * a SnackBar that includes additional information.
     */
    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestCameraPermission() {
        // Permission has not been granted and must be requested.
        if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // Display a SnackBar with a button to request the missing permission.
            /*layout.showSnackbar(R.string.camera_access_required,
                Snackbar.LENGTH_INDEFINITE, R.string.ok) {

            }
*/
            requestPermissions(
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA),
                PERMISSION_REQUEST_CODE)
        } else {
           // layout.showSnackbar(R.string.camera_permission_not_available, Snackbar.LENGTH_SHORT)

            // Request the permission. The result will be received in onRequestPermissionResult().
            requestPermissions(
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA),
                PERMISSION_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() &&
                            grantResults[0] == PackageManager.PERMISSION_GRANTED)
                ) {
                    // Permission is granted. Continue the action or workflow
                    // in your app.
                    val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                    startActivityForResult(gallery, PERMISSION_REQUEST_CODE)
                } else {
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }

}