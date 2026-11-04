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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dimas.secrenocturne.ui.theme.LocalNocturnePalette

@Composable
fun OnboardingScreen(
    initialName: String = "",
    onComplete: (name: String, targetTime: String, commitment: String) -> Unit
) {
    val palette = LocalNocturnePalette.current
    var nameInput by remember { mutableStateOf(initialName) }
    var selectedTime by remember { mutableStateOf("06:30 WIB") }
    var selectedCommitment by remember { mutableStateOf("Disiplin Fajar & Ritual Pagi") }
    val scrollState = rememberScrollState()

    val timeOptions = listOf("05:00 WIB", "05:30 WIB", "06:00 WIB", "06:30 WIB", "07:00 WIB")
    val commitmentOptions = listOf(
        "Disiplin Fajar & Ritual Pagi",
        "Ketenangan Jiwa & Menahan Diri",
        "Menebar Kebaikan & Empati",
        "Kebugaran Raga & Olahraga",
        "Fokus Kerja & Menuntaskan Tugas"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(palette.background)
            .verticalScroll(scrollState)
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        // Nero Avatar Halo
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(palette.surfaceLow, CircleShape)
                .border(2.dp, palette.gold.copy(alpha = 0.5f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "🐦‍⬛", fontSize = 38.sp)
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "SECRE NOCTURNE",
                color = palette.gold,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Perkenalan Ruhani",
                color = palette.textPrimary,
                fontSize = 22.sp,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "“Aku Secre. Aku menjaga hening malam dan membimbing fajar. Sebelum kita merajut rasi bintang bersama, perkenalkan dirimu.”",
                color = palette.textMuted,
                fontSize = 12.sp,
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                textAlign = TextAlign.Center,
                lineHeight = 18.sp
            )
        }

        // Form Card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(palette.surface, RoundedCornerShape(24.dp))
                .border(1.dp, palette.borderSubtle, RoundedCornerShape(24.dp))
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Input Nama
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "1. Siapa Namamu?",
                    color = palette.textPrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                OutlinedTextField(
                    value = nameInput,
                    onValueChange = { nameInput = it },
                    placeholder = { Text("Masukkan namamu (misal: Dimas)", color = palette.textMuted, fontSize = 12.sp) },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
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

            // 2. Target Bangun Fajar
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "2. Target Bangun Fajar",
                    color = palette.textPrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Secre akan mematuk dan membangunkanmu di waktu ini:",
                    color = palette.textMuted,
                    fontSize = 10.sp
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    timeOptions.take(3).forEach { time ->
                        val isSelected = selectedTime == time
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .background(
                                    if (isSelected) palette.gold.copy(alpha = 0.2f) else palette.surfaceLow,
                                    RoundedCornerShape(12.dp)
                                )
                                .border(
                                    1.dp,
                                    if (isSelected) palette.gold else palette.borderSubtle,
                                    RoundedCornerShape(12.dp)
                                )
                                .clickable { selectedTime = time }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = time,
                                color = if (isSelected) palette.gold else palette.textMuted,
                                fontSize = 10.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    timeOptions.drop(3).forEach { time ->
                        val isSelected = selectedTime == time
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .background(
                                    if (isSelected) palette.gold.copy(alpha = 0.2f) else palette.surfaceLow,
                                    RoundedCornerShape(12.dp)
                                )
                                .border(
                                    1.dp,
                                    if (isSelected) palette.gold else palette.borderSubtle,
                                    RoundedCornerShape(12.dp)
                                )
                                .clickable { selectedTime = time }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = time,
                                color = if (isSelected) palette.gold else palette.textMuted,
                                fontSize = 10.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }

            // 3. Komitmen Batin
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "3. Komitmen / Niat Batin Utama",
                    color = palette.textPrimary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                commitmentOptions.forEach { opt ->
                    val isSelected = selectedCommitment == opt
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
                            .clickable { selectedCommitment = opt }
                            .padding(horizontal = 12.dp, vertical = 9.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = opt,
                            color = if (isSelected) palette.gold else palette.textPrimary,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                        )
                        if (isSelected) {
                            Text(text = "✓", color = palette.gold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // Submit Button
        Button(
            onClick = {
                val finalName = if (nameInput.isNotBlank()) nameInput.trim() else "Dimas"
                onComplete(finalName, selectedTime, selectedCommitment)
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(containerColor = palette.gold),
            shape = RoundedCornerShape(26.dp)
        ) {
            Text(
                text = "✦ Ikat Janji dengan Secre & Mulai",
                color = palette.goldDark,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )
        }

        Text(
            text = "🔒 Janji batin ini mengaktifkan Rasi Aquila Noctis dan Vault pribadimu",
            color = palette.textMuted,
            fontSize = 10.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))
    }
}
