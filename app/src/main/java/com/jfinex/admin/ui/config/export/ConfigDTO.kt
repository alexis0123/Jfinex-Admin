<<<<<<<< HEAD:app/src/main/java/com/jfinex/admin/ui/config/ConfigDTO.kt
package com.jfinex.admin.ui.config
========
package com.jfinex.admin.ui.config.export
>>>>>>>> 2470641 (refactor):app/src/main/java/com/jfinex/admin/ui/config/export/ConfigDTO.kt

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