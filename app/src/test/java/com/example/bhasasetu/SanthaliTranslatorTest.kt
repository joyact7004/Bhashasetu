package com.example.bhasasetu

import com.example.bhasasetu.util.OlChikiTransliterator
import com.example.bhasasetu.util.SanthaliTranslator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SanthaliTranslatorTest {

    @Test
    fun testDirectPhraseTranslation() {
        val result1 = SanthaliTranslator.translate("आप कैसे हैं")
        assertEquals("ᱟᱢ ᱪᱮᱫ ᱞᱮᱠᱟ ᱢᱮᱱᱟᱢᱟ", result1)

        val result2 = SanthaliTranslator.translate("शुभ प्रभात")
        assertEquals("ᱥᱟᱹᱜᱩᱱ ᱥᱮᱛᱟ", result2)

        val result3 = SanthaliTranslator.translate("किताब खोलो")
        assertEquals("ᱯᱩᱛᱷᱤ ᱡᱷᱤᱡᱽ ᱢᱮ", result3)
    }

    @Test
    fun testPunctuationHandling() {
        val result = SanthaliTranslator.translate("नमस्ते, आप कैसे हैं?")
        // Should match greetings and question smoothly
        assertTrue(result.contains("ᱡᱚᱦᱟᱨ") || result.contains("ᱟᱢ ᱪᱮᱫ ᱞᱮᱠᱟ ᱢᱮᱱᱟᱢᱟ"))
    }

    @Test
    fun testWordByWordTranslation() {
        val greeting = SanthaliTranslator.translate("नमस्ते")
        assertEquals("ᱡᱚᱦᱟᱨ", greeting)

        val water = SanthaliTranslator.translate("पानी")
        assertEquals("ᱫᱟᱜ", water)

        val school = SanthaliTranslator.translate("स्कूल")
        assertEquals("ᱤᱛᱩᱱ ᱟᱥᱲᱟ", school)
    }

    @Test
    fun testNumberConversion() {
        val result = SanthaliTranslator.translate("1 2 3")
        assertEquals("᱑ ᱒ ᱓", result)
    }

    @Test
    fun testPhoneticFallback() {
        val transliterated = SanthaliTranslator.translateWordToPhoneticOlChiki("कमल")
        // क (ᱠ) + म (ᱢ) + ल (ᱞ)
        assertEquals("ᱠᱢᱞ", transliterated)
    }

    @Test
    fun testOlChikiTransliteratorToDevanagari() {
        val devanagari = OlChikiTransliterator.transliterate("ᱡᱚᱦᱟᱨ")
        assertTrue(devanagari.isNotEmpty())
    }
}
