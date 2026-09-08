package com.anrstudio.template.ui.component.creat.component

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
import com.anrstudio.template.ui.bases.compose.component.ActionsSheetCloseButton
import com.anrstudio.template.ui.bases.compose.theme.Color161127
import com.anrstudio.template.ui.bases.compose.theme.ColorE8C3AC
import com.anrstudio.template.ui.bases.compose.theme.ColorFDFDFD
import com.anrstudio.template.ui.bases.compose.theme.OutfitBold
import com.anrstudio.template.ui.bases.compose.theme.OutfitMedium
import com.anrstudio.template.ui.bases.compose.theme.SdpR_12
import com.anrstudio.template.ui.bases.compose.theme.SdpR_16
import com.anrstudio.template.ui.bases.compose.theme.SdpR_20
import com.anrstudio.template.ui.bases.compose.theme.SdpR_24
import com.anrstudio.template.ui.bases.compose.theme.SdpR_4
import com.anrstudio.template.ui.bases.compose.theme.SdpR_40
import com.anrstudio.template.ui.bases.compose.theme.SdpR_8
import com.anrstudio.template.ui.bases.compose.theme.nonScaledSp
import com.pegas.aura.aigirlfriend.soul.R

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
        containerColor = Color161127,
        contentColor = ColorFDFDFD,
        scrimColor = Color.Black.copy(alpha = 0.68f),
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
                        color = ColorFDFDFD.copy(alpha = 0.2f),
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
                    fontFamily = OutfitBold,
                    fontWeight = FontWeight.Bold,
                    fontSize = SdpR_20.nonScaledSp,
                    color = ColorFDFDFD
                )

                if (isMultiSelect) {
                    Text(
                        text = stringResource(R.string.options_done),
                        fontFamily = OutfitMedium,
                        fontSize = SdpR_16.nonScaledSp,
                        color = ColorE8C3AC,
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

            Spacer(modifier = Modifier.height(SdpR_12))

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(options) {
                    val isSelected = if (isMultiSelect) {
                        localSelected.contains(it)
                    } else {
                        selectedOptions.contains(it) || (it == noneOptionString && selectedOptions.isEmpty())
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                if (isMultiSelect) {
                                    if (it == noneOptionString) {
                                        localSelected.clear()
                                    } else {
                                        localSelected.remove(noneOptionString)
                                        if (localSelected.contains(it)) {
                                            localSelected.remove(it)
                                        } else {
                                            localSelected.add(it)
                                        }
                                    }
                                } else {
                                    onOptionSelected(if (it == noneOptionString) "" else it)
                                    onDismiss()
                                }
                            }
                            .padding(vertical = SdpR_16),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = it,
                            modifier = Modifier.weight(1f),
                            fontFamily = OutfitMedium,
                            fontSize = SdpR_16.nonScaledSp,
                            color = if (isSelected) ColorE8C3AC else ColorFDFDFD
                        )

                        if (isSelected) {
                            Icon(
                                painter = painterResource(R.drawable.ic_check),
                                contentDescription = null,
                                modifier = Modifier.size(SdpR_20),
                                tint = ColorE8C3AC
                            )
                        }
                    }
                }
            }
        }
    }
}
