package com.example.videoeditorv2

import android.app.Activity
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import com.google.android.material.slider.RangeSlider

class TrimActivity : AppCompatActivity() {
    val GALLARY = 1
    var videoView: VideoView? = null
    var textViewLeft: TextView?= null
    var textViewRight: TextView?= null
    var seekBar: RangeSlider?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trim)

        videoView = findViewById<VideoView>(R.id.vv2)
        textViewLeft = findViewById(R.id.tvLeft) as TextView?
        textViewRight = findViewById(R.id.tvRight) as TextView?
        seekBar = findViewById(R.id.seekbar) as RangeSlider?

        val galleryIntent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI
            )
        startActivityForResult(galleryIntent, 1)



    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val contentURI = data!!.data
        if (resultCode == Activity.RESULT_CANCELED) {
            Log.d("what", "cancle")
            return
        }
        if (requestCode == GALLARY) {
            Log.d("what", "gale")
            val mediaController = MediaController(this)
            mediaController.setAnchorView(videoView!!)
            videoView!!.setMediaController(mediaController)
            videoView!!.setVideoURI(contentURI)

            val mediaPlayer = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MOVIE)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )
                setDataSource(applicationContext, Uri.parse(contentURI.toString()))
                prepare()
            }
            var duration:String = getTime(mediaPlayer.duration).toString()
//            seekBar!!.valueTo= getTime(mediaPlayer.duration)!!.toFloat()
            textViewRight!!.setText(duration)

            videoView!!.requestFocus()
            videoView!!.start()


        }
    }
    fun getTime(m: Int): String? {
        var finalString:String="0"
        var minutesString:String="00"
        var secondsString:String="00"

        var hour = m / (1000*60*60)
        var minutes = (m % (1000*60*60)) / (1000*60)
        var seconds = (m % (1000*60*60)) % (1000*60) / 1000

        if(hour > 0){
            if (hour > 9){
                finalString = hour.toString()
            }else{
                finalString = "0" + hour.toString()
            }
        }
        if (minutes > 0){
            if (minutes > 9){
                minutesString = minutes.toString()
            }else{
                minutesString = "0" + minutes.toString()
            }
        }

        if (seconds > 0){
            if (seconds > 9){
                secondsString = seconds.toString()
            }else{
                secondsString = "0" + seconds.toString()
            }
        }


        finalString = finalString + ":" +minutesString + ":" + secondsString

        return finalString
    }

//    fun getTimeF(m: Int): Float{
//
//
//
//        return
//    }


}