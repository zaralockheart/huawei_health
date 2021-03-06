package com.huawei.hms.huawei_health

import android.app.Activity
import androidx.annotation.NonNull
import com.huawei.hms.huawei_health.scenes.HmsMethodHandler
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.PluginRegistry.Registrar


/** HuaweiHealthPlugin */
class HuaweiHealthPlugin : FlutterPlugin, ActivityAware {

    private var hmsMethodHandler: HmsMethodHandler? = null

    private var methodChannel: MethodChannel? = null

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        startListening(flutterPluginBinding.binaryMessenger)
    }


    companion object {
        @JvmStatic
        fun registerWith(registrar: Registrar) {
            val plugin = HuaweiHealthPlugin()
            plugin.startListening(registrar.messenger())
            if (registrar.activeContext() is Activity) {
                plugin.startListeningToActivity(
                        registrar.activity()
                )
            }
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        stopListening()
    }

    override fun onDetachedFromActivity() {
        stopListeningToActivity()
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
    }

    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        startListeningToActivity(binding.activity)
    }

    override fun onDetachedFromActivityForConfigChanges() {
    }

    private fun startListening(messenger: BinaryMessenger) {
        methodChannel = MethodChannel(
                messenger,
                "huawei_health")
        hmsMethodHandler = HmsMethodHandler()
        methodChannel?.setMethodCallHandler(hmsMethodHandler)
    }

    private fun startListeningToActivity(
            activity: Activity
    ) {
        if (hmsMethodHandler != null) {
            hmsMethodHandler?.setActivity(activity)
        }
    }

    private fun stopListeningToActivity() {
        if (hmsMethodHandler != null) {
            hmsMethodHandler?.setActivity(null)
        }
    }

    private fun stopListening() {
        methodChannel?.setMethodCallHandler(null)
        methodChannel = null
        hmsMethodHandler = null
    }
}
