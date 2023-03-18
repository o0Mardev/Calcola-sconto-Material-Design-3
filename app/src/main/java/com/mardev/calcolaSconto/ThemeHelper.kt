package com.mardev.calcolaSconto

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.util.Log
import android.view.View
import androidx.preference.PreferenceManager


object ThemeHelper {


    //Restituisce vero se l' app si trova in modalità scura, altrimenti falso
    private fun isNightMode(context: Context): Boolean {
        return context.resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
    }


    //Restituisce l' attuale modalità selezionata dall' utente:
        //nightModeFollowSystem (default)
        //nightModeOn (modalità scura selezionata)
        //nightModeOff (modalità chiara selezionata)
    fun nightModeChoice(context: Context): String? {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getString("modTema", "nightModeFollowSystem")
    }


    //Gestisce i cambiamenti di tema aggiornando l' attività solo se necessario
    fun onNightModeChange(context: Context, activity: Activity) {
        var flag = true
        val isCurrentNightMode = isNightMode(context)
        if (isCurrentNightMode && nightModeChoice(context) == "nightModeOn") {
            Log.d("THEME_HELPER", "Non c'è bisogno di aggiornare")
            flag = false
        }

        if (!isCurrentNightMode && nightModeChoice(context) == "nightModeOff") {
            Log.d("THEME_HELPER", "Non c'è bisogno di aggiornare")
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

    //Applica il tema Amoled (Work in Progress)
    fun applyAmoled(context: Context, view: View){
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        if (prefs.getBoolean("modAmoled", false) && isNightMode(context)){
            view.setBackgroundColor(Color.BLACK)
        }
    }

}