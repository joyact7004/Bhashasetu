package com.example.bhasasetu.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bhasasetu.presentation.curriculum.WorksheetGeneratorScreen
import com.example.bhasasetu.presentation.flashcards.FlashcardStudioScreen
import com.example.bhasasetu.presentation.translator.LiveTranslatorScreen
import com.example.bhasasetu.util.SpeechRecognizerHelper
import com.example.bhasasetu.util.TextToSpeechHelper
import com.example.bhasasetu.presentation.curriculum.CurriculumViewModel

sealed class Screen(val route: String, val label: String) {
    object Translator : Screen("translator", "Live Translate")
    object Flashcards : Screen("flashcards", "Flashcards")
    object Worksheets : Screen("worksheets", "Worksheets")
}

@Composable
fun NavGraph(
    navController: NavHostController,
    speechHelper: SpeechRecognizerHelper,
    ttsHelper: TextToSpeechHelper,
    curriculumViewModel: CurriculumViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Translator.route
    ) {
        composable(Screen.Translator.route) {
            LiveTranslatorScreen(speechHelper, ttsHelper)
        }
        composable(Screen.Flashcards.route) {
            FlashcardStudioScreen(ttsHelper)
        }
        composable(Screen.Worksheets.route) {
            WorksheetGeneratorScreen(curriculumViewModel)
        }
    }
}