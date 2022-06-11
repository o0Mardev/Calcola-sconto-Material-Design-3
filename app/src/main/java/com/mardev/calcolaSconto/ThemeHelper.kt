package com.mardev.calcolaSconto

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.util.Log
import androidx.preference.PreferenceManager

object ThemeHelper {


    private fun isNightMode(context: Context): Boolean {
        return context.resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
    }


    fun nightModeChoice(context: Context): String? {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString("modTema", "nightModeFollowSystem")
    }

    fun recreate(context: Context, activity: Activity) {
        var flag = true
        if (isNightMode(context) && nightModeChoice(context) == "nightModeOn") {
            Log.d("THEME_HELPER", "Non c'è bisogno di aggiornare")
            flag = false
        }

        if (!isNightMode(context) && nightModeChoice(context) == "nightModeOff") {
            Log.d("THEME_HELPER", "Non c'è bisogno di aggiornare")
            flag = false
        }
        if (flag) {
            val intent = activity.intent
            intent?.putExtra("TEMA_CAMBIATO", true)
            activity.finish()
            activity.startActivity(intent)
            activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

}