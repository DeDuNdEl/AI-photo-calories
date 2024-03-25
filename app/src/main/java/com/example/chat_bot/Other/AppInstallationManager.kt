package com.example.chat_bot.Other

import android.content.Context
import android.content.SharedPreferences
import java.util.Date

class AppInstallationManager(private val context: Context) {

    private val PREF_NAME = "AppInstallationPrefs"
    private val KEY_INSTALLATION_DATE = "installationDate"

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun saveInstallationDate() {
        val currentDate = Date().time
        sharedPreferences.edit().putLong(KEY_INSTALLATION_DATE, currentDate).apply()
    }



    fun getDaysSinceInstallation(): Long {
        val installationDate = sharedPreferences.getLong(KEY_INSTALLATION_DATE, 0)
        val currentDate = Date().time
        val differenceInMillis = currentDate - installationDate
        return differenceInMillis / (1000 * 60 * 60 * 24)
    }
}