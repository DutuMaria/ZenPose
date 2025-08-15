package com.example.zenpose.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zenpose.R
import com.example.zenpose.data.DifficultyLevel

@Composable
fun DifficultyFilterCircles(
    selected: DifficultyLevel,
    onSelect: (DifficultyLevel) -> Unit,
    modifier: Modifier = Modifier
) {
    data class Item(
        val level: DifficultyLevel,
        val emoji: String,
        val label: String,
        val color: Color
    )

    val items = listOf(
        Item(
            DifficultyLevel.AllLevels,
            "✨",
            stringResource(R.string.all_levels),
            Color(0xFF90A4AE)
        ),
        Item(DifficultyLevel.Beginner, "🌱", stringResource(R.string.beginner), Color(0xFF81C784)),
        Item(
            DifficultyLevel.Intermediate,
            "🔆",
            stringResource(R.string.intermediate),
            Color(0xFFFFB74D)
        ),
        Item(DifficultyLevel.Advanced, "🔥", stringResource(R.string.advanced), Color(0xFFE57373))
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items.forEach { item ->
            val isSelected = item.level == selected
            val borderColor =
                if (isSelected) MaterialTheme.colorScheme.primary else item.color.copy(alpha = 0.6f)
            val bgColor = if (isSelected) item.color.copy(alpha = 0.16f) else Color.Transparent

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.widthIn(min = 64.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(52.dp)
                        .background(bgColor, CircleShape)
                        .border(
                            width = if (isSelected) 2.dp else 1.dp,
                            color = borderColor,
                            shape = CircleShape
                        )
                        .clickable(role = Role.Button) { onSelect(item.level) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = item.emoji, fontSize = 22.sp)
                }

                Spacer(Modifier.height(6.dp))

                Text(
                    text = item.label,
                    fontSize = 12.sp,
                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                    color = if (isSelected)
                        MaterialTheme.colorScheme.onSurface
                    else
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
            }
        }
    }
}
