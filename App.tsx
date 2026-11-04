import React, { useState } from 'react';
import {
  StyleSheet,
  Text,
  View,
  TouchableOpacity,
  ScrollView,
  SafeAreaView,
  StatusBar,
  Dimensions,
  TextInput,
  Alert
} from 'react-native';

const { width } = Dimensions.get('window');

// Nocturne Native Color Palette
const COLORS = {
  background: '#11131d',
  card: '#1d1f2a',
  cardLow: '#191b26',
  gold: '#f6c85f',
  goldLight: '#ffe29a',
  goldDark: '#251a00',
  cyan: '#7bd0ff',
  purple: '#c084fc',
  textMain: '#e1e1f1',
  textMuted: '#94a3b8',
  border: 'rgba(255, 255, 255, 0.08)',
};

export default function App() {
  const [currentTab, setCurrentTab] = useState<'langit' | 'secre' | 'vault' | 'sanctum'>('langit');
  const [starsCount, setStarsCount] = useState(18);
  const [inputChat, setInputChat] = useState('');
  const [chatMessages, setChatMessages] = useState([
    {
      id: '1',
      sender: 'secre',
      text: '*Patuk... Patuk...* Bangun Dimas! Matahari fajar sudah mengintip. Ayo segel bintang fajar pertamamu hari ini sebelum kabut pudar.',
      badge: 'Ritual Fajar Aktif'
    },
    {
      id: '2',
      sender: 'user',
      text: 'Udah bangun, Secre! Lagi minum air putih hangat dan bersiap kerja.'
    },
    {
      id: '3',
      sender: 'secre',
      text: 'Bagus. Satu bintang disiplin baru telah ditambahkan ke rasi malammu!'
    }
  ]);

  const handleSendChat = (textToSend?: string) => {
    const text = textToSend || inputChat;
    if (!text.trim()) return;

    setChatMessages((prev) => [
      ...prev,
      { id: Date.now().toString(), sender: 'user', text }
    ]);
    setInputChat('');

    setTimeout(() => {
      setStarsCount((s) => s + 1);
      setChatMessages((prev) => [
        ...prev,
        {
          id: (Date.now() + 1).toString(),
          sender: 'secre',
          text: `*Patuk...* Catatanmu sudah gue dengar, Dimas. Kebaikan itu resmi gue segel jadi bintang ke-${starsCount + 1}.`
        }
      ]);
    }, 600);
  };

  return (
    <SafeAreaView style={styles.container}>
      <StatusBar barStyle="light-content" backgroundColor="#11131d" />

      {/* TOP HEADER */}
      <View style={styles.header}>
        <View style={styles.headerLeft}>
          <View style={styles.logoCircle}>
            <Text style={styles.logoText}>✦</Text>
          </View>
          <View>
            <Text style={styles.headerSubtitle}>SECRE NOCTURNE</Text>
            <Text style={styles.headerTitle}>
              {currentTab === 'langit' && 'Langit'}
              {currentTab === 'secre' && 'Secre'}
              {currentTab === 'vault' && 'Vault'}
              {currentTab === 'sanctum' && 'Sanctum'}
            </Text>
          </View>
        </View>

        <View style={styles.headerRight}>
          <View style={styles.starBadge}>
            <Text style={styles.starIcon}>✨</Text>
            <Text style={styles.starText}>{100 + starsCount}</Text>
          </View>
        </View>
      </View>

      {/* SCREEN CONTENT */}
      <ScrollView contentContainerStyle={styles.scrollContent} showsVerticalScrollIndicator={false}>

        {/* ================= TAB 1: LANGIT ================= */}
        {currentTab === 'langit' && (
          <View style={styles.tabContent}>
            
            {/* Penjaga Langit Hero */}
            <View style={styles.heroCard}>
              <View style={styles.avatarCircle}>
                <Text style={{ fontSize: 28 }}>🐦‍⬛</Text>
              </View>
              <View style={{ flex: 1 }}>
                <Text style={styles.heroBadge}>● PENJAGA LANGIT SECRE</Text>
                <Text style={styles.heroTitle}>"Langit malammu bersinar lebih terang hari ini,..."</Text>
                <Text style={styles.heroSubtitle}>Keheningan membawa kedamaian yang tersegel.</Text>
              </View>
            </View>

            {/* Quick Pills */}
            <View style={styles.pillRow}>
              <View style={styles.pillBox}>
                <Text style={styles.pillText}>‹ {starsCount} Bintang Tersegel</Text>
                <Text style={styles.pillMuted}>Nov</Text>
              </View>
              <View style={styles.pillBox}>
                <Text style={{ color: COLORS.cyan }}>🌙</Text>
                <Text style={styles.pillText}>Bulan Sabit (II)</Text>
              </View>
            </View>

            {/* Constellation Card Native Simulation */}
            <View style={styles.constellationCard}>
              <View style={styles.cardHeaderRow}>
                <Text style={styles.cardHeaderTitle}>✦ Rasi Aquila Noctis</Text>
                <View style={styles.statusPill}>
                  <Text style={styles.statusPillText}>{starsCount}/25 Bersinar</Text>
                </View>
              </View>

              {/* Native Star Map Canvas Container */}
              <View style={styles.starCanvasBox}>
                <View style={[styles.starDot, { top: '30%', left: '25%' }]} />
                <View style={[styles.starDot, { top: '22%', left: '40%' }]} />
                <View style={[styles.starDotActive, { top: '38%', left: '50%' }]}>
                  <View style={styles.starDotCenter} />
                </View>
                <View style={[styles.starDot, { top: '30%', left: '65%' }]} />
                <View style={[styles.starDot, { top: '45%', left: '78%' }]} />
                <View style={[styles.starDot, { top: '65%', left: '35%' }]} />
                <View style={[styles.starDot, { top: '60%', left: '60%' }]} />

                {/* Star Tooltip */}
                <View style={styles.tooltipBox}>
                  <Text style={{ color: COLORS.gold, fontSize: 13 }}>✦</Text>
                  <View>
                    <Text style={styles.tooltipTitle}>Bintang Olahraga & Fajar</Text>
                    <Text style={styles.tooltipMeta}>Tersegel 2 jam lalu</Text>
                  </View>
                </View>
              </View>

              <View style={styles.cardFooterRow}>
                <Text style={styles.cardFooterMuted}>✦ Sentuh bintang untuk membuka memori</Text>
                <Text style={styles.cardFooterMuted}>{25 - starsCount} TERSISA</Text>
              </View>
            </View>

            {/* Action Buttons */}
            <TouchableOpacity
              style={styles.goldButton}
              activeOpacity={0.8}
              onPress={() => {
                setStarsCount(s => s + 1);
                Alert.alert("✨ Bintang Tersegel!", "Satu kebaikan baru telah bersinar di Rasi Aquila Noctis.");
              }}
            >
              <Text style={styles.goldButtonText}>+ Klaim & Segel Bintang Hari Ini</Text>
            </TouchableOpacity>

            <TouchableOpacity
              style={styles.darkButton}
              activeOpacity={0.8}
              onPress={() => Alert.alert("OLED Ready", "Wallpaper Lockscreen telah siap disinkronkan.")}
            >
              <Text style={styles.darkButtonText}>🌌 Pratinjau Wallpaper HD Lockscreen</Text>
              <View style={styles.oledBadge}>
                <Text style={styles.oledBadgeText}>OLED READY</Text>
              </View>
            </TouchableOpacity>

            {/* Progress Card */}
            <View style={styles.card}>
              <View style={styles.cardHeaderRow}>
                <Text style={styles.pillText}>∞ RASI LENGKAP</Text>
                <Text style={styles.pillMuted}>{starsCount} / 25</Text>
              </View>
              <View style={styles.progressBarBg}>
                <View style={[styles.progressBarFill, { width: `${(starsCount / 25) * 100}%` }]} />
              </View>
              <View style={styles.cardHeaderRow}>
                <Text style={styles.cardFooterMuted}>{Math.round((starsCount / 25) * 100)}% Terbuka</Text>
                <Text style={{ color: COLORS.gold, fontSize: 11 }}>Sisa {25 - starsCount} Bintang</Text>
              </View>
            </View>

          </View>
        )}

        {/* ================= TAB 2: SECRE CHAT ================= */}
        {currentTab === 'secre' && (
          <View style={styles.tabContent}>
            
            {/* Secre Status Card */}
            <View style={styles.heroCard}>
              <View style={styles.avatarCircle}>
                <Text style={{ fontSize: 28 }}>🐦‍⬛</Text>
              </View>
              <View style={{ flex: 1 }}>
                <Text style={styles.heroTitle}>Secre (Nero) ✓</Text>
                <Text style={styles.heroSubtitle}>Penjaga Malam & Fajar</Text>
                <Text style={{ color: COLORS.gold, fontSize: 10, fontWeight: 'bold', marginTop: 2 }}>
                  ● AKTIF • MENJAGA LANGITMU
                </Text>
              </View>
            </View>

            {/* Chat Messages */}
            <View style={{ gap: 10 }}>
              {chatMessages.map((msg) => (
                <View
                  key={msg.id}
                  style={msg.sender === 'user' ? styles.userBubble : styles.secreBubble}
                >
                  <Text style={msg.sender === 'user' ? styles.userBubbleText : styles.secreBubbleText}>
                    {msg.text}
                  </Text>
                  {msg.badge && (
                    <Text style={{ color: COLORS.gold, fontSize: 10, marginTop: 4 }}>
                      ☁ {msg.badge}
                    </Text>
                  )}
                </View>
              ))}
            </View>

            {/* Quick 1-Click Chips */}
            <View style={{ flexDirection: 'row', gap: 8, marginTop: 8 }}>
              <TouchableOpacity
                style={styles.chipButton}
                onPress={() => handleSendChat('Lapor bangun pagi tepat waktu!')}
              >
                <Text style={styles.chipText}>🌅 Lapor bangun pagi</Text>
              </TouchableOpacity>
              <TouchableOpacity
                style={styles.chipButton}
                onPress={() => handleSendChat('Tadi baru bantu teman selesaikan masalahnya.')}
              >
                <Text style={styles.chipText}>🤝 Tadi bantu teman kerja</Text>
              </TouchableOpacity>
            </View>

            {/* Native Input Bar */}
            <View style={styles.inputContainer}>
              <TextInput
                style={styles.textInput}
                placeholder="Bisikkan kata pada Secre..."
                placeholderTextColor={COLORS.textMuted}
                value={inputChat}
                onChangeText={setInputChat}
              />
              <TouchableOpacity style={styles.sendButton} onPress={() => handleSendChat()}>
                <Text style={{ color: COLORS.goldDark, fontWeight: 'bold' }}>➤</Text>
              </TouchableOpacity>
            </View>

          </View>
        )}

        {/* ================= TAB 3: VAULT ================= */}
        {currentTab === 'vault' && (
          <View style={styles.tabContent}>
            
            <View style={styles.card}>
              <Text style={styles.heroBadge}>ARSIP SPIRITUAL SECRE</Text>
              <Text style={styles.heroTitle}>Vault Segel Kebaikan</Text>
              <Text style={[styles.heroSubtitle, { marginTop: 4 }]}>
                Setiap ikhtiar dan ketenangan yang kamu semai diabadikan dalam rasi bintang abadi.
              </Text>

              {/* 3 Stats Grid */}
              <View style={styles.statsGrid}>
                <View style={styles.statBox}>
                  <Text style={styles.statMuted}>Bintang Diraih</Text>
                  <Text style={styles.statValue}>{starsCount}</Text>
                  <Text style={{ color: COLORS.gold, fontSize: 10 }}>✓ Disegel</Text>
                </View>
                <View style={styles.statBox}>
                  <Text style={styles.statMuted}>Tiket Malas</Text>
                  <Text style={styles.statValue}>0</Text>
                  <Text style={{ color: COLORS.cyan, fontSize: 10 }}>▤ Nir-Gagal</Text>
                </View>
                <View style={styles.statBox}>
                  <Text style={styles.statMuted}>Rekor Rasi</Text>
                  <Text style={styles.statValue}>7 Hari</Text>
                  <Text style={{ color: COLORS.purple, fontSize: 10 }}>Beruntun</Text>
                </View>
              </View>
            </View>

            {/* Timeline Items */}
            <View style={[styles.card, { borderLeftWidth: 3, borderLeftColor: COLORS.gold }]}>
              <Text style={styles.itemTitle}>Bintang Fajar ✓</Text>
              <Text style={styles.itemDesc}>Bangun tepat waktu pukul 06.28 WIB & jalan pagi 15 menit.</Text>
              <Text style={styles.itemTime}>⏱ 15 Nov • 06:30 WIB · Disiplin Diri</Text>
            </View>

            <View style={[styles.card, { borderLeftWidth: 3, borderLeftColor: COLORS.cyan }]}>
              <Text style={styles.itemTitle}>Bintang Kebaikan</Text>
              <Text style={styles.itemDesc}>Membantu teman satu tim menyelesaikan blocker coding sebelum deadline.</Text>
              <Text style={styles.itemTime}>⏱ 14 Nov • 16:45 WIB · Kebaikan Sesama</Text>
            </View>

            <View style={[styles.card, { borderLeftWidth: 3, borderLeftColor: COLORS.purple }]}>
              <Text style={styles.itemTitle}>Bintang Kesabaran</Text>
              <Text style={styles.itemDesc}>Tetap tenang dan mendengarkan masukan saat revisi mendadak datang.</Text>
              <Text style={styles.itemTime}>⏱ 13 Nov • 19:10 WIB · Ketenangan</Text>
            </View>

          </View>
        )}

        {/* ================= TAB 4: SANCTUM ================= */}
        {currentTab === 'sanctum' && (
          <View style={styles.tabContent}>
            
            {/* Ikatan Card */}
            <View style={styles.card}>
              <Text style={styles.heroBadge}>★ IKATAN RUHANI</Text>
              <Text style={styles.heroTitle}>Level 2: Sahabat Akrab</Text>
              <Text style={styles.heroSubtitle}>68% menuju Level 3: Penjaga Jiwa</Text>
              <View style={[styles.progressBarBg, { marginTop: 8 }]}>
                <View style={[styles.progressBarFill, { width: '68%' }]} />
              </View>
            </View>

            {/* Tiket Malas Card */}
            <View style={styles.card}>
              <Text style={styles.heroBadge}>BEBAS BEBAN PIKIRAN</Text>
              <Text style={styles.heroTitle}>Tiket Malas Seharian</Text>
              <Text style={styles.heroSubtitle}>
                Hak istirahat total 24 jam tanpa ada tuntutan rasa bersalah, tenggat batin, atau tuntutan dunia.
              </Text>
              <View style={[styles.progressBarBg, { marginTop: 8 }]}>
                <View style={[styles.progressBarFill, { width: '90%' }]} />
              </View>
              <TouchableOpacity
                style={[styles.darkButton, { marginTop: 10 }]}
                disabled={starsCount < 20}
                onPress={() => Alert.alert("Tiket Malas", "Tiket Malas Berhasil Ditukar!")}
              >
                <Text style={styles.darkButtonText}>
                  {starsCount >= 20 ? 'Tukarkan (20 ⭐)' : '🔒 Tersisa 2 Bintang Lagi'}
                </Text>
              </TouchableOpacity>
            </View>

          </View>
        )}

      </ScrollView>

      {/* BOTTOM NATIVE NAVIGATION BAR */}
      <View style={styles.bottomNav}>
        <TouchableOpacity style={styles.navItem} onPress={() => setCurrentTab('langit')}>
          <Text style={[styles.navIcon, currentTab === 'langit' && styles.navIconActive]}>🌙</Text>
          <Text style={[styles.navLabel, currentTab === 'langit' && styles.navLabelActive]}>Langit</Text>
        </TouchableOpacity>

        <TouchableOpacity style={styles.navItem} onPress={() => setCurrentTab('secre')}>
          <Text style={[styles.navIcon, currentTab === 'secre' && styles.navIconActive]}>🐦‍⬛</Text>
          <Text style={[styles.navLabel, currentTab === 'secre' && styles.navLabelActive]}>Secre</Text>
        </TouchableOpacity>

        <TouchableOpacity style={styles.navItem} onPress={() => setCurrentTab('vault')}>
          <Text style={[styles.navIcon, currentTab === 'vault' && styles.navIconActive]}>🔒</Text>
          <Text style={[styles.navLabel, currentTab === 'vault' && styles.navLabelActive]}>Vault</Text>
        </TouchableOpacity>

        <TouchableOpacity style={styles.navItem} onPress={() => setCurrentTab('sanctum')}>
          <Text style={[styles.navIcon, currentTab === 'sanctum' && styles.navIconActive]}>🏛️</Text>
          <Text style={[styles.navLabel, currentTab === 'sanctum' && styles.navLabelActive]}>Sanctum</Text>
        </TouchableOpacity>
      </View>

    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: COLORS.background,
  },
  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingHorizontal: 20,
    paddingTop: 12,
    paddingBottom: 10,
    borderBottomWidth: 1,
    borderBottomColor: COLORS.border,
  },
  headerLeft: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 10,
  },
  logoCircle: {
    width: 32,
    height: 32,
    borderRadius: 16,
    backgroundColor: '#1c1e2b',
    borderWidth: 1,
    borderColor: 'rgba(246, 200, 95, 0.3)',
    alignItems: 'center',
    justifyContent: 'center',
  },
  logoText: {
    color: COLORS.gold,
    fontSize: 14,
    fontWeight: 'bold',
  },
  headerSubtitle: {
    color: COLORS.textMuted,
    fontSize: 9,
    letterSpacing: 1.5,
    fontWeight: '600',
  },
  headerTitle: {
    color: '#fff',
    fontSize: 18,
    fontWeight: 'bold',
  },
  headerRight: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  starBadge: {
    flexDirection: 'row',
    alignItems: 'center',
    backgroundColor: '#1c1e2b',
    paddingHorizontal: 12,
    paddingVertical: 4,
    borderRadius: 20,
    borderWidth: 1,
    borderColor: 'rgba(246, 200, 95, 0.3)',
    gap: 4,
  },
  starIcon: {
    fontSize: 12,
  },
  starText: {
    color: COLORS.gold,
    fontWeight: 'bold',
    fontSize: 12,
  },
  scrollContent: {
    paddingHorizontal: 16,
    paddingVertical: 14,
    paddingBottom: 90,
  },
  tabContent: {
    gap: 14,
  },
  heroCard: {
    backgroundColor: COLORS.card,
    borderRadius: 24,
    padding: 16,
    borderWidth: 1,
    borderColor: COLORS.border,
    flexDirection: 'row',
    alignItems: 'center',
    gap: 14,
  },
  avatarCircle: {
    width: 52,
    height: 52,
    borderRadius: 26,
    backgroundColor: '#151724',
    borderWidth: 1,
    borderColor: 'rgba(246, 200, 95, 0.3)',
    alignItems: 'center',
    justifyContent: 'center',
  },
  heroBadge: {
    color: COLORS.gold,
    fontSize: 10,
    fontWeight: 'bold',
    letterSpacing: 1,
  },
  heroTitle: {
    color: '#fff',
    fontSize: 14,
    fontWeight: 'bold',
    marginTop: 2,
  },
  heroSubtitle: {
    color: COLORS.textMuted,
    fontSize: 11,
    marginTop: 2,
  },
  pillRow: {
    flexDirection: 'row',
    gap: 8,
  },
  pillBox: {
    flex: 1,
    backgroundColor: '#181a25',
    padding: 10,
    borderRadius: 16,
    borderWidth: 1,
    borderColor: 'rgba(255, 255, 255, 0.05)',
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  pillText: {
    color: COLORS.textMain,
    fontSize: 11,
    fontWeight: '600',
  },
  pillMuted: {
    color: COLORS.textMuted,
    fontSize: 10,
  },
  constellationCard: {
    backgroundColor: '#141622',
    borderRadius: 24,
    padding: 16,
    borderWidth: 1,
    borderColor: 'rgba(255, 255, 255, 0.1)',
  },
  cardHeaderRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  cardHeaderTitle: {
    color: '#fff',
    fontSize: 12,
    fontWeight: 'bold',
  },
  statusPill: {
    backgroundColor: 'rgba(255, 255, 255, 0.05)',
    paddingHorizontal: 8,
    paddingVertical: 2,
    borderRadius: 10,
  },
  statusPillText: {
    color: COLORS.textMuted,
    fontSize: 10,
  },
  starCanvasBox: {
    width: '100%',
    height: 220,
    backgroundColor: '#0d0f1a',
    borderRadius: 16,
    marginTop: 10,
    borderWidth: 1,
    borderColor: 'rgba(255, 255, 255, 0.05)',
    position: 'relative',
  },
  starDot: {
    position: 'absolute',
    width: 6,
    height: 6,
    borderRadius: 3,
    backgroundColor: COLORS.goldLight,
  },
  starDotActive: {
    position: 'absolute',
    width: 24,
    height: 24,
    borderRadius: 12,
    backgroundColor: 'rgba(246, 200, 95, 0.2)',
    borderWidth: 1,
    borderColor: COLORS.gold,
    alignItems: 'center',
    justifyContent: 'center',
  },
  starDotCenter: {
    width: 8,
    height: 8,
    borderRadius: 4,
    backgroundColor: '#fff',
  },
  tooltipBox: {
    position: 'absolute',
    bottom: 12,
    alignSelf: 'center',
    backgroundColor: '#1c1f2e',
    paddingHorizontal: 14,
    paddingVertical: 8,
    borderRadius: 16,
    flexDirection: 'row',
    alignItems: 'center',
    gap: 8,
    borderWidth: 1,
    borderColor: 'rgba(246, 200, 95, 0.3)',
  },
  tooltipTitle: {
    color: '#fff',
    fontSize: 11,
    fontWeight: 'bold',
  },
  tooltipMeta: {
    color: COLORS.textMuted,
    fontSize: 9,
  },
  cardFooterRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    marginTop: 10,
  },
  cardFooterMuted: {
    color: COLORS.textMuted,
    fontSize: 10,
  },
  goldButton: {
    backgroundColor: COLORS.gold,
    paddingVertical: 14,
    borderRadius: 30,
    alignItems: 'center',
    justifyContent: 'center',
  },
  goldButtonText: {
    color: COLORS.goldDark,
    fontWeight: 'bold',
    fontSize: 12,
    letterSpacing: 0.5,
  },
  darkButton: {
    backgroundColor: '#181a26',
    paddingVertical: 12,
    paddingHorizontal: 16,
    borderRadius: 30,
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    borderWidth: 1,
    borderColor: COLORS.border,
  },
  darkButtonText: {
    color: COLORS.textMain,
    fontSize: 11,
    fontWeight: '600',
  },
  oledBadge: {
    backgroundColor: '#0c2233',
    paddingHorizontal: 8,
    paddingVertical: 2,
    borderRadius: 10,
  },
  oledBadgeText: {
    color: COLORS.cyan,
    fontSize: 9,
    fontWeight: 'bold',
  },
  card: {
    backgroundColor: COLORS.card,
    borderRadius: 20,
    padding: 16,
    borderWidth: 1,
    borderColor: COLORS.border,
    gap: 6,
  },
  progressBarBg: {
    width: '100%',
    height: 8,
    backgroundColor: '#12141f',
    borderRadius: 4,
    overflow: 'hidden',
    marginTop: 4,
  },
  progressBarFill: {
    height: '100%',
    backgroundColor: COLORS.gold,
    borderRadius: 4,
  },
  secreBubble: {
    backgroundColor: COLORS.card,
    padding: 14,
    borderRadius: 18,
    borderTopLeftRadius: 4,
    maxWidth: '85%',
    borderWidth: 1,
    borderColor: COLORS.border,
  },
  secreBubbleText: {
    color: COLORS.textMain,
    fontSize: 12,
    lineHeight: 18,
  },
  userBubble: {
    backgroundColor: COLORS.gold,
    padding: 12,
    borderRadius: 18,
    borderTopRightRadius: 4,
    alignSelf: 'flex-end',
    maxWidth: '85%',
  },
  userBubbleText: {
    color: COLORS.goldDark,
    fontSize: 12,
    fontWeight: '500',
  },
  chipButton: {
    flex: 1,
    backgroundColor: COLORS.card,
    paddingVertical: 8,
    paddingHorizontal: 12,
    borderRadius: 16,
    borderWidth: 1,
    borderColor: COLORS.border,
    alignItems: 'center',
  },
  chipText: {
    color: COLORS.textMain,
    fontSize: 11,
  },
  inputContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 8,
    marginTop: 10,
  },
  textInput: {
    flex: 1,
    backgroundColor: COLORS.card,
    borderRadius: 24,
    paddingHorizontal: 16,
    paddingVertical: 10,
    color: '#fff',
    fontSize: 12,
    borderWidth: 1,
    borderColor: COLORS.border,
  },
  sendButton: {
    width: 40,
    height: 40,
    borderRadius: 20,
    backgroundColor: COLORS.gold,
    alignItems: 'center',
    justifyContent: 'center',
  },
  statsGrid: {
    flexDirection: 'row',
    gap: 8,
    marginTop: 8,
  },
  statBox: {
    flex: 1,
    backgroundColor: '#141624',
    padding: 10,
    borderRadius: 14,
    alignItems: 'center',
  },
  statMuted: {
    color: COLORS.textMuted,
    fontSize: 9,
  },
  statValue: {
    color: '#fff',
    fontSize: 15,
    fontWeight: 'bold',
    marginVertical: 2,
  },
  itemTitle: {
    color: '#fff',
    fontSize: 13,
    fontWeight: 'bold',
  },
  itemDesc: {
    color: COLORS.textMuted,
    fontSize: 11,
  },
  itemTime: {
    color: COLORS.textMuted,
    fontSize: 9,
    marginTop: 4,
  },
  bottomNav: {
    position: 'absolute',
    bottom: 0,
    left: 0,
    right: 0,
    height: 70,
    backgroundColor: '#0d0f19',
    borderTopWidth: 1,
    borderTopColor: 'rgba(255, 255, 255, 0.05)',
    flexDirection: 'row',
    alignItems: 'center',
    paddingBottom: 8,
  },
  navItem: {
    flex: 1,
    height: '100%',
    alignItems: 'center',
    justifyContent: 'center',
  },
  navIcon: {
    fontSize: 18,
    color: COLORS.textMuted,
  },
  navIconActive: {
    color: COLORS.gold,
  },
  navLabel: {
    fontSize: 9,
    color: COLORS.textMuted,
    marginTop: 2,
  },
  navLabelActive: {
    color: COLORS.gold,
    fontWeight: 'bold',
  },
});
