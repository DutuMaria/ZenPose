package com.example.zenpose.data

import com.example.zenpose.R

data class YogaPose (
    val day: Int,
    val name: String,
    val description: String,
    val imageResId: Int,
    val difficulty: DifficultyLevel
)

enum class DifficultyLevel {
    AllLevels, Beginner, Intermediate, Advanced
}

fun getYogaPoses(): List<YogaPose> = listOf(
    YogaPose(1, "Mountain Pose", "Improves posture and balance.", R.drawable.pose1, DifficultyLevel.Beginner),
    YogaPose(2, "Downward Dog", "Stretches the spine and calms the mind.", R.drawable.pose1, DifficultyLevel.Beginner),
    YogaPose(3, "Warrior I", "Builds strength in legs and shoulders.", R.drawable.pose1, DifficultyLevel.Intermediate),
    YogaPose(4, "Warrior II", "Increases stamina and focus.", R.drawable.pose1, DifficultyLevel.Intermediate),
    YogaPose(5, "Triangle Pose", "Stretches the legs and torso.", R.drawable.pose1, DifficultyLevel.Beginner),
    YogaPose(6, "Tree Pose", "Enhances balance and stability.", R.drawable.pose1, DifficultyLevel.Beginner),
    YogaPose(7, "Chair Pose", "Strengthens legs and back.", R.drawable.pose1, DifficultyLevel.Intermediate),
    YogaPose(8, "Plank Pose", "Tones the core muscles.", R.drawable.pose1, DifficultyLevel.Intermediate),
    YogaPose(9, "Cobra Pose", "Opens the chest and relieves fatigue.", R.drawable.pose1, DifficultyLevel.Beginner),
    YogaPose(10, "Bridge Pose", "Improves spine flexibility.", R.drawable.pose1, DifficultyLevel.Beginner),
    YogaPose(11, "Child’s Pose", "Relaxes the body and mind.", R.drawable.pose1, DifficultyLevel.Beginner),
    YogaPose(12, "Cat-Cow Pose", "Improves spine mobility.", R.drawable.pose1, DifficultyLevel.Beginner),
    YogaPose(13, "Seated Forward Bend", "Calms the mind and stretches the hamstrings.", R.drawable.pose1, DifficultyLevel.Intermediate),
    YogaPose(14, "Camel Pose", "Opens the heart and chest.", R.drawable.pose1, DifficultyLevel.Intermediate),
    YogaPose(15, "Boat Pose", "Builds abdominal strength.", R.drawable.pose1, DifficultyLevel.Intermediate),
    YogaPose(16, "Eagle Pose", "Improves focus and balance.", R.drawable.pose1, DifficultyLevel.Advanced),
    YogaPose(17, "Half Moon Pose", "Strengthens legs and improves balance.", R.drawable.pose1, DifficultyLevel.Intermediate),
    YogaPose(18, "Reclining Twist", "Releases tension from spine.", R.drawable.pose1, DifficultyLevel.Beginner),
    YogaPose(19, "Happy Baby", "Relieves stress and fatigue.", R.drawable.pose1, DifficultyLevel.Beginner),
    YogaPose(20, "Pigeon Pose", "Opens hips and relieves tension.", R.drawable.pose1, DifficultyLevel.Intermediate),
    YogaPose(21, "Supine Butterfly", "Opens hips and calms the nervous system.", R.drawable.pose1, DifficultyLevel.Beginner),
    YogaPose(22, "Fish Pose", "Expands the chest and throat.", R.drawable.pose1, DifficultyLevel.Intermediate),
    YogaPose(23, "Crow Pose", "Builds arm strength and confidence.", R.drawable.pose1, DifficultyLevel.Advanced),
    YogaPose(24, "Head-to-Knee Forward Bend", "Soothes anxiety and stretches the spine.", R.drawable.pose1, DifficultyLevel.Intermediate),
    YogaPose(25, "Lizard Pose", "Deep stretch for hips and legs.", R.drawable.pose1, DifficultyLevel.Intermediate),
    YogaPose(26, "Low Lunge", "Strengthens thighs and groin.", R.drawable.pose1, DifficultyLevel.Beginner),
    YogaPose(27, "Revolved Triangle", "Stimulates digestion and strengthens the legs.", R.drawable.pose1, DifficultyLevel.Advanced),
    YogaPose(28, "Butterfly Pose", "Improves hip flexibility.", R.drawable.pose1, DifficultyLevel.Beginner),
    YogaPose(29, "Legs-Up-The-Wall", "Relieves tired legs and promotes relaxation.", R.drawable.pose1, DifficultyLevel.Beginner),
    YogaPose(30, "Savasana", "Full body relaxation and integration.", R.drawable.pose1, DifficultyLevel.Beginner)
)

