package com.example.news.models.onboarding

import androidx.annotation.DrawableRes


data class Onboard (

    @DrawableRes
    val image: Int?,


    val title: String?,

    val text: String?
)
