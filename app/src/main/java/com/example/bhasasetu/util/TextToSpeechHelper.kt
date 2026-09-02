package com.example.bhasasetu.util

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.Voice
import android.util.Log
import java.util.Locale

class TextToSpeechHelper(private val context: Context) : TextToSpeech.OnInitListener {

    private var tts: TextToSpeech? = null
    private var isInitialized = false
    private val TAG = "TextToSpeechHelper"

    init {
        tts = TextToSpeech(context, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            isInitialized = true
            Log.d(TAG, "TTS Initialized successfully")
        } else {
            Log.e(TAG, "TTS Initialization failed")
        }
    }

    fun speak(text: String, isOlChiki: Boolean = false) {
        if (!isInitialized) return

        if (isOlChiki) {
            val santhaliLocale = Locale("sat", "IN")
            val santhaliVoice = findSanthaliVoice(santhaliLocale)
            
            if (santhaliVoice != null) {
                tts?.voice = santhaliVoice
                tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
            } else {
                // Fallback: Transliterate Ol Chiki to Devanagari and use Hindi TTS
                val devanagariText = OlChikiTransliterator.transliterate(text)
                tts?.language = Locale("hi", "IN")
                tts?.speak(devanagariText, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        } else {
            tts?.language = Locale("hi", "IN")
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    private fun findSanthaliVoice(locale: Locale): Voice? {
        return tts?.voices?.find { it.locale == locale || it.locale.language == "sat" }
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
    }
}