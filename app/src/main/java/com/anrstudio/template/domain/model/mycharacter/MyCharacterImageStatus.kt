package com.anrstudio.template.domain.model.mycharacter

data class MyCharacterImageStatus(
    val taskId: String,
    val status: String,
    val character: MyCharacter?,
    val message: String?
) {
    val isPending: Boolean get() = status == STATUS_PENDING
    val isSuccess: Boolean get() = status == STATUS_SUCCESS

    companion object {
        const val STATUS_PENDING = "PENDING"
        const val STATUS_SUCCESS = "SUCCESS"
        const val STATUS_FAILED = "FAILED"
    }
}
