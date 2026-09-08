package com.anrstudio.template.data.network.model.mycharacter

import com.anrstudio.template.data.network.model.base.ApiEnvelopeDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

typealias MyCharacterGuideEnvelopeDto = ApiEnvelopeDto<MyCharacterGuideDto>
typealias MyCharacterEnvelopeDto = ApiEnvelopeDto<MyCharacterDto>
typealias MyCharactersEnvelopeDto = ApiEnvelopeDto<List<MyCharacterDto>>
typealias MyCharacterGenerateImageEnvelopeDto = ApiEnvelopeDto<MyCharacterGenerateImageDto>
typealias MyCharacterImageStatusEnvelopeDto = ApiEnvelopeDto<MyCharacterImageStatusDto>

@JsonClass(generateAdapter = true)
data class MyCharacterGuideDto(
    @param:Json(name = "steps") val steps: List<MyCharacterGuideStepDto>? = null,
    @param:Json(name = "personalityPresets") val personalityPresets: List<String>? = null,
    @param:Json(name = "speakingStylePresets") val speakingStylePresets: List<String>? = null,
    @param:Json(name = "scenarioTemplates") val scenarioTemplates: List<String>? = null,
    @param:Json(name = "appearanceTips") val appearanceTips: List<String>? = null,
    @param:Json(name = "safetyTips") val safetyTips: List<String>? = null,
    @param:Json(name = "quota") val quota: MyCharacterQuotaDto? = null,
    @param:Json(name = "limits") val limits: MyCharacterLimitsDto? = null
)

@JsonClass(generateAdapter = true)
data class MyCharacterGuideStepDto(
    @param:Json(name = "id") val id: String? = null,
    @param:Json(name = "title") val title: String? = null,
    @param:Json(name = "hint") val hint: String? = null
)

@JsonClass(generateAdapter = true)
data class MyCharacterQuotaDto(
    @param:Json(name = "charactersUsed") val charactersUsed: Int? = null,
    @param:Json(name = "characterCreateCost") val characterCreateCost: Int? = null,
    @param:Json(name = "imageGenCoinCost") val imageGenCoinCost: Int? = null,
    @param:Json(name = "charactersRemaining") val charactersRemaining: Int? = null,
    @param:Json(name = "requireImageToPublish") val requireImageToPublish: Boolean? = null,
    @param:Json(name = "imageGenDailyLimit") val imageGenDailyLimit: Int? = null
)

@JsonClass(generateAdapter = true)
data class MyCharacterLimitsDto(
    @param:Json(name = "nameMax") val nameMax: Int? = null,
    @param:Json(name = "personalityMax") val personalityMax: Int? = null,
    @param:Json(name = "appearancePromptMax") val appearancePromptMax: Int? = null,
    @param:Json(name = "maxTags") val maxTags: Int? = null,
    @param:Json(name = "maxPerUser") val maxPerUser: Int? = null,
    @param:Json(name = "requireImageToPublish") val requireImageToPublish: Boolean? = null
)

@JsonClass(generateAdapter = true)
data class MyCharacterDto(
    @param:Json(name = "id") val id: String? = null,
    @param:Json(name = "name") val name: String? = null,
    @param:Json(name = "personality") val personality: String? = null,
    @param:Json(name = "description") val description: String? = null,
    @param:Json(name = "image") val image: String? = null,
    @param:Json(name = "status") val status: String? = null,
    @param:Json(name = "gender") val gender: String? = null,
    @param:Json(name = "age") val age: Int? = null,
    @param:Json(name = "tags") val tags: List<String>? = null,
    @param:Json(name = "appearancePrompt") val appearancePrompt: String? = null,
    @param:Json(name = "personaDraft") val personaDraft: MyCharacterPersonaDraftDto? = null,
    @param:Json(name = "createdAt") val createdAt: String? = null,
    @param:Json(name = "updatedAt") val updatedAt: String? = null
)

@JsonClass(generateAdapter = true)
data class MyCharacterPersonaDraftDto(
    @param:Json(name = "personalityTraits") val personalityTraits: List<String>? = null,
    @param:Json(name = "speakingStyle") val speakingStyle: String? = null,
    @param:Json(name = "scenario") val scenario: String? = null,
    @param:Json(name = "customTrait") val customTrait: String? = null
)

@JsonClass(generateAdapter = true)
data class MyCharacterGenerateImageDto(
    @param:Json(name = "taskId") val taskId: String? = null,
    @param:Json(name = "status") val status: String? = null,
    @param:Json(name = "balanceAfter") val balanceAfter: Int? = null
)

@JsonClass(generateAdapter = true)
data class MyCharacterImageStatusDto(
    @param:Json(name = "taskId") val taskId: String? = null,
    @param:Json(name = "status") val status: String? = null,
    @param:Json(name = "character") val character: MyCharacterDto? = null,
    @param:Json(name = "message") val message: String? = null
)

@JsonClass(generateAdapter = true)
data class CreateMyCharacterRequestDto(
    @param:Json(name = "name") val name: String,
    @param:Json(name = "personality") val personality: String,
    @param:Json(name = "gender") val gender: String? = null,
    @param:Json(name = "age") val age: Int? = null,
    @param:Json(name = "tags") val tags: List<String> = emptyList()
)

@JsonClass(generateAdapter = true)
data class UpdateMyCharacterRequestDto(
    @param:Json(name = "name") val name: String? = null,
    @param:Json(name = "personality") val personality: String? = null,
    @param:Json(name = "gender") val gender: String? = null,
    @param:Json(name = "age") val age: Int? = null,
    @param:Json(name = "tags") val tags: List<String>? = null
)

@JsonClass(generateAdapter = true)
data class MyCharacterPersonaDraftRequestDto(
    @param:Json(name = "personalityTraits") val personalityTraits: List<String> = emptyList(),
    @param:Json(name = "speakingStyle") val speakingStyle: String? = null,
    @param:Json(name = "scenario") val scenario: String? = null,
    @param:Json(name = "customTrait") val customTrait: String? = null
)

@JsonClass(generateAdapter = true)
data class GenerateMyCharacterImageRequestDto(
    @param:Json(name = "prompt") val prompt: String
)
