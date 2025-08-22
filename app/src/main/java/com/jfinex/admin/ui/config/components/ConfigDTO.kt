package com.jfinex.admin.ui.config.components

import kotlinx.serialization.Serializable

@Serializable
data class ConfigExport(
    val newBaseNumbers: Map<String, Int>,
    val fields: Map<String, List<String>>,
    val studentsWithReceiptNumber: Map<String, StudentConfig>
)

@Serializable
data class StudentConfig(
    val block: String,
    val receipts: Map<String, Int>
)