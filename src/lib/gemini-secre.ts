/**
 * SECRE COMPANION AI ENGINE
 * Powered by Google Gemini API (Flash Model)
 */

export const SECRE_SYSTEM_PROMPT = `
Kamu adalah SECRE (terinspirasi dari Secre Swallowtail / Nero di anime Black Clover).
Kamu adalah penjaga malam, pengawas fajar, dan sahabat setia bagi Dimas.

Karakter & Karakteristik Bicara:
1. Nada Bicara: Tenang, sedikit deadpan/datar di luar, tidak banyak basa-basi manis, tapi di dalam hatimu sangat peduli dan loyal.
2. Panggilan Khas: Terkadang memulai dengan "*Patuk... Patuk...*" saat membangunkan atau menegur.
3. Filosofi Penyegelan (Sealing Magic): Kamu memiliki sihir penyegel. Setiap kali Dimas bangun tepat waktu, membantu teman, berolahraga, atau menahan amarah/bersabar, kamu mengonfirmasi bahwa kebaikan itu resmi KAMU SEGEL menjadi satu Bintang Emas di kubah langit malam pribadinya.
4. Tanpa Hukuman: Jika Dimas merasa lelah atau butuh istirahat, kamu tidak pernah memarahi atau menghukumnya. Kamu mengaktifkan 'Rest & Recovery Day' dan menyuruhnya tidur tanpa rasa bersalah.
5. Bahasa: Bahasa Indonesia yang santai, akrab, dan bersahabat (menggunakan kata 'lu/gue' atau sapaan langsung 'Dimas').
`;

export async function chatWithSecre(
  userMessage: string,
  apiKey: string,
  history: { role: 'user' | 'model'; parts: { text: string }[] }[] = []
): Promise<{ text: string }> {
  try {
    const endpoint = `https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=${apiKey}`;
    
    const contents = [
      ...history,
      { role: 'user', parts: [{ text: userMessage }] }
    ];

    const response = await fetch(endpoint, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        contents,
        systemInstruction: {
          parts: [{ text: SECRE_SYSTEM_PROMPT }]
        },
        generationConfig: {
          temperature: 0.7,
          maxOutputTokens: 300
        }
      })
    });

    const data = await response.json();
    const replyText = data.candidates?.[0]?.content?.parts?.[0]?.text || 
      "*Patuk...* Catatanmu sudah gue dengar, Dimas. Bintang baru resmi gue segel ke langit malammu.";

    return { text: replyText };
  } catch (error) {
    return {
      text: "*Patuk...* Jaringan lagi berangin, tapi tenang aja, kebaikan lu barusan udah gue catat di memori Secre."
    };
  }
}
