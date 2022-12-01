package com.sub.domain.model

data class AppsflyerModel(
    val campaign: String?
) {
    class Builder(
        private var campaign: String? = null
    ) {
        fun setCampaign(v: String)  = apply { this.campaign = v }
        fun build() = AppsflyerModel(campaign = campaign)
    }
}