package com.example.bhasasetu.presentation.flashcards

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.bhasasetu.domain.model.FlashcardItem
import com.example.bhasasetu.presentation.theme.OlChikiFont
import com.example.bhasasetu.util.TextToSpeechHelper

@Composable
fun FlashcardStudioScreen(ttsHelper: TextToSpeechHelper) {
    // Mock data for UI preview
    val mockFlashcards = listOf(
        FlashcardItem("1", "Animals", "हाथी", "Elephant", "ᱮᱞᱮᱯᱷᱟᱱᱴ", "ᱮᱞᱮᱯᱷᱟᱱᱴ", "Easy"),
        FlashcardItem("2", "Numbers", "एक", "One", "ᱢᱤᱫ", "ᱢᱤᱫ", "Easy")
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Flashcard Studio",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(mockFlashcards) { card ->
                FlashcardView(card, ttsHelper)
            }
        }
    }
}

@Composable
fun FlashcardView(card: FlashcardItem, ttsHelper: TextToSpeechHelper) {
    ElevatedCard(
        modifier = Modifier.size(width = 300.dp, height = 450.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = card.topic, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.secondary)
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Text(text = card.questionHindi, style = MaterialTheme.typography.headlineMedium, textAlign = TextAlign.Center)
            
            HorizontalDivider(modifier = Modifier.padding(vertical = 24.dp))
            
            Text(
                text = card.questionSanthali, 
                style = MaterialTheme.typography.headlineLarge.copy(fontFamily = OlChikiFont),
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.weight(1f))
            
            FilledTonalButton(onClick = { ttsHelper.speak(card.questionSanthali, isOlChiki = true) }) {
                Icon(Icons.Default.VolumeUp, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Listen")
            }
        }
    }
}