package com.dimas.secrenocturne.util

import android.app.WallpaperManager
import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.Color as AndroidColor
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import kotlin.math.sin

enum class WallpaperTarget {
    LOCKSCREEN,
    HOMESCREEN,
    BOTH
}

data class StarNode(
    val normX: Float,
    val normY: Float,
    val isAnchor: Boolean = false
)

object ConstellationData {
    const val TOTAL_STARS = 25

    // 25 Star Points representing Rasi Aquila Noctis (The Celestial Night Hawk)
    val starNodes = listOf(
        // Paruh & Kepala (0..3)
        StarNode(0.18f, 0.68f),
        StarNode(0.24f, 0.74f),
        StarNode(0.28f, 0.62f),
        StarNode(0.32f, 0.50f),

        // Sayap Kiri & Bulu (4..9)
        StarNode(0.24f, 0.38f),
        StarNode(0.17f, 0.28f),
        StarNode(0.11f, 0.19f),
        StarNode(0.21f, 0.17f),
        StarNode(0.29f, 0.23f),
        StarNode(0.35f, 0.33f),

        // Jantung Rasi / Altair Noctis Anchor (10..12)
        StarNode(0.46f, 0.48f, isAnchor = true), // Bintang Pusat Altair
        StarNode(0.42f, 0.58f),
        StarNode(0.50f, 0.38f),

        // Sayap Kanan & Rentangan (13..18)
        StarNode(0.58f, 0.30f),
        StarNode(0.67f, 0.22f),
        StarNode(0.78f, 0.16f),
        StarNode(0.85f, 0.22f),
        StarNode(0.81f, 0.32f),
        StarNode(0.71f, 0.39f),

        // Tubuh, Tulang Punggung & Ekor (19..24)
        StarNode(0.61f, 0.46f),
        StarNode(0.55f, 0.60f),
        StarNode(0.63f, 0.68f),
        StarNode(0.72f, 0.75f),
        StarNode(0.80f, 0.68f),
        StarNode(0.84f, 0.56f, isAnchor = true) // Bintang Penuntun Fajar
    )

    // Garis Penghubung Rasi
    val connections = listOf(
        0 to 1, 1 to 2, 2 to 3,
        3 to 4, 4 to 5, 5 to 6, 6 to 7, 7 to 8, 8 to 9, 9 to 4,
        4 to 10, 9 to 10, 3 to 11, 11 to 10,
        10 to 12, 12 to 13, 13 to 14, 14 to 15, 15 to 16, 16 to 17, 17 to 18, 18 to 13,
        12 to 19, 19 to 18,
        10 to 20, 11 to 20, 20 to 21, 21 to 22, 22 to 23, 23 to 24, 21 to 24
    )
}

object WallpaperHelper {
    private const val TAG = "WallpaperHelper"
    const val PREFS_NAME = "secre_wallpaper_prefs"
    const val KEY_AUTO_SYNC = "wallpaper_auto_sync"
    const val KEY_TARGET = "wallpaper_target"
    const val KEY_LAST_STARS = "wallpaper_last_stars"

    fun isAutoSyncEnabled(context: Context): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(KEY_AUTO_SYNC, false)
    }

    fun setAutoSyncEnabled(context: Context, enabled: Boolean, target: WallpaperTarget) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit()
            .putBoolean(KEY_AUTO_SYNC, enabled)
            .putString(KEY_TARGET, target.name)
            .apply()
        Log.i(TAG, "Wallpaper auto-sync set to $enabled with target $target")
    }

    fun getSavedWallpaperTarget(context: Context): WallpaperTarget {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val targetName = prefs.getString(KEY_TARGET, WallpaperTarget.BOTH.name)
        return try {
            WallpaperTarget.valueOf(targetName ?: WallpaperTarget.BOTH.name)
        } catch (e: Exception) {
            WallpaperTarget.BOTH
        }
    }

    fun drawConstellation(
        canvas: Canvas,
        width: Float,
        height: Float,
        starsCount: Int,
        timeMs: Long = 0L
    ) {
        // 1. OLED Pure Cosmic Black
        val bgPaint = Paint().apply {
            color = AndroidColor.parseColor("#030508")
            style = Paint.Style.FILL
        }
        canvas.drawRect(0f, 0f, width, height, bgPaint)

        // 2. Cosmic Dust Stars Grid (Ambient Twinkling)
        val dotPaint = Paint().apply {
            isAntiAlias = true
            color = AndroidColor.parseColor("#F6C85F")
            style = Paint.Style.FILL
        }
        val rows = 22
        val cols = 12
        val stepX = width / cols
        val stepY = height / rows
        for (c in 1 until cols) {
            for (r in 1 until rows) {
                val twinkle = if (timeMs > 0L) {
                    ((sin((timeMs / 400.0) + (c * 3) + (r * 2)) + 1.0) * 12).toInt()
                } else 0
                val baseAlpha = if ((c + r) % 4 == 0) 38 else if ((c * r) % 3 == 0) 24 else 12
                dotPaint.alpha = (baseAlpha + twinkle).coerceIn(8, 70)
                canvas.drawCircle(c * stepX, r * stepY, 2.0f, dotPaint)
            }
        }

        // 3. Coordinate calculation (centered in 48% height span)
        val centerY = height * 0.46f
        val spanH = height * 0.32f
        val points = ConstellationData.starNodes.map { node ->
            val px = node.normX * width
            val py = centerY - spanH * 0.32f + node.normY * spanH
            Pair(px, py)
        }

        // 4. Connecting Lines
        val litLinePaint = Paint().apply {
            isAntiAlias = true
            color = AndroidColor.parseColor("#F6C85F")
            alpha = 135
            strokeWidth = 3.6f
            style = Paint.Style.STROKE
        }
        val unlitLinePaint = Paint().apply {
            isAntiAlias = true
            color = AndroidColor.parseColor("#384259")
            alpha = 45
            strokeWidth = 1.8f
            style = Paint.Style.STROKE
        }

        ConstellationData.connections.forEach { (s, e) ->
            if (s < points.size && e < points.size) {
                val isLit = (s < starsCount) && (e < starsCount)
                canvas.drawLine(
                    points[s].first, points[s].second,
                    points[e].first, points[e].second,
                    if (isLit) litLinePaint else unlitLinePaint
                )
            }
        }

        // 5. Stars (Lit vs Dormant)
        val litGlowPaint = Paint().apply {
            isAntiAlias = true
            style = Paint.Style.FILL
        }
        val unlitStarPaint = Paint().apply {
            isAntiAlias = true
            color = AndroidColor.parseColor("#475569")
            alpha = 60
            style = Paint.Style.STROKE
            strokeWidth = 2.2f
        }
        val unlitCorePaint = Paint().apply {
            isAntiAlias = true
            color = AndroidColor.parseColor("#64748B")
            alpha = 80
            style = Paint.Style.FILL
        }

        points.forEachIndexed { idx, (px, py) ->
            val isUnlocked = idx < starsCount
            val isAnchor = ConstellationData.starNodes[idx].isAnchor

            if (isUnlocked) {
                if (isAnchor) {
                    litGlowPaint.color = AndroidColor.parseColor("#F6C85F")
                    litGlowPaint.alpha = 45
                    canvas.drawCircle(px, py, 55f, litGlowPaint)
                    litGlowPaint.alpha = 110
                    canvas.drawCircle(px, py, 32f, litGlowPaint)
                    litGlowPaint.color = AndroidColor.WHITE
                    litGlowPaint.alpha = 255
                    canvas.drawCircle(px, py, 14f, litGlowPaint)
                } else {
                    litGlowPaint.color = AndroidColor.parseColor("#F6C85F")
                    litGlowPaint.alpha = 75
                    canvas.drawCircle(px, py, 22f, litGlowPaint)
                    litGlowPaint.color = AndroidColor.parseColor("#FFE29A")
                    litGlowPaint.alpha = 255
                    canvas.drawCircle(px, py, 8.5f, litGlowPaint)
                }
            } else {
                // Dormant Star awaiting sealing
                canvas.drawCircle(px, py, if (isAnchor) 14f else 8f, unlitStarPaint)
                canvas.drawCircle(px, py, if (isAnchor) 4.5f else 2.5f, unlitCorePaint)
            }
        }

        // 6. Typography
        val titlePaint = Paint().apply {
            isAntiAlias = true
            color = AndroidColor.parseColor("#F6C85F")
            textSize = 44f
            letterSpacing = 0.22f
            textAlign = Paint.Align.CENTER
            typeface = Typeface.create(Typeface.SERIF, Typeface.BOLD)
        }
        canvas.drawText("✦ SECRE NOCTURNE ✦", width / 2f, height * 0.77f, titlePaint)

        val clampedStars = starsCount.coerceIn(0, ConstellationData.TOTAL_STARS)
        val subPaint = Paint().apply {
            isAntiAlias = true
            color = AndroidColor.parseColor("#7CD4FD")
            textSize = 28f
            letterSpacing = 0.14f
            textAlign = Paint.Align.CENTER
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
        }
        canvas.drawText("RASI AQUILA NOCTIS  •  $clampedStars / 25 BINTANG", width / 2f, height * 0.80f, subPaint)

        val quotePaint = Paint().apply {
            isAntiAlias = true
            color = AndroidColor.parseColor("#64748B")
            textSize = 22f
            letterSpacing = 0.08f
            textAlign = Paint.Align.CENTER
            typeface = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL)
        }
        canvas.drawText("Menyegel Kebaikan & Disiplin Diri di Keheningan Fajar", width / 2f, height * 0.83f, quotePaint)
    }

    fun generateOledWallpaperBitmap(
        starsCount: Int,
        width: Int = 1080,
        height: Int = 2400
    ): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawConstellation(canvas, width.toFloat(), height.toFloat(), starsCount)
        return bitmap
    }

    suspend fun applyWallpaper(
        context: Context,
        starsCount: Int,
        target: WallpaperTarget,
        enableAutoSync: Boolean = true
    ): Boolean = withContext(Dispatchers.IO) {
        try {
            val wallpaperManager = WallpaperManager.getInstance(context)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && !wallpaperManager.isSetWallpaperAllowed) {
                Log.w(TAG, "Setting wallpaper is not allowed by device policy")
                return@withContext false
            }

            val metrics = context.resources.displayMetrics
            val screenW = if (metrics.widthPixels > 0) metrics.widthPixels else 1080
            val screenH = if (metrics.heightPixels > 0) metrics.heightPixels else 2400

            val bitmap = generateOledWallpaperBitmap(starsCount, width = screenW, height = screenH)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                val flag = when (target) {
                    WallpaperTarget.LOCKSCREEN -> WallpaperManager.FLAG_LOCK
                    WallpaperTarget.HOMESCREEN -> WallpaperManager.FLAG_SYSTEM
                    WallpaperTarget.BOTH -> WallpaperManager.FLAG_LOCK or WallpaperManager.FLAG_SYSTEM
                }
                wallpaperManager.setBitmap(bitmap, null, true, flag)
            } else {
                wallpaperManager.setBitmap(bitmap)
            }

            if (enableAutoSync) {
                setAutoSyncEnabled(context, true, target)
            }

            // Save last synced star count
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
                .putInt(KEY_LAST_STARS, starsCount)
                .apply()

            Log.i(TAG, "Successfully applied wallpaper target: $target with $starsCount stars. Auto-sync: $enableAutoSync")
            true
        } catch (e: Exception) {
            Log.e(TAG, "Failed to apply wallpaper", e)
            false
        }
    }

    suspend fun syncWallpaperIfEnabled(context: Context, starsCount: Int): Boolean = withContext(Dispatchers.IO) {
        if (!isAutoSyncEnabled(context)) {
            Log.d(TAG, "Auto-sync disabled, skipping wallpaper update")
            return@withContext false
        }
        val target = getSavedWallpaperTarget(context)
        Log.i(TAG, "Auto-sync triggered! Updating wallpaper with $starsCount stars on $target")
        applyWallpaper(context, starsCount, target, enableAutoSync = true)
    }

    suspend fun saveWallpaperToGallery(context: Context, starsCount: Int): Boolean = withContext(Dispatchers.IO) {
        try {
            val metrics = context.resources.displayMetrics
            val screenW = if (metrics.widthPixels > 0) metrics.widthPixels else 1080
            val screenH = if (metrics.heightPixels > 0) metrics.heightPixels else 2400
            val bitmap = generateOledWallpaperBitmap(starsCount, width = screenW, height = screenH)
            val fileName = "Secre_Nocturne_${starsCount}Stars_${System.currentTimeMillis()}.png"

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/SecreNocturne")
                }
                val uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                if (uri != null) {
                    context.contentResolver.openOutputStream(uri)?.use { stream ->
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                    }
                    true
                } else {
                    false
                }
            } else {
                @Suppress("DEPRECATION")
                val picturesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                val appDir = File(picturesDir, "SecreNocturne")
                if (!appDir.exists()) appDir.mkdirs()
                val file = File(appDir, fileName)
                FileOutputStream(file).use { stream ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                }
                true
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to save wallpaper to gallery", e)
            false
        }
    }
}
