package com.example.bhasasetu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.Style
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.bhasasetu.data.local.db.AppDatabase
import com.example.bhasasetu.data.repository.SyncRepositoryImpl
import com.example.bhasasetu.data.repository.TranslationRepositoryImpl
import com.example.bhasasetu.presentation.curriculum.CurriculumViewModel
import com.example.bhasasetu.presentation.navigation.NavGraph
import com.example.bhasasetu.presentation.navigation.Screen
import com.example.bhasasetu.ui.theme.BhasasetuTheme
import com.example.bhasasetu.util.SpeechRecognizerHelper
import com.example.bhasasetu.util.TextToSpeechHelper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bhasasetu.data.remote.CurriculumApiService
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType

class MainActivity : ComponentActivity() {

    private lateinit var db: AppDatabase
    private lateinit var speechHelper: SpeechRecognizerHelper
    private lateinit var ttsHelper: TextToSpeechHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "bhasasetu-db")
            .fallbackToDestructiveMigration()
            .build()
        speechHelper = SpeechRecognizerHelper(this)
        ttsHelper = TextToSpeechHelper(this)

        val translationRepo = TranslationRepositoryImpl(db.bhasasetuDao())
        
        // Mock API
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.example.com/") 
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
        val apiService = retrofit.create(CurriculumApiService::class.java)
        
        val syncRepo = SyncRepositoryImpl(apiService, db.bhasasetuDao(), translationRepo)

        val curriculumViewModelFactory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CurriculumViewModel(syncRepo) as T
            }
        }

        enableEdgeToEdge()
        setContent {
            BhasasetuTheme {
                MainScreen(speechHelper, ttsHelper, curriculumViewModelFactory)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ttsHelper.shutdown()
    }
}

@Composable
fun MainScreen(
    speechHelper: SpeechRecognizerHelper,
    ttsHelper: TextToSpeechHelper,
    curriculumViewModelFactory: ViewModelProvider.Factory
) {
    val navController = rememberNavController()
    val curriculumViewModel: CurriculumViewModel = viewModel(factory = curriculumViewModelFactory)
    
    val items = listOf(
        Screen.Translator to Icons.Default.Translate,
        Screen.Flashcards to Icons.Default.Style,
        Screen.Worksheets to Icons.Default.LibraryBooks
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { (screen, icon) ->
                    NavigationBarItem(
                        icon = { Icon(icon, contentDescription = screen.label) },
                        label = { Text(screen.label) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.padding(innerPadding),
            color = MaterialTheme.colorScheme.background
        ) {
            NavGraph(
                navController = navController,
                speechHelper = speechHelper,
                ttsHelper = ttsHelper,
                curriculumViewModel = curriculumViewModel
            )
        }
    }
}