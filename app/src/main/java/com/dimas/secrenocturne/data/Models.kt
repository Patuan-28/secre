package com.dimas.secrenocturne.data

import androidx.compose.ui.graphics.Color
import java.util.UUID

data class StarEntry(
    val id: String = UUID.randomUUID().toString(),
    val nodeIcon: String,
    val ringColor: Color,
    val title: String,
    val hasCheck: Boolean = false,
    val category: String,
    val description: String,
    val time: String,
    val footerBadge: String,
    val badgeColor: Color
)

data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    val sender: String, // "Secre" or "Dimas"
    val time: String,
    val text: String,
    val isAlarm: Boolean = false,
    val awardedStar: Boolean = false,
    val starTitle: String? = null,
    val starSubtitle: String? = null
)

data class UserProfile(
    val name: String = "Dimas",
    val title: String = "Penjaga Fajar Tingkat II",
    val targetWakeTime: String = "06:30 WIB",
    val commitment: String = "Disiplin Fajar & Ketenangan Jiwa",
    val joinDate: String = "15 Nov 2024",
    val streakDays: Int = 7
)
