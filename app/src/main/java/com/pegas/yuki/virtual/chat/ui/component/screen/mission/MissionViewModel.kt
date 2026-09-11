package com.pegas.yuki.virtual.chat.ui.component.screen.mission

import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.domain.model.common.AppResult
import com.pegas.yuki.virtual.chat.domain.model.common.PaginationQuery
import com.pegas.yuki.virtual.chat.domain.usecase.coins.GetCoinTransactionsUseCase
import com.pegas.yuki.virtual.chat.ui.bases.compose.mvi.BaseComposeViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

@HiltViewModel
class MissionViewModel @Inject constructor(
    private val getCoinTransactionsUseCase: GetCoinTransactionsUseCase
) : BaseComposeViewModel<MissionUiState, MissionIntent, MissionEffect>(MissionUiState()) {

    init {
        loadHistoryData()
    }

    override fun handleIntent(intent: MissionIntent) {
        when (intent) {
            MissionIntent.Initialize -> loadHistoryData()
            is MissionIntent.SelectTab -> updateState { copy(selectedTab = intent.tab) }
        }
    }

    private fun loadHistoryData() {
        launchIO {
            updateState { copy(isLoading = true) }
            when (val result = getCoinTransactionsUseCase(PaginationQuery(page = 1, limit = 50))) {
                is AppResult.Success -> {
                    val realItems = result.data.items.map { tx ->
                        val (resId, raw) = mapTransactionTypeToTitleRes(tx.type)
                        MissionHistoryItemUiState(
                            id = tx.id,
                            titleRes = resId,
                            titleRaw = raw,
                            gemsEarned = tx.amount,
                            timeAgo = formatTimeAgo(tx.createdAt),
                            exactTime = formatExactTime(tx.createdAt)
                        )
                    }
                    updateState {
                        copy(
                            isLoading = false,
                            historyItems = realItems,
                            totalQuestsCompleted = realItems.size,
                            totalGemsObtained = realItems.filter { it.gemsEarned > 0 }
                                .sumOf { it.gemsEarned }
                        )
                    }
                }

                is AppResult.Failure -> {
                    updateState {
                        copy(
                            isLoading = false,
                            historyItems = emptyList(),
                            totalQuestsCompleted = 0,
                            totalGemsObtained = 0
                        )
                    }
                }
            }
        }
    }

    private fun mapTransactionTypeToTitleRes(type: String): Pair<Int?, String?> {
        return when (type.lowercase()) {
            "checkin", "check_in" -> R.string.mission_type_daily_login to null
            "ad", "watch_ad", "ads" -> R.string.mission_type_watch_ad to null
            "game", "play_game" -> R.string.mission_type_play_game to null
            "online" -> R.string.mission_type_online to null
            "share" -> R.string.mission_type_share to null
            "welcome" -> R.string.first_day to null
            "iap_purchase" -> R.string.mission_type_iap_purchase to null
            "promo_code" -> R.string.mission_type_promo_code to null
            else -> if (type.isNotBlank()) null to type.replaceFirstChar { it.uppercase() } else R.string.mission_type_reward_claim to null
        }
    }

    private fun parseUtcToLocalDate(createdAt: String): Date? {
        if (createdAt.isBlank()) return null
        val normalizedValue = createdAt.replace(
            regex = Regex("""(\.\d{3})\d+"""),
            replacement = "$1"
        )
        val formats = listOf(
            "yyyy-MM-dd'T'HH:mm:ss.SSSXXX" to false,
            "yyyy-MM-dd'T'HH:mm:ssXXX" to false,
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" to true,
            "yyyy-MM-dd'T'HH:mm:ss'Z'" to true,
            "yyyy-MM-dd HH:mm:ss" to false,
            "yyyy-MM-dd HH:mm" to false
        )
        for ((pattern, useUtc) in formats) {
            try {
                val sdf = SimpleDateFormat(pattern, Locale.US).apply {
                    isLenient = false
                    if (useUtc) timeZone = TimeZone.getTimeZone("UTC")
                }
                val date = sdf.parse(normalizedValue)
                if (date != null) return date
            } catch (e: Exception) {
                // Try next
            }
        }
        return null
    }

    private fun formatTimeAgo(createdAt: String): String {
        val date = parseUtcToLocalDate(createdAt) ?: return "Today"

        val utcTz = TimeZone.getTimeZone("UTC")
        val targetCal = Calendar.getInstance(utcTz).apply { time = date }
        val nowCal = Calendar.getInstance(utcTz)
        val yesterdayCal = Calendar.getInstance(utcTz).apply { add(Calendar.DAY_OF_YEAR, -1) }

        val isToday = targetCal.get(Calendar.YEAR) == nowCal.get(Calendar.YEAR) &&
                targetCal.get(Calendar.DAY_OF_YEAR) == nowCal.get(Calendar.DAY_OF_YEAR)

        if (isToday) return "Today"

        val isYesterday = targetCal.get(Calendar.YEAR) == yesterdayCal.get(Calendar.YEAR) &&
                targetCal.get(Calendar.DAY_OF_YEAR) == yesterdayCal.get(Calendar.DAY_OF_YEAR)

        if (isYesterday) return "Yesterday"

        val localFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).apply {
            timeZone = utcTz
        }
        return localFormat.format(date)
    }

    private fun formatExactTime(createdAt: String): String {
        if (createdAt.length >= 16) {
            return createdAt.substring(0, 16).replace("T", ", ")
        }
        return createdAt
    }
}
