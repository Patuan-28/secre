package com.dimas.secrenocturne.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.dimas.secrenocturne.data.ChatMessage
import com.dimas.secrenocturne.ui.theme.*

@Composable
fun SecreScreen(
    messages: List<ChatMessage> = emptyList(),
    isTyping: Boolean = false,
    liveTime: String = "",
    onSendMessage: (String) -> Unit = {}
) {
    val palette = LocalNocturnePalette.current
    var inputText by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    LaunchedEffect(messages.size, isTyping) {
        scrollState.animateScrollTo(scrollState.maxValue)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(palette.background)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        // Companion Status Card matching Screenshot 2
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
                    .size(52.dp)
                    .background(palette.cardBackground, CircleShape)
                    .border(1.dp, palette.gold.copy(alpha = 0.35f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "🐦‍⬛", fontSize = 24.sp)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Secre",
                        color = palette.textPrimary,
                        fontSize = 15.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = "✓", color = palette.gold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
                Text(text = "Penjaga Malam & Fajar", color = palette.textMuted, fontSize = 11.sp)
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "● AKTIF • MENJAGA LANGITMU",
                    color = palette.gold,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }

            // Top Right Badges
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(palette.surfaceLow, CircleShape)
                        .border(1.dp, palette.borderSubtle, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = if (palette.isDark) "🌙" else "☀️", fontSize = 14.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Time Indicator Badge showing real-time clock & sync
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .background(palette.surfaceLow, RoundedCornerShape(14.dp))
                    .border(1.dp, palette.borderSubtle, RoundedCornerShape(14.dp))
                    .padding(horizontal = 14.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(text = "🕒", fontSize = 12.sp)
                Text(
                    text = if (liveTime.isNotBlank()) "Waktu Nyata: $liveTime WIB • Sinkron" else "Fajar Hari Ini • 06:30 WIB",
                    color = palette.cyan,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Chat Message Stream
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            messages.forEach { msg ->
                if (msg.sender == "Secre") {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.92f)
                            .background(
                                palette.surface,
                                RoundedCornerShape(topStart = 4.dp, topEnd = 20.dp, bottomEnd = 20.dp, bottomStart = 20.dp)
                            )
                            .border(
                                1.dp,
                                palette.borderSubtle,
                                RoundedCornerShape(topStart = 4.dp, topEnd = 20.dp, bottomEnd = 20.dp, bottomStart = 20.dp)
                            )
                            .padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "Secre ${msg.time}", color = palette.gold, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            if (msg.isAlarm) {
                                Box(
                                    modifier = Modifier
                                        .background(palette.cyan.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                                        .padding(horizontal = 8.dp, vertical = 2.dp)
                                ) {
                                    Text(text = "⏱ ALARM TERJAWAB", color = palette.cyan, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                        Text(
                            text = msg.text,
                            color = palette.textPrimary,
                            fontSize = 12.sp,
                            lineHeight = 18.sp
                        )

                        if (msg.awardedStar) {
                            // Embedded Star Award Card
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(palette.cardBackground, RoundedCornerShape(14.dp))
                                    .border(1.dp, palette.gold.copy(alpha = 0.35f), RoundedCornerShape(14.dp))
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(34.dp)
                                            .background(palette.gold.copy(alpha = 0.15f), CircleShape),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(text = "⭐", fontSize = 16.sp)
                                    }
                                    Column {
                                        Text(
                                            text = msg.starTitle ?: "+1 Bintang Kebaikan",
                                            color = palette.textPrimary,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = msg.starSubtitle ?: "Disegel ke dalam Langit & Vault",
                                            color = palette.textMuted,
                                            fontSize = 10.sp
                                        )
                                    }
                                }
                                Text(text = "✨", color = palette.gold, fontSize = 16.sp)
                            }
                        }
                    }
                } else {
                    // User Reply
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.End
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .background(
                                    palette.gold,
                                    RoundedCornerShape(topStart = 20.dp, topEnd = 4.dp, bottomEnd = 20.dp, bottomStart = 20.dp)
                                )
                                .padding(14.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "${msg.time}  Kamu",
                                color = palette.goldDark,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = msg.text,
                                color = if (palette.isDark) Color(0xFF1F1600) else Color(0xFFFFFFFF),
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                lineHeight = 17.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = "✓ Didengar Secre",
                            color = palette.gold,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(end = 4.dp)
                        )
                    }
                }
            }

            if (isTyping) {
                // Typing Indicator
                Row(
                    modifier = Modifier
                        .background(palette.surface, RoundedCornerShape(14.dp))
                        .padding(horizontal = 14.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .background(palette.gold, CircleShape)
                    )
                    Text(
                        text = "Secre sedang merajut sihir kata...",
                        color = palette.goldLight,
                        fontSize = 11.sp,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    )
                }
            } else {
                // Status Indicator
                Row(
                    modifier = Modifier.padding(start = 6.dp, top = 2.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .background(palette.gold, CircleShape)
                    )
                    Text(
                        text = "Secre sedang menyimak hembusan nafas pagimu...",
                        color = palette.textMuted,
                        fontSize = 11.sp,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Quick 1-Click Report Action Header & Buttons
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
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
                    Text(text = "⚡", color = palette.gold, fontSize = 11.sp)
                    Text(
                        text = "LAPORAN 1-KLIK BINTANG FAJAR",
                        color = palette.gold,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.8.sp
                    )
                }
                Text(text = "Pilih untuk mencatat", color = palette.textMuted, fontSize = 10.sp)
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { onSendMessage("Lapor bangun pagi tepat waktu!") },
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = palette.surface),
                    shape = RoundedCornerShape(18.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, palette.borderSubtle),
                    contentPadding = PaddingValues(horizontal = 10.dp)
                ) {
                    Text(text = "🌅 Lapor bangun pagi", color = palette.textPrimary, fontSize = 11.sp)
                }
                Button(
                    onClick = { onSendMessage("Tadi bantu teman selesaikan blocker sprint.") },
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = palette.surface),
                    shape = RoundedCornerShape(18.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, palette.borderSubtle),
                    contentPadding = PaddingValues(horizontal = 10.dp)
                ) {
                    Text(text = "🤝 Tadi bantu teman kerja", color = palette.textPrimary, fontSize = 11.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Input Bar with Field & Send
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Mic icon button
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .background(palette.cardBackground, CircleShape)
                    .border(1.dp, palette.borderSubtle, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "🎙️", fontSize = 17.sp)
            }

            // Input field
            OutlinedTextField(
                value = inputText,
                onValueChange = { inputText = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text(text = "Bisikkan kata pada Secre", color = palette.textMuted, fontSize = 12.sp) },
                trailingIcon = {
                    Text(text = "✨", color = palette.gold, fontSize = 14.sp)
                },
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = palette.gold,
                    unfocusedBorderColor = palette.borderSubtle,
                    focusedContainerColor = palette.surface,
                    unfocusedContainerColor = palette.surface,
                    focusedTextColor = palette.textPrimary,
                    unfocusedTextColor = palette.textPrimary
                ),
                maxLines = 1
            )

            // Send button
            Button(
                onClick = {
                    if (inputText.isNotBlank()) {
                        onSendMessage(inputText)
                        inputText = ""
                    }
                },
                modifier = Modifier.size(46.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = palette.gold),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "➤",
                    color = if (palette.isDark) palette.goldDark else Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Security Encryption Note
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "🔒", fontSize = 10.sp)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Obrolan terenkripsi ruang batin, terjaga di Sanctum Secre",
                color = palette.textMuted,
                fontSize = 10.sp
            )
        }

        Spacer(modifier = Modifier.height(50.dp))
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF11131D, widthDp = 390, heightDp = 844)
@Composable
fun SecreScreenPreview() {
    SecreNocturneTheme {
        SecreScreen()
    }
}
