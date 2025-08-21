package com.jfinex.admin.ui.config

import kotlinx.serialization.Serializable

@Serializable
data class ConfigExport(
    val fields: Map<String, List<String>>,
    val students_with_receiptNumber: Map<String, StudentConfig>
)

@Serializable
data class StudentConfig(
    val block: String,
    val receipts: Map<String, Int>
)