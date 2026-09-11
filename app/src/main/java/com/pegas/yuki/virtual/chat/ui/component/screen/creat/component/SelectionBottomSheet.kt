package com.pegas.yuki.virtual.chat.ui.component.screen.creat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.pegas.yuki.virtual.chat.R
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ManropeBold
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.ManropeMedium
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_12
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_14
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_16
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_18
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_20
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_24
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_4
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_40
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.SdpR_8
import com.pegas.yuki.virtual.chat.ui.bases.compose.theme.nonScaledSp
import com.pegas.yuki.virtual.chat.ui.component.screen.chatroom.component.ActionsSheetCloseButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectionBottomSheet(
    title: String,
    options: List<String>,
    selectedOptions: List<String>,
    isMultiSelect: Boolean = false,
    onDismiss: () -> Unit,
    onOptionSelected: (String) -> Unit,
    onMultiOptionsSelected: (List<String>) -> Unit = {}
) {
    val localSelected = remember(selectedOptions) {
        mutableStateListOf<String>().apply { addAll(selectedOptions) }
    }

    val noneOptionString = stringResource(R.string.option_none)

    ModalBottomSheet(
        onDismissRequest = {
            if (isMultiSelect) {
                onMultiOptionsSelected(localSelected.toList())
            }
            onDismiss()
        },
        containerColor = Color.White,
        contentColor = Color(0xFF150F25),
        scrimColor = Color.Black.copy(alpha = 0.45f),
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        shape = RoundedCornerShape(
            topStart = SdpR_24,
            topEnd = SdpR_24
        ),
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = SdpR_8)
                    .width(SdpR_40)
                    .height(SdpR_4)
                    .background(
                        color = Color(0xFFE5E7EB),
                        shape = RoundedCornerShape(SdpR_4)
                    )
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .navigationBarsPadding()
                .padding(
                    start = SdpR_20,
                    end = SdpR_20,
                    bottom = SdpR_16
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = SdpR_12),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    modifier = Modifier.weight(1f),
                    fontFamily = ManropeBold,
                    fontWeight = FontWeight.Bold,
                    fontSize = SdpR_18.nonScaledSp,
                    color = Color(0xFF150F25)
                )

                if (isMultiSelect) {
                    Text(
                        text = stringResource(R.string.options_done),
                        fontFamily = ManropeBold,
                        fontWeight = FontWeight.Bold,
                        fontSize = SdpR_16.nonScaledSp,
                        color = Color(0xFFFF4081),
                        modifier = Modifier
                            .clickable {
                                onMultiOptionsSelected(localSelected.toList())
                                onDismiss()
                            }
                            .padding(horizontal = SdpR_8)
                    )
                }

                ActionsSheetCloseButton(onClick = {
                    if (isMultiSelect) {
                        onMultiOptionsSelected(localSelected.toList())
                    }
                    onDismiss()
                })
            }

            Spacer(modifier = Modifier.height(SdpR_8))

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(options) {
                    val option = it
                    val isSelected = if (isMultiSelect) {
                        localSelected.contains(option)
                    } else {
                        selectedOptions.contains(option) || (option == noneOptionString && selectedOptions.isEmpty())
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                if (isMultiSelect) {
                                    if (option == noneOptionString) {
                                        localSelected.clear()
                                    } else {
                                        localSelected.remove(noneOptionString)
                                        if (localSelected.contains(option)) {
                                            localSelected.remove(option)
                                        } else {
                                            localSelected.add(option)
                                        }
                                    }
                                } else {
                                    onOptionSelected(if (option == noneOptionString) "" else option)
                                    onDismiss()
                                }
                            }
                            .padding(vertical = SdpR_14),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = option,
                            modifier = Modifier.weight(1f),
                            fontFamily = if (isSelected) ManropeBold else ManropeMedium,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            fontSize = SdpR_16.nonScaledSp,
                            color = if (isSelected) Color(0xFFFF4081) else Color(0xFF150F25)
                        )

                        if (isSelected) {
                            Icon(
                                painter = painterResource(R.drawable.ic_check),
                                contentDescription = null,
                                modifier = Modifier.size(SdpR_20),
                                tint = Color(0xFFFF4081)
                            )
                        }
                    }
                }
            }
        }
    }
}
