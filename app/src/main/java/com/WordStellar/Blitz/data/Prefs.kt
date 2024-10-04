package com.WordStellar.Blitz.data

import android.content.Context
import android.content.SharedPreferences
import com.WordStellar.Blitz.domain.levels

object Prefs {
    private lateinit var prefs: SharedPreferences
    private lateinit var sharedPrefs: android.content.SharedPreferences

    private const val KEY_START_STEP_COMPLETED = "StartStepCompleted"

    fun init(context: Context) {
        prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    }

    val level: Int
        get() = prefs.getInt("level", 1)


    fun passLevel() {
        if (level < levels.size) prefs.edit().putInt("level", level + 1).apply()
    }
    var music: Float
        get() = prefs.getFloat("music", 1f)
        set(value) {
            prefs.edit().putFloat("music", value).apply()
        }
    var sound: Float
        get() = prefs.getFloat("sound", 1f)
        set(value) {
            prefs.edit().putFloat("sound", value).apply()
        }
}