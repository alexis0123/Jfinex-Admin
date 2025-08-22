package com.jfinex.admin.ui.config.export

import javax.inject.Inject
import com.jfinex.admin.ui.field.FieldsRepository

class ConfigRepository @Inject constructor(
    private val fieldsRepository: FieldsRepository,
    private val receiptRepo: ReceiptNoGeneratorRepo
) {

    // Helper to check if there are any fields added
    fun hasFields(): Boolean = fieldsRepository.fields.isNotEmpty()

    fun buildConfig(students: List<List<String>>): ConfigExport {
        val fields = fieldsRepository.fields.associate { field ->
            field.name to field.category
        }

        if (fields.isEmpty()) throw IllegalStateException("No fields added. Add at least one field before export.")
        if (students.isEmpty()) throw IllegalStateException("CSV has no valid students.")

        val (studentsList, newBases) = receiptRepo.generateReceiptNo(
            studentsList = students,
            fields = fields.keys.toList()
        )

        val studentsWithRN = studentsList.associate { student ->
            student.name to StudentConfig(
                block = student.block,
                receipts = student.receiptNumbers
            )
        }

        return ConfigExport(
            newBaseNumbers = newBases,
            fields = fields,
            studentsWithReceiptNumber = studentsWithRN
        )
    }
}