package com.pegas.yuki.virtual.chat.data.network.model.mycharacter

import com.pegas.yuki.virtual.chat.domain.model.mycharacter.CreateMyCharacterInput
import com.pegas.yuki.virtual.chat.domain.model.mycharacter.GenerateMyCharacterImageInput
import com.pegas.yuki.virtual.chat.domain.model.mycharacter.MyCharacter
import com.pegas.yuki.virtual.chat.domain.model.mycharacter.MyCharacterCreationGuide
import com.pegas.yuki.virtual.chat.domain.model.mycharacter.MyCharacterGenerateImageResult
import com.pegas.yuki.virtual.chat.domain.model.mycharacter.MyCharacterGuideStep
import com.pegas.yuki.virtual.chat.domain.model.mycharacter.MyCharacterImageStatus
import com.pegas.yuki.virtual.chat.domain.model.mycharacter.MyCharacterLimits
import com.pegas.yuki.virtual.chat.domain.model.mycharacter.MyCharacterPersonaDraft
import com.pegas.yuki.virtual.chat.domain.model.mycharacter.MyCharacterPersonaDraftInput
import com.pegas.yuki.virtual.chat.domain.model.mycharacter.MyCharacterQuota
import com.pegas.yuki.virtual.chat.domain.model.mycharacter.UpdateMyCharacterInput

fun MyCharacterGuideDto.toDomain(): MyCharacterCreationGuide = MyCharacterCreationGuide(
    steps = steps.orEmpty().map { it.toDomain() },
    personalityPresets = personalityPresets.orEmpty(),
    speakingStylePresets = speakingStylePresets.orEmpty(),
    scenarioTemplates = scenarioTemplates.orEmpty(),
    appearanceTips = appearanceTips.orEmpty(),
    safetyTips = safetyTips.orEmpty(),
    quota = quota?.toDomain() ?: MyCharacterQuota(0, 0, 0, 0, false, 0),
    limits = limits?.toDomain() ?: MyCharacterLimits(0, 0, 0, 0, 0, false)
)

fun MyCharacterGuideStepDto.toDomain(): MyCharacterGuideStep = MyCharacterGuideStep(
    id = id.orEmpty(),
    title = title.orEmpty(),
    hint = hint.orEmpty()
)

fun MyCharacterQuotaDto.toDomain(): MyCharacterQuota = MyCharacterQuota(
    charactersUsed = charactersUsed ?: 0,
    characterCreateCost = characterCreateCost ?: 0,
    imageGenCoinCost = imageGenCoinCost ?: 0,
    charactersRemaining = charactersRemaining ?: 0,
    requireImageToPublish = requireImageToPublish == true,
    imageGenDailyLimit = imageGenDailyLimit ?: 0
)

fun MyCharacterLimitsDto.toDomain(): MyCharacterLimits = MyCharacterLimits(
    nameMax = nameMax ?: 0,
    personalityMax = personalityMax ?: 0,
    appearancePromptMax = appearancePromptMax ?: 0,
    maxTags = maxTags ?: 0,
    maxPerUser = maxPerUser ?: 0,
    requireImageToPublish = requireImageToPublish == true
)

fun MyCharacterDto.toDomain(): MyCharacter = MyCharacter(
    id = id.orEmpty(),
    name = name.orEmpty(),
    description = personality ?: description.orEmpty(),
    image = image,
    status = status.orEmpty(),
    gender = gender,
    age = age,
    tags = tags.orEmpty(),
    appearancePrompt = appearancePrompt,
    personaDraft = personaDraft?.toDomain() ?: MyCharacterPersonaDraft(
        personalityTraits = emptyList(),
        speakingStyle = null,
        scenario = null,
        customTrait = null
    ),
    createdAt = createdAt.orEmpty(),
    updatedAt = updatedAt.orEmpty()
)

fun MyCharacterPersonaDraftDto.toDomain(): MyCharacterPersonaDraft = MyCharacterPersonaDraft(
    personalityTraits = personalityTraits.orEmpty(),
    speakingStyle = speakingStyle,
    scenario = scenario,
    customTrait = customTrait
)

fun MyCharacterGenerateImageDto.toDomain(): MyCharacterGenerateImageResult =
    MyCharacterGenerateImageResult(
        taskId = taskId.orEmpty(),
        status = status.orEmpty(),
        balanceAfter = balanceAfter
    )

fun MyCharacterImageStatusDto.toDomain(): MyCharacterImageStatus =
    MyCharacterImageStatus(
        taskId = taskId.orEmpty(),
        status = status.orEmpty(),
        character = character?.toDomain(),
        message = message
    )

fun CreateMyCharacterInput.toRequestDto(): CreateMyCharacterRequestDto =
    CreateMyCharacterRequestDto(
        name = name,
        personality = personality,
        gender = gender,
        age = age,
        tags = tags
    )

fun UpdateMyCharacterInput.toRequestDto(): UpdateMyCharacterRequestDto =
    UpdateMyCharacterRequestDto(
        name = name,
        personality = personality,
        gender = gender,
        age = age,
        tags = tags
    )

fun MyCharacterPersonaDraftInput.toRequestDto(): MyCharacterPersonaDraftRequestDto =
    MyCharacterPersonaDraftRequestDto(
        personalityTraits = personalityTraits,
        speakingStyle = speakingStyle,
        scenario = scenario,
        customTrait = customTrait
    )

fun GenerateMyCharacterImageInput.toRequestDto(): GenerateMyCharacterImageRequestDto =
    GenerateMyCharacterImageRequestDto(prompt = prompt)
