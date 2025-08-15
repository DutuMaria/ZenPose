package com.example.zenpose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.zenpose.data.DifficultyLevel
import com.example.zenpose.data.YogaPose
import com.example.zenpose.data.getYogaPoses
import com.example.zenpose.ui.components.DifficultyFilterCircles
import com.example.zenpose.ui.components.ToggleYogaPoseCard
import com.example.zenpose.ui.components.ZenPoseAppBar
import com.example.zenpose.ui.components.ZenSearchBar
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
                    },
                    containerColor = MaterialTheme.colorScheme.background
                ) { innerPadding ->
                    YogaPosesScreen(
                        poses = getYogaPoses(),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun YogaPosesScreen(poses: List<YogaPose>, modifier: Modifier) {
    val focusManager = LocalFocusManager.current
    var selectedDifficulty by remember { mutableStateOf(DifficultyLevel.AllLevels) }
    var query by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))

        ZenSearchBar(
            query = query,
            onQueryChange = { query = it },
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 10.dp)
                .fillMaxWidth()
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
                    q.isEmpty() || it.name.contains(
                        q,
                        ignoreCase = true
                    ) || it.description.contains(q, ignoreCase = true)
                }
                .toList()
        }

        LazyColumn {
            items(items = filteredPoses, key = { it.day }) { pose ->
                ToggleYogaPoseCard(pose, Modifier.padding(12.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun YogaPoseCardPreview() {
    ZenPoseTheme {
        YogaPosesScreen(getYogaPoses(), modifier = Modifier)
    }
}
