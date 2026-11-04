package com.dimas.secrenocturne.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import com.dimas.secrenocturne.data.StarEntry
import com.dimas.secrenocturne.ui.theme.LocalNocturnePalette

@Composable
fun VaultScreen(
    starsCount: Int = 18,
    lazyTickets: Int = 0,
    streakDays: Int = 7,
    entries: List<StarEntry> = emptyList()
) {
    val palette = LocalNocturnePalette.current
    val scrollState = rememberScrollState()
    var selectedFilter by remember { mutableStateOf("Semua") }

    val filteredEntries = when (selectedFilter) {
        "Disiplin Diri" -> entries.filter { it.category == "Disiplin Diri" }
        "Kebaikan Sesama" -> entries.filter { it.category == "Kebaikan Sesama" }
        "Ketenangan" -> entries.filter { it.category == "Ketenangan" }
        "Kebugaran" -> entries.filter { it.category == "Kebugaran" }
        else -> entries
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(palette.background)
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Vault Hero Card
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .background(palette.gold.copy(alpha = 0.15f), CircleShape)
                            .border(1.dp, palette.gold.copy(alpha = 0.4f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = "⭐", fontSize = 18.sp)
                    }
                    Column {
                        Text(
                            text = "ARSIP SPIRITUAL SECRE",
                            color = palette.gold,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "Vault Segel Kebaikan",
                            color = palette.textPrimary,
                            fontSize = 16.sp,
                            fontFamily = FontFamily.Serif,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .background(palette.surfaceLow, RoundedCornerShape(10.dp))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = if (streakDays > 0) "● $streakDays Hari Aktif" else "● Belum Aktif",
                        color = if (streakDays > 0) palette.gold else palette.textMuted,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Text(
                text = "Setiap ikhtiar dan ketenangan yang kamu semai diabadikan dalam rasi bintang abadi. Tak ada usaha kecil yang luput dari pandangan semesta.",
                color = palette.textMuted,
                fontSize = 11.sp,
                lineHeight = 16.sp
            )

            // 3 Stats Grid matching real data!
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(
                    Triple(
                        "Bintang Dimiliki",
                        if (starsCount > 0) "$starsCount ⭐" else "0",
                        (if (starsCount > 0) "✓ Tersedia" else "Kosong") to palette.gold
                    ),
                    Triple(
                        "Tiket Malas",
                        if (lazyTickets > 0) "$lazyTickets 🎫" else "0",
                        (if (lazyTickets > 0) "Siap Pakai" else "Belum Ada") to palette.cyan
                    ),
                    Triple(
                        "Rekor Rasi",
                        if (streakDays > 0) "$streakDays Hari" else "0 Hari",
                        (if (streakDays > 0) "Beruntun" else "Mulai Hari Ini") to palette.lavender
                    )
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
                            fontSize = 15.sp,
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

        // Filter Chips Row
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Pilah Jejak Spiritual",
                    color = palette.textPrimary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Menampilkan ${filteredEntries.size} dari ${entries.size} Jejak",
                    color = palette.textMuted,
                    fontSize = 10.sp
                )
            }

            // Horizontally Scrollable Filter Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Filter Chip 1: Semua (menghitung total entri riil di Vault)
                val isSemua = selectedFilter == "Semua"
                Box(
                    modifier = Modifier
                        .background(
                            if (isSemua) palette.gold else palette.surfaceLow,
                            RoundedCornerShape(16.dp)
                        )
                        .border(
                            1.dp,
                            if (isSemua) palette.gold else palette.borderSubtle,
                            RoundedCornerShape(16.dp)
                        )
                        .clickable { selectedFilter = "Semua" }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "✦ Semua (${entries.size})",
                        color = if (isSemua) palette.goldDark else palette.textMuted,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Filter Chip 2: Disiplin Diri
                val disiplinCount = entries.count { it.category == "Disiplin Diri" }
                val isDisiplin = selectedFilter == "Disiplin Diri"
                Box(
                    modifier = Modifier
                        .background(
                            if (isDisiplin) palette.gold else palette.surfaceLow,
                            RoundedCornerShape(16.dp)
                        )
                        .border(
                            1.dp,
                            if (isDisiplin) palette.gold else palette.borderSubtle,
                            RoundedCornerShape(16.dp)
                        )
                        .clickable { selectedFilter = "Disiplin Diri" }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "⭐ Disiplin Diri ($disiplinCount)",
                        color = if (isDisiplin) palette.goldDark else palette.textMuted,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                // Filter Chip 3: Kebaikan Sesama
                val kebaikanCount = entries.count { it.category == "Kebaikan Sesama" }
                val isKebaikan = selectedFilter == "Kebaikan Sesama"
                Box(
                    modifier = Modifier
                        .background(
                            if (isKebaikan) palette.gold else palette.surfaceLow,
                            RoundedCornerShape(16.dp)
                        )
                        .border(
                            1.dp,
                            if (isKebaikan) palette.gold else palette.borderSubtle,
                            RoundedCornerShape(16.dp)
                        )
                        .clickable { selectedFilter = "Kebaikan Sesama" }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "💙 Kebaikan ($kebaikanCount)",
                        color = if (isKebaikan) palette.goldDark else palette.textMuted,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                // Filter Chip 4: Ketenangan
                val tenangCount = entries.count { it.category == "Ketenangan" }
                val isTenang = selectedFilter == "Ketenangan"
                Box(
                    modifier = Modifier
                        .background(
                            if (isTenang) palette.gold else palette.surfaceLow,
                            RoundedCornerShape(16.dp)
                        )
                        .border(
                            1.dp,
                            if (isTenang) palette.gold else palette.borderSubtle,
                            RoundedCornerShape(16.dp)
                        )
                        .clickable { selectedFilter = "Ketenangan" }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "🧘 Ketenangan ($tenangCount)",
                        color = if (isTenang) palette.goldDark else palette.textMuted,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                // Filter Chip 5: Kebugaran
                val bugarCount = entries.count { it.category == "Kebugaran" }
                val isBugar = selectedFilter == "Kebugaran"
                Box(
                    modifier = Modifier
                        .background(
                            if (isBugar) palette.gold else palette.surfaceLow,
                            RoundedCornerShape(16.dp)
                        )
                        .border(
                            1.dp,
                            if (isBugar) palette.gold else palette.borderSubtle,
                            RoundedCornerShape(16.dp)
                        )
                        .clickable { selectedFilter = "Kebugaran" }
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "🌱 Kebugaran ($bugarCount)",
                        color = if (isBugar) palette.goldDark else palette.textMuted,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        // Dynamic Vertical Timeline mapped from filteredEntries
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (filteredEntries.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Belum ada segel untuk kategori $selectedFilter.",
                        color = palette.textMuted,
                        fontSize = 12.sp
                    )
                }
            } else {
                filteredEntries.forEachIndexed { index, entry ->
                    VaultTimelineEntry(
                        nodeIcon = entry.nodeIcon,
                        ringColor = entry.ringColor,
                        title = entry.title,
                        hasCheck = entry.hasCheck,
                        category = entry.category,
                        description = entry.description,
                        time = entry.time,
                        footerBadge = entry.footerBadge,
                        badgeColor = entry.badgeColor,
                        isLast = index == filteredEntries.lastIndex
                    )
                }
            }
        }

        // Footer: Scroll Down Prompt
        Column(
            modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(palette.surfaceLow, CircleShape)
                    .border(1.dp, palette.borderSubtle, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "↓", color = palette.gold, fontSize = 14.sp)
            }
            Text(
                text = "Seluruh ikhtiar dan kebaikan tersimpan aman di kubah batin Secre",
                color = palette.textPrimary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "${entries.size} segel tersimpan abadi tanpa ada yang luput",
                color = palette.textMuted,
                fontSize = 10.sp
            )
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun VaultTimelineEntry(
    nodeIcon: String,
    ringColor: Color,
    title: String,
    hasCheck: Boolean,
    category: String,
    description: String,
    time: String,
    footerBadge: String,
    badgeColor: Color,
    isLast: Boolean = false
) {
    val palette = LocalNocturnePalette.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Vertical Timeline Node with Connecting Line
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(36.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(ringColor.copy(alpha = 0.2f), CircleShape)
                    .border(1.5.dp, ringColor, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(text = nodeIcon, fontSize = 15.sp)
            }
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(1.5.dp)
                        .height(90.dp)
                        .background(palette.borderSubtle)
                )
            }
        }

        // Timeline Content Card
        Column(
            modifier = Modifier
                .weight(1f)
                .background(palette.surface, RoundedCornerShape(20.dp))
                .border(1.dp, palette.borderSubtle, RoundedCornerShape(20.dp))
                .padding(14.dp),
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
                    Text(
                        text = title,
                        color = palette.textPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif
                    )
                    if (hasCheck) {
                        Text(text = "✓", color = palette.gold, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
                Box(
                    modifier = Modifier
                        .background(palette.surfaceLow, RoundedCornerShape(8.dp))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(text = category, color = palette.gold, fontSize = 9.sp, fontWeight = FontWeight.Bold)
                }
            }

            Text(
                text = description,
                color = palette.textPrimary,
                fontSize = 11.sp,
                lineHeight = 16.sp
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 2.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = time, color = palette.textMuted, fontSize = 10.sp)
                Text(text = footerBadge, color = badgeColor, fontSize = 10.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}
