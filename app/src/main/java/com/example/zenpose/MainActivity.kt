package com.example.zenpose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.zenpose.data.DifficultyLevel
import com.example.zenpose.data.YogaPose
import com.example.zenpose.data.getYogaPoses
import com.example.zenpose.ui.theme.ZenPoseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZenPoseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val poses = getYogaPoses()
                    YogaPosesScreen(poses, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun YogaPosesScreen(poses: List<YogaPose>, modifier: Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn {
            items(poses) { pose ->
                YogaPoseCard(pose, modifier.padding(8.dp))
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
//            modifier = Modifier.height(IntrinsicSize.Min), // Înălțimea rândului va fi dictată de cel mai mare element (imaginea)
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(2f)
                    .padding(16.dp)
            ) {
                Text(
                    "Day ${pose.day}: ${pose.name}",
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
                contentDescription = pose.name,
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
    val (icon, color) = when (level) {
        DifficultyLevel.Beginner -> "🌱" to Color(0xFF81C784)
        DifficultyLevel.Intermediate -> "🔆" to Color(0xFFFFB74D)
        DifficultyLevel.Advanced -> "🔥" to Color(0xFFE57373)
        DifficultyLevel.AllLevels -> TODO()
    }

    Text(
        text = "$icon ${level.name}",
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