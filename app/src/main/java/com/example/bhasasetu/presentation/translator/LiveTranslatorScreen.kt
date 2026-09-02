package com.example.bhasasetu.presentation.translator

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.bhasasetu.presentation.theme.OlChikiFont
import com.example.bhasasetu.util.SanthaliTranslator
import com.example.bhasasetu.util.SpeechRecognizerHelper
import com.example.bhasasetu.util.SpeechState
import com.example.bhasasetu.util.TextToSpeechHelper

@Composable
fun LiveTranslatorScreen(
    speechHelper: SpeechRecognizerHelper,
    ttsHelper: TextToSpeechHelper
) {
    var inputText by remember { mutableStateOf("") }
    var hindiText by remember { mutableStateOf("नमस्ते, आप कैसे हैं?") }
    var santhaliText by remember { mutableStateOf(SanthaliTranslator.translate("नमस्ते, आप कैसे हैं?")) }
    var isListening by remember { mutableStateOf(false) }
    var statusMessage by remember { mutableStateOf<String?>(null) }
    
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val quickPhrases = listOf(
        "नमस्ते",
        "आप कैसे हैं",
        "किताब खोलो",
        "ध्यान से सुनो",
        "बैठ जाओ",
        "यहाँ आओ",
        "बोर्ड देखो",
        "हाथ उठाओ",
        "चुप रहो",
        "बहुत अच्छा"
    )

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                isListening = true
                statusMessage = "Listening..."
            } else {
                Toast.makeText(context, "Microphone permission required for speech translation", Toast.LENGTH_SHORT).show()
                statusMessage = "Audio permission denied"
            }
        }
    )

    LaunchedEffect(isListening) {
        if (isListening) {
            speechHelper.startListening().collect { state ->
                when (state) {
                    is SpeechState.Listening -> {
                        statusMessage = "Listening to Hindi speech..."
                    }
                    is SpeechState.PartialResult -> {
                        hindiText = state.text
                        // Simultaneous real-time translation as words are spoken!
                        santhaliText = SanthaliTranslator.translate(state.text)
                        statusMessage = "Translating live..."
                    }
                    is SpeechState.FinalResult -> {
                        hindiText = state.text
                        santhaliText = SanthaliTranslator.translate(state.text)
                        isListening = false
                        statusMessage = "Translation complete"
                    }
                    is SpeechState.Error -> {
                        statusMessage = state.message
                        isListening = false
                        Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bhasasetu Live Translate",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "Offline Hindi ➔ Santhali (Ol Chiki)",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Quick Classroom Phrases Carousel
        Text(
            text = "Quick Classroom Phrases",
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp)
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(quickPhrases) { phrase ->
                SuggestionChip(
                    onClick = {
                        hindiText = phrase
                        santhaliText = SanthaliTranslator.translate(phrase)
                        statusMessage = null
                    },
                    label = { Text(phrase) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Manual Text Input / Edit Row
        OutlinedTextField(
            value = inputText,
            onValueChange = {
                inputText = it
                if (it.isNotBlank()) {
                    hindiText = it
                    santhaliText = SanthaliTranslator.translate(it)
                }
            },
            placeholder = { Text("Type Hindi or tap mic to speak...") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                if (inputText.isNotEmpty()) {
                    IconButton(onClick = { inputText = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Hindi Source Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Hindi (हिंदी)",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = {
                        clipboardManager.setText(AnnotatedString(hindiText))
                        Toast.makeText(context, "Copied Hindi text", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(Icons.Default.ContentCopy, "Copy Hindi", tint = MaterialTheme.colorScheme.secondary)
                    }
                    IconButton(onClick = { ttsHelper.speak(hindiText, isOlChiki = false) }) {
                        Icon(Icons.AutoMirrored.Filled.VolumeUp, "Speak Hindi", tint = MaterialTheme.colorScheme.secondary)
                    }
                }
                Text(
                    text = hindiText.ifEmpty { "Waiting for speech or text input..." },
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Santhali Target Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Santhali (ᱥᱟᱱᱛᱟᱲᱤ - Ol Chiki)",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = {
                        clipboardManager.setText(AnnotatedString(santhaliText))
                        Toast.makeText(context, "Copied Ol Chiki text", Toast.LENGTH_SHORT).show()
                    }) {
                        Icon(Icons.Default.ContentCopy, "Copy Ol Chiki", tint = MaterialTheme.colorScheme.primary)
                    }
                    IconButton(onClick = { ttsHelper.speak(santhaliText, isOlChiki = true) }) {
                        Icon(Icons.AutoMirrored.Filled.VolumeUp, "Speak Santhali", tint = MaterialTheme.colorScheme.primary)
                    }
                }
                Text(
                    text = santhaliText.ifEmpty { "ᱥᱟᱱᱛᱟᱲᱤ ᱛᱮ ᱛᱚᱨᱡᱚᱢᱟ..." },
                    style = MaterialTheme.typography.headlineSmall.copy(fontFamily = OlChikiFont),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }

        if (statusMessage != null) {
            Text(
                text = statusMessage!!,
                style = MaterialTheme.typography.bodySmall,
                color = if (isListening) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Mic Button
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(96.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = if (isListening) listOf(Color.Red.copy(alpha = 0.4f), Color.Transparent)
                        else listOf(MaterialTheme.colorScheme.primary.copy(alpha = 0.25f), Color.Transparent)
                    ),
                    shape = CircleShape
                )
        ) {
            FilledIconButton(
                onClick = {
                    if (!isListening) {
                        permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                    } else {
                        isListening = false
                        statusMessage = "Stopped listening"
                    }
                },
                modifier = Modifier.size(64.dp),
                shape = CircleShape,
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = if (isListening) Color.Red else MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    imageVector = if (isListening) Icons.Default.MicOff else Icons.Default.Mic,
                    contentDescription = if (isListening) "Stop Listening" else "Start Listening",
                    modifier = Modifier.size(32.dp),
                    tint = Color.White
                )
            }
        }

        Text(
            text = if (isListening) "Listening... Speak now" else "Tap Mic for Live Simultaneous Translate",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}