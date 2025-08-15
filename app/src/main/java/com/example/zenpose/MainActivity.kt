package com.example.zenpose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zenpose.data.DifficultyLevel
import com.example.zenpose.data.YogaPose
import com.example.zenpose.data.getYogaPoses
import com.example.zenpose.ui.theme.ZenPoseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            var isLightTheme by remember { mutableStateOf(true) }

            ZenPoseTheme(darkTheme = !isLightTheme) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        ZenPoseAppBar(
                            isLightTheme = isLightTheme,
                            onThemeToggle = { isLightTheme = !isLightTheme }
                        )
                    }
                ) { innerPadding ->
                    val poses = getYogaPoses()
                    YogaPosesScreen(poses, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ZenPoseAppBar(isLightTheme: Boolean, onThemeToggle: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_title),
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        actions = {
            IconButton(onClick = onThemeToggle) {
                Icon(
                    imageVector = if (isLightTheme) Icons.Filled.DarkMode else Icons.Filled.LightMode,
                    contentDescription = stringResource(R.string.cd_toggle_theme),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
fun YogaPosesScreen(poses: List<YogaPose>, modifier: Modifier) {
    val focusManager = LocalFocusManager.current
    var selectedDifficulty by remember { mutableStateOf(DifficultyLevel.AllLevels) }
    var query by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        ZenSearchBar(
            query = query,
            onQueryChange = { query = it },
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp)
        )

        Spacer(modifier = Modifier.height(14.dp))

        DifficultyFilterCircles(
            selected = selectedDifficulty,
            onSelect = { selectedDifficulty = it },
            modifier = Modifier.padding(vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        val filteredPoses = remember(poses, selectedDifficulty, query) {
            poses.asSequence()
                .filter { selectedDifficulty == DifficultyLevel.AllLevels || it.difficulty == selectedDifficulty }
                .filter {
                    val q = query.trim()
                    q.isEmpty() || it.name.contains(q, true) || it.description.contains(q, true)
                }
                .toList()
        }

        LazyColumn {
            items(items = filteredPoses, key = { it.day }) { pose ->
                YogaPoseCard(pose, Modifier.padding(12.dp))
            }
        }
    }
}

@Composable
fun ZenSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier,
) {
    Surface(
        shape = RoundedCornerShape(28.dp),
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
        tonalElevation = 6.dp,
        shadowElevation = 2.dp,
        modifier = modifier
            .fillMaxWidth()
    ) {
        TextField(
            value = query,
            onValueChange = onQueryChange,
            singleLine = true,
            placeholder = { Text(stringResource(R.string.search_placeholder)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(R.string.cd_search)
                )
            },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = { onQueryChange("") }) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = stringResource(R.string.cd_clear_search)
                        )
                    }
                }
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

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
        Item(DifficultyLevel.AllLevels, "✨", stringResource(R.string.all_levels), Color(0xFF90A4AE)),
        Item(DifficultyLevel.Beginner, "🌱", stringResource(R.string.beginner), Color(0xFF81C784)),
        Item(DifficultyLevel.Intermediate, "🔆", stringResource(R.string.intermediate), Color(0xFFFFB74D)),
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
                        .size(52.dp)
                        .clip(CircleShape)
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

@Composable
fun YogaPoseCard(pose: YogaPose, modifier: Modifier) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(125.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.day_prefix, pose.day, pose.name),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    pose.description,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(4.dp))
                DifficultyIcon(pose.difficulty)
            }

            Image(
                painter = painterResource(id = pose.imageResId),
                contentDescription = stringResource(R.string.cd_pose_image, pose.name),
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}

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

@Preview(showBackground = true)
@Composable
fun YogaPoseCardPreview() {
    ZenPoseTheme {
        YogaPosesScreen(
            getYogaPoses(),
            modifier = Modifier
        )
    }
}