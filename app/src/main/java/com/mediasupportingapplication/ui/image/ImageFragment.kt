package com.mediasupportingapplication.ui.image

import android.Manifest
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.mediasupportingapplication.BottomSheetActivity
import com.mediasupportingapplication.R
import kotlinx.android.synthetic.main.fragment_image.*
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.jvm.Throws

class ImageFragment : Fragment() {
    private val REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA
    )
    private lateinit var imageViewModel: ImageViewModel
    val PERMISSION_REQUEST_CODE_GALLARY = 101
    val PERMISSION_REQUEST_CODE_CAMERA = 102
    var activity: BottomSheetActivity? = null
    lateinit var currentPhotoPath: String
    var photoFile: File? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /*  imageViewModel =
              ViewModelProvider(this).get(ImageViewModel::class.java)*/
        val root = inflater.inflate(R.layout.fragment_image, container, false)
        return root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        actionListner()
    }

    private fun initView() {

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun actionListner() {
        btnonClickMpGallary.setOnClickListener {

            if (allPermissionsGranted()) {
                // You can use the API that requires the permission.
                val gallery =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                startActivityForResult(gallery, PERMISSION_REQUEST_CODE_GALLARY)
            } else {
                // You can directly ask for the permission.
                requestGallaryPermission()
            }

        }

        btnonClickCamera.setOnClickListener {

            if (allPermissionsGranted()) {
                /*  // You can use the API that requires the permission.
                  val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                  try {
                      startActivityForResult(takePictureIntent, PERMISSION_REQUEST_CODE_CAMERA)
                  } catch (e: ActivityNotFoundException) {
                      // display error state to the user
                  }*/
                dispatchTakePictureIntent()
            } else {
                // You can directly ask for the permission.
                requestCameraPermission()
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == PERMISSION_REQUEST_CODE_GALLARY) {
            var intent = Intent(requireActivity(), ImagePreviewActivty::class.java)
            println("Gallary Image--" + data?.data)
            println("Gallary Image--" + data?.data!!.path)
            var imageInUri = data?.data.toString()
            intent.putExtra("imageInUri", imageInUri!!)
            startActivity(intent)
        } else if (resultCode == AppCompatActivity.RESULT_OK && requestCode == PERMISSION_REQUEST_CODE_CAMERA) {

            println("Camera Image--3--" + photoFile!!.path)
            val takenImage = BitmapFactory.decodeFile(photoFile!!.path)
            println("Camera Image--" + takenImage)
            println("Camera Image--1--" + bitmapToFile(takenImage))
            println("Camera Image--2--" + takenImage)
            var intent = Intent(requireActivity(), ImagePreviewActivty::class.java)
           var imageInUri = photoFile!!.path.toString()
            intent.putExtra("imageInUri",imageInUri)
            startActivity(intent)
        }
    }

    fun rotationImage(filePath: String): Bitmap {
        var exif = ExifInterface(filePath)
        val orientation =
            exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
        println("orientation--" + orientation)

        var angle = 0
        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
            angle = 90
        } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
            angle = 180
        } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
            angle = 270
        }

        val mat = Matrix()
        mat.postRotate(angle.toFloat())
        val bmp = BitmapFactory.decodeStream(FileInputStream(filePath), null, null)
        var correctBmp = Bitmap.createBitmap(bmp!!, 0, 0, bmp!!.width, bmp!!.height, mat, true)
        return correctBmp
    }

    private fun dispatchTakePictureIntent() {
         Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
             // Ensure that there's a camera activity to handle the intent
             takePictureIntent.resolveActivity(requireActivity()!!.packageManager)?.also {
                 // Create the File where the photo should go
                 photoFile= try {
                     createImageFile()
                 } catch (ex: IOException) {
                     // Error occurred while creating the File
                     null
                 }
                 // Continue only if the File was successfully created
                 photoFile?.also {
                     val photoURI: Uri = FileProvider.getUriForFile(
                         requireActivity(),
                         "com.mediasupportingapplication.fileprovider",
                         it
                     )
                     takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                     startActivityForResult(takePictureIntent, PERMISSION_REQUEST_CODE_CAMERA)
                 }
             }
         }
    }

    // Method to save an bitmap to a file
    private fun bitmapToFile(bitmap: Bitmap): String {
        // Get the context wrapper
        val wrapper = ContextWrapper(requireActivity())

        // Initialize a new file instance to save bitmap object
        var file = wrapper.getDir("Images", Context.MODE_PRIVATE)
        file = File(file, "${UUID.randomUUID()}.jpg")

        try {
            // Compress the bitmap and save in jpg format
            val stream: OutputStream = FileOutputStream(file)
            val mat = Matrix()
            mat.postRotate(0.toFloat())
           // Bitmap.createBitmap(bitmap!!, 0, 0, bitmap!!.width, bitmap!!.height,mat, true)
             bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        // Return the saved bitmap uri
        return file.path
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
                REQUIRED_PERMISSIONS,
                PERMISSION_REQUEST_CODE_CAMERA
            )
        } else {
            // layout.showSnackbar(R.string.camera_permission_not_available, Snackbar.LENGTH_SHORT)

            // Request the permission. The result will be received in onRequestPermissionResult().
            requestPermissions(
                REQUIRED_PERMISSIONS,
                PERMISSION_REQUEST_CODE_CAMERA
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestGallaryPermission() {
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
                REQUIRED_PERMISSIONS,
                PERMISSION_REQUEST_CODE_GALLARY
            )
        } else {
            // layout.showSnackbar(R.string.camera_permission_not_available, Snackbar.LENGTH_SHORT)

            // Request the permission. The result will be received in onRequestPermissionResult().
            requestPermissions(
                REQUIRED_PERMISSIONS,
                PERMISSION_REQUEST_CODE_GALLARY
            )
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            requireActivity(), it
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_REQUEST_CODE_GALLARY -> {
                // If request is cancelled, the result arrays are empty.
                if (allPermissionsGranted()) {
                    val gallery =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                    startActivityForResult(gallery, PERMISSION_REQUEST_CODE_GALLARY)
                } else {
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                    Toast.makeText(
                        requireActivity(),
                        "Permissions not granted by the user.",
                        Toast.LENGTH_SHORT
                    ).show()
                    requireActivity()!!.finish()
                }
                return
            }
            PERMISSION_REQUEST_CODE_CAMERA -> {
                // If request is cancelled, the result arrays are empty.
                if (allPermissionsGranted()) {
                    /*   val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                       try {
                           startActivityForResult(takePictureIntent, PERMISSION_REQUEST_CODE_CAMERA)
                       } catch (e: ActivityNotFoundException) {
                           // display error state to the user
                       }*/
                    dispatchTakePictureIntent()
                } else {
                    // Explain to the user that the feature is unavailable because
                    // the features requires a permission that the user has denied.
                    // At the same time, respect the user's decision. Don't link to
                    // system settings in an effort to convince the user to change
                    // their decision.
                    Toast.makeText(
                        requireActivity(),
                        "Permissions not granted by the user.",
                        Toast.LENGTH_SHORT
                    ).show()
                    requireActivity()!!.finish()
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

    @Throws(IOException::class)
    private fun createImageFile(): File {

        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File =
            requireActivity()!!.getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
          //  currentPhotoPath = absolutePath
        }
    }
}