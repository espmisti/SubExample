package com.sub.domain.model

data class AppsflyerModel(
    val campaign: String?
) {
    class Builder(private val campaign: String?) {
        fun build() : AppsflyerModel = AppsflyerModel(campaign = campaign)
    }
}