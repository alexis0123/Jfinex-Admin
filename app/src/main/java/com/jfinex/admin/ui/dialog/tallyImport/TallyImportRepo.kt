package com.jfinex.admin.ui.dialog.tallyImport

import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import com.google.gson.GsonBuilder
import com.jfinex.admin.data.local.features.collection.CollectionDao
import com.jfinex.admin.data.local.features.fields.FieldDao
import com.jfinex.admin.data.local.features.receipt.ReceiptDao
import com.jfinex.admin.data.local.features.receipt.json
import com.jfinex.admin.data.local.features.students.Student
import com.jfinex.admin.data.local.features.students.StudentDao
import com.jfinex.admin.ui.config.components.xor.xorDe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.io.InputStreamReader
import java.time.LocalDate
import javax.inject.Inject

class TallyImportRepo @Inject constructor(
    private val collectionDao: CollectionDao,
    private val fieldDao: FieldDao,
    private val receiptDao: ReceiptDao,
    private val studentDao: StudentDao
) {
    suspend fun importTally(uri: Uri, contentResolver: ContentResolver): Result<Unit> =
        withContext(Dispatchers.IO) {
            try {
                // 1. Read file
                val input = contentResolver.openInputStream(uri) ?: return@withContext Result.failure(Exception("Failed to open file"))
                val raw = InputStreamReader(input, Charsets.UTF_8).use { it.readText() }
                val decrypted = xorDe(raw)

                // 2. Parse JSON
                val tallyData = json.decodeFromString<TallyData>(decrypted)

                // 3. Validate fields
                val savedFields = fieldDao.getFieldsFlow().first()
                val savedNormalized = savedFields.map { it.name }
                val tallyNormalized = tallyData.fields.map { it.name }

                if (tallyData.fields.isEmpty()) {
                    return@withContext Result.failure(Exception("Missing fields in tally file"))
                }

                if (savedNormalized.toSet() != tallyNormalized.toSet()) {
                    return@withContext Result.failure(Exception("Field mismatch"))
                }

                // 4. Purge receipts + collections for officer
                receiptDao.removeByOfficer(tallyData.officerName)
                collectionDao.removeByOfficer(tallyData.officerName)

                // 5. Insert collections
                tallyData.collections.forEach { collection ->
                    collectionDao.addCollection(collection.copy(id = 0))

                    // 6. Handle Added Student
                    if (collection.type == "Added Student") {
                        val student = studentDao.getStudent(collection.name, collection.block)
                        if (student == null) {
                            val fields = savedFields
                            val receiptNumbers = fields.associate { field ->
                                field.name to (field.newBase..field.newBase + 9_999).random()
                            }
                            studentDao.insert(
                                Student(
                                    block = collection.block,
                                    name = collection.name,
                                    receiptNumber = receiptNumbers
                                )
                            )
                        }
                    }
                }

                // 7. Insert receipts (overwrite if exists)
                tallyData.receipts.forEach { receipt ->
                    val existing = receiptDao.getReceipt(receipt.name, receipt.block, receipt.item)
                    if (existing != null) {
                        receiptDao.updateReceipt(receipt.copy(id = existing.id))
                    } else {
                        receiptDao.addReceipt(receipt.copy(id = 0))
                    }
                }

                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
}