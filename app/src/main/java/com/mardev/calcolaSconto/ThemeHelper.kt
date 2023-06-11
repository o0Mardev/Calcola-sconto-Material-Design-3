package com.mardev.calcolaSconto

import android.app.Activity
import android.app.UiModeManager
import android.content.Context
import android.content.res.Configuration
import android.util.Log
import androidx.preference.PreferenceManager


object ThemeHelper {


    /**
     * Restituisce true se l' app si trova in modalità scura, altrimenti false
     */
    private fun isAppInNightMode(context: Context): Boolean {
        return context.resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
    }

    /**
     * Restituisce true se il sistema si trova in modalità scura, altrimenti false
     */
    private fun isSystemInNightMode(context: Context): Boolean{
        val uiModeManager: UiModeManager = context.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
        val mode: Int = uiModeManager.nightMode
        return mode == UiModeManager.MODE_NIGHT_YES
    }

    /**
     * Restituisce l' attuale modalità selezionata dall' utente come stringa:
     * "nightModeFollowSystem" (default)
     * "nightModeOn" (modalità scura selezionata)
     * "nightModeOff" (modalità chiara selezionata)
     */
    fun getNightModeChoice(context: Context): String? {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString("modTema", "nightModeFollowSystem")
    }

    /**
     * Gestisce i cambiamenti di tema aggiornando l' attività solo se necessario
     */
    fun onNightModeChange(context: Context, activity: Activity) {
        var flag = true
        val appCurrentNightMode = isAppInNightMode(context)
        val systemCurrentNightMode = isSystemInNightMode(context)
        val nightModeChoice = getNightModeChoice(context)

        if (appCurrentNightMode && nightModeChoice == "nightModeOn") {
            Log.d("THEME_HELPER", "Non c'è bisogno di aggiornare")
            flag = false
        }

        if (!appCurrentNightMode && nightModeChoice == "nightModeOff") {
            Log.d("THEME_HELPER", "Non c'è bisogno di aggiornare")
            flag = false
        }

        if (appCurrentNightMode && systemCurrentNightMode && nightModeChoice == "nightModeFollowSystem"){
            Log.d("THEME_HELPER", "AppInNightMode && SystemInNightMode")
            flag = false
        }

        if (!appCurrentNightMode && !systemCurrentNightMode && nightModeChoice == "nightModeFollowSystem"){
            Log.d("THEME_HELPER", "AppInNightMode && SystemInNightMode")
            flag = false
        }

        if (flag) {
            val intent = activity.intent
            intent.putExtra("TEMA_CAMBIATO", true)//Valore che viene inviato a MainActivity (vedi onResume())
            activity.finish()
            activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            activity.startActivity(intent)
        }
    }

}