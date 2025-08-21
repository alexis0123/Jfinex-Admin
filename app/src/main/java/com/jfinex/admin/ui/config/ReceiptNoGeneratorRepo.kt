package com.jfinex.admin.ui.config

import kotlinx.serialization.Serializable
import javax.inject.Inject

@Serializable
data class StudentsWithRN(
    val block: String,
    val name: String,
    val receiptNumbers: Map<String, Int>
)

const val START = 100000
const val INCREMENT = 10000

class ReceiptNoGeneratorRepo @Inject constructor() {

    fun generateReceiptNo(
        studentsList: List<List<String>>, // each = [block, name]
        fields: List<String>
    ): List<StudentsWithRN> {

        // Prepare a shuffled list of receipt numbers for each field
        val fieldToReceipts: Map<String, List<Int>> = fields.mapIndexed { index, field ->
            val base = START + (index * INCREMENT)
            val receipts = (base until base + studentsList.size).shuffled()
            field to receipts
        }.toMap()

        // Assign each student one receipt per field
        return studentsList.mapIndexed { studentIndex, studentRow ->
            val block = studentRow.getOrNull(0).orEmpty()
            val name = studentRow.getOrNull(1).orEmpty()

            val receiptNumbers = fields.associateWith { field ->
                fieldToReceipts[field]!![studentIndex]
            }

            StudentsWithRN(
                block = block,
                name = name,
                receiptNumbers = receiptNumbers
            )
        }
    }
}