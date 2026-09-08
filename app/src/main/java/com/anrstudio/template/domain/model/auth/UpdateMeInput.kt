package com.anrstudio.template.domain.model.auth

data class UpdateMeInput(
    val displayName: String? = null,
    val name: String? = null,
    val email: String? = null,
    val avatar: String? = null
)
