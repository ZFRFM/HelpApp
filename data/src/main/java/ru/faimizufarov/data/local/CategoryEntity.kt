package ru.faimizufarov.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class CategoryEntity(
    @PrimaryKey val id: String,
    @ColumnInfo("title") val title: String,
    @ColumnInfo("image_path") val imagePath: String,
)
