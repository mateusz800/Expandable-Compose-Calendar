package com.mabn.calendarlibrary.component

import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun ToggleExpandCalendarButton(
    isExpanded: Boolean,
    expand: () -> Unit,
    collapse: () -> Unit,
    color: Color
) {
    IconToggleButton(
        checked = isExpanded,
        onCheckedChange = { isChecked -> if (isChecked) expand() else collapse() }
    ) {
        if (isExpanded) {
            Icon(Icons.Default.KeyboardArrowUp, "Collapse calendar", tint = color)
        } else {
            Icon(Icons.Default.KeyboardArrowDown, "Expand calendar", tint = color)
        }

    }
}