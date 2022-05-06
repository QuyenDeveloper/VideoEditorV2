package com.example.videoeditorv2

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.MediaController
import android.widget.SeekBar
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var videoView: VideoView? = null
    val GALLERY = 1
    val CAMERA = 2
    var context:Context?=null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        videoView = findViewById(R.id.vv2) as VideoView?


        btVideoProject.setOnClickListener {
            val intent = Intent(this,TrimActivity::class.java)
            this.startActivity(intent)
//            val galleryIntent = Intent(
//                Intent.ACTION_PICK,
//                MediaStore.Video.Media.EXTERNAL_CONTENT_URI
//            )
//            startActivityForResult(galleryIntent, GALLERY)
        }
    }


    fun getPath(uri: Uri?): String? {
        val projection = arrayOf(MediaStore.Video.Media.DATA)
        val cursor = contentResolver.query(uri!!, projection, null, null, null)
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            val column_index = cursor!!
                .getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            cursor!!.moveToFirst()
            return cursor!!.getString(column_index)
        } else
            return null
    }
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)



//        intent.putExtra("requestCode",requestCode)
//        intent.putExtra("resultCode",resultCode)
//        intent.putExtra("contentURI",contentURI.toString())
//        context!!.startActivity(intent)

        if (resultCode == Activity.RESULT_CANCELED) {
            Log.d("what", "cancle")
            return
        }
        if (requestCode == GALLERY) {
            Log.d("what", "gale")
            if (data != null) {

                val contentURI = data!!.data

                val mediaController = MediaController(this)
                mediaController.setAnchorView(videoView!!)
                videoView!!.setMediaController(mediaController)

                videoView!!.setVideoURI(contentURI)
                videoView!!.requestFocus()
                videoView!!.start()

            }

        }
    }
}


