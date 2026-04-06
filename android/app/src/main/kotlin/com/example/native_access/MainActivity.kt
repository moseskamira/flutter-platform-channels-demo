package com.example.native_access

import android.content.Context
import android.database.Cursor
import android.media.RingtoneManager
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity : FlutterActivity() {

    private val CHANNEL = "flutter_channel"

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        val ringtoneService = RingtoneService(this)

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL)
            .setMethodCallHandler { call, result ->
                when (call.method) {
                    "getRingTones" -> {
                        result.success(ringtoneService.getRingTones())

                    }
                    "handleRingTone" -> {
                        try {
                            val ringtoneName = call.argument<String>("name")
                            if (ringtoneName == null) {
                                result.error("NULL_NAME", "Ringtone name is null", null)
                                return@setMethodCallHandler
                            }
                            ringtoneService.saveSelectedRingtone(ringtoneName)
                            result.success("Received: $ringtoneName")
                        } catch (e: Exception) {
                            result.error("ERROR", e.message, null)
                        }
                    }

                }
            }
    }

    private fun getRingTones(context: Context): List<String> {
        val manager = RingtoneManager(context)
        manager.setType(RingtoneManager.TYPE_RINGTONE)
        val cursor: Cursor = manager.cursor
        val list: MutableList<String> = mutableListOf()
        while (cursor.moveToNext()) {
            val notificationTitle: String = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX)
            list.add(notificationTitle)
        }
        return list

    }

    private fun handleSelectedRingtone(name: String) {
        val prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        prefs.edit()
            .putString("selected_ringtone", name)
            .apply()
        println("Saved Ringtone: $name")
    }


}