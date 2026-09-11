package com.pegas.yuki.virtual.chat.data.network.model.mycharacter

import com.pegas.yuki.virtual.chat.data.network.model.base.ApiEnvelopeDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

typealias MyCharacterGuideEnvelopeDto = ApiEnvelopeDto<MyCharacterGuideDto>
typealias MyCharacterEnvelopeDto = ApiEnvelopeDto<MyCharacterDto>
typealias MyCharactersEnvelopeDto = ApiEnvelopeDto<List<MyCharacterDto>>
typealias MyCharacterGenerateImageEnvelopeDto = ApiEnvelopeDto<MyCharacterGenerateImageDto>
typealias MyCharacterImageStatusEnvelopeDto = ApiEnvelopeDto<MyCharacterImageStatusDto>

@JsonClass(generateAdapter = true)
data class MyCharacterGuideDto(
    @Json(name = "steps") val steps: List<MyCharacterGuideStepDto>? = null,
    @Json(name = "personalityPresets") val personalityPresets: List<String>? = null,
    @Json(name = "speakingStylePresets") val speakingStylePresets: List<String>? = null,
    @Json(name = "scenarioTemplates") val scenarioTemplates: List<String>? = null,
    @Json(name = "appearanceTips") val appearanceTips: List<String>? = null,
    @Json(name = "safetyTips") val safetyTips: List<String>? = null,
    @Json(name = "quota") val quota: MyCharacterQuotaDto? = null,
    @Json(name = "limits") val limits: MyCharacterLimitsDto? = null
)

@JsonClass(generateAdapter = true)
data class MyCharacterGuideStepDto(
    @Json(name = "id") val id: String? = null,
    @Json(name = "title") val title: String? = null,
    @Json(name = "hint") val hint: String? = null
)

@JsonClass(generateAdapter = true)
data class MyCharacterQuotaDto(
    @Json(name = "charactersUsed") val charactersUsed: Int? = null,
    @Json(name = "characterCreateCost") val characterCreateCost: Int? = null,
    @Json(name = "imageGenCoinCost") val imageGenCoinCost: Int? = null,
    @Json(name = "charactersRemaining") val charactersRemaining: Int? = null,
    @Json(name = "requireImageToPublish") val requireImageToPublish: Boolean? = null,
    @Json(name = "imageGenDailyLimit") val imageGenDailyLimit: Int? = null
)

@JsonClass(generateAdapter = true)
data class MyCharacterLimitsDto(
    @Json(name = "nameMax") val nameMax: Int? = null,
    @Json(name = "personalityMax") val personalityMax: Int? = null,
    @Json(name = "appearancePromptMax") val appearancePromptMax: Int? = null,
    @Json(name = "maxTags") val maxTags: Int? = null,
    @Json(name = "maxPerUser") val maxPerUser: Int? = null,
    @Json(name = "requireImageToPublish") val requireImageToPublish: Boolean? = null
)

@JsonClass(generateAdapter = true)
data class MyCharacterDto(
    @Json(name = "id") val id: String? = null,
    @Json(name = "name") val name: String? = null,
    @Json(name = "personality") val personality: String? = null,
    @Json(name = "description") val description: String? = null,
    @Json(name = "image") val image: String? = null,
    @Json(name = "status") val status: String? = null,
    @Json(name = "gender") val gender: String? = null,
    @Json(name = "age") val age: Int? = null,
    @Json(name = "tags") val tags: List<String>? = null,
    @Json(name = "appearancePrompt") val appearancePrompt: String? = null,
    @Json(name = "personaDraft") val personaDraft: MyCharacterPersonaDraftDto? = null,
    @Json(name = "createdAt") val createdAt: String? = null,
    @Json(name = "updatedAt") val updatedAt: String? = null
)

@JsonClass(generateAdapter = true)
data class MyCharacterPersonaDraftDto(
    @Json(name = "personalityTraits") val personalityTraits: List<String>? = null,
    @Json(name = "speakingStyle") val speakingStyle: String? = null,
    @Json(name = "scenario") val scenario: String? = null,
    @Json(name = "customTrait") val customTrait: String? = null
)

@JsonClass(generateAdapter = true)
data class MyCharacterGenerateImageDto(
    @Json(name = "taskId") val taskId: String? = null,
    @Json(name = "status") val status: String? = null,
    @Json(name = "balanceAfter") val balanceAfter: Int? = null
)

@JsonClass(generateAdapter = true)
data class MyCharacterImageStatusDto(
    @Json(name = "taskId") val taskId: String? = null,
    @Json(name = "status") val status: String? = null,
    @Json(name = "character") val character: MyCharacterDto? = null,
    @Json(name = "message") val message: String? = null
)

@JsonClass(generateAdapter = true)
data class CreateMyCharacterRequestDto(
    @Json(name = "name") val name: String,
    @Json(name = "personality") val personality: String,
    @Json(name = "gender") val gender: String? = null,
    @Json(name = "age") val age: Int? = null,
    @Json(name = "tags") val tags: List<String> = emptyList()
)

@JsonClass(generateAdapter = true)
data class UpdateMyCharacterRequestDto(
    @Json(name = "name") val name: String? = null,
    @Json(name = "personality") val personality: String? = null,
    @Json(name = "gender") val gender: String? = null,
    @Json(name = "age") val age: Int? = null,
    @Json(name = "tags") val tags: List<String>? = null
)

@JsonClass(generateAdapter = true)
data class MyCharacterPersonaDraftRequestDto(
    @Json(name = "personalityTraits") val personalityTraits: List<String> = emptyList(),
    @Json(name = "speakingStyle") val speakingStyle: String? = null,
    @Json(name = "scenario") val scenario: String? = null,
    @Json(name = "customTrait") val customTrait: String? = null
)

@JsonClass(generateAdapter = true)
data class GenerateMyCharacterImageRequestDto(
    @Json(name = "prompt") val prompt: String
)
