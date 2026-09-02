# Palash (Bhasasetu) 🌿
> **Offline-First Multilingual Educational Assistant for Tribal Primary Schools**  
> *Bridging Hindi and Santhali (Ol Chiki Script ᱥᱟᱱᱛᱟᱲᱤ)*

[![Kotlin](https://img.shields.io/badge/Kotlin-2.1.0-blue.svg?logo=kotlin)](https://kotlinlang.org)
[![Android](https://img.shields.io/badge/Platform-Android%208.0%2B%20(API%2026%2B)-green.svg?logo=android)](https://android.com)
[![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose%20%2F%20Material%203-4285F4.svg?logo=jetpackcompose)](https://developer.android.com/jetpack/compose)
[![Clean Architecture](https://img.shields.io/badge/Architecture-MVVM%20%2B%20Clean%20Architecture-purple.svg)]()
[![Offline First](https://img.shields.io/badge/Operation-100%25%20Offline-success.svg)]()

---

## 📖 Project Overview

In remote and tribal schools across Jharkhand, Odisha, West Bengal, and Assam, primary school teachers often face a severe linguistic barrier: curricula and standard textbooks are published in **Hindi**, while primary-grade children speak and think in **Santhali (ᱥᱟᱱᱛᱟᱲᱤ)**.

**Palash (Bhasasetu)** is a production-grade, offline-first mobile assistant engineered to bridge this classroom gap. It empowers educators with **real-time simultaneous voice translation**, on-device phonetic speech synthesis in **Ol Chiki script**, and a **hybrid RAG Teacher Aid Generator** for curriculum worksheets and flashcards.

---

## ✨ Core Pillars & Architectural Features

### 1. 🎙️ Live Simultaneous Classroom Speech Pipeline (100% Offline)
- **ASR (Speech-to-Text):** Android native `SpeechRecognizer` (`Locale("hi", "IN")`) configured with `EXTRA_PREFER_OFFLINE = true` for zero-connectivity classrooms.
- **Translation Engine:** High-performance, on-device offline translation mapping Hindi pedagogical terms, sentences, and numbers (`᱐-᱙`) to authentic **Ol Chiki Unicode (`U+1C50 - U+1C7F`)** with automatic phonetic transliteration fallback.
- **Simultaneous Streaming:** Real-time translation emits translations on `PartialResult` as the teacher speaks word-by-word.
- **TTS (Text-to-Speech):** Android native `TextToSpeech` with dynamic detection for Google's native Santhali voice (`sat_IN`), backed by an automated **Ol Chiki ➔ Devanagari phonetic transliterator fallback** for universal hardware compatibility.

### 2. 📚 Hybrid Teacher Aid & Worksheet Generator
- **1-Click Sync:** Teachers tap *"Sync Daily"* whenever an internet connection is available (in town or via hotspot) to pull curated curriculum packs.
- **Offline Room Database Cache:** Downloaded NCERT/SCERT lesson plans and flashcard decks are cached in **Room DB** for offline access in remote classrooms.
- **Offline Dynamic Card Translation:** Templates can be translated to Santhali locally on-device without internet.

### 3. 🗂️ Interactive Flashcard Studio
- Visual card decks for vocabulary acquisition (Animals, Numbers, Classroom Objects, Mathematics).
- Ol Chiki typography rendering with dedicated font assets.
- Interactive audio pronunciation buttons for student learning.

---

## 🛠️ Tech Stack & Standards

| Layer | Technology |
|---|---|
| **Language** | Kotlin (100%), Kotlin Coroutines, StateFlow, Flow |
| **UI Framework** | Jetpack Compose, Material 3, Material Design Icons |
| **Architecture** | MVVM + Clean Architecture (Presentation, Domain, Data) |
| **Local Database** | Android Room DB + KSP (Kotlin Symbol Processing) |
| **Networking & JSON**| Retrofit 2 + KotlinX Serialization + OkHttp |
| **Audio & Speech** | Android Native `SpeechRecognizer` + `TextToSpeech` |
| **SDK Compatibility** | Target SDK: Android 14 (API 34/35), Min SDK: Android 8.0 (API 26) |

--
