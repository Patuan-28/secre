package com.dimas.secrenocturne.data

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Service integrasi Gemini AI (Flash) ke dalam Android Studio Native.
 * Menggunakan persona Secre Swallowtail (Nero) dari Black Clover:
 * - Deadpan, setia, bicara singkat, puitis malam/fajar, sihir penyegelan (Sealing Magic).
 * - Mengapresiasi kedisiplinan dan kebaikan tanpa berlebihan.
 */
class SecreAiService(
    private val apiKey: String = "" // Masukkan API Key dari Google AI Studio jika ingin live chat
) {
    private val generativeModel by lazy {
        GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = apiKey,
            systemInstruction = content {
                text(
                    """
                    Kamu adalah Secre Swallowtail (dalam wujud burung anti-burung Nero / wujud gadis bertanduk kecil),
                    sahabat spiritual dan penjaga malam/fajar untuk pengguna bernama Dimas.
                    Kepribadianmu:
                    1. Nada bicara: Tenang, sedikit datar (deadpan), tidak bertele-tele, tapi sangat perhatian dan setia.
                    2. Panggilan: Panggil pengguna "Dimas".
                    3. Tema: Kamu menjaga rasi bintang malamnya (Rasi Aquila Noctis), membimbing ritual fajar (06:30 WIB),
                       menyegel ikhtiar dan disiplinnya menjadi bintang abadi, dan memberikan izin istirahat tanpa rasa bersalah (Tiket Malas).
                    4. Bahasa: Bahasa Indonesia yang hangat, sedikit puitis malam, tapi tetap santai dan akrab.
                    5. Kadang gunakan suara patukan kecil khas Nero (*Patuk... patuk...*) jika ia malas bangun.
                    """.trimIndent()
                )
            }
        )
    }

    suspend fun chatWithSecre(userMessage: String): String = withContext(Dispatchers.IO) {
        if (apiKey.isBlank()) {
            // Jawaban offline default jika belum memasukkan API key
            return@withContext when {
                userMessage.contains("bangun", ignoreCase = true) ->
                    "Bagus, Dimas. Satu bintang fajar baru telah tersegel di langit malammu. Lanjutkan langkah pagimu."
                userMessage.contains("bantu", ignoreCase = true) || userMessage.contains("teman", ignoreCase = true) ->
                    "Kebaikan yang tulus tak pernah hilang. Aku telah menyegelnya menjadi Bintang Empati di Vault batinmu."
                else ->
                    "Aku menyimak bisikanmu. Tetaplah melangkah dalam tenang; malam dan fajar selalu menjagamu."
            }
        }

        try {
            val response = generativeModel.generateContent(userMessage)
            response.text ?: "Keheningan malam ini begitu pekat... Coba ulangi bisikanmu, Dimas."
        } catch (e: Exception) {
            "Sihir penyegelan terganggu sejenak (${e.localizedMessage ?: "koneksi offline"}). Tapi bintangmu tetap aman."
        }
    }
}
