package com.example.shoppinglist.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


//for storage note from notepad
@Entity(tableName = "note_list")
data class NoteItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,

    @ColumnInfo(name = "title")
    val title: String, //name of note

    @ColumnInfo(name = "content")
    val content: String,

    @ColumnInfo(name = "time")
    val time: String,  // when create note

    @ColumnInfo(name = "category")
    val category: String //for filtering
)
