package com.adaptatorplayer.adaptator.inputs

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioManager
import com.adaptatorplayer.adaptator.AdaptatorInput

class VolumeInput(private val context: Context) : AdaptatorInput {
    override fun getCurrentValue(): Int {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        return (currentVolume * 10) / (maxVolume+1)
    }

    override fun onChange(listener: () -> Unit) {
        val filter = IntentFilter("android.media.VOLUME_CHANGED_ACTION")
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == "android.media.VOLUME_CHANGED_ACTION") {
                    listener()
                }
            }
        }
        context.registerReceiver(receiver, filter)
    }
}