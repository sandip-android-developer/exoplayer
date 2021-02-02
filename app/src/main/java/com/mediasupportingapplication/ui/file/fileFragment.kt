package com.mediasupportingapplication.ui.file

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ContentResolver
import android.content.Intent
import android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION
import android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION
import android.content.pm.PackageManager
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.provider.OpenableColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.mediasupportingapplication.R
import kotlinx.android.synthetic.main.fragment_file.*
import java.io.*

class fileFragment : Fragment() {

    private val REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    private lateinit var fileViewModel: fileViewModel
    val PERMISSION_REQUEST_CODE_FILE: Int = 103
    private val TAG = "BottomSheetActivity"
    private val DOCUMENT_URI_ARGUMENT =
        "com.example.android.actionopendocument.args.DOCUMENT_URI_ARGUMENT"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /* fileViewModel =
             ViewModelProvider(this).get(fileViewModel::class.java)*/
        val root = inflater.inflate(R.layout.fragment_file, container, false)
        /* val textView: TextView = root.findViewById(R.id.text_notifications)
         fileViewModel.text.observe(viewLifecycleOwner, Observer {
             textView.text = it
         })*/
        return root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        actionListner()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun actionListner() {
        btnonClickForFile?.setOnClickListener {
            if (allPermissionsGranted()) {
                // You can use the API that requires the permission.
                openDocumentPicker()
            } else {
                // You can directly ask for the permission.
                requestFilePermission()
            }
        }
        buttonOpen?.setOnClickListener {
            openDocument(textFile.text.toString().trim().toUri())
        }
    }

    private fun initView() {
        /*  fileViewModel =
                   ViewModelProvider(this).get(fileViewModel::class.java)*/
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
            PERMISSION_REQUEST_CODE_FILE -> {
                // If request is cancelled, the result arrays are empty.
                if (allPermissionsGranted()) {
                    openDocumentPicker()
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

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestFilePermission() {
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
                PERMISSION_REQUEST_CODE_FILE
            )
        } else {
            // layout.showSnackbar(R.string.camera_permission_not_available, Snackbar.LENGTH_SHORT)

            // Request the permission. The result will be received in onRequestPermissionResult().
            requestPermissions(
                REQUIRED_PERMISSIONS,
                PERMISSION_REQUEST_CODE_FILE
            )
        }
    }

    private fun openDocumentPicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            /**
             * It's possible to limit the types of files by mime-type. Since this
             * app displays pages from a PDF file, we'll specify `application/pdf`
             * in `type`.
             * See [Intent.setType] for more details.
             */
            type = "application/*"

            /**
             * Because we'll want to use [ContentResolver.openFileDescriptor] to read
             * the data of whatever file is picked, we set [Intent.CATEGORY_OPENABLE]
             * to ensure this will succeed.
             */
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        startActivityForResult(intent, PERMISSION_REQUEST_CODE_FILE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)

        if (requestCode == PERMISSION_REQUEST_CODE_FILE && resultCode == Activity.RESULT_OK) {
            resultData?.data?.also { documentUri ->
                buttonOpen?.visibility = View.VISIBLE
                textFile.text = dumpImageMetaData(documentUri)
                println("File--" + dumpImageMetaData(documentUri))
            }
        }
    }

    fun dumpImageMetaData(uri: Uri): String {

        val returnCursor =
            requireActivity()!!.contentResolver.query(uri, null, null, null, null)
        /*
     * Get the column indexes of the data in the Cursor,
     *     * move to the first row in the Cursor, get the data,
     *     * and display it.
     * */

        val nameIndex = returnCursor!!.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        val sizeIndex = returnCursor!!.getColumnIndex(OpenableColumns.SIZE)
        returnCursor.moveToFirst()
        val name = returnCursor.getString(nameIndex)
        val size = java.lang.Long.toString(returnCursor.getLong(sizeIndex))
        val file = File(requireActivity().filesDir, name)
        try {
            val inputStream: InputStream? = requireActivity().contentResolver.openInputStream(uri)
            val outputStream = FileOutputStream(file)
            var read = 0
            val maxBufferSize = 25 * 1024 * 1024
            val bytesAvailable: Int = inputStream!!.available()

            //int bufferSize = 1024;
            //   val bufferSize = Math.min(bytesAvailable, size.toDouble())
            val buffers = ByteArray(size.toInt())
            while (inputStream.read(buffers).also({ read = it }) != -1) {
                outputStream.write(buffers, 0, read)
            }
            Log.e("File Size", "Size " + file.length())
            inputStream.close()
            outputStream.close()
            Log.e("File Path", "Path " + file.path)
            Log.e("File Size", "Size " + file.length())
        } catch (e: Exception) {
            Log.e("Exception", e.message!!)
        }
        return file.path
    }
    private fun openDocument(documentUri: Uri) {
        val builder: StrictMode.VmPolicy.Builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        builder.detectFileUriExposure()

        val docUri: Uri = FileProvider.getUriForFile(
            requireActivity()!!,
            "com.mediasupportingapplication.fileprovider",
            File(documentUri.toString())
        ) // same as defined in Manifest file in android:authorities="com.sample.example.provider"

        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(docUri, "application/pdf")
        try {
            intent.flags = FLAG_GRANT_READ_URI_PERMISSION or FLAG_GRANT_WRITE_URI_PERMISSION
            requireActivity()!!.startActivity(intent)

        } catch (e: ActivityNotFoundException) {
            //user does not have a pdf viewer installed
            Log.d(TAG, "shouldOverrideUrlLoading: " + e.getLocalizedMessage())
            Toast.makeText(requireContext()!!, "No application to open file", Toast.LENGTH_SHORT)
                .show()
        }
    }

}