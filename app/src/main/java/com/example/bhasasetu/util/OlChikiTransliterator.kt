package com.example.bhasasetu.util

/**
 * OlChikiTransliterator: Utility to map Santhali Ol Chiki Unicode characters 
 * (U+1C50 - U+1C7F) to phonetic Devanagari for TTS voice synthesis fallback.
 */
object OlChikiTransliterator {

    private val olChikiToDevanagari = mapOf(
        'ᱚ' to "ओ",  // O / LA (U+1C5A)
        'ᱛ' to "त्", // AT (U+1C5B)
        'ᱜ' to "ग्", // AG (U+1C5C)
        'ᱝ' to "ंग", // ANG (U+1C5D)
        'ᱞ' to "ल्", // AL (U+1C5E)
        'ᱟ' to "आ",  // LAA (U+1C5F)
        'ᱠ' to "क्", // AAK (U+1C60)
        'ᱡ' to "ज्", // AAJ (U+1C61)
        'ᱢ' to "म्", // AAM (U+1C62)
        'ᱣ' to "व्", // AAW (U+1C63)
        'ᱤ' to "इ",  // LI (U+1C64)
        'ᱥ' to "स्", // IS (U+1C65)
        'ᱦ' to "ह्", // IH (U+1C66)
        'ᱧ' to "ञ्", // INY (U+1C67)
        'ᱨ' to "र्", // IR (U+1C68)
        'ᱩ' to "उ",  // LU (U+1C69)
        'ᱪ' to "च्", // UC (U+1C6A)
        'ᱫ' to "द्", // UD (U+1C6B)
        'ᱬ' to "ण्", // UN (U+1C6C)
        'ᱭ' to "य्", // UP (U+1C6D)
        'ᱮ' to "ए",  // ER (U+1C6E)
        'ᱯ' to "प्", // OK (U+1C6F)
        'ᱰ' to "ड्", // AD (U+1C70)
        'ᱱ' to "न्", // ANY (U+1C71)
        'ᱲ' to "ड़", // AH (U+1C72)
        'ᱳ' to "ओ",  // OTT (U+1C73)
        'ᱴ' to "ट्", // TTA (U+1C74)
        'ᱵ' to "ब्", // BAA (U+1C75)
        'ᱶ' to "ँ",  // MANG (U+1C76)
        'ᱷ' to "ह",  // OH (U+1C77)
        'ᱸ' to "ं",  // MU-TTUDD (U+1C78)
        'ᱹ' to "़",  // GA-HLUDD (U+1C79)
        'ᱺ' to "़",  // RELAA (U+1C7A)
        'ᱻ' to "़",  // PHAARKAA (U+1C7B)
        'ᱼ' to "",   // AHAD (U+1C7C)
        'ᱽ' to " "   // DEG (U+1C7D)
    )

    fun transliterate(text: String): String {
        val rawPhonetic = buildString {
            text.forEach { char ->
                append(olChikiToDevanagari[char] ?: char)
            }
        }
        
        // Clean up halant matra combinations for smooth TTS pronunciation
        return rawPhonetic
            .replace("्ओ", "ो")
            .replace("्आ", "ा")
            .replace("्इ", "ि")
            .replace("्उ", "ु")
            .replace("्ᱮ", "े")
            .replace("्ᱮ", "े")
            .replace("् ", " ")
    }
}