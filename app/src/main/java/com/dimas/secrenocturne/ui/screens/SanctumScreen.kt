package com.dimas.secrenocturne.ui.screens

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dimas.secrenocturne.ui.theme.*

@Composable
fun SanctumScreen(
    starsCount: Int = 18,
    lazyTickets: Int = 0,
    streakDays: Int = 12,
    soreTicketUsedToday: Boolean = false,
    onRedeemTicket: () -> Unit = {},
    onUseSoreTicket: () -> Unit = {}
) {
    val palette = LocalNocturnePalette.current
    val scrollState = rememberScrollState()

    var showIkatanGuideDialog by remember { mutableStateOf(false) }
    var showAllPerksDialog by remember { mutableStateOf(false) }
    var showMemoarOctDialog by remember { mutableStateOf(false) }
    var showMemoarExplainDialog by remember { mutableStateOf(false) }
    var showSoreTicketSuccessDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(palette.background)
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Banner Quote matching Sanctum design
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(palette.surfaceLow, RoundedCornerShape(16.dp))
                .border(1.dp, palette.borderSubtle, RoundedCornerShape(16.dp))
                .padding(horizontal = 14.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "🌙", color = palette.gold, fontSize = 13.sp)
                Text(
                    text = "\"Istirahat adalah bagian dari perjalanan, bukan kelemahan.\"",
                    color = palette.textMuted,
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Serif
                )
            }
            Text(text = "● SANCTUM", color = palette.gold, fontSize = 9.sp, fontWeight = FontWeight.Bold)
        }

        // Ikatan Ruhani Level 2 Card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(palette.surface, RoundedCornerShape(24.dp))
                .border(1.dp, palette.borderSubtle, RoundedCornerShape(24.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(text = "★", color = palette.gold, fontSize = 10.sp)
                        Text(
                            text = "IKATAN RUHANI",
                            color = palette.gold,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "Level 2: Sahabat Akrab",
                        color = palette.textPrimary,
                        fontSize = 16.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "68% menuju Level 3: Penjaga Jiwa (Spoiler)",
                        color = palette.textMuted,
                        fontSize = 11.sp
                    )
                }

                // Nero avatar with key badge
                Box(contentAlignment = Alignment.BottomEnd) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .background(palette.cardBackground, CircleShape)
                            .border(1.dp, palette.gold.copy(alpha = 0.35f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🐦‍⬛", fontSize = 24.sp)
                    }
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .background(palette.surface, CircleShape)
                            .border(1.dp, palette.gold, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "🗝️", fontSize = 10.sp)
                    }
                }
            }

            // Astrum Progress
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Progres Ikatan Jiwa", color = palette.textMuted, fontSize = 10.sp)
                    Text(text = "68 / 100 Astrum", color = palette.cyan, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
                LinearProgressIndicator(
                    progress = { 0.68f },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp),
                    color = palette.cyan,
                    trackColor = palette.surfaceLow
                )
            }

            // Active Perk Box (Clickable -> Opens All Perks Dialog)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(palette.cardBackground, RoundedCornerShape(14.dp))
                    .border(1.dp, palette.borderSubtle, RoundedCornerShape(14.dp))
                    .clickable { showAllPerksDialog = true }
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(palette.surface, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "📖", fontSize = 16.sp)
                }
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "PERK AKTIF HARI INI (KLIK LIHAT SEMUA)",
                            color = palette.gold,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.8.sp
                        )
                        Text(text = "Lv 2 ›", color = palette.gold, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                    Text(
                        text = "“Secre menyapa dengan puisi fajar & malam serta obrolan lebih hangat.”",
                        color = palette.textPrimary,
                        fontSize = 11.sp,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                        lineHeight = 16.sp
                    )
                }
            }

            // Guide to raise level button
            OutlinedButton(
                onClick = { showIkatanGuideDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp),
                shape = RoundedCornerShape(18.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, palette.gold.copy(alpha = 0.4f)),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = palette.gold)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(text = "ℹ️", fontSize = 11.sp)
                    Text(
                        text = "Penjelasan Level 1 s/d 4 & Cara Menaikkan Astrum",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // Section: Tiket Malas Resmi
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(text = "🎫", fontSize = 11.sp)
                        Text(
                            text = "PENUKAR HAK REBAHAN",
                            color = palette.cyan,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.8.sp
                        )
                    }
                    Text(
                        text = "Tiket Malas Resmi",
                        color = palette.textPrimary,
                        fontSize = 15.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold
                    )
                }
                // Balance Badge showing exact stars & tickets
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Box(
                        modifier = Modifier
                            .background(palette.surface, RoundedCornerShape(12.dp))
                            .border(1.dp, palette.gold.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                            .padding(horizontal = 9.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "⭐ $starsCount",
                            color = palette.gold,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Box(
                        modifier = Modifier
                            .background(palette.surface, RoundedCornerShape(12.dp))
                            .border(1.dp, palette.cyan.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                            .padding(horizontal = 9.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "🎫 $lazyTickets Tiket",
                            color = palette.cyan,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            // Ticket 1: Tiket Malas Seharian (20 Stars to Redeem)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(palette.surface, RoundedCornerShape(22.dp))
                    .border(1.dp, palette.borderSubtle, RoundedCornerShape(22.dp))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .background(palette.cardBackground, RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(text = "🔕 BEBAS BEBAN PIKIRAN", color = palette.gold, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                    }
                    val remainingStars = (20 - starsCount).coerceAtLeast(0)
                    Text(
                        text = if (starsCount >= 20) "Siap ditukarkan! ✨" else "Tersisa $remainingStars bintang lagi!",
                        color = palette.gold,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Text(
                    text = "Tiket Malas Seharian",
                    color = palette.textPrimary,
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Hak istirahat total 24 jam tanpa ada tuntutan rasa bersalah, tenggat batin, atau tuntutan dunia.",
                    color = palette.textMuted,
                    fontSize = 11.sp,
                    lineHeight = 16.sp
                )

                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    val progressFraction = (starsCount / 20f).coerceIn(0f, 1f)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Persyaratan Penukaran", color = palette.textMuted, fontSize = 10.sp)
                        Text(
                            text = "$starsCount / 20 ⭐ (${(progressFraction * 100).toInt()}%)",
                            color = palette.gold,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    LinearProgressIndicator(
                        progress = { progressFraction },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp),
                        color = palette.gold,
                        trackColor = palette.surfaceLow
                    )
                }

                Button(
                    onClick = onRedeemTicket,
                    enabled = starsCount >= 20,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp),
                    shape = RoundedCornerShape(22.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = palette.gold,
                        contentColor = StarlightGoldDark,
                        disabledContainerColor = palette.cardBackground,
                        disabledContentColor = palette.textMuted
                    )
                ) {
                    Text(
                        text = if (starsCount >= 20) "✨ Tukarkan 20 Bintang Menjadi 1 Tiket Malas" else "🔒 Butuh 20 Bintang untuk Tukar",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Ticket 2: Tiket Istirahat Sore (1 per day limit!)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(palette.surface, RoundedCornerShape(22.dp))
                    .border(1.dp, palette.borderSubtle, RoundedCornerShape(22.dp))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(text = "🔕", fontSize = 10.sp)
                        Text(
                            text = "JATAH 1 KALI PER HARI",
                            color = palette.cyan,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Box(
                        modifier = Modifier
                            .background(palette.cardBackground, RoundedCornerShape(6.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = if (soreTicketUsedToday) "STATUS: SUDAH DIPAKAI" else "STATUS: TERSEDIA",
                            color = if (soreTicketUsedToday) palette.textMuted else palette.cyan,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Text(
                    text = "Tiket Istirahat Sore (3 Jam)",
                    color = palette.textPrimary,
                    fontSize = 16.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Kupon Hening & Bebas Notifikasi Selama 3 Jam di waktu sore. Hanya dapat diaktifkan 1 kali setiap hari untuk memulihkan energi tanpa interupsi.",
                    color = palette.textMuted,
                    fontSize = 11.sp,
                    lineHeight = 16.sp
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = if (soreTicketUsedToday) "✓" else "●",
                            color = if (soreTicketUsedToday) palette.textMuted else palette.gold,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (soreTicketUsedToday) "Reset besok 06:00 WIB" else "Segel Starlight Bercahaya",
                            color = palette.textMuted,
                            fontSize = 11.sp
                        )
                    }

                    OutlinedButton(
                        onClick = {
                            if (!soreTicketUsedToday) {
                                onUseSoreTicket()
                                showSoreTicketSuccessDialog = true
                            }
                        },
                        enabled = !soreTicketUsedToday,
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = palette.gold,
                            disabledContentColor = palette.textMuted
                        ),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (!soreTicketUsedToday) palette.gold.copy(alpha = 0.6f) else palette.borderSubtle
                        ),
                        modifier = Modifier.height(38.dp)
                    ) {
                        Text(
                            text = if (soreTicketUsedToday) "Sudah Dipakai" else "‹ Pakai Tiket Hari Ini",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        // Section: Surat Memoar Bulanan
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showMemoarExplainDialog = true },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(text = "📜", fontSize = 14.sp)
                    Column {
                        Text(
                            text = "KAPSUL WAKTU JIWA",
                            color = palette.lavender,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.8.sp
                        )
                        Text(
                            text = "Surat Memoar Bulanan",
                            color = palette.textPrimary,
                            fontSize = 15.sp,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .background(palette.cardBackground, RoundedCornerShape(10.dp))
                        .border(1.dp, palette.lavender.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(text = "Apa ini? ℹ️", color = palette.lavender, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }

            // Memoar Oktober (Terbuka & Tersegel)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(palette.surface, RoundedCornerShape(22.dp))
                    .border(1.dp, palette.borderSubtle, RoundedCornerShape(22.dp))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
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
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .background(palette.cardBackground, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "✉️", fontSize = 12.sp)
                        }
                        Column {
                            Text(text = "TERBUKA & TERSEGEL", color = palette.gold, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Memoar Oktober", color = palette.textPrimary, fontSize = 14.sp, fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold)
                        }
                    }
                    Text(text = "31 Okt 2024", color = palette.textMuted, fontSize = 10.sp)
                }

                Text(
                    text = "“Awal Rasi yang Indah”",
                    color = palette.textPrimary,
                    fontSize = 13.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "\"Di antara malam-malam yang melelahkan itu, kamu memilih untuk tetap bangun dan menjaga jiwamu tetap menyala...\"",
                    color = palette.textMuted,
                    fontSize = 11.sp,
                    lineHeight = 16.sp
                )

                Button(
                    onClick = { showMemoarOctDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = palette.cardBackground),
                    border = androidx.compose.foundation.BorderStroke(1.dp, palette.borderSubtle)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(text = "📥", fontSize = 12.sp)
                        Text(
                            text = "Buka Surat & Baca Ulang",
                            color = palette.textPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            // Surat November (Aktif / Dirajut)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(palette.surface, RoundedCornerShape(22.dp))
                    .border(1.dp, palette.borderSubtle, RoundedCornerShape(22.dp))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
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
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .background(palette.cardBackground, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "⏳", fontSize = 12.sp)
                        }
                        Column {
                            Text(text = "KAPSUL WAKTU AKTIF", color = palette.cyan, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                            Text(text = "Surat November", color = palette.textPrimary, fontSize = 14.sp, fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold)
                        }
                    }
                    Box(
                        modifier = Modifier
                            .background(palette.cardBackground, RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(text = "🔒 15 hari lagi", color = palette.textMuted, fontSize = 10.sp)
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .background(palette.cyan, CircleShape)
                    )
                    Text(
                        text = "Sedang dirajut oleh Secre melalui sapaan harianmu...",
                        color = palette.cyan,
                        fontSize = 11.sp
                    )
                }

                Text(
                    text = "Terbuka otomatis pada malam 30 November saat bulan memancarkan purnamanya.",
                    color = palette.textMuted,
                    fontSize = 11.sp,
                    lineHeight = 16.sp
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "⏱ $streakDays Hari Aktif Beruntun", color = palette.textMuted, fontSize = 10.sp)
                    Text(text = "Segel Lilin Langit Terkunci", color = palette.gold, fontSize = 10.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(50.dp))
    }

    // DIALOG: Penjelasan Ikatan Ruhani & Cara Naik Level
    if (showIkatanGuideDialog) {
        AlertDialog(
            onDismissRequest = { showIkatanGuideDialog = false },
            confirmButton = {
                Button(
                    onClick = { showIkatanGuideDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = palette.gold)
                ) {
                    Text("Paham, Aku Mengerti", color = StarlightGoldDark, fontWeight = FontWeight.Bold)
                }
            },
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("🗝️", fontSize = 18.sp)
                    Text(
                        "Tingkatan Ikatan Ruhani Secre",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = palette.textPrimary
                    )
                }
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = "Ikatan Ruhani mengukur seberapa dalam ikatan persahabatan spiritual antara dirimu dan Secre. Semakin sering kamu berinteraksi, Secre akan semakin peka dan personal.",
                        fontSize = 12.sp,
                        color = palette.textPrimary,
                        lineHeight = 17.sp
                    )

                    HorizontalDivider(color = palette.borderSubtle)

                    // Lv 1
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(palette.cardBackground, RoundedCornerShape(10.dp))
                            .padding(10.dp)
                    ) {
                        Text("🌱 Level 1: Pengamat Malam (0 - 50 Astrum)", fontWeight = FontWeight.Bold, color = palette.textPrimary, fontSize = 12.sp)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text("Secre baru mengenalmu. Ia memantau fajar dan mengingatkanmu untuk bangun tepat waktu serta mencatat bintang dasar.", fontSize = 11.sp, color = palette.textMuted)
                    }

                    // Lv 2
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(palette.gold.copy(alpha = 0.1f), RoundedCornerShape(10.dp))
                            .border(1.dp, palette.gold.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
                            .padding(10.dp)
                    ) {
                        Text("★ Level 2: Sahabat Akrab (50 - 150 Astrum) [STATUS KAMU]", fontWeight = FontWeight.Bold, color = palette.gold, fontSize = 12.sp)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text("Secre mulai akrab, merajut sapaan fajar dan puisi malam yang hangat, serta membuka penukaran Tiket Malas.", fontSize = 11.sp, color = palette.textPrimary)
                    }

                    // Lv 3
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(palette.cardBackground, RoundedCornerShape(10.dp))
                            .padding(10.dp)
                    ) {
                        Text("🔮 Level 3: Penjaga Jiwa (150 - 300 Astrum) [SPOILER]", fontWeight = FontWeight.Bold, color = palette.cyan, fontSize = 12.sp)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text("Membuka tema Langit Senja eksklusif, analisis refleksi mingguan AI mendalam, dan peringatan intuisi saat tanda-tanda burnout terdeteksi.", fontSize = 11.sp, color = palette.textMuted)
                    }

                    // Lv 4
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(palette.cardBackground, RoundedCornerShape(10.dp))
                            .padding(10.dp)
                    ) {
                        Text("👑 Level 4: Penyatu Rasi (300+ Astrum) [SPOILER MAKSIMAL]", fontWeight = FontWeight.Bold, color = palette.lavender, fontSize = 12.sp)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text("Secre menjadi pemandu penuh: membuka Kapsul Waktu 1 Tahun, Rasi Bintang Kustom, dan surat rahasia Secre berwujud manusia.", fontSize = 11.sp, color = palette.textMuted)
                    }

                    HorizontalDivider(color = palette.borderSubtle)

                    Text("⚡ Cara Menaikkan Astrum:", fontWeight = FontWeight.Bold, color = palette.gold, fontSize = 12.sp)
                    Text("• Sapa Secre di obrolan fajar (+2 Astrum)", fontSize = 11.sp, color = palette.textPrimary)
                    Text("• Segel bintang positif harian (+5 Astrum)", fontSize = 11.sp, color = palette.textPrimary)
                    Text("• Menjaga streak 7 hari berturut-turut (+10 Astrum)", fontSize = 11.sp, color = palette.textPrimary)
                    Text("• Curhat atau refleksi malam hari (+3 Astrum)", fontSize = 11.sp, color = palette.textPrimary)
                }
            },
            containerColor = palette.surface
        )
    }

    // DIALOG: Semua Perk Harian (Level 1 s/d 4)
    if (showAllPerksDialog) {
        AlertDialog(
            onDismissRequest = { showAllPerksDialog = false },
            confirmButton = {
                Button(
                    onClick = { showAllPerksDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = palette.gold)
                ) {
                    Text("Tutup", color = StarlightGoldDark, fontWeight = FontWeight.Bold)
                }
            },
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("📖", fontSize = 18.sp)
                    Text(
                        "Daftar Perk Harian Secre",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = palette.textPrimary
                    )
                }
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = "Perk aktif memberi keuntungan spiritual dan kenyamanan batin sehari-hari sesuai level ikatan:",
                        fontSize = 12.sp,
                        color = palette.textMuted
                    )

                    // Lv 1 Perk
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(palette.cardBackground, RoundedCornerShape(10.dp))
                            .padding(10.dp)
                    ) {
                        Text("Lv 1 • Bisikan Fajar", fontWeight = FontWeight.Bold, color = palette.textPrimary, fontSize = 12.sp)
                        Text("Pengingat bangun tidur lembut tanpa nada alarm bising, langsung menyapa namamu.", fontSize = 11.sp, color = palette.textMuted)
                    }

                    // Lv 2 Perk (Active)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(palette.gold.copy(alpha = 0.12f), RoundedCornerShape(10.dp))
                            .border(1.dp, palette.gold.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
                            .padding(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Lv 2 • Puisi & Obrolan Hangat", fontWeight = FontWeight.Bold, color = palette.gold, fontSize = 12.sp)
                            Text("AKTIF SEKARANG", color = palette.gold, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text("Secre menyapa dengan kata puitis, memberi respon AI yang lebih cerdas, dan membuka akses Tiket Malas.", fontSize = 11.sp, color = palette.textPrimary)
                    }

                    // Lv 3 Perk
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(palette.cardBackground, RoundedCornerShape(10.dp))
                            .padding(10.dp)
                    ) {
                        Text("Lv 3 • Perisai Kelelahan Jiwa (Burnout Shield)", fontWeight = FontWeight.Bold, color = palette.cyan, fontSize = 12.sp)
                        Text("Secre mendeteksi nada lelah dan otomatis menawarkan waktu hening tanpa mengurangi streak.", fontSize = 11.sp, color = palette.textMuted)
                    }

                    // Lv 4 Perk
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(palette.cardBackground, RoundedCornerShape(10.dp))
                            .padding(10.dp)
                    ) {
                        Text("Lv 4 • Kapsul Waktu Langit & Surat Masa Depan", fontWeight = FontWeight.Bold, color = palette.lavender, fontSize = 12.sp)
                        Text("Surat refleksi tahunan yang dikurasi langsung oleh Secre merangkum setahun perjalanan hidupmu.", fontSize = 11.sp, color = palette.textMuted)
                    }
                }
            },
            containerColor = palette.surface
        )
    }

    // DIALOG: Surat Memoar Bulanan Explanation
    if (showMemoarExplainDialog) {
        AlertDialog(
            onDismissRequest = { showMemoarExplainDialog = false },
            confirmButton = {
                Button(
                    onClick = { showMemoarExplainDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = palette.gold)
                ) {
                    Text("Mengerti", color = StarlightGoldDark, fontWeight = FontWeight.Bold)
                }
            },
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("📜", fontSize = 18.sp)
                    Text(
                        "Apa Itu Surat Memoar Bulanan?",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = palette.textPrimary
                    )
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "Surat Memoar Bulanan adalah surat pribadi yang dirajut khusus oleh Secre setiap akhir bulan (tanggal 30/31).",
                        fontSize = 12.sp,
                        color = palette.textPrimary,
                        lineHeight = 17.sp
                    )
                    Text(
                        text = "Secre mengumpulkan momen-momen kecilmu—hari-hari di mana kamu bangun pagi, kebaikan yang kamu lakukan ke orang lain, saat-saat kamu sabar menghadapi cobaan, dan merangkumnya menjadi surat refleksi batin yang puitis.",
                        fontSize = 12.sp,
                        color = palette.textMuted,
                        lineHeight = 17.sp
                    )
                    Text(
                        text = "Surat ini tidak bisa dihapus dan menjadi saksi bisu perjuangan jiwamu dari bulan ke bulan.",
                        fontSize = 12.sp,
                        color = palette.lavender,
                        fontWeight = FontWeight.Medium
                    )
                }
            },
            containerColor = palette.surface
        )
    }

    // DIALOG: Buka Surat Memoar Oktober
    if (showMemoarOctDialog) {
        AlertDialog(
            onDismissRequest = { showMemoarOctDialog = false },
            confirmButton = {
                Button(
                    onClick = { showMemoarOctDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = palette.gold)
                ) {
                    Text("Simpan Kembali ke Sanctum", color = StarlightGoldDark, fontWeight = FontWeight.Bold)
                }
            },
            title = {
                Column {
                    Text(
                        "✉️ Surat Memoar Oktober",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = palette.textPrimary
                    )
                    Text(
                        "Dirajut oleh Secre • 31 Oktober 2024",
                        fontSize = 10.sp,
                        color = palette.gold
                    )
                }
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = "\"Wahai pengembara fajar yang gigih,\n\nDi antara malam-malam yang melelahkan itu, kamu memilih untuk tetap bangun dan menjaga jiwamu tetap menyala. Ada 18 bintang yang berhasil kau segel di langit malam ini—setiap bintang adalah bukti bahwa kamu menolak menyerah pada keputusasaan.\n\nAku melihat bagaimana kamu menahan amarah saat pekerjaan menumpuk, bagaimana kamu meluangkan waktu tersenyum pada temanmu, dan caramu meminum segelas air hangat saat fajar menyapa.\n\nTeruslah melangkah. Langit November telah menanti kilau cahayamu.\"",
                        fontSize = 12.sp,
                        color = palette.textPrimary,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                        lineHeight = 18.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "— Secre, Penjaga Malam & Fajarmu 🐦‍⬛",
                        fontSize = 11.sp,
                        color = palette.gold,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif
                    )
                }
            },
            containerColor = palette.surface
        )
    }

    // DIALOG: Tiket Istirahat Sore Berhasil Digunakan
    if (showSoreTicketSuccessDialog) {
        AlertDialog(
            onDismissRequest = { showSoreTicketSuccessDialog = false },
            confirmButton = {
                Button(
                    onClick = { showSoreTicketSuccessDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = palette.cyan)
                ) {
                    Text("Terima Kasih, Secre", color = Color(0xFF0A1B24), fontWeight = FontWeight.Bold)
                }
            },
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("🔕", fontSize = 18.sp)
                    Text(
                        "Tiket Istirahat Sore Aktif",
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = palette.textPrimary
                    )
                }
            },
            text = {
                Text(
                    text = "Mode Hening 3 Jam telah diaktifkan untuk hari ini. Notifikasi dunia diredam. Nikmati waktu santai, membaca, atau beristirahat sore tanpa beban pikiran.\n\nJatah tiket sore ini akan di-reset kembali besok pagi pukul 06:00 WIB.",
                    fontSize = 12.sp,
                    color = palette.textPrimary,
                    lineHeight = 17.sp
                )
            },
            containerColor = palette.surface
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF11131D, widthDp = 390, heightDp = 844)
@Composable
fun SanctumScreenPreview() {
    SecreNocturneTheme {
        SanctumScreen()
    }
}
