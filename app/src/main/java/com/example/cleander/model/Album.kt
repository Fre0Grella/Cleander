package com.example.cleander.model

import android.icu.text.DateFormat
import java.io.Serializable

data class Album (
    val id: Int,
    val date: String,
    val imageId: Int,
    val swiped: Boolean = false
): Serializable