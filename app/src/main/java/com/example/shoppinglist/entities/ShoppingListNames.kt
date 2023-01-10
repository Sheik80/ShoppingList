package com.example.shoppinglist.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "shopping_list_names")
data class ShoppingListNames(
    @PrimaryKey (autoGenerate = true)
    val id: Int?, //here we generater primaryKey for DB and add new peremmennay to implement this key, generator works only with nullable

    @ColumnInfo (name = "name")
    val name: String, //can't be null because we create column of table, and BD can't be without names

    @ColumnInfo (name = "time")
    val time: String, //we want to know when user create shopping

    @ColumnInfo (name = "allItemCounter")
    val allItemCounter: Int, //we want to know how items we shop, and create progressbar for shop next items

    @ColumnInfo (name = "checkedItemsCounter")
    val checkedItemsCounter: Int,  //we create this count for ours calculations how items we buy, and how we need to buy more

    @ColumnInfo (name = "itemsIds")
    val itemsIds: String, //we need to write to BD our id List of Shopping? find to some elements of list

): Serializable //this way we can to receive all columns to our nex classes
