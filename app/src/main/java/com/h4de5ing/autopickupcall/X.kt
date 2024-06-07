package com.h4de5ing.autopickupcall

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


private val flow2 = MutableStateFlow("")
val title22: StateFlow<String> = flow2

fun onSPChange(msg: String) {
    flow2.value = msg
}

var isAutoPickupCall = false