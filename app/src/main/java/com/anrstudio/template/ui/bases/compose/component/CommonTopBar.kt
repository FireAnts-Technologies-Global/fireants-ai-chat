package com.pegas.aura.aigirlfriend.soul.ui.bases.compose.component

import android.content.Intent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pegas.aura.aigirlfriend.soul.R
import com.pegas.aura.aigirlfriend.soul.ui.bases.compose.theme.*
import com.pegas.aura.aigirlfriend.soul.ui.component.setting.SettingActivity
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeEffect

enum class CommonTopBarStyle {
    DEFAULT,
    CHAT,
    CHARACTER_DETAIL
}

@Composable
fun CommonTopBar(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    coinCount: Int = 480,
    showActions: Boolean = true,
    style: CommonTopBarStyle = CommonTopBarStyle.DEFAULT,
    isOnline: Boolean = true,
    isShowTitle: Boolean = true,
    hazeState: HazeState? = null,
    onBack: (() -> Unit)? = null,
    onAddCoinClick: (() -> Unit)? = null,
    onTrophyClick: (() -> Unit)? = null,
    onSettingClick: (() -> Unit)? = null,
    onMoreClick: (() -> Unit)? = null,
    customActions: (@Composable () -> Unit)? = null
) {
    val context = LocalContext.current

    val resolvedSettingClick = onSettingClick ?: {
        context.startActivity(Intent(context, SettingActivity::class.java))
    }

    val topBarModifier = if (
        style == CommonTopBarStyle.CHAT &&
        hazeState != null
    ) {
        modifier
            .fillMaxWidth()
            .height(
                TopAppBarDefaults.TopAppBarExpandedHeight
            )
            .hazeEffect(state = hazeState) {
                blurRadius = 18.dp
                backgroundColor = Color08030F.copy(alpha = 0.08f)
            }
            .padding(
                horizontal = SdpR_12
            )
    } else {
        modifier
            .fillMaxWidth()
            .height(
                TopAppBarDefaults.TopAppBarExpandedHeight
            )
            .padding(
                horizontal = SdpR_12
            )
    }

    Box(
        modifier = topBarModifier
    ) {
        if (onBack != null) {
            BackButton(
                modifier = Modifier.align(Alignment.CenterStart),
                onClick = onBack
            )
        }

        when (style) {
            CommonTopBarStyle.DEFAULT -> {
                if (isShowTitle) {
                    DefaultTitleContent(
                        title = title,
                        showLogo = onBack == null,
                        hasBackButton = onBack != null,
                        modifier = Modifier.align(
                            if (onBack != null) {
                                Alignment.Center
                            } else {
                                Alignment.CenterStart
                            }
                        )
                    )
                }
            }

            CommonTopBarStyle.CHAT -> {
                ChatTitleContent(
                    title = title,
                    subtitle = subtitle.orEmpty(),
                    isOnline = isOnline,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(
                            start = if (onBack != null) {
                                SdpR_44
                            } else {
                                SdpR_0
                            }
                        )
                )
            }

            CommonTopBarStyle.CHARACTER_DETAIL -> Unit
        }

        if (customActions != null) {
            Box(
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                customActions()
            }
        } else if (showActions) {
            when (style) {
                CommonTopBarStyle.DEFAULT -> {
                    if (onBack == null) {
                        DefaultTopBarActions(
                            coinCount = coinCount,
                            onAddCoinClick = onAddCoinClick,
                            onTrophyClick = onTrophyClick,
                            onSettingClick = resolvedSettingClick,
                            modifier = Modifier.align(Alignment.CenterEnd)
                        )
                    }
                }

                CommonTopBarStyle.CHAT -> {
                    ChatTopBarActions(
                        coinCount = coinCount,
                        onMoreClick = onMoreClick,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    )
                }

                CommonTopBarStyle.CHARACTER_DETAIL -> Unit
            }
        }
    }
}

@Composable
private fun BackButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .size(SdpR_36)
            .shadow(
                elevation = SdpR_1,
                shape = CircleShape,
                clip = false
            )
            .background(
                color = Color.White,
                shape = CircleShape
            )
            .border(
                width = SdpR_1,
                color = Color(0xFFE9DDF2),
                shape = CircleShape
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_chevron_left),
            contentDescription = stringResource(R.string.close),
            tint = Color(0xFFE25798),
            modifier = Modifier.size(SdpR_15)
        )
    }
}

@Composable
private fun DefaultTitleContent(
    title: String,
    showLogo: Boolean,
    hasBackButton: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showLogo) {
            Image(
                painter = painterResource(R.drawable.img_dot),
                contentDescription = null,
                modifier = Modifier.size(SdpR_20)
            )

            Spacer(modifier = Modifier.width(SdpR_2))
        }

        Text(
            text = title,
            fontSize = SdpR_20.nonScaledSp,
            style = MaterialTheme.typography.titleLarge,
            fontFamily = if (hasBackButton) {
                ManropeExtraBold
            } else {
                ManropeBold
            },
            fontWeight = if (hasBackButton) {
                FontWeight.ExtraBold
            } else {
                FontWeight.Bold
            },
            color = Color000000,
            maxLines = 1
        )
    }
}

@Composable
private fun ChatTitleContent(
    title: String,
    subtitle: String,
    isOnline: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            fontFamily = OutfitBold,
            fontWeight = FontWeight.Bold,
            fontSize = SdpR_18.nonScaledSp,
            color = ColorFDFDFD,
            maxLines = 1
        )

        if (subtitle.isNotBlank()) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isOnline) {
                    Box(
                        modifier = Modifier
                            .size(SdpR_6)
                            .background(
                                color = Color(0xFF4CD137),
                                shape = CircleShape
                            )
                    )

                    Spacer(modifier = Modifier.width(SdpR_4))
                }

                Text(
                    text = subtitle,
                    fontFamily = ManropeMedium,
                    fontWeight = FontWeight.Medium,
                    fontSize = SdpR_11.nonScaledSp,
                    color = ColorF1CBB7,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
private fun DefaultTopBarActions(
    coinCount: Int,
    modifier: Modifier = Modifier,
    onAddCoinClick: (() -> Unit)?,
    onTrophyClick: (() -> Unit)?,
    onSettingClick: (() -> Unit)?
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(SdpR_5)
    ) {
        CoinAction(
            coinCount = coinCount,
            onAddClick = onAddCoinClick,
            showAddButton = true
        )

        CircleAction(
            iconRes = R.drawable.img_trophy,
            contentDescription = "Trophy",
            onClick = onTrophyClick
        )

        CircleAction(
            iconRes = R.drawable.ic_setting,
            contentDescription = "Settings",
            onClick = onSettingClick
        )
    }
}

@Composable
private fun ChatTopBarActions(
    coinCount: Int,
    modifier: Modifier = Modifier,
    onMoreClick: (() -> Unit)?
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(SdpR_10)
    ) {
        CoinAction(
            coinCount = coinCount,
            showAddButton = false
        )

        Box(
            modifier = Modifier
                .size(SdpR_32)
                .clickable(
                    enabled = onMoreClick != null,
                    interactionSource = remember {
                        MutableInteractionSource()
                    },
                    indication = null
                ) {
                    onMoreClick?.invoke()
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_more),
                contentDescription = "More",
                tint = Color.Unspecified,
                modifier = Modifier.size(SdpR_32)
            )
        }
    }
}


@Composable
fun CoinAction(
    coinCount: Int,
    onAddClick: (() -> Unit)? = null,
    showAddButton: Boolean
) {
    Row(
        modifier = Modifier
            .height(SdpR_28)
            .background(
                color = ColorFFFFFF,
                shape = RoundedCornerShape(SdpR_16)
            )
            .border(
                width = SdpR_1,
                color = Color(0x17FFFFFF),
                shape = RoundedCornerShape(SdpR_20)
            )
            .clickable(
                enabled = !showAddButton && onAddClick != null,
                interactionSource = remember {
                    MutableInteractionSource()
                },
                indication = null
            ) {
                onAddClick?.invoke()
            }
            .padding(
                start = SdpR_10,
                end = if (showAddButton) SdpR_4 else SdpR_10
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_coin),
            contentDescription = null,
            modifier = Modifier.size(SdpR_18)
        )

        Spacer(modifier = Modifier.width(SdpR_6))

        Text(
            text = coinCount.toString(),
            fontFamily = ManropeBold,
            fontSize = SdpR_13.nonScaledSp,
            fontWeight = FontWeight.Bold,
            color = Color000000
        )

        if (showAddButton) {
            Spacer(modifier = Modifier.width(SdpR_8))

            AddCoinButton(
                onClick = onAddClick
            )
        }
    }
}

@Composable
private fun AddCoinButton(
    onClick: (() -> Unit)?
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    Box(
        modifier = Modifier
            .size(SdpR_18)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFD94BEE),
                        Color(0xFFF84DB5),
                        Color(0xFFFF6B8A)
                    )
                ),
                shape = CircleShape
            )
            .clickable(
                enabled = onClick != null,
                interactionSource = interactionSource,
                indication = null
            ) {
                onClick?.invoke()
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_add_coin),
            contentDescription = null,
            tint = ColorFFFFFF,
            modifier = Modifier.size(SdpR_10)
        )
    }
}

@Composable
private fun CircleAction(
    @DrawableRes iconRes: Int,
    contentDescription: String,
    onClick: (() -> Unit)?
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }

    Box(
        modifier = Modifier
            .size(SdpR_32)
            .clickable(
                enabled = onClick != null,
                interactionSource = interactionSource,
                indication = null
            ) {
                onClick?.invoke()
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = contentDescription,
            tint = Color.Unspecified,
            modifier = Modifier.size(SdpR_32)
        )
    }
}

@Preview(
    showBackground = true,
    widthDp = 430
)
@Composable
private fun CommonTopBarPreview() {
    CommonTopBar(
        title = "Discover",
        showActions = true
    )
}

@Preview(
    showBackground = true,
    widthDp = 430
)
@Composable
private fun CommonTopBarWithBackPreview() {
    CommonTopBar(
        title = "Settings",
        showActions = false,
        onBack = {}
    )
}

@Preview(
    showBackground = true,
    widthDp = 430
)
@Composable
private fun CommonTopBarNotTitleWithBackPreview() {
    CommonTopBar(
        title = "Settings",
        showActions = false,
        isShowTitle = false,
        onBack = {}
    )
}


@Preview(
    showBackground = true,
    widthDp = 430
)
@Composable
private fun ChatTopBarPreview() {
    CommonTopBar(
        title = "Luna",
        subtitle = "Soulmate • Level 12",
        coinCount = 480,
        style = CommonTopBarStyle.CHAT,
        showActions = true,
        onBack = {},
        onAddCoinClick = {},
        onMoreClick = {}
    )
}
