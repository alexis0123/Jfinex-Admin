package com.jfinex.admin.ui.dialog.exportToCsv

import android.content.ContentResolver
import android.net.Uri
import com.jfinex.admin.data.local.features.fields.FieldDao
import com.jfinex.admin.data.local.features.receipt.ReceiptDao
import com.jfinex.admin.data.local.features.students.Student
import com.jfinex.admin.data.local.features.students.StudentDao
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ExportDataRepository @Inject constructor(
    private val studentDao: StudentDao,
    private val receiptDao: ReceiptDao,
    private val fieldDao: FieldDao
) {
    suspend fun exportData(
        contentResolver: ContentResolver,
        uri: Uri,
        fieldName: String,
        yearFilter: Int? = null,
        blockFilter: String? = null
    ) {

        val students = studentDao.getStudents().first()
        val receipts = receiptDao.getAllReceipt()
        val fields = fieldDao.getFieldsFlow().first()

        val field = fields.find { it.name == fieldName }
            ?: throw IllegalArgumentException("Field $fieldName not found")

        val filteredStudents = students.filter { student ->
            val matchesYear = yearFilter?.let { student.block.startsWith(it.toString()) } ?: true
            val matchesBlock = blockFilter?.let { student.block == it } ?: true
            matchesYear && matchesBlock
        }.sortedWith(compareBy<Student> { it.block }.thenBy { it.name })

        contentResolver.openOutputStream(uri)?.bufferedWriter().use { writer ->
            writer?.appendLine("Block,Name,${field.name},Receipt Number,Officer Name,Comment")

            val receiptMap = receipts.associateBy { Triple(it.name, it.block, it.category.ifBlank { "Paid" }) }

            for (student in filteredStudents) {
                val categories = if (field.categories.isEmpty()) listOf("Paid") else field.categories

                // try to find a matching receipt
                val receipt = categories
                    .map { cat -> receiptMap[Triple(student.name, student.block, cat.ifBlank { "Paid" })] }
                    .firstOrNull { it != null }

                val category = when {
                    field.categories.isNotEmpty() -> receipt?.category?.ifBlank { "Paid" } ?: ""

                    else -> if (receipt != null) "Paid" else ""
                }

                val receiptNumber = receipt?.receiptNumber?.toString() ?: ""
                val officerName   = receipt?.officerName ?: ""
                val comment       = receipt?.comment ?: ""

                fun esc(value: String): String {
                    // replace 'ñ' and 'Ñ' with 'n' and 'N'
                    val sanitized = value.replace('ñ', 'n').replace('Ñ', 'N')
                    return if (sanitized.contains(",") || sanitized.contains("\"") || sanitized.contains("\n")) {
                        "\"" + sanitized.replace("\"", "\"\"") + "\""
                    } else {
                        sanitized
                    }
                }

                writer?.appendLine(
                    "${esc(student.block)}," +
                            "${esc(student.name)}," +
                            "${esc(category)}," +
                            "${esc(receiptNumber)}," +
                            "${esc(officerName)}," +
                            esc(comment)
                )
            }
        }
    }
}