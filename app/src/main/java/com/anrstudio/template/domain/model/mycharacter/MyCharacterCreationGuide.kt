package com.pegas.aura.aigirlfriend.soul.domain.model.mycharacter

data class MyCharacterCreationGuide(
    val steps: List<MyCharacterGuideStep>,
    val personalityPresets: List<String>,
    val speakingStylePresets: List<String>,
    val scenarioTemplates: List<String>,
    val appearanceTips: List<String>,
    val safetyTips: List<String>,
    val quota: MyCharacterQuota,
    val limits: MyCharacterLimits
)

data class MyCharacterGuideStep(
    val id: String,
    val title: String,
    val hint: String
)
