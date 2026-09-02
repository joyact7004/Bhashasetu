package com.example.bhasasetu.util

/**
 * SanthaliTranslator: An offline Hindi -> Santhali (Ol Chiki Script) translation engine.
 * Maps Hindi classroom phrases, words, grammar, and numbers into true Ol Chiki Unicode characters (U+1C50-U+1C7F).
 */
object SanthaliTranslator {

    private val phraseMap = linkedMapOf(
        "आप कैसे हैं" to "ᱟᱢ ᱪᱮᱫ ᱞᱮᱠᱟ ᱢᱮᱱᱟᱢᱟ",
        "आप कैसे हो" to "ᱟᱢ ᱪᱮᱫ ᱞᱮᱠᱟ ᱢᱮᱱᱟᱢᱟ",
        "तुम कैसे हो" to "ᱟᱢ ᱪᱮᱫ ᱞᱮᱠᱟ ᱢᱮᱱᱟᱢᱟ",
        "सब लोग कैसे हैं" to "ᱡᱚᱛᱚ ᱦᱚᱲ ᱪᱮᱫ ᱞᱮᱠᱟ ᱢᱮᱱᱟᱯᱮᱭᱟ",
        "आपका नाम क्या है" to "ᱟᱢᱟᱜ ᱧᱩᱛᱩᱢ ᱪᱮᱫ",
        "तुम्हारा नाम क्या है" to "ᱟᱢᱟᱜ ᱧᱩᱛᱩᱢ ᱪᱮᱫ",
        "मेरा नाम" to "ᱤᱧᱟᱜ ᱧᱩᱛᱩᱢ",
        "शुभ प्रभात" to "ᱥᱟᱹᱜᱩᱱ ᱥᱮᱛᱟ",
        "शुभ रात्रि" to "ᱥᱟᱹᱜᱩᱱ ᱧᱤᱫᱟᱹ",
        "बहुत अच्छा" to "ᱟᱹᱰᱤ ᱵᱮᱥ",
        "बहुत बढ़िया" to "ᱟᱹᱰᱤ ᱵᱮᱥ",
        "किताब खोलो" to "ᱯᱩᱛᱷᱤ ᱡᱷᱤᱡᱽ ᱢᱮ",
        "किताब बंद करो" to "ᱯᱩᱛᱷᱤ ᱵᱚᱸᱫᱽ ᱢᱮ",
        "किताब निकालो" to "ᱯᱩᱛᱷᱤ ᱚᱰᱚᱠ ᱢᱮ",
        "ध्यान से सुनो" to "ᱫᱷᱭᱟᱱ ᱛᱮ ᱟᱸᱡᱚᱢ ᱢᱮ",
        "चुप रहो" to "ᱛᱷᱤᱨ ᱛᱟᱦᱮᱸᱱ ᱢᱮ",
        "शांत रहो" to "ᱛᱷᱤᱨ ᱛᱟᱦᱮᱸᱱ ᱢᱮ",
        "खड़े हो जाओ" to "ᱛᱤᱸᱜᱩᱱ ᱢᱮ",
        "खड़े हो जाइए" to "ᱛᱤᱸᱜᱩᱱ ᱢᱮ",
        "बैठ जाओ" to "ᱫᱩᱲᱩᱵ ᱢᱮ",
        "बैठ जाइए" to "ᱫᱩᱲᱩᱵ ᱢᱮ",
        "यहाँ आओ" to "ᱱᱚᱸᱰᱮ ᱦᱤᱡᱩᱜ ᱢᱮ",
        "वहाँ जाओ" to "ᱦᱟᱸᱰᱮ ᱪᱟᱞᱟᱣ ᱢᱮ",
        "हाथ उठाओ" to "ᱛᱤ ᱛᱩᱞ ᱢᱮ",
        "बोर्ड देखो" to "ᱵᱚᱨᱰ ᱧᱮᱞ ᱢᱮ",
        "सवाल पूछो" to "ᱠᱩᱠᱞᱤ ᱠᱩᱞᱤ ᱢᱮ",
        "उत्तर दो" to "ᱛᱮᱞᱟ ᱮᱢ ᱢᱮ",
        "मुझे समझ आ गया" to "ᱤᱧ ᱵᱩᱡᱷᱟᱹᱣ ᱠᱮᱫᱟ",
        "मुझे समझ नहीं आया" to "ᱤᱧ ᱵᱟᱹᱧ ᱵᱩᱡᱷᱟᱹᱣ ᱞᱮᱫᱟ",
        "फिर से बोलो" to "ᱟᱨ ᱢᱤᱫᱫᱷᱟᱣ ᱢᱮᱱ ᱢᱮ",
        "फिर से बताइए" to "ᱟᱨ ᱢᱤᱫᱫᱷᱟᱣ ᱞᱟᱹᱭ ᱢᱮ"
    )

    private val wordMap = mapOf(
        // Greetings & Etiquette
        "नमस्ते" to "ᱡᱚᱦᱟᱨ",
        "नमस्कार" to "ᱡᱚᱦᱟᱨ",
        "प्रणाम" to "ᱡᱚᱦᱟᱨ",
        "धन्यवाद" to "ᱥᱟᱨᱦᱟᱣ",
        "स्वागत" to "ᱥᱟᱨᱦᱟᱣ",
        "अलविदा" to "ᱡᱚᱦᱟᱨ",

        // People & Roles
        "बच्चा" to "ᱜᱤᱫᱽᱨᱟᱹ",
        "बच्चे" to "ᱜᱤᱫᱽᱨᱟᱹ ᱠᱚ",
        "बच्चों" to "ᱜᱤᱫᱽᱨᱟᱹ ᱠᱚ",
        "लड़का" to "ᱠᱚᱲᱟ",
        "लड़के" to "ᱠᱚᱲᱟ ᱠᱚ",
        "लड़की" to "ᱠᱩᱲᱤ",
        "लड़कियां" to "ᱠᱩᱲᱤ ᱠᱚ",
        "शिक्षक" to "ᱥᱮᱪᱮᱫᱤᱭᱟᱹ",
        "अध्यापक" to "ᱥᱮᱪᱮᱫᱤᱭᱟᱹ",
        "गुरुजी" to "ᱥᱮᱪᱮᱫᱤᱭᱟᱹ",
        "छात्र" to "ᱯᱟᱹᱴᱷᱩᱣᱟᱹ",
        "छात्रा" to "ᱯᱟᱹᱴᱷᱩᱣᱟᱹ",
        "लोग" to "ᱦᱚᱲ",
        "दोस्त" to "ᱜᱟᱛᱮ",
        "मित्र" to "ᱜᱟᱛᱮ",
        "माँ" to "ᱟᱭᱳ",
        "माता" to "ᱟᱭᱳ",
        "पिता" to "ᱵᱟᱵᱟ",
        "भाई" to "ᱵᱚᱭᱦᱟ",
        "बहन" to "ᱢᱤᱥᱨᱟ",

        // Classroom Objects & School
        "स्कूल" to "ᱤᱛᱩᱱ ᱟᱥᱲᱟ",
        "विद्यालय" to "ᱤᱛᱩᱱ ᱟᱥᱲᱟ",
        "पाठशाला" to "ᱤᱛᱩᱱ ᱟᱥᱲᱟ",
        "कक्षा" to "ᱪᱟᱱᱟᱪ",
        "किताब" to "ᱯᱩᱛᱷᱤ",
        "पुस्तक" to "ᱯᱩᱛᱷᱤ",
        "कॉपी" to "ᱚᱞ ᱯᱩᱛᱷᱤ",
        "कलम" to "ᱠᱚᱞᱚᱢ",
        "पेन" to "ᱠᱚᱞᱚᱢ",
        "पेंसिल" to "ᱯᱮᱱᱥᱤᱞ",
        "बोर्ड" to "ᱵᱚᱨᱰ",
        "श्यामपट्ट" to "ᱵᱚᱨᱰ",
        "पाठ" to "ᱯᱟᱴᱷ",
        "सबक" to "ᱯᱟᱴᱷ",
        "सवाल" to "ᱠᱩᱠᱞᱤ",
        "प्रश्न" to "ᱠᱩᱠᱞᱤ",
        "उत्तर" to "ᱛᱮᱞᱟ",
        "नाम" to "ᱧᱩᱛᱩᱢ",

        // Verbs & Actions
        "पढ़ो" to "ᱯᱟᱲᱦᱟᱣ ᱢᱮ",
        "पढ़ना" to "ᱯᱟᱲᱦᱟᱣ",
        "पढ़िए" to "ᱯᱟᱲᱦᱟᱣ ᱢᱮ",
        "लिखो" to "ᱚᱞ ᱢᱮ",
        "लिखना" to "ᱚᱞ",
        "लिखिए" to "ᱚᱞ ᱢᱮ",
        "सुनो" to "ᱟᱸᱡᱚᱢ ᱢᱮ",
        "सुनना" to "ᱟᱸᱡᱚᱢ",
        "सुनिए" to "ᱟᱸᱡᱚᱢ ᱢᱮ",
        "बोलो" to "ᱢᱮᱱ ᱢᱮ",
        "बोलना" to "ᱢᱮᱱ",
        "बोलिए" to "ᱢᱮᱱ ᱢᱮ",
        "देखो" to "ᱧᱮᱞ ᱢᱮ",
        "देखना" to "ᱧᱮᱞ",
        "देखिए" to "ᱧᱮᱞ ᱢᱮ",
        "बैठो" to "ᱫᱩᱲᱩᱵ ᱢᱮ",
        "बैठना" to "ᱫᱩᱲᱩᱵ",
        "बैठिए" to "ᱫᱩᱲᱩᱵ ᱢᱮ",
        "आओ" to "ᱦᱤᱡᱩᱜ ᱢᱮ",
        "आना" to "ᱦᱤᱡᱩᱜ",
        "आइए" to "ᱦᱤᱡᱩᱜ ᱢᱮ",
        "जाओ" to "ᱪᱟᱞᱟᱣ ᱢᱮ",
        "जाना" to "ᱪᱟᱞᱟᱣ",
        "जाइए" to "ᱪᱟᱞᱟᱣ ᱢᱮ",
        "खोलो" to "ᱡᱷᱤᱡᱽ ᱢᱮ",
        "खोलना" to "ᱡᱷᱤᱡᱽ",
        "बंद" to "ᱵᱚᱸᱫᱽ",
        "सीखो" to "ᱥᱮᱪᱮᱫᱚᱜ ᱢᱮ",
        "सीखना" to "ᱥᱮᱪᱮᱫ",
        "सिखाना" to "ᱥᱮᱪᱮᱫ",
        "समझो" to "ᱵᱩᱡᱷᱟᱹᱣ ᱢᱮ",
        "समझना" to "ᱵᱩᱡᱷᱟᱹᱣ",
        "खेलना" to "ᱮᱱᱮᱡ",
        "खेलो" to "ᱮᱱᱮᱡ ᱢᱮ",
        "खाना" to "ᱡᱚᱢ",
        "खाओ" to "ᱡᱚᱢ ᱢᱮ",
        "पीना" to "ᱧᱩ",
        "पीओ" to "ᱧᱩ ᱢᱮ",

        // Environment & Basic Nouns
        "पानी" to "ᱫᱟᱜ",
        "जल" to "ᱫᱟᱜ",
        "घर" to "ᱚᱲᱟᱜ",
        "मकान" to "ᱚᱲᱟᱜ",
        "भोजन" to "ᱡᱚᱢᱟᱜ",
        "पेड़" to "ᱫᱟᱨᱮ",
        "वृक्ष" to "ᱫᱟᱨᱮ",
        "फूल" to "ᱵᱟᱦᱟ",
        "फल" to "ᱡᱚ",
        "सूर्य" to "ᱵᱮᱲᱟ",
        "सूरज" to "ᱵᱮᱲᱟ",
        "चाँद" to "ᱪᱟᱸᱫᱚ",
        "चंद्रमा" to "ᱪᱟᱸᱫᱚ",
        "नदी" to "ᱜᱟᱰᱟ",
        "जंगल" to "ᱵᱤᱨ",
        "गाँव" to "ᱟᱹᱛᱩ",
        "शहर" to "ᱵᱟᱡᱟᱨ",
        "दिन" to "ᱢᱟᱦᱟᱸ",
        "रात" to "ᱧᱤᱫᱟᱹ",
        "सुबह" to "ᱥᱮᱛᱟ",
        "शाम" to "ᱟᱹᱭᱩᱵ",
        "आज" to "ᱛᱮᱦᱮᱧ",
        "कल" to "ᱜᱟᱯᱟ",

        // Pronouns & Modifiers
        "मैं" to "ᱤᱧ",
        "हम" to "ᱟᱵᱚ",
        "आप" to "ᱟᱢ",
        "तुम" to "ᱟᱢ",
        "वह" to "ᱩᱱᱤ",
        "वे" to "ᱩᱱᱠᱩ",
        "यह" to "ᱱᱚᱣᱟ",
        "ये" to "ᱱᱚᱣᱟ ᱠᱚ",
        "वो" to "ᱦᱟᱱᱟ",
        "मेरा" to "ᱤᱧᱟᱜ",
        "मेरी" to "ᱤᱧᱟᱜ",
        "हमारा" to "ᱟᱵᱚᱣᱟᱜ",
        "हमारी" to "ᱟᱵᱚᱣᱟᱜ",
        "आपका" to "ᱟᱢᱟᱜ",
        "आपकी" to "ᱟᱢᱟᱜ",
        "तुम्हारा" to "ᱟᱢᱟᱜ",
        "तुम्हारी" to "ᱟᱢᱟᱜ",
        "उसका" to "ᱩᱱᱤᱭᱟᱜ",
        "उसकी" to "ᱩᱱᱤᱭᱟᱜ",
        "अच्छा" to "ᱵᱮᱥ",
        "अच्छी" to "ᱵᱮᱥ",
        "बढ़िया" to "ᱵᱮᱥ",
        "खराब" to "ᱵᱟᱹᱲᱤᱡ",
        "बड़ा" to "ᱢᱟᱨᱟᱝ",
        "छोटा" to "ᱠᱟᱹᱴᱤᱡ",
        "नया" to "ᱱᱟᱣᱟ",
        "पुराना" to "ᱢᱟᱨᱮ",
        "हाँ" to "ᱦᱮᱸ",
        "नहीं" to "ᱵᱟᱝ",
        "और" to "ᱟᱨ",
        "लेकिन" to "ᱢᱮᱱᱠᱷᱟᱱ",
        "बहुत" to "ᱟᱹᱰᱤ",
        "थोड़ा" to "ᱠᱟᱹᱴᱤᱡ",

        // Question Words
        "क्या" to "ᱪᱮᱫ",
        "कहाँ" to "ᱚᱠᱟᱨᱮ",
        "कब" to "ᱛᱤᱥ",
        "कौन" to "ᱚᱠᱚᱭ",
        "कैसे" to "ᱪᱮᱫ ᱞᱮᱠᱟ",
        "कैसा" to "ᱪᱮᱫ ᱞᱮᱠᱟ",
        "कैसी" to "ᱪᱮᱫ ᱞᱮᱠᱟ",
        "क्यों" to "ᱪᱮᱫᱟᱜ",
        "कितना" to "ᱛᱤᱱᱟᱹᱜ",

        // Numbers (Hindi words -> Santhali spoken words)
        "शून्य" to "ᱥᱩᱱ",
        "एक" to "ᱢᱤᱫ",
        "दो" to "ᱵᱟᱨ",
        "तीन" to "ᱯᱮ",
        "चार" to "ᱯᱩᱱ",
        "पाँच" to "ᱢᱚᱬᱮ",
        "पांच" to "ᱢᱚᱬᱮ",
        "छह" to "ᱛᱩᱨᱩᱭ",
        "सात" to "ᱮᱭᱟᱭ",
        "आठ" to "ᱤᱨᱟᱹᱞ",
        "नौ" to "ᱟᱨᱮ",
        "दस" to "ᱜᱮᱞ"
    )

    private val devToOlChikiNumbers = mapOf(
        '0' to '᱐', '1' to '᱑', '2' to '᱒', '3' to '᱓', '4' to '᱔',
        '5' to '᱕', '6' to '᱖', '7' to '᱗', '8' to '᱘', '9' to '᱙',
        '०' to '᱐', '१' to '᱑', '२' to '᱒', '३' to '᱓', '४' to '᱔',
        '५' to '᱕', '६' to '᱖', '७' to '᱗', '८' to '᱘', '९' to '᱙'
    )

    fun translate(hindiText: String): String {
        val trimmed = hindiText.trim()
        if (trimmed.isEmpty()) return ""

        // Normalize punctuation for matching: replace danda '।' and common separators
        val cleanPunctuation = trimmed.replace(Regex("[।?!.,;:]"), "").trim()

        // 1. Direct phrase matching
        for ((phrase, translation) in phraseMap) {
            if (cleanPunctuation.equals(phrase, ignoreCase = true) || trimmed.equals(phrase, ignoreCase = true)) {
                return translation
            }
        }

        // 2. Greedy phrase matching within sentences
        var remaining = cleanPunctuation
        val translatedSegments = mutableListOf<String>()

        // Split text into tokens/words
        val words = remaining.split(Regex("\\s+"))
        var i = 0
        while (i < words.size) {
            var matched = false

            // Try 4-word, 3-word, 2-word phrases
            for (len in 4 downTo 2) {
                if (i + len <= words.size) {
                    val subPhrase = words.subList(i, i + len).joinToString(" ")
                    val subPhraseClean = subPhrase.replace(Regex("[^\\p{L}\\p{Nd}\\s]"), "").trim()
                    val phraseMatch = phraseMap[subPhraseClean] ?: phraseMap[subPhrase]
                    if (phraseMatch != null) {
                        translatedSegments.add(phraseMatch)
                        i += len
                        matched = true
                        break
                    }
                }
            }

            if (!matched) {
                val currentWord = words[i]
                val cleanWord = currentWord.replace(Regex("[^\\p{L}\\p{Nd}]"), "")
                val wordTranslation = wordMap[cleanWord] 
                    ?: wordMap[currentWord]
                    ?: translateWordWithDigitsOrPhonetics(cleanWord.ifEmpty { currentWord })
                
                if (wordTranslation.isNotEmpty()) {
                    translatedSegments.add(wordTranslation)
                }
                i++
            }
        }

        return if (translatedSegments.isNotEmpty()) {
            translatedSegments.joinToString(" ")
        } else {
            translateWordToPhoneticOlChiki(trimmed)
        }
    }

    private fun translateWordWithDigitsOrPhonetics(word: String): String {
        // If it contains numbers, convert to Ol Chiki digits
        if (word.any { it.isDigit() || devToOlChikiNumbers.containsKey(it) }) {
            return word.map { devToOlChikiNumbers[it] ?: it }.joinToString("")
        }
        return translateWordToPhoneticOlChiki(word)
    }

    /**
     * Converts any unmapped Devanagari word directly into Ol Chiki script characters 
     * so that the output is ALWAYS true Ol Chiki Unicode and can be spoken phonetically.
     */
    fun translateWordToPhoneticOlChiki(hindiWord: String): String {
        if (hindiWord.isEmpty()) return ""
        
        // Handle composite characters first
        val preprocessed = hindiWord
            .replace("ड़", "ᱲ")
            .replace("ढ़", "ᱲᱷ")
            .replace("क्ष", "ᱠᱥ")
            .replace("ज्ञ", "ᱜᱭ")
            .replace("त्र", "ᱛᱨ")

        val devToOl = mapOf(
            'अ' to "ᱚ", 'आ' to "ᱟ", 'इ' to "ᱤ", 'ई' to "ᱤ", 'उ' to "ᱩ", 'ऊ' to "ᱩ", 
            'ऋ' to "ᱨᱤ", 'ए' to "ᱮ", 'ऐ' to "ᱮ", 'ओ' to "ᱳ", 'औ' to "ᱳ",
            'क' to "ᱠ", 'ख' to "ᱠᱷ", 'ग' to "ᱜ", 'घ' to "ᱜᱷ", 'ङ' to "ᱝ",
            'च' to "ᱪ", 'छ' to "ᱪᱷ", 'ज' to "ᱡ", 'झ' to "ᱡᱷ", 'ञ' to "ᱧ",
            'ट' to "ᱴ", 'ठ' to "ᱴᱷ", 'ड' to "ᱰ", 'ढ' to "ᱰᱷ", 'ण' to "ᱬ",
            'त' to "ᱛ", 'थ' to "ᱛᱷ", 'द' to "ᱫ", 'ध' to "ᱫᱷ", 'न' to "ᱱ",
            'प' to "ᱯ", 'फ' to "ᱯᱷ", 'ब' to "ᱵ", 'भ' to "ᱵᱷ", 'म' to "ᱢ",
            'य' to "ᱭ", 'र' to "ᱨ", 'ल' to "ᱞ", 'व' to "ᱣ", 'श' to "ᱥ", 
            'ष' to "ᱥ", 'स' to "ᱥ", 'ह' to "ᱦ",
            'ा' to "ᱟ", 'ि' to "ᱤ", 'ी' to "ᱤ", 'ु' to "ᱩ", 'ू' to "ᱩ", 
            'े' to "ᱮ", 'ै' to "ᱮ", 'ो' to "ᱳ", 'ौ' to "ᱳ", 'ृ' to "ᱨᱤ",
            'ं' to "ᱸ", 'ँ' to "ᱸ", 'ः' to "ᱷ", '्' to "", '़' to ""
        )

        val result = StringBuilder()
        for (char in preprocessed) {
            val mapped = devToOl[char]
            if (mapped != null) {
                result.append(mapped)
            } else if (devToOlChikiNumbers.containsKey(char)) {
                result.append(devToOlChikiNumbers[char])
            } else {
                result.append(char)
            }
        }
        return result.toString()
    }
}