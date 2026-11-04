package com.dimas.secrenocturne.service

import android.content.Context
import android.content.SharedPreferences
import android.os.Handler
import android.os.Looper
import android.service.wallpaper.WallpaperService
import android.view.SurfaceHolder
import com.dimas.secrenocturne.util.WallpaperHelper

class SecreLiveWallpaperService : WallpaperService() {

    override fun onCreateEngine(): Engine {
        return NocturneLiveEngine()
    }

    inner class NocturneLiveEngine : Engine(), SharedPreferences.OnSharedPreferenceChangeListener {
        private val handler = Handler(Looper.getMainLooper())
        private var isVisible = false
        private var surfaceWidth = 1080f
        private var surfaceHeight = 2400f
        private var currentStars = 18

        private val prefs by lazy {
            applicationContext.getSharedPreferences("secre_prefs", MODE_PRIVATE)
        }

        private val drawRunnable = object : Runnable {
            override fun run() {
                drawFrame()
                if (isVisible) {
                    handler.postDelayed(this, 80) // ~12 fps for ambient cosmic twinkling while saving battery
                }
            }
        }

        override fun onCreate(surfaceHolder: SurfaceHolder?) {
            super.onCreate(surfaceHolder)
            currentStars = prefs.getInt("stars_count", 18)
            prefs.registerOnSharedPreferenceChangeListener(this)
        }

        override fun onDestroy() {
            super.onDestroy()
            prefs.unregisterOnSharedPreferenceChangeListener(this)
            handler.removeCallbacks(drawRunnable)
        }

        override fun onVisibilityChanged(visible: Boolean) {
            isVisible = visible
            if (visible) {
                currentStars = prefs.getInt("stars_count", 18)
                handler.post(drawRunnable)
            } else {
                handler.removeCallbacks(drawRunnable)
            }
        }

        override fun onSurfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
            super.onSurfaceChanged(holder, format, width, height)
            surfaceWidth = width.toFloat()
            surfaceHeight = height.toFloat()
            drawFrame()
        }

        override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
            if (key == "stars_count") {
                currentStars = prefs.getInt("stars_count", 18)
                drawFrame()
            }
        }

        private fun drawFrame() {
            val holder = surfaceHolder ?: return
            val canvas = holder.lockCanvas() ?: return
            try {
                WallpaperHelper.drawConstellation(
                    canvas = canvas,
                    width = surfaceWidth,
                    height = surfaceHeight,
                    starsCount = currentStars,
                    timeMs = System.currentTimeMillis()
                )
            } finally {
                holder.unlockCanvasAndPost(canvas)
            }
        }
    }
}
