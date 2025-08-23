package com.jfinex.admin.data.local.fields

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FieldDao {

    @Query("SELECT * FROM fields")
    suspend fun getFields(): List<Field>

    @Insert
    suspend fun insert(field: Field)

    @Query("DELETE FROM fields")
    suspend fun clear()

}