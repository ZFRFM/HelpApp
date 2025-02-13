package ru.faimizufarov.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val id: String,
    val title: String,
    val imagePath: String,
)
