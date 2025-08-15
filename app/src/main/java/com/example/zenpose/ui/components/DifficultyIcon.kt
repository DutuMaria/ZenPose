package com.example.zenpose.ui.components


import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.zenpose.R
import com.example.zenpose.data.DifficultyLevel

@Composable
fun DifficultyIcon(level: DifficultyLevel) {
    val (icon, color, label) = when (level) {
        DifficultyLevel.Beginner     -> Triple("🌱", Color(0xFF81C784), stringResource(R.string.beginner))
        DifficultyLevel.Intermediate -> Triple("🔆", Color(0xFFFFB74D), stringResource(R.string.intermediate))
        DifficultyLevel.Advanced     -> Triple("🔥", Color(0xFFE57373), stringResource(R.string.advanced))
        DifficultyLevel.AllLevels    -> Triple("✨", Color(0xFF90A4AE), stringResource(R.string.all_levels))
    }

    Text(
        text = "$icon $label",
        color = color,
        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium)
    )
}
