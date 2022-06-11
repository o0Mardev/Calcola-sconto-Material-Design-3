package com.mardev.calcolaSconto

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.preference.PreferenceManager

object VibratorHelper {

    private fun isVibrationOn(context: Context): Boolean {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getBoolean("vibra", false)
    }


    //Funzione per la vibrazione
    @Suppress("DEPRECATION")
    @JvmStatic
    fun vibra(context: Context, milliseconds: Long) {

        if (isVibrationOn(context)) {

            if (Build.VERSION.SDK_INT >= 31) {
                val vibratorManager =
                    context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                val vibrator = vibratorManager.defaultVibrator
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        milliseconds,
                        VibrationEffect.EFFECT_CLICK
                    )
                )
            } else { //Retrocompatibilità per Android API < 31
                val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                vibrator.vibrate(milliseconds)
            }
        }
    }

}