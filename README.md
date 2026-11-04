# 🚀 SECRE NOCTURNE — Native Android Studio Project (Jetpack Compose)

Proyek ini telah dikonfigurasikan sebagai **Proyek Resmi Android Studio** berbasis **Kotlin + Jetpack Compose** dengan dukungan **Live Preview & Hot Reload**.

---

## 📱 Cara Membuka & Menjalankan di Android Studio:

### Langkah 1: Buka Proyek di Android Studio
1. Buka aplikasi **Android Studio** di laptop Anda.
2. Di layar awal, pilih **File** $\to$ **Open...** (atau tombol **Open** di menu utama).
3. Cari dan pilih folder:
   📁 **`C:\KERJA\secre`**
4. Klik **OK**. Android Studio akan otomatis mengenali proyek ini dan menjalankan proses *Gradle Sync*.

---

### Langkah 2: Cara Melihat Hasil Koding Langsung (*Live Preview*)
Tanpa menyalakan emulator pun, Anda sudah bisa melihat layar HP langsung di sebelah kode Anda:
1. Di panel proyek sebelah kiri, buka berkas:
   `app/src/main/java/com/dimas/secrenocturne/ui/screens/LangitScreen.kt`
2. Di pojok kanan atas jendela kode, ada 3 tombol mode tampilan:
   * **Code** (Hanya kode)
   * **Split** (Kode di kiri, HP di kanan) ◄── **Pilih yang ini!**
   * **Design** (Hanya HP)
3. Klik **Split**: Layar HP **Secre Nocturne** (Rasi Aquila Noctis, tombol gold, hero Secre) akan langsung muncul tepat di samping kode Anda!
4. Setiap kali Anda mengubah teks atau warna di kode, tampilannya langsung berubah seketika (*Live Edit*).

---

### Langkah 3: Menjalankan di Emulator HP Virtual
1. Di bilah atas Android Studio, pastikan Device Manager emulator Anda sudah terpilih (misal: *Pixel 8 Pro*).
2. Tekan tombol hijau **Run ▶** (atau shortcut `Shift + F10`).
3. Emulator HP Android akan menyala dan aplikasi **SECRE NOCTURNE** akan otomatis terpasang dan terbuka!

---

## 📁 Struktur Berkas Android Studio di `C:\KERJA\secre\`:

```text
C:\KERJA\secre\
├── build.gradle.kts             ◄── Root Gradle Configuration
├── settings.gradle.kts          ◄── Project Settings & Modules
├── gradle.properties            ◄── JVM & AndroidX Settings
├── gradle\wrapper\              ◄── Gradle Wrapper 8.7
└── app\
    ├── build.gradle.kts         ◄── App Gradle (Jetpack Compose & Material3)
    └── src\main\
        ├── AndroidManifest.xml  ◄── Manifest Android
        ├── res\values\          ◄── strings.xml, colors.xml, themes.xml
        └── java\com\dimas\secrenocturne\
            ├── MainActivity.kt  ◄── Activity Utama dengan Bottom Navigation
            └── ui\
                ├── theme\       ◄── Color.kt, Theme.kt, Type.kt (Nocturne Palette)
                └── screens\
                    ├── LangitScreen.kt   ◄── Rasi Aquila Noctis (@Preview Aktif)
                    ├── SecreScreen.kt    ◄── Obrolan Fajar Nero (@Preview Aktif)
                    ├── VaultScreen.kt    ◄── Timeline Segel Kebaikan (@Preview Aktif)
                    └── SanctumScreen.kt  ◄── Tiket Malas & Memoar (@Preview Aktif)
```
