package com.example.native_access

import android.content.Context
import android.database.Cursor
import android.media.RingtoneManager

class RingtoneService(private val context: Context) {

    fun getRingTones(): List<String> {
        val manager = RingtoneManager(context)
        manager.setType(RingtoneManager.TYPE_RINGTONE)
        val cursor: Cursor = manager.cursor
        val list = mutableListOf<String>()
        while (cursor.moveToNext()) {
            val title = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX)
            list.add(title)
        }
        cursor.close()
        return list
    }

    fun saveSelectedRingtone(name: String) {
        val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        prefs.edit()
            .putString("selected_ringtone", name)
            .apply()

        println("Saved Ringtone: $name")
    }
}