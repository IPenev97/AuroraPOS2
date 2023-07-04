package com.sistechnology.aurorapos2.core.ui.components

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sistechnology.aurorapos2.R


@Composable
fun CustomSwitch(
    modifier: Modifier,
    color: Color = Color(0xFF35898F),
    onClick: () -> Unit,
    switchedOn: Boolean = true,
    rightText: String = stringResource(id = R.string.on),
    leftText: String = stringResource(id = R.string.off)
) {

    // this is to disable the ripple effect
    val interactionSource = remember {
        MutableInteractionSource()
    }

    // state of the switch
    var switchOn by remember {
        mutableStateOf(switchedOn)
    }

    // for moving the thumb
    val alignment by animateAlignmentAsState(if (switchOn) 1f else -1f)

    // outer rectangle with border
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = color,
                    shape = RoundedCornerShape(5.dp)
                )
                .clickable(
                    indication = null,
                    interactionSource = interactionSource,

                    ) {
                    switchOn = !switchOn;
                    onClick()
                },
            contentAlignment = Alignment.Center
        ) {

            // this is to add padding at the each horizontal side
            Box(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxSize(),
                contentAlignment = alignment,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(0.5f)
                        .clip(RoundedCornerShape(2.dp))
                        .background(color),
                    contentAlignment = Alignment.Center
                )
                {
                    Text(
                        text = if (switchOn) rightText else leftText,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.White
                        )
                    )
                }

            }
        }

        // gap between switch and the text
    }


}

@SuppressLint("UnrememberedMutableState")
@Composable
private fun animateAlignmentAsState(
    targetBiasValue: Float
): State<BiasAlignment> {
    val bias by animateFloatAsState(targetBiasValue)
    return derivedStateOf { BiasAlignment(horizontalBias = bias, verticalBias = 0f) }
}


