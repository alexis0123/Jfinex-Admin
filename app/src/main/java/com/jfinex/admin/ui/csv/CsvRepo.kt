package com.jfinex.admin.ui.csv

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.OpenableColumns
import com.opencsv.CSVReader
import dagger.hilt.android.scopes.ViewModelScoped
import java.io.InputStreamReader
import javax.inject.Inject

@ViewModelScoped
class CsvRepository @Inject constructor() {

    @Throws(Exception::class)
    fun readCsvFromUri(contentResolver: ContentResolver, uri: Uri): List<List<String>> {
        val output = mutableListOf<List<String>>()

        contentResolver.openInputStream(uri)?.use { inputStream ->
            InputStreamReader(inputStream).use { isr ->
                CSVReader(isr).use { csvReader ->
                    var nextLine = csvReader.readNext()
                    while (nextLine != null) {
                        output.add(nextLine.toList())
                        nextLine = csvReader.readNext()
                    }
                }
            }
        } ?: throw IllegalArgumentException("Unable to open stream for URI: $uri")

        return output
    }

    fun getFileName(contentResolver: ContentResolver, uri: Uri): String? {
        var name: String? = null
        val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val index = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (index >= 0) {
                    name = it.getString(index)
                }
            }
        }
        return name
    }
}