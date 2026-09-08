package com.anrstudio.template.data.network.model.base

import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type
import kotlin.reflect.jvm.javaType
import kotlin.reflect.typeOf

@JsonClass(generateAdapter = true)
data class ApiEnvelopeDto<T>(
    @param:Json(name = "data") val data: T? = null,
    @param:Json(name = "message") val message: String? = null,
    @param:Json(name = "code") val code: String? = null,
    @param:Json(name = "statusCode") val statusCode: Int? = null
)

fun <T> ApiEnvelopeDto<T>.requireData(): T = data ?: error(message ?: "Missing envelope data")

@OptIn(ExperimentalStdlibApi::class)
inline fun <reified T> Moshi.envelopeAdapter(): JsonAdapter<ApiEnvelopeDto<T>> {
    val type: Type = Types.newParameterizedType(ApiEnvelopeDto::class.java, typeOf<T>().javaType)
    return adapter(type)
}
