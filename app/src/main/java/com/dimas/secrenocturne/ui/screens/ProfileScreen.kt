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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dimas.secrenocturne.data.UserProfile
import com.dimas.secrenocturne.ui.theme.LocalNocturnePalette

@Composable
fun ProfileScreen(
    profile: UserProfile,
    starsCount: Int,
    lazyTickets: Int,
    currentThemeMode: String = "auto", // "auto", "light", "dark"
    onThemeModeChange: (String) -> Unit = {},
    onUpdateProfile: (UserProfile) -> Unit = {},
    onRestartIntroduction: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    val palette = LocalNocturnePalette.current
    val scrollState = rememberScrollState()

    var showEditDialog by remember { mutableStateOf(false) }
    var editName by remember { mutableStateOf(profile.name) }
    var editWakeTime by remember { mutableStateOf(profile.targetWakeTime) }
    var editCommitment by remember { mutableStateOf(profile.commitment) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(palette.background)
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Bar Navigation
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = onBack,
                colors = ButtonDefaults.buttonColors(containerColor = palette.surface),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, palette.borderSubtle),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(text = "←", color = palette.gold, fontSize = 14.sp)
                    Text(text = "Kembali ke Langit", color = palette.textPrimary, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                }
            }

            Box(
                modifier = Modifier
                    .background(palette.surfaceLow, RoundedCornerShape(12.dp))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(text = "● PROFIL RUHANI", color = palette.gold, fontSize = 9.sp, fontWeight = FontWeight.Bold)
            }
        }

        // Profile Identity Card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(palette.surface, RoundedCornerShape(24.dp))
                .border(1.dp, palette.borderSubtle, RoundedCornerShape(24.dp))
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(contentAlignment = Alignment.BottomEnd) {
                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .background(palette.surfaceLow, CircleShape)
                        .border(2.dp, palette.gold.copy(alpha = 0.5f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "👤", fontSize = 32.sp)
                }
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(palette.gold, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "✨", fontSize = 11.sp, color = palette.goldDark)
                }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = profile.name,
                    color = palette.textPrimary,
                    fontSize = 20.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = profile.title,
                    color = palette.gold,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = "Terikat janji batin sejak ${profile.joinDate}",
                    color = palette.textMuted,
                    fontSize = 10.sp
                )
            }

            // 3 Stats Grid
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(
                    Triple("Rekor Rasi", "${profile.streakDays} Hari", "Beruntun" to palette.gold),
                    Triple("Bintang Dimiliki", "$starsCount ⭐", "Tersedia" to palette.cyan),
                    Triple("Tiket Malas", "$lazyTickets 🎫", "Hak Rebahan" to palette.lavender)
                ).forEach { (label, value, statusPair) ->
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .background(palette.surfaceLow, RoundedCornerShape(14.dp))
                            .border(1.dp, palette.borderSubtle, RoundedCornerShape(14.dp))
                            .padding(horizontal = 8.dp, vertical = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = label, color = palette.textMuted, fontSize = 9.sp)
                        Text(
                            text = value,
                            color = palette.textPrimary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                        Text(
                            text = statusPair.first,
                            color = statusPair.second,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        // Section: Niat & Komitmen Batin
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
                Text(
                    text = "KOMITMEN RUHANI",
                    color = palette.gold,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp
                )
                TextButton(
                    onClick = {
                        editName = profile.name
                        editWakeTime = profile.targetWakeTime
                        editCommitment = profile.commitment
                        showEditDialog = true
                    },
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(text = "Ubah ✎", color = palette.cyan, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(palette.surfaceLow, RoundedCornerShape(14.dp))
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(text = "⏰", fontSize = 16.sp)
                Column {
                    Text(text = "Target Bangun Fajar", color = palette.textMuted, fontSize = 10.sp)
                    Text(text = profile.targetWakeTime, color = palette.textPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(palette.surfaceLow, RoundedCornerShape(14.dp))
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(text = "🕊️", fontSize = 16.sp)
                Column {
                    Text(text = "Niat Ikhtiar Utama", color = palette.textMuted, fontSize = 10.sp)
                    Text(text = profile.commitment, color = palette.textPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        // Section: Pengaturan Tema (Siang & Malam)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(palette.surface, RoundedCornerShape(22.dp))
                .border(1.dp, palette.borderSubtle, RoundedCornerShape(22.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(text = "🎨", fontSize = 12.sp)
                Text(
                    text = "TEMA LANGIT (SIANG & MALAM)",
                    color = palette.gold,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.8.sp
                )
            }

            Text(
                text = "Pilih tampilan langit Secre. Mode otomatis akan menyesuaikan waktu: Fajar cerah (06:00 - 18:00 WIB) dan Nocturne Gelap (18:00 - 06:00 WIB).",
                color = palette.textMuted,
                fontSize = 11.sp,
                lineHeight = 16.sp
            )

            listOf(
                Triple("auto", "🌗 Otomatis (Waktu Nyata)", "Siang cerah 06:00-18:00, Malam gelap 18:00-06:00"),
                Triple("light", "☀️ Fajar Cerah (Light Mode)", "Nuansa putih & emas siang hari"),
                Triple("dark", "🌙 Nocturne Gelap (Dark Mode)", "Nuansa obsidian & starlight malam hari")
            ).forEach { (mode, title, desc) ->
                val isSelected = currentThemeMode == mode
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            if (isSelected) palette.gold.copy(alpha = 0.15f) else palette.surfaceLow,
                            RoundedCornerShape(14.dp)
                        )
                        .border(
                            1.dp,
                            if (isSelected) palette.gold else palette.borderSubtle,
                            RoundedCornerShape(14.dp)
                        )
                        .clickable { onThemeModeChange(mode) }
                        .padding(horizontal = 14.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = title,
                            color = if (isSelected) palette.gold else palette.textPrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = desc,
                            color = palette.textMuted,
                            fontSize = 10.sp
                        )
                    }
                    if (isSelected) {
                        Text(text = "✓", color = palette.gold, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                }
            }
        }

        // Section: Ulangi Perkenalan Diri
        Button(
            onClick = onRestartIntroduction,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = palette.surfaceLow),
            border = androidx.compose.foundation.BorderStroke(1.dp, palette.borderSubtle),
            shape = RoundedCornerShape(24.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(text = "🐦‍⬛", fontSize = 14.sp)
                Text(
                    text = "Ulangi Perkenalan Diri dengan Secre",
                    color = palette.textPrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }

    // Dialog Edit Profil
    if (showEditDialog) {
        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            containerColor = palette.surface,
            titleContentColor = palette.gold,
            textContentColor = palette.textPrimary,
            title = {
                Text(
                    text = "Perbarui Komitmen Ruhani",
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = palette.gold
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(text = "Nama Panggilan:", fontSize = 11.sp, color = palette.textMuted)
                    OutlinedTextField(
                        value = editName,
                        onValueChange = { editName = it },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = palette.gold,
                            unfocusedBorderColor = palette.borderSubtle,
                            focusedContainerColor = palette.surfaceLow,
                            unfocusedContainerColor = palette.surfaceLow,
                            focusedTextColor = palette.textPrimary,
                            unfocusedTextColor = palette.textPrimary
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(text = "Target Bangun Fajar:", fontSize = 11.sp, color = palette.textMuted)
                    OutlinedTextField(
                        value = editWakeTime,
                        onValueChange = { editWakeTime = it },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = palette.gold,
                            unfocusedBorderColor = palette.borderSubtle,
                            focusedContainerColor = palette.surfaceLow,
                            unfocusedContainerColor = palette.surfaceLow,
                            focusedTextColor = palette.textPrimary,
                            unfocusedTextColor = palette.textPrimary
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(text = "Niat Ikhtiar Utama:", fontSize = 11.sp, color = palette.textMuted)
                    OutlinedTextField(
                        value = editCommitment,
                        onValueChange = { editCommitment = it },
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = palette.gold,
                            unfocusedBorderColor = palette.borderSubtle,
                            focusedContainerColor = palette.surfaceLow,
                            unfocusedContainerColor = palette.surfaceLow,
                            focusedTextColor = palette.textPrimary,
                            unfocusedTextColor = palette.textPrimary
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val newProfile = profile.copy(
                            name = editName.ifBlank { profile.name },
                            targetWakeTime = editWakeTime.ifBlank { profile.targetWakeTime },
                            commitment = editCommitment.ifBlank { profile.commitment }
                        )
                        onUpdateProfile(newProfile)
                        showEditDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = palette.gold),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(text = "Simpan", color = palette.goldDark, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditDialog = false }) {
                    Text(text = "Batal", color = palette.textMuted, fontSize = 12.sp)
                }
            }
        )
    }
}
