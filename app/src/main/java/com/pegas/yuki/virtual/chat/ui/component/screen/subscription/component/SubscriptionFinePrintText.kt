package com.pegas.yuki.virtual.chat.ui.component.screen.subscription.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pegas.yuki.virtual.chat.ui.bases.compose.component.AppText
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ManropeRegular
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.OutfitRegular
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_11
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_4
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_6
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_8
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.nonScaledSp

private val FinePrintColor = Color(0xFF756A8A)

@Composable
fun SubscriptionFinePrintText(
    textRes: Int,
    textColor: Color = FinePrintColor,
    onClick: (() -> Unit)? = null
) {
    val rawText = stringResource(id = textRes)
    val baseModifier = Modifier
        .fillMaxWidth()
        .padding(bottom = SdpR_6, start = SdpR_8, end = SdpR_8)

    val modifier = if (onClick != null) {
        baseModifier.clickable { onClick() }
    } else {
        baseModifier
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Top
    ) {
        AppText(
            text = "•",
            color = textColor,
            fontSize = SdpR_11.nonScaledSp,
            fontFamily = ManropeRegular,
            modifier = Modifier.padding(end = SdpR_4, top = 1.dp)
        )

        val annotatedText = remember(rawText) {
            val targetPhrases = listOf(
                "Subscriptions section of Google Play",
                "phần Đăng ký của Google Play",
                "Google Play的“订阅”部分",
                "одељку Претплате на Гоогле Плаи-у",
                "розділі «Підписки» в Google Play",
                "seção de Assinaturas do Google Play",
                "قسم الاشتراكات في Google Play",
                "Bereich Abonnements von Google Play",
                "sección de Suscripciones de Google Play",
                "odjeljku Pretplate na Google Playu",
                "Google Play의 구독 섹션",
                "Google Play के सदस्यता अनुभाग",
                "sectie Abonnementen van Google Play",
                "section Abonnements de Google Play",
                "sekcji Subskrypcje w Google Play",
                "avsnittet Prenumerationer på Google Play",
                "ส่วนการสมัครสมาชิก của Google Play",
                "ส่วนการสมัครสมาชิกของ Google Play",
                "Google Play'in\nAbonelikler bölümünden",
                "Google Play'in Abonelikler bölümünden",
                "bahagian Langganan Google Play",
                "Google Playの「定期購入」セクション",
                "bagian Langganan Google Play",
                "sezione Abbonamenti di Google Play",
                "разделе «Подписки» в Google Play",
                "seksyon ng Mga Subscription ng Google Play"
            )

            var matchIndex = -1
            var matchLength = 0
            for (phrase in targetPhrases) {
                val index = rawText.indexOf(phrase)
                if (index != -1) {
                    matchIndex = index
                    matchLength = phrase.length
                    break
                }
            }

            buildAnnotatedString {
                append(rawText)
                if (matchIndex != -1) {
                    addStyle(
                        style = SpanStyle(textDecoration = TextDecoration.Underline),
                        start = matchIndex,
                        end = matchIndex + matchLength
                    )
                }
            }
        }

        Text(
            text = annotatedText,
            color = textColor,
            fontFamily = OutfitRegular,
            fontSize = SdpR_11.nonScaledSp,
            lineHeight = 14.sp
        )
    }
}
