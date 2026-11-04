package com.dimas.secrenocturne

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dimas.secrenocturne.data.ChatMessage
import com.dimas.secrenocturne.data.SecreAiService
import com.dimas.secrenocturne.data.StarEntry
import com.dimas.secrenocturne.data.UserProfile
import com.dimas.secrenocturne.ui.screens.*
import com.dimas.secrenocturne.ui.theme.*
import com.dimas.secrenocturne.util.WallpaperHelper
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainApp()
        }
    }
}

@Composable
fun MainApp() {
    val context = LocalContext.current
    val prefs = remember { context.getSharedPreferences("secre_nocturne_prefs", Context.MODE_PRIVATE) }

    var themeMode by remember {
        mutableStateOf(prefs.getString("theme_mode", "auto") ?: "auto")
    }

    var currentHour by remember {
        mutableIntStateOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY))
    }
    var liveTimeString by remember {
        mutableStateOf(SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date()))
    }

    LaunchedEffect(Unit) {
        while (true) {
            val now = Calendar.getInstance()
            currentHour = now.get(Calendar.HOUR_OF_DAY)
            liveTimeString = SimpleDateFormat("HH:mm", Locale.getDefault()).format(now.time)
            delay(1000)
        }
    }

    val isDaytime = currentHour in 6..17

    val isDark = when (themeMode) {
        "light" -> false
        "dark" -> true
        else -> !isDaytime // 06:00 - 18:00 is light, 18:00 - 06:00 is dark
    }

    SecreNocturneTheme(isDark = isDark) {
        var isOnboarded by remember {
            mutableStateOf(prefs.getBoolean("is_onboarded", false))
        }
        var showProfileScreen by remember { mutableStateOf(false) }

        var userProfile by remember {
            mutableStateOf(
                UserProfile(
                    name = prefs.getString("user_name", "") ?: "",
                    title = prefs.getString("user_title", "Penjaga Fajar") ?: "Penjaga Fajar",
                    targetWakeTime = prefs.getString("user_wake_time", "06:30 WIB") ?: "06:30 WIB",
                    commitment = prefs.getString(
                        "user_commitment",
                        "Membangun disiplin diri, berbuat baik tanpa pamrih, dan merawat ketenangan jiwa setiap hari."
                    ) ?: "Membangun disiplin diri, berbuat baik tanpa pamrih, dan merawat ketenangan jiwa setiap hari.",
                    joinDate = prefs.getString("user_join_date", "Hari Ini") ?: "Hari Ini",
                    streakDays = prefs.getInt("user_streak", 1)
                )
            )
        }

        var currentTab by remember { mutableStateOf("langit") }
        var starsCount by remember { mutableIntStateOf(prefs.getInt("stars_count", 18)) }
        var lazyTickets by remember { mutableIntStateOf(prefs.getInt("lazy_tickets", 0)) }
        var soreTicketUsedToday by remember { mutableStateOf(false) }
        var lastStarTitle by remember { mutableStateOf("Bangun Pagi & Olahraga") }
        var lastStarTime by remember { mutableStateOf("⏱ Disegel 2 jam lalu") }

        val vaultEntries = remember {
            mutableStateListOf(
                StarEntry(
                    nodeIcon = "☀️",
                    ringColor = StarlightGold,
                    title = "Bintang Fajar",
                    hasCheck = true,
                    category = "Disiplin Diri",
                    description = "Bangun tepat waktu pukul 06.28 WIB & jalan pagi 15 menit menikmati udara segar.",
                    time = "⏱ 15 Nov • 06:30 WIB",
                    footerBadge = "☀️ Cahaya Emas Terang",
                    badgeColor = StarlightGold
                ),
                StarEntry(
                    nodeIcon = "💙",
                    ringColor = NebulaCyan,
                    title = "Bintang Kebaikan",
                    hasCheck = false,
                    category = "Kebaikan Sesama",
                    description = "Membantu teman satu tim menyelesaikan blocker coding sebelum deadline sprint malam.",
                    time = "⏱ 14 Nov • 16:45 WIB",
                    footerBadge = "💙 Empati Tulus",
                    badgeColor = NebulaCyan
                ),
                StarEntry(
                    nodeIcon = "🧠",
                    ringColor = TwilightLavender,
                    title = "Bintang Kesabaran",
                    hasCheck = false,
                    category = "Ketenangan",
                    description = "Tetap tenang dan mendengarkan masukan saat revisi mendadak datang tanpa frustrasi.",
                    time = "⏱ 13 Nov • 19:10 WIB",
                    footerBadge = "💨 Nafas Sadar",
                    badgeColor = TwilightLavender
                ),
                StarEntry(
                    nodeIcon = "⚡",
                    ringColor = Color(0xFF4A4E63),
                    title = "Bintang Raga",
                    hasCheck = false,
                    category = "Kebugaran",
                    description = "Olahraga ringan dan peregangan 20 menit saat sore untuk menjaga vitalitas tubuh.",
                    time = "⏱ 12 Nov • 17:00 WIB",
                    footerBadge = "🧘 Harmoni Fisik",
                    badgeColor = StarlightGold
                ),
                StarEntry(
                    nodeIcon = "⏰",
                    ringColor = Color(0xFF4A4E63),
                    title = "Bintang Fajar",
                    hasCheck = false,
                    category = "Disiplin Diri",
                    description = "Bangun fajar tanpa menekan tombol snooze sama sekali. Kesadaran penuh seketika.",
                    time = "⏱ 11 Nov • 06:15 WIB",
                    footerBadge = "☀️ Bangkit Kuat",
                    badgeColor = StarlightGold
                )
            )
        }

        val effectiveName = if (userProfile.name.isNotBlank()) userProfile.name else "Pengembara"

        val chatMessages = remember(userProfile.name) {
            mutableStateListOf(
                ChatMessage(
                    sender = "Secre",
                    time = "06:30 WIB",
                    text = "*Patuk... Patuk...* Bangun $effectiveName! Matahari fajar sudah mengintip. Ayo segel bintang fajar pertamamu hari ini sebelum kabut pudar.",
                    isAlarm = true
                ),
                ChatMessage(
                    sender = effectiveName,
                    time = "06:32 WIB",
                    text = "Udah bangun, Secre! Lagi minum air putih hangat dan bersiap berkarya hari ini."
                ),
                ChatMessage(
                    sender = "Secre",
                    time = "06:33 WIB",
                    text = "Bagus sekali. Setiap fajar yang kau menangkan mengukuhkan satu bintang di rasi jiwamu!",
                    awardedStar = true,
                    starTitle = "+1 Bintang Fajar",
                    starSubtitle = "Rasi Kedisiplinan Disegel"
                )
            )
        }

        val coroutineScope = rememberCoroutineScope()
        val secreAi = remember { SecreAiService() }
        var isTyping by remember { mutableStateOf(false) }

        fun updateStars(newCount: Int) {
            val clamped = newCount.coerceAtLeast(0)
            starsCount = clamped
            prefs.edit().putInt("stars_count", clamped).apply()
            coroutineScope.launch {
                WallpaperHelper.syncWallpaperIfEnabled(context, clamped)
            }
        }

        fun sealStar(title: String, category: String, desc: String) {
            updateStars(starsCount + 1)
            val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date()) + " WIB"
            lastStarTitle = title
            lastStarTime = "⏱ Disegel baru saja"
            vaultEntries.add(
                0,
                StarEntry(
                    nodeIcon = when (category) {
                        "Kebaikan Sesama" -> "💙"
                        "Ketenangan" -> "🧠"
                        "Kebugaran" -> "🌱"
                        else -> "☀️"
                    },
                    ringColor = when (category) {
                        "Kebaikan Sesama" -> NebulaCyan
                        "Ketenangan" -> TwilightLavender
                        "Kebugaran" -> Color(0xFF55D688)
                        else -> StarlightGold
                    },
                    title = title,
                    hasCheck = true,
                    category = category,
                    description = desc,
                    time = "⏱ Hari ini • $currentTime",
                    footerBadge = "✨ Segel Baru",
                    badgeColor = StarlightGold
                )
            )
        }

        fun handleSendMessage(userText: String) {
            val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date()) + " WIB"
            chatMessages.add(
                ChatMessage(
                    sender = effectiveName,
                    time = currentTime,
                    text = userText
                )
            )

            val positiveKeywords = listOf(
                "bangun", "pagi", "fajar", "subuh", "bantu", "tolong", "teman", "sedekah",
                "olahraga", "lari", "jalan", "senyum", "kerja", "belajar", "coding",
                "sabar", "tenang", "syukur", "alhamdulillah", "semangat", "sehat",
                "selesai", "tuntas", "rapi", "baca", "buku", "ikhlas", "berhasil", "sukses"
            )
            val isPositive = positiveKeywords.any { userText.contains(it, ignoreCase = true) }

            isTyping = true
            coroutineScope.launch {
                delay(600)
                val replyText = secreAi.chatWithSecre(userText)
                val replyTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date()) + " WIB"

                if (isPositive) {
                    val starTitle = if (userText.contains("bantu", true) || userText.contains("teman", true)) {
                        "Bintang Kebaikan"
                    } else if (userText.contains("olahraga", true) || userText.contains("lari", true) || userText.contains("jalan", true)) {
                        "Bintang Kebugaran"
                    } else {
                        "Bintang Fajar & Disiplin"
                    }

                    val starCategory = if (userText.contains("bantu", true) || userText.contains("teman", true)) {
                        "Kebaikan Sesama"
                    } else if (userText.contains("olahraga", true) || userText.contains("lari", true) || userText.contains("jalan", true)) {
                        "Kebugaran"
                    } else {
                        "Disiplin Diri"
                    }

                    sealStar(title = starTitle, category = starCategory, desc = userText)

                    chatMessages.add(
                        ChatMessage(
                            sender = "Secre",
                            time = replyTime,
                            text = replyText,
                            awardedStar = true,
                            starTitle = "+1 $starTitle",
                            starSubtitle = "Tercatat di Vault dan Langit Malam"
                        )
                    )
                } else {
                    chatMessages.add(
                        ChatMessage(
                            sender = "Secre",
                            time = replyTime,
                            text = replyText,
                            awardedStar = false
                        )
                    )
                }
                isTyping = false
            }
        }

        // Khusus pemakaian pertama setelah install: Halaman Perkenalan Diri
        if (!isOnboarded) {
            OnboardingScreen(
                initialName = userProfile.name,
                onComplete = { name, time, commitment ->
                    val todayFormatted = SimpleDateFormat("d MMMM yyyy", Locale("id", "ID")).format(Date())
                    val updated = userProfile.copy(
                        name = name,
                        targetWakeTime = time,
                        commitment = commitment,
                        joinDate = todayFormatted,
                        streakDays = 1
                    )
                    userProfile = updated
                    prefs.edit()
                        .putBoolean("is_onboarded", true)
                        .putString("user_name", name)
                        .putString("user_wake_time", time)
                        .putString("user_commitment", commitment)
                        .putString("user_join_date", todayFormatted)
                        .putInt("user_streak", 1)
                        .apply()
                    isOnboarded = true
                }
            )
        } else if (showProfileScreen) {
            ProfileScreen(
                profile = userProfile,
                starsCount = starsCount,
                lazyTickets = lazyTickets,
                currentThemeMode = themeMode,
                onThemeModeChange = { mode ->
                    themeMode = mode
                    prefs.edit().putString("theme_mode", mode).apply()
                },
                onUpdateProfile = { updated ->
                    userProfile = updated
                    prefs.edit()
                        .putString("user_name", updated.name)
                        .putString("user_wake_time", updated.targetWakeTime)
                        .putString("user_commitment", updated.commitment)
                        .apply()
                },
                onRestartIntroduction = {
                    prefs.edit().putBoolean("is_onboarded", false).apply()
                    showProfileScreen = false
                    isOnboarded = false
                },
                onBack = { showProfileScreen = false }
            )
        } else {
            val palette = LocalNocturnePalette.current

            Scaffold(
                containerColor = palette.background,
                topBar = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(palette.background)
                            .padding(horizontal = 20.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(34.dp)
                                    .background(palette.surface, CircleShape)
                                    .border(1.dp, palette.gold.copy(alpha = 0.4f), CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "SECRE",
                                    color = palette.gold,
                                    fontSize = 7.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.5.sp
                                )
                            }
                            Column {
                                Text(
                                    text = "SECRE NOCTURNE",
                                    color = palette.textMuted,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp
                                )
                                Text(
                                    text = when (currentTab) {
                                        "langit" -> "Langit"
                                        "secre" -> "Secre"
                                        "vault" -> "Vault"
                                        else -> "Sanctum"
                                    },
                                    color = palette.textPrimary,
                                    fontSize = 18.sp,
                                    fontFamily = FontFamily.Serif,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            // Live Digital Clock Pill
                            Box(
                                modifier = Modifier
                                    .background(palette.surface, RoundedCornerShape(14.dp))
                                    .border(1.dp, palette.cyan.copy(alpha = 0.45f), RoundedCornerShape(14.dp))
                                    .padding(horizontal = 8.dp, vertical = 5.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(5.dp)
                                            .background(palette.cyan, CircleShape)
                                    )
                                    Text(
                                        text = liveTimeString,
                                        color = palette.cyan,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily.Monospace
                                    )
                                }
                            }

                            // Gold Stars Counter Pill
                            Box(
                                modifier = Modifier
                                    .background(palette.surface, CircleShape)
                                    .border(1.dp, palette.gold.copy(alpha = 0.35f), CircleShape)
                                    .padding(horizontal = 10.dp, vertical = 5.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(text = "✨", fontSize = 11.sp)
                                    Text(
                                        text = "$starsCount",
                                        color = palette.goldLight,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            // User Profile Button
                            Box(
                                modifier = Modifier
                                    .size(34.dp)
                                    .background(palette.surface, CircleShape)
                                    .border(1.dp, palette.gold.copy(alpha = 0.4f), CircleShape)
                                    .clickable { showProfileScreen = true },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "👤", fontSize = 15.sp)
                            }
                        }
                    }
                },
                bottomBar = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(palette.bottomNavBg)
                            .border(width = 0.5.dp, color = palette.borderSubtle)
                            .navigationBarsPadding(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        listOf(
                            Triple("langit", "🌙", "Langit"),
                            Triple("secre", "🐦‍⬛", "Secre"),
                            Triple("vault", "🔒", "Vault"),
                            Triple("sanctum", "⛩️", "Sanctum")
                        ).forEach { (tabId, icon, label) ->
                            val isActive = currentTab == tabId
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { currentTab = tabId }
                                    .padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(
                                                if (isActive) palette.gold.copy(alpha = 0.15f)
                                                else Color.Transparent
                                            )
                                            .padding(horizontal = 14.dp, vertical = 4.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = icon,
                                            fontSize = 17.sp,
                                            color = if (isActive) palette.gold else palette.textMuted
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = label,
                                        color = if (isActive) palette.gold else palette.textMuted,
                                        fontSize = 11.sp,
                                        fontWeight = if (isActive) FontWeight.SemiBold else FontWeight.Normal
                                    )
                                    if (isActive) {
                                        Box(
                                            modifier = Modifier
                                                .padding(top = 3.dp)
                                                .size(3.5.dp)
                                                .background(palette.gold, CircleShape)
                                        )
                                    } else {
                                        Spacer(modifier = Modifier.height(6.5.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            ) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    when (currentTab) {
                        "langit" -> LangitScreen(
                            starsCount = starsCount,
                            lastStarTitle = lastStarTitle,
                            lastStarTime = lastStarTime,
                            liveTime = liveTimeString,
                            onAddStar = { title, category, desc ->
                                sealStar(title, category, desc)
                            }
                        )
                        "secre" -> SecreScreen(
                            messages = chatMessages,
                            isTyping = isTyping,
                            liveTime = liveTimeString,
                            onSendMessage = { text ->
                                handleSendMessage(text)
                            }
                        )
                        "vault" -> VaultScreen(
                            starsCount = starsCount,
                            lazyTickets = lazyTickets,
                            streakDays = userProfile.streakDays,
                            entries = vaultEntries
                        )
                        "sanctum" -> SanctumScreen(
                            starsCount = starsCount,
                            lazyTickets = lazyTickets,
                            streakDays = userProfile.streakDays,
                            soreTicketUsedToday = soreTicketUsedToday,
                            onRedeemTicket = {
                                if (starsCount >= 20) {
                                    updateStars(starsCount - 20)
                                    lazyTickets++
                                    prefs.edit().putInt("lazy_tickets", lazyTickets).apply()
                                }
                            },
                            onUseSoreTicket = {
                                soreTicketUsedToday = true
                            }
                        )
                    }
                }
            }
        }
    }
}
