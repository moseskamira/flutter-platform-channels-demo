package com.example.native_access

import android.content.Context
import android.database.Cursor
import android.media.RingtoneManager
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.EventChannel

class MainActivity : FlutterActivity() {

    private val CHANNEL = "ringtone_channel"

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        val ringtoneService = RingtoneService(this)

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL)
            .setMethodCallHandler { call, result ->
                when (call.method) {
                    "getRingTones" -> {
                        result.success(ringtoneService.getRingTonesForDisplay())
                        //result.success(getStudentsNames())

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

    private fun getStudentsNames(): List<String> {
        return listOf("Moses", "Kamira", "James", "Honest")
    }

    private fun handleSelectedRingtone(name: String) {
        val prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        prefs.edit()
            .putString("selected_ringtone", name)
            .apply()
        println("Saved Ringtone: $name")
    }


}