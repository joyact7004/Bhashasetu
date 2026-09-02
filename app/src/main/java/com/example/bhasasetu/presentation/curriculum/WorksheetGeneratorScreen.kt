package com.example.bhasasetu.presentation.curriculum

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.Print
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.bhasasetu.domain.model.Worksheet

@Composable
fun WorksheetGeneratorScreen(viewModel: CurriculumViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Teacher Aid Hub",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Button(
                onClick = { viewModel.syncCurriculum("3rd", "Mathematics", "Addition") },
                enabled = !uiState.isSyncing
            ) {
                if (uiState.isSyncing) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp), color = MaterialTheme.colorScheme.onPrimary)
                } else {
                    Icon(Icons.Default.CloudSync, contentDescription = null)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (uiState.isSyncing) "Syncing..." else "Sync Daily")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (uiState.error != null) {
            Text(text = uiState.error!!, color = MaterialTheme.colorScheme.error)
            Button(onClick = { viewModel.clearError() }) { Text("Dismiss") }
        }

        Text("Recent Worksheets", style = MaterialTheme.typography.titleLarge)
        
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(uiState.worksheets) { worksheet ->
                WorksheetItem(worksheet)
            }
        }
    }
}

@Composable
fun WorksheetItem(worksheet: Worksheet) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = worksheet.subject, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "Grade ${worksheet.grade} • Chapter: ${worksheet.chapter}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            IconButton(onClick = { /* Export PDF Logic */ }) {
                Icon(Icons.Default.Print, contentDescription = "Print")
            }
        }
    }
}