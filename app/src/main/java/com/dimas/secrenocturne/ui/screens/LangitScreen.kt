package com.dimas.secrenocturne.ui.screens

import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.dimas.secrenocturne.service.SecreLiveWallpaperService
import com.dimas.secrenocturne.ui.theme.LocalNocturnePalette
import com.dimas.secrenocturne.util.ConstellationData
import com.dimas.secrenocturne.util.WallpaperHelper
import com.dimas.secrenocturne.util.WallpaperTarget
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun LangitScreen(
    starsCount: Int = 18,
    lastStarTitle: String = "Bangun Pagi & Olahraga",
    lastStarTime: String = "⏱ Disegel 2 jam lalu",
    liveTime: String = "",
    onAddStar: (title: String, category: String, desc: String) -> Unit = { _, _, _ -> }
) {
    val palette = LocalNocturnePalette.current
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()

    var showClaimDialog by remember { mutableStateOf(false) }
    var selectedPresetIndex by remember { mutableIntStateOf(0) }
    var customNote by remember { mutableStateOf("") }
    var showSuccessToast by remember { mutableStateOf(false) }

    var showMoonPhaseDialog by remember { mutableStateOf(false) }
    var showConstellationDialog by remember { mutableStateOf(false) }
    var showWallpaperModal by remember { mutableStateOf(false) }
    var showTargetSelectionSheet by remember { mutableStateOf(false) }
    var previewMode by remember { mutableStateOf("lock") } // "lock" or "home"
    var isApplyingWallpaper by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val todayDateStr = remember {
        SimpleDateFormat("EEEE, d MMMM", Locale("id", "ID")).format(Date()).uppercase()
    }

    val presets = listOf(
        Triple("Bangun Fajar & Ritual Pagi", "Disiplin Diri", "Bangun fajar tepat waktu dan menyegel ritme pagi."),
        Triple("Membantu Teman & Empati", "Kebaikan Sesama", "Membantu rekan atau berbuat kebaikan dengan tulus."),
        Triple("Olahraga & Kebugaran Raga", "Kebugaran", "Menjaga stamina tubuh dengan olahraga atau peregangan."),
        Triple("Ketenangan & Sabar", "Ketenangan", "Menjaga ketenangan batin dalam menghadapi dinamika hari."),
        Triple("Fokus Kerja & Belajar", "Disiplin Diri", "Menyelesaikan tanggung jawab dengan fokus tanpa distraksi.")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(palette.background)
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Hero: Penjaga Langit Secre
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(palette.surface, RoundedCornerShape(24.dp))
                .border(1.dp, palette.borderSubtle, RoundedCornerShape(24.dp))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .background(palette.surfaceLow, CircleShape)
                    .border(1.dp, palette.gold.copy(alpha = 0.35f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "🐦‍⬛", fontSize = 26.sp)
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "● PENJAGA LANGIT SECRE",
                        color = palette.gold,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                    if (liveTime.isNotBlank()) {
                        Text(
                            text = "⏱ $liveTime",
                            color = palette.cyan,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "\"Langit malammu bersinar lebih terang hari ini,...\"",
                    color = palette.textPrimary,
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 19.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Keheningan membawa kedamaian yang tersegel.",
                    color = palette.textMuted,
                    fontSize = 11.sp
                )
            }
        }

        // Quick Sub-Pills
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .background(palette.surfaceLow, RoundedCornerShape(16.dp))
                    .border(1.dp, palette.borderSubtle, RoundedCornerShape(16.dp))
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "‹ $starsCount Bintang Tersegel",
                        color = palette.textPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = if (liveTime.isNotBlank()) "⏱ $liveTime • Sinkron" else "· Nov",
                        color = palette.cyan,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            // Clickable Moon Phase Badge
            Row(
                modifier = Modifier
                    .weight(1f)
                    .background(palette.surfaceLow, RoundedCornerShape(16.dp))
                    .border(1.dp, palette.gold.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                    .clickable { showMoonPhaseDialog = true }
                    .padding(horizontal = 14.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(text = "🌙", color = palette.cyan, fontSize = 12.sp)
                Column {
                    Text(
                        text = "Bulan Sabit (Tingkat II)",
                        color = palette.textPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Sentuh untuk info ⓘ",
                        color = palette.gold,
                        fontSize = 9.sp
                    )
                }
            }
        }

        // Constellation Card (Rasi Aquila Noctis)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(palette.cardBackground, RoundedCornerShape(24.dp))
                .border(1.dp, palette.borderSubtle, RoundedCornerShape(24.dp))
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showConstellationDialog = true },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(text = "❖", color = palette.gold, fontSize = 12.sp)
                    Text(
                        text = "Rasi Aquila Noctis",
                        color = palette.textPrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Box(
                    modifier = Modifier
                        .background(palette.surfaceLow, RoundedCornerShape(10.dp))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(text = "$starsCount/25 Bersinar ⓘ", color = palette.gold, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Star Map Canvas with Dot Grid
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp)
                    .background(if (palette.isDark) Color(0xFF0B0D17) else Color(0xFF1E2130), RoundedCornerShape(18.dp))
                    .border(1.dp, palette.borderSubtle, RoundedCornerShape(18.dp)),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val w = size.width
                    val h = size.height

                    // 1. Draw dot grid background
                    val rows = 9
                    val cols = 13
                    val stepX = w / cols
                    val stepY = h / rows
                    for (c in 1 until cols) {
                        for (r in 1 until rows) {
                            drawCircle(
                                color = Color(0xFFF6C85F).copy(alpha = 0.12f),
                                radius = 1.5f,
                                center = Offset(c * stepX, r * stepY)
                            )
                        }
                    }

                    // 2. Constellation Star Points (25 Points)
                    val starPoints = ConstellationData.starNodes.map { node ->
                        Offset(node.normX * w, node.normY * h)
                    }

                    // 3. Constellation Interconnecting Lines
                    ConstellationData.connections.forEach { (startIdx, endIdx) ->
                        if (startIdx < starPoints.size && endIdx < starPoints.size) {
                            val isLit = (startIdx < starsCount) && (endIdx < starsCount)
                            drawLine(
                                color = if (isLit) Color(0xFFF6C85F).copy(alpha = 0.55f) else Color(0xFF384259).copy(alpha = 0.22f),
                                start = starPoints[startIdx],
                                end = starPoints[endIdx],
                                strokeWidth = if (isLit) 2f else 1.2f
                            )
                        }
                    }

                    // 4. Glowing Stars (Lit vs Dormant depending on starsCount)
                    starPoints.forEachIndexed { index, pt ->
                        val isUnlocked = index < starsCount
                        val isAnchor = ConstellationData.starNodes[index].isAnchor
                        if (isUnlocked) {
                            if (isAnchor) {
                                drawCircle(color = Color(0xFFF6C85F).copy(alpha = 0.18f), radius = 22f, center = pt)
                                drawCircle(color = Color(0xFFF6C85F).copy(alpha = 0.40f), radius = 14f, center = pt)
                                drawCircle(color = Color.White, radius = 5.5f, center = pt)
                            } else {
                                drawCircle(color = Color(0xFFF6C85F).copy(alpha = 0.28f), radius = 9f, center = pt)
                                drawCircle(color = Color(0xFFFFE29A), radius = 3.5f, center = pt)
                            }
                        } else {
                            // Dormant Star
                            drawCircle(color = Color(0xFF64748B).copy(alpha = 0.4f), radius = if (isAnchor) 6.5f else 4f, center = pt, style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.2f))
                            drawCircle(color = Color(0xFF475569).copy(alpha = 0.5f), radius = if (isAnchor) 2.5f else 1.5f, center = pt)
                        }
                    }
                }

                // Interactive Popup Tooltip on Central Star
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 14.dp)
                        .background(Color(0xFF1E2132), RoundedCornerShape(16.dp))
                        .border(1.dp, Color(0xFFF6C85F).copy(alpha = 0.35f), RoundedCornerShape(16.dp))
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(text = "❖", color = Color(0xFFF6C85F), fontSize = 13.sp)
                        Column {
                            Text(
                                text = "Bintang Olahraga & Fajar",
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Tersegel · 2 jam lalu",
                                color = Color(0xFF94A3B8),
                                fontSize = 9.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Star Map Footer
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "❖ Sentuh bintang untuk membuka memori",
                    color = palette.textMuted,
                    fontSize = 10.sp
                )
                val remaining = (25 - starsCount).coerceAtLeast(0)
                Text(
                    text = "$remaining TERSISA",
                    color = palette.cyan,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // 1. Primary Gold CTA Button
        Button(
            onClick = { showClaimDialog = true },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = palette.gold),
            shape = RoundedCornerShape(25.dp)
        ) {
            Text(
                text = "+ Klaim & Segel Bintang Hari Ini",
                color = palette.goldDark,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )
        }

        // Notification Banner after successful star claim
        if (showSuccessToast) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(palette.surfaceLow, RoundedCornerShape(14.dp))
                    .border(1.dp, Color(0xFF43A047).copy(alpha = 0.5f), RoundedCornerShape(14.dp))
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "✨", fontSize = 14.sp)
                Text(
                    text = "Bintang berhasil disegel dan diabadikan di Vault!",
                    color = palette.gold,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        // 2. OLED Ready Wallpaper Preview & Info Button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { showWallpaperModal = true },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = palette.surface),
                shape = RoundedCornerShape(24.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, palette.borderSubtle)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = "🌌", fontSize = 14.sp)
                        Text(
                            text = "Wallpaper Lockscreen HD",
                            color = palette.textPrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Box(
                        modifier = Modifier
                            .background(palette.cyan.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
                            .border(0.5.dp, palette.cyan.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                            .padding(horizontal = 7.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "OLED READY",
                            color = palette.cyan,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            IconButton(
                onClick = { showConstellationDialog = true },
                modifier = Modifier
                    .size(48.dp)
                    .background(palette.surface, CircleShape)
                    .border(1.dp, palette.borderSubtle, CircleShape)
            ) {
                Text(
                    text = "ⓘ",
                    color = palette.gold,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Bintang Terakhir Card
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(palette.surface, RoundedCornerShape(20.dp))
                .border(1.dp, palette.borderSubtle, RoundedCornerShape(20.dp))
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .background(palette.surfaceLow, RoundedCornerShape(14.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "⚙️", color = palette.gold, fontSize = 18.sp)
            }
            Column {
                Text(
                    text = "BINTANG TERAKHIR",
                    color = palette.textMuted,
                    fontSize = 9.sp,
                    letterSpacing = 1.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = lastStarTitle,
                    color = palette.textPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
                Text(
                    text = lastStarTime,
                    color = palette.textMuted,
                    fontSize = 10.sp
                )
            }
        }

        // Progress Rasi Lengkap (Clickable to explain)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(palette.surface, RoundedCornerShape(20.dp))
                .border(1.dp, palette.borderSubtle, RoundedCornerShape(20.dp))
                .clickable { showConstellationDialog = true }
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(text = "∞", color = palette.cyan, fontSize = 14.sp)
                    Text(
                        text = "RASI LENGKAP",
                        color = palette.textPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = "(Ketuk untuk info ⓘ)", color = palette.gold, fontSize = 9.sp)
                }
                Text(
                    text = "$starsCount / 25",
                    color = palette.textPrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            val progressVal = (starsCount / 25f).coerceIn(0f, 1f)
            LinearProgressIndicator(
                progress = { progressVal },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = palette.gold,
                trackColor = palette.surfaceLow
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${(progressVal * 100).toInt()}% Terbuka",
                    color = palette.textMuted,
                    fontSize = 10.sp
                )
                val remaining = (25 - starsCount).coerceAtLeast(0)
                Text(
                    text = "Sisa $remaining Bintang",
                    color = palette.gold,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // Bottom Quote Card
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(palette.surfaceLow, RoundedCornerShape(16.dp))
                .border(1.dp, palette.borderSubtle, RoundedCornerShape(16.dp))
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "🌙", color = palette.gold, fontSize = 14.sp)
                Text(
                    text = "\"Malam menjaga rahasia mereka yang tekun melangkah...\"",
                    color = palette.textMuted,
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Serif
                )
            }
            Text(text = "Nov 2024", color = palette.textMuted, fontSize = 10.sp)
        }

        Spacer(modifier = Modifier.height(50.dp))
    }

    // 1. Dialog Klaim Bintang
    if (showClaimDialog) {
        AlertDialog(
            onDismissRequest = { showClaimDialog = false },
            containerColor = palette.surface,
            titleContentColor = palette.gold,
            textContentColor = palette.textPrimary,
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "⭐", fontSize = 18.sp)
                    Text(
                        text = "Klaim & Segel Bintang",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        color = palette.gold
                    )
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Pilih ikhtiar atau kebaikan yang kamu lakukan hari ini:",
                        fontSize = 12.sp,
                        color = palette.textMuted
                    )
                    presets.forEachIndexed { index, (title, category, _) ->
                        val isSelected = selectedPresetIndex == index
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    if (isSelected) palette.gold.copy(alpha = 0.15f) else palette.surfaceLow,
                                    RoundedCornerShape(12.dp)
                                )
                                .border(
                                    1.dp,
                                    if (isSelected) palette.gold else palette.borderSubtle,
                                    RoundedCornerShape(12.dp)
                                )
                                .clickable { selectedPresetIndex = index }
                                .padding(horizontal = 12.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = title,
                                    color = if (isSelected) palette.gold else palette.textPrimary,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    text = category,
                                    color = palette.textMuted,
                                    fontSize = 10.sp
                                )
                            }
                            if (isSelected) {
                                Text(text = "✓", color = palette.gold, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                    OutlinedTextField(
                        value = customNote,
                        onValueChange = { customNote = it },
                        placeholder = { Text("Catatan tambahan (opsional)", color = palette.textMuted, fontSize = 11.sp) },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = palette.gold,
                            unfocusedBorderColor = palette.borderSubtle,
                            focusedContainerColor = palette.surfaceLow,
                            unfocusedContainerColor = palette.surfaceLow,
                            focusedTextColor = palette.textPrimary,
                            unfocusedTextColor = palette.textPrimary
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 2
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val selected = presets[selectedPresetIndex]
                        val note = if (customNote.isNotBlank()) customNote.trim() else selected.third
                        onAddStar(selected.first, selected.second, note)
                        showClaimDialog = false
                        customNote = ""
                        showSuccessToast = true
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = palette.gold),
                    shape = RoundedCornerShape(18.dp)
                ) {
                    Text(
                        text = "✦ Segel Bintang (+1 ⭐)",
                        color = palette.goldDark,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { showClaimDialog = false }) {
                    Text(text = "Batal", color = palette.textMuted, fontSize = 12.sp)
                }
            }
        )
    }

    // 2. Dialog Penjelasan Tingkatan Fase Bulan
    if (showMoonPhaseDialog) {
        AlertDialog(
            onDismissRequest = { showMoonPhaseDialog = false },
            containerColor = palette.surface,
            titleContentColor = palette.gold,
            textContentColor = palette.textPrimary,
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "🌙", fontSize = 20.sp)
                    Text(
                        text = "Fase Bulan & Tingkatan",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = palette.gold
                    )
                }
            },
            text = {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Tingkatan ini menggambarkan pertumbuhan kedisiplinan dan keheningan batinmu:",
                        fontSize = 11.sp,
                        color = palette.textMuted
                    )

                    listOf(
                        Triple("🌑 Tingkat I: Bulan Baru", "0 - 9 Bintang Tersegel", "Awal mula perjalanan. Baru mengenal Secre, menata niat fajar, dan membiasakan diri bangun tepat waktu."),
                        Triple("🌙 Tingkat II: Bulan Sabit (Saat Ini)", "10 - 24 Bintang Tersegel", "Cahaya mulai stabil! Ritme fajar telah terbentuk, kebaikan tercatat di Vault, dan membuka hak tukar Tiket Malas 24 Jam di Sanctum."),
                        Triple("🌓 Tingkat III: Bulan Separuh", "25 - 49 Bintang Tersegel", "Kebiasaan mengkristal menjadi karakter kokoh. Secre mulai memperingatkanmu saat begadang lewat jam 23:00."),
                        Triple("🌕 Tingkat IV: Bulan Purnama", "50+ Bintang Tersegel", "Puncak keharmonisan jiwa. Rasi bintang penuh abadi dan seluruh berkah Sanctum terbuka sempurna.")
                    ).forEach { (title, range, desc) ->
                        val isCurrent = title.contains("Saat Ini")
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    if (isCurrent) palette.gold.copy(alpha = 0.12f) else palette.surfaceLow,
                                    RoundedCornerShape(12.dp)
                                )
                                .border(
                                    1.dp,
                                    if (isCurrent) palette.gold else palette.borderSubtle,
                                    RoundedCornerShape(12.dp)
                                )
                                .padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(3.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = title,
                                    color = if (isCurrent) palette.gold else palette.textPrimary,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(text = range, color = palette.cyan, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                            }
                            Text(text = desc, color = palette.textMuted, fontSize = 10.sp, lineHeight = 15.sp)
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { showMoonPhaseDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = palette.gold),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(text = "Mengerti", color = palette.goldDark, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        )
    }

    // 3. Dialog Penjelasan Rasi Lengkap
    if (showConstellationDialog) {
        AlertDialog(
            onDismissRequest = { showConstellationDialog = false },
            containerColor = palette.surface,
            titleContentColor = palette.gold,
            textContentColor = palette.textPrimary,
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "❖", color = palette.gold, fontSize = 18.sp)
                    Text(
                        text = "Apa Maksud Rasi Lengkap?",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = palette.gold
                    )
                }
            },
            text = {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "Rasi Lengkap adalah pencapaian konstelasi bintang 'Rasi Aquila Noctis' dalam satu siklus bulan.",
                        fontSize = 12.sp,
                        color = palette.textPrimary,
                        lineHeight = 17.sp
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(palette.surfaceLow, RoundedCornerShape(12.dp))
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(text = "✦ Mengapa Berjumlah 25 Bintang?", color = palette.gold, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        Text(
                            text = "Peta bintang langit Secre terdiri dari 25 titik konstelasi utama. Setiap kali kamu menyegel bintang harian atau berbuat kebaikan, satu titik bintang menyala keemasan.",
                            color = palette.textMuted,
                            fontSize = 10.sp,
                            lineHeight = 15.sp
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(palette.surfaceLow, RoundedCornerShape(12.dp))
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(text = "✨ Apa yang Terjadi Saat 25/25?", color = palette.cyan, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        Text(
                            text = "Ketika rasi mencapai 25/25, seluruh garis rasi menyatu utuh (Full Alignment). Langit malammu berubah menjadi Full Nocturne Wallpaper, membuka Lencana Emas Abadi di Vault, dan merestart rasi ke tingkat berikutnya!",
                            color = palette.textMuted,
                            fontSize = 10.sp,
                            lineHeight = 15.sp
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { showConstellationDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = palette.gold),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(text = "Paham, Lanjutkan", color = palette.goldDark, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        )
    }

    // 4. Modal Pratinjau / Review Wallpaper Native Phone Experience
    if (showWallpaperModal) {
        val displayClock = if (liveTime.isNotBlank()) liveTime else "06:30"

        Dialog(
            onDismissRequest = { showWallpaperModal = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF020408).copy(alpha = 0.96f))
                    .padding(horizontal = 16.dp, vertical = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(palette.surface, RoundedCornerShape(26.dp))
                        .border(1.dp, palette.borderSubtle, RoundedCornerShape(26.dp))
                        .padding(horizontal = 16.dp, vertical = 14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Header Bar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(text = "🌌", fontSize = 20.sp)
                            Column {
                                Text(
                                    text = "Review Wallpaper",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = palette.gold
                                )
                                Text(
                                    text = "Simulasi Tampilan HP • OLED HD",
                                    fontSize = 10.sp,
                                    color = palette.cyan
                                )
                            }
                        }
                        IconButton(
                            onClick = { showWallpaperModal = false },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Text(text = "✕", color = palette.textMuted, fontSize = 16.sp)
                        }
                    }

                    // Mode Switcher: Layar Kunci vs Layar Utama
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(palette.surfaceLow, RoundedCornerShape(12.dp))
                            .padding(3.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        val isLock = previewMode == "lock"
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .background(
                                    if (isLock) palette.gold else Color.Transparent,
                                    RoundedCornerShape(10.dp)
                                )
                                .clickable { previewMode = "lock" }
                                .padding(vertical = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "🔒 Layar Kunci",
                                color = if (isLock) palette.goldDark else palette.textMuted,
                                fontSize = 11.sp,
                                fontWeight = if (isLock) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .background(
                                    if (!isLock) palette.gold else Color.Transparent,
                                    RoundedCornerShape(10.dp)
                                )
                                .clickable { previewMode = "home" }
                                .padding(vertical = 6.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "📱 Layar Utama",
                                color = if (!isLock) palette.goldDark else palette.textMuted,
                                fontSize = 11.sp,
                                fontWeight = if (!isLock) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }

                    // Authentic Smartphone Mockup Frame
                    Box(
                        modifier = Modifier
                            .width(230.dp)
                            .height(410.dp)
                            .background(Color(0xFF030508), RoundedCornerShape(26.dp))
                            .border(2.dp, Color(0xFF333A4D), RoundedCornerShape(26.dp))
                            .padding(vertical = 8.dp, horizontal = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        // Phone Screen Inner Layout
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Top Notch Camera + Status Bar
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                // Camera Punch-Hole
                                Box(
                                    modifier = Modifier
                                        .size(9.dp)
                                        .background(Color(0xFF151922), CircleShape)
                                        .border(0.5.dp, Color(0xFF2B3245), CircleShape)
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                // Status Bar (Clock without seconds, WiFi, Battery)
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 4.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = displayClock,
                                        color = Color.White.copy(alpha = 0.9f),
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        fontFamily = FontFamily.Monospace
                                    )
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(text = "📶", fontSize = 8.sp)
                                        Text(text = "🔋", fontSize = 8.sp)
                                    }
                                }
                            }

                            // Dynamic Mode Content
                            if (previewMode == "lock") {
                                // Lockscreen Header Clock & Date
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(2.dp),
                                    modifier = Modifier.padding(top = 4.dp)
                                ) {
                                    Text(text = "🔒", fontSize = 9.sp)
                                    Text(
                                        text = displayClock,
                                        color = Color.White,
                                        fontSize = 38.sp,
                                        fontWeight = FontWeight.Light,
                                        letterSpacing = (-1).sp,
                                        fontFamily = FontFamily.Monospace
                                    )
                                    Text(
                                        text = todayDateStr,
                                        color = palette.goldLight,
                                        fontSize = 8.sp,
                                        fontWeight = FontWeight.Medium,
                                        letterSpacing = 0.5.sp
                                    )
                                }
                            } else {
                                // Homescreen Widget Preview
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 6.dp),
                                    verticalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    // Search Bar Mockup
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(Color.White.copy(alpha = 0.12f), RoundedCornerShape(12.dp))
                                            .padding(horizontal = 8.dp, vertical = 5.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(text = "🔍 Cari aplikasi & web...", color = Color.White.copy(alpha = 0.6f), fontSize = 7.sp)
                                        Text(text = "🎙️", fontSize = 7.sp)
                                    }
                                }
                            }

                            // Constellation Graphic Canvas
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(vertical = 4.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Canvas(modifier = Modifier.fillMaxSize()) {
                                    val w = size.width
                                    val h = size.height

                                    // Ambient stars
                                    val stepX = w / 7
                                    val stepY = h / 6
                                    for (c in 1 until 7) {
                                        for (r in 1 until 6) {
                                            drawCircle(
                                                color = Color(0xFFF6C85F).copy(alpha = 0.12f),
                                                radius = 1f,
                                                center = Offset(c * stepX, r * stepY)
                                            )
                                        }
                                    }

                                    // Constellation Points (25 Points)
                                    val pts = ConstellationData.starNodes.map { node ->
                                        Offset(node.normX * w, node.normY * h)
                                    }

                                    ConstellationData.connections.forEach { (s, e) ->
                                        if (s < pts.size && e < pts.size) {
                                            val isLit = (s < starsCount) && (e < starsCount)
                                            drawLine(
                                                color = if (isLit) Color(0xFFF6C85F).copy(alpha = 0.55f) else Color(0xFF384259).copy(alpha = 0.22f),
                                                start = pts[s],
                                                end = pts[e],
                                                strokeWidth = if (isLit) 1.4f else 0.8f
                                            )
                                        }
                                    }

                                    pts.forEachIndexed { i, p ->
                                        val isUnlocked = i < starsCount
                                        val isAnchor = ConstellationData.starNodes[i].isAnchor
                                        if (isUnlocked) {
                                            if (isAnchor) {
                                                drawCircle(Color(0xFFF6C85F).copy(alpha = 0.35f), 12f, p)
                                                drawCircle(Color.White, 3.8f, p)
                                            } else {
                                                drawCircle(Color(0xFFF6C85F).copy(alpha = 0.25f), 6f, p)
                                                drawCircle(Color(0xFFFFE29A), 2.4f, p)
                                            }
                                        } else {
                                            drawCircle(Color(0xFF64748B).copy(alpha = 0.35f), if (isAnchor) 5f else 3.5f, p, style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1f))
                                            drawCircle(Color(0xFF475569).copy(alpha = 0.45f), if (isAnchor) 2f else 1.2f, p)
                                        }
                                    }
                                }

                                if (previewMode == "home") {
                                    // App Icons Grid Mockup
                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        verticalArrangement = Arrangement.spacedBy(10.dp)
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceAround
                                        ) {
                                            listOf("🐦‍⬛" to "Secre", "🔒" to "Vault", "⛩️" to "Sanctum", "📝" to "Catatan").forEach { (ic, label) ->
                                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                    Box(
                                                        modifier = Modifier
                                                            .size(28.dp)
                                                            .background(Color.White.copy(alpha = 0.15f), RoundedCornerShape(8.dp)),
                                                        contentAlignment = Alignment.Center
                                                    ) {
                                                        Text(text = ic, fontSize = 13.sp)
                                                    }
                                                    Text(text = label, color = Color.White.copy(alpha = 0.8f), fontSize = 7.sp)
                                                }
                                            }
                                        }
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceAround
                                        ) {
                                            listOf("📷" to "Kamera", "🌐" to "Web", "🎵" to "Musik", "⚙️" to "Setelan").forEach { (ic, label) ->
                                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                    Box(
                                                        modifier = Modifier
                                                            .size(28.dp)
                                                            .background(Color.White.copy(alpha = 0.15f), RoundedCornerShape(8.dp)),
                                                        contentAlignment = Alignment.Center
                                                    ) {
                                                        Text(text = ic, fontSize = 13.sp)
                                                    }
                                                    Text(text = label, color = Color.White.copy(alpha = 0.8f), fontSize = 7.sp)
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    // Lockscreen Notification Pill Card
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.BottomCenter)
                                            .padding(bottom = 6.dp)
                                            .fillMaxWidth()
                                            .background(Color.White.copy(alpha = 0.10f), RoundedCornerShape(12.dp))
                                            .border(0.5.dp, Color.White.copy(alpha = 0.18f), RoundedCornerShape(12.dp))
                                            .padding(horizontal = 8.dp, vertical = 5.dp)
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                                        ) {
                                            Text(text = "🐦‍⬛", fontSize = 12.sp)
                                            Column {
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween
                                                ) {
                                                    Text(text = "Secre Nocturne", color = palette.gold, fontSize = 8.sp, fontWeight = FontWeight.Bold)
                                                    Text(text = "sekarang", color = Color.White.copy(alpha = 0.5f), fontSize = 6.sp)
                                                }
                                                Text(
                                                    text = "Ritme fajar & $starsCount bintang tersegel di langit malam.",
                                                    color = Color.White.copy(alpha = 0.85f),
                                                    fontSize = 7.sp
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            // Mockup Bottom Footer (Watermark & Phone Controls)
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "✦ SECRE NOCTURNE • AQUILA NOCTIS ✦",
                                    color = palette.gold.copy(alpha = 0.8f),
                                    fontSize = 7.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.8.sp
                                )

                                if (previewMode == "lock") {
                                    // Flashlight and Camera Lockscreen Shortcuts
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 6.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(20.dp)
                                                .background(Color.White.copy(alpha = 0.15f), CircleShape),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(text = "🔦", fontSize = 9.sp)
                                        }
                                        // Swipe Navigation Bar Indicator
                                        Box(
                                            modifier = Modifier
                                                .width(42.dp)
                                                .height(2.5.dp)
                                                .background(Color.White.copy(alpha = 0.6f), RoundedCornerShape(2.dp))
                                        )
                                        Box(
                                            modifier = Modifier
                                                .size(20.dp)
                                                .background(Color.White.copy(alpha = 0.15f), CircleShape),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(text = "📷", fontSize = 9.sp)
                                        }
                                    }
                                } else {
                                    // Homescreen Bottom Dock
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(Color.White.copy(alpha = 0.08f), RoundedCornerShape(14.dp))
                                            .padding(horizontal = 10.dp, vertical = 4.dp),
                                        horizontalArrangement = Arrangement.SpaceAround,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        listOf("📞", "💬", "🐦‍⬛", "📷").forEach { ic ->
                                            Text(text = ic, fontSize = 13.sp)
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Bottom Action Bar: Native "Apply Wallpaper" Experience
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Prominent Primary Button: Terapkan Wallpaper (Apply Wallpaper)
                        Button(
                            onClick = { showTargetSelectionSheet = true },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(46.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = palette.gold),
                            shape = RoundedCornerShape(23.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(text = "✨", fontSize = 16.sp)
                                Text(
                                    text = "Terapkan Wallpaper / Apply Wallpaper",
                                    color = palette.goldDark,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }
                        }

                        // Secondary Options: Simpan Gambar ke Galeri & Tutup
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedButton(
                                onClick = {
                                    coroutineScope.launch {
                                        val saved = WallpaperHelper.saveWallpaperToGallery(context, starsCount)
                                        if (saved) {
                                            Toast.makeText(context, "📥 Berhasil disimpan ke Galeri Pictures/SecreNocturne!", Toast.LENGTH_LONG).show()
                                        } else {
                                            Toast.makeText(context, "Gagal menyimpan gambar ke penyimpanan.", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(38.dp),
                                shape = RoundedCornerShape(19.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, palette.borderSubtle),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = palette.goldLight)
                            ) {
                                Text(text = "💾 Simpan ke Galeri", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                            }

                            OutlinedButton(
                                onClick = { showWallpaperModal = false },
                                modifier = Modifier
                                    .weight(1f)
                                    .height(38.dp),
                                shape = RoundedCornerShape(19.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, palette.borderSubtle),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = palette.textMuted)
                            ) {
                                Text(text = "Tutup", fontSize = 11.sp, fontWeight = FontWeight.Normal)
                            }
                        }
                    }
                }
            }
        }
    }

    // 5. Native Target Selector: Pilih Layar untuk Pasang Wallpaper
    if (showTargetSelectionSheet) {
        AlertDialog(
            onDismissRequest = {
                if (!isApplyingWallpaper) showTargetSelectionSheet = false
            },
            containerColor = palette.surface,
            titleContentColor = palette.gold,
            textContentColor = palette.textPrimary,
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "📱", fontSize = 18.sp)
                    Text(
                        text = "Terapkan Wallpaper",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = palette.gold
                    )
                }
            },
            text = {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Live Sync Announcement Banner
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(palette.cyan.copy(alpha = 0.12f), RoundedCornerShape(10.dp))
                            .border(0.5.dp, palette.cyan.copy(alpha = 0.35f), RoundedCornerShape(10.dp))
                            .padding(horizontal = 10.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(text = "⚡", fontSize = 14.sp)
                        Column {
                            Text(
                                text = "SINKRONISASI LIVE OTOMATIS",
                                color = palette.cyan,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                            Text(
                                text = "Setiap kamu meraih bintang baru di aplikasi, wallpaper HP akan otomatis diperbarui secara live!",
                                color = palette.textPrimary,
                                fontSize = 9.sp,
                                lineHeight = 13.sp
                            )
                        }
                    }

                    Text(
                        text = "Pilih opsi penerapan wallpaper Rasi Noctis:",
                        fontSize = 11.sp,
                        color = palette.textMuted
                    )

                    if (isApplyingWallpaper) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            CircularProgressIndicator(color = palette.gold)
                            Text(
                                text = "Sedang menerapkan wallpaper & mengaktifkan Live Sync...",
                                color = palette.gold,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    } else {
                        val targets = listOf(
                            Triple("🔒 Layar Kunci (Lock Screen)", "Pasang di layar kunci. Bintang akan terus sinkron live.", WallpaperTarget.LOCKSCREEN),
                            Triple("📱 Layar Utama (Home Screen)", "Pasang di beranda aplikasi. Bintang akan terus sinkron live.", WallpaperTarget.HOMESCREEN),
                            Triple("🌌 Layar Kunci & Layar Utama", "Terapkan ke kedua layar. Selalu sinkron live dengan aplikasi.", WallpaperTarget.BOTH)
                        )

                        targets.forEach { (title, desc, target) ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(palette.surfaceLow, RoundedCornerShape(14.dp))
                                    .border(1.dp, palette.borderSubtle, RoundedCornerShape(14.dp))
                                    .clickable {
                                        coroutineScope.launch {
                                            isApplyingWallpaper = true
                                            val success = WallpaperHelper.applyWallpaper(
                                                context = context,
                                                starsCount = starsCount,
                                                target = target,
                                                enableAutoSync = true
                                            )
                                            isApplyingWallpaper = false
                                            if (success) {
                                                Toast.makeText(
                                                    context,
                                                    "✨ Wallpaper diterapkan & Live Sync Aktif! Bintang di wallpaper akan terus sama dengan aplikasi.",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                                showTargetSelectionSheet = false
                                                showWallpaperModal = false
                                            } else {
                                                Toast.makeText(
                                                    context,
                                                    "Penerapan otomatis terhalang kebijakan sistem. Menyimpan gambar ke Galeri...",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                val saved = WallpaperHelper.saveWallpaperToGallery(context, starsCount)
                                                if (saved) {
                                                    Toast.makeText(
                                                        context,
                                                        "📥 Gambar wallpaper HD tersimpan di Galeri Pictures/SecreNocturne!",
                                                        Toast.LENGTH_LONG
                                                    ).show()
                                                }
                                                showTargetSelectionSheet = false
                                            }
                                        }
                                    }
                                    .padding(horizontal = 14.dp, vertical = 10.dp),
                                verticalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                Text(
                                    text = title,
                                    color = palette.gold,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = desc,
                                    color = palette.textMuted,
                                    fontSize = 10.sp,
                                    lineHeight = 13.sp
                                )
                            }
                        }

                        // Option 4: Animated Realtime Live Wallpaper Service
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(palette.gold.copy(alpha = 0.12f), RoundedCornerShape(14.dp))
                                .border(1.dp, palette.gold.copy(alpha = 0.5f), RoundedCornerShape(14.dp))
                                .clickable {
                                    try {
                                        val intent = Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER).apply {
                                            putExtra(
                                                WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                                                ComponentName(context, SecreLiveWallpaperService::class.java)
                                            )
                                        }
                                        context.startActivity(intent)
                                        WallpaperHelper.setAutoSyncEnabled(context, true, WallpaperTarget.BOTH)
                                        showTargetSelectionSheet = false
                                        showWallpaperModal = false
                                    } catch (e: Exception) {
                                        try {
                                            val fallbackIntent = Intent(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER)
                                            context.startActivity(fallbackIntent)
                                            showTargetSelectionSheet = false
                                            showWallpaperModal = false
                                        } catch (_: Exception) {
                                            Toast.makeText(context, "Perangkat tidak mendukung live wallpaper intent", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(text = "✨", fontSize = 13.sp)
                                Text(
                                    text = "Live Wallpaper Bergerak (Realtime)",
                                    color = palette.gold,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Text(
                                text = "Wallpaper animasi dengan bintang berkelip halus & langsung sinkron live dengan data aplikasi.",
                                color = palette.textMuted,
                                fontSize = 10.sp,
                                lineHeight = 13.sp
                            )
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {
                if (!isApplyingWallpaper) {
                    TextButton(onClick = { showTargetSelectionSheet = false }) {
                        Text(text = "Batal", color = palette.textMuted, fontSize = 12.sp)
                    }
                }
            }
        )
    }
}

