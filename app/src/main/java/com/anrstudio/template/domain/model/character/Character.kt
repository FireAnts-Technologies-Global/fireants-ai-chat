package com.anrstudio.template.domain.model.character

data class Character(
    val id: String,
    val name: String,
    val slug: String,
    val image: String?,
    val description: String,
    val task: String,
    val tags: List<String> = emptyList(),
    val age: Int? = null,
    val gender: String? = null,
    val categoryId: String = "",
    val category: CharacterCategorySummary? = null,
    val sort: Int = 0,
    val isHot: Boolean = false,
    val likes: Int = 0,
    val ratingStars: Double? = null,
    val ratingCount: Int = 0,
    val createdAt: String = "",
    val updatedAt: String = ""
)
