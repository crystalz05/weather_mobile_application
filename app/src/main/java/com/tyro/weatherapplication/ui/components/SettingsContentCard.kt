package com.tyro.weatherapplication.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SettingCard(icon: ImageVector, title: String, description: String, action: @Composable ()-> Unit){
    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background.copy(0.2f)),
        border = BorderStroke(width = 0.4.dp, color = MaterialTheme.colorScheme.onBackground.copy(0.2f)),
        modifier = Modifier.wrapContentHeight()
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = icon, contentDescription = null, Modifier.size(30.dp), tint = MaterialTheme.colorScheme.primary)
                Spacer(Modifier.width(16.dp))
                Column {
                    Text(title, color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.titleMedium)
                    Text(description, color = MaterialTheme.colorScheme.onBackground.copy(0.4f), style = MaterialTheme.typography.titleSmall)
                }
            }
            action()
        }
    }
}

@Composable
fun AboutCard(){
    Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background.copy(0.2f)),
        border = BorderStroke(width = 0.4.dp, color = MaterialTheme.colorScheme.onBackground.copy(0.2f)),
        modifier = Modifier.wrapContentHeight()
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text("Tyro Weather App", color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.titleMedium)
                    Text("Version 1.0.0", color = MaterialTheme.colorScheme.onBackground.copy(0.5f))
                    Spacer(Modifier.height(8.dp))
                    Text("Your daily weather companion", color = MaterialTheme.colorScheme.onBackground.copy(0.5f))
                }
            }
        }
    }
}


@Composable
fun ThinSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    size: SwitchSize = SwitchSize.Medium,
    colors: SwitchColors = SwitchDefaults.thinSwitchColors()
) {
    val thumbOffset by animateDpAsState(
        targetValue = if (checked) size.trackWidth - size.thumbSize - 4.dp else 2.dp,
        label = "thumbOffset"
    )

    val trackColor by animateColorAsState(
        targetValue = if (checked) colors.checkedTrackColor else colors.uncheckedTrackColor,
        label = "trackColor"
    )

    Box(
        modifier = modifier
            .size(width = size.trackWidth, height = size.trackHeight)
            .clip(RoundedCornerShape(50))
            .background(trackColor)
            .clickable(
                enabled = enabled,
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onCheckedChange(!checked)
            }
            .padding(2.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .offset(x = thumbOffset)
                .size(size.thumbSize)
                .clip(CircleShape)
                .background(colors.thumbColor)
                .shadow(2.dp, CircleShape)
        )
    }
}

enum class SwitchSize(val trackWidth: Dp, val trackHeight: Dp, val thumbSize: Dp) {
    Small(36.dp, 18.dp, 14.dp),
    Medium(44.dp, 22.dp, 18.dp),
    Large(52.dp, 26.dp, 22.dp)
}

// Color configuration
data class SwitchColors(
    val checkedTrackColor: Color,
    val uncheckedTrackColor: Color,
    val thumbColor: Color
)

object SwitchDefaults {
    @Composable
    fun thinSwitchColors(
        checkedTrackColor: Color = MaterialTheme.colorScheme.primary,
        uncheckedTrackColor: Color = MaterialTheme.colorScheme.surfaceVariant,
        thumbColor: Color = Color.White
    ) = SwitchColors(
        checkedTrackColor = checkedTrackColor,
        uncheckedTrackColor = uncheckedTrackColor,
        thumbColor = thumbColor
    )
}

@Composable
fun ThinCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    size: Dp = 24.dp,
    color: Color = MaterialTheme.colorScheme.primary
) {
    val progress by animateFloatAsState(
        targetValue = if (checked) 1f else 0f,
        animationSpec = tween(200),
        label = "checkmarkProgress"
    )

    val backgroundColor by animateColorAsState(
        targetValue = if (checked) color else Color.Transparent,
        label = "backgroundColor"
    )

    val borderColor by animateColorAsState(
        targetValue = if (checked) color else MaterialTheme.colorScheme.outline,
        label = "borderColor"
    )

    Box(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(4.dp))
            .background(backgroundColor)
            .border(2.dp, borderColor, RoundedCornerShape(4.dp))
            .clickable(
                enabled = enabled,
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { onCheckedChange(!checked) },
        contentAlignment = Alignment.Center
    ) {
        if (progress > 0f) {
            Canvas(
                modifier = Modifier
                    .size(size * 1f)
                    .graphicsLayer {
                        scaleX = progress
                        scaleY = progress
                        alpha = progress
                    }
            ) {
                val path = Path().apply {
                    moveTo(size.toPx() * 0.2f, size.toPx() * 0.5f)
                    lineTo(size.toPx() * 0.45f, size.toPx() * 0.75f)
                    lineTo(size.toPx() * 0.8f, size.toPx() * 0.25f)
                }

                drawPath(
                    path = path,
                    color = Color.White,
                    style = Stroke(width = 3.dp.toPx(), cap = StrokeCap.Round)
                )
            }
        }
    }
}


@Composable
fun CustomDropDownMenu(options: List<String>, selectedOption: String, onOptionSelected: (String) -> Unit){

    var expanded by remember { mutableStateOf(false) }
    Box() {
        Row(modifier = Modifier
            .width(60.dp)
            .height(37.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colorScheme.onPrimary)
            .clickable { expanded = !expanded }
            .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = selectedOption,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Icon(imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {expanded = false},
            containerColor = Color.Unspecified,
            modifier = Modifier
                .width(60.dp),
            shadowElevation = 0.dp
        ) {
            options.forEach{ option ->
                DropdownMenuItem(
                    text = { Text(option,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    ) },
                    modifier = Modifier.height(30.dp)
                        .background(
                            color = if (option == selectedOption)
                                MaterialTheme.colorScheme.primary.copy(0.2f)
                            else
                                Color.White
                        ),
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}