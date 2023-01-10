package com.example.shoppinglist.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shop_list_item") //here we will storage our items from our list of shopping
data class ShoppingListItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,

    @ColumnInfo(name = "name")
    val name: String, //here we write name of items

    @ColumnInfo(name = "itemInfo")
    val itemInfo: String?, //info about items, maybe 300 gram

    @ColumnInfo(name = "itemChecked")
    val itemChecked: Int=0,     //info click buy or not our item in basket, if choose we will write 1

    @ColumnInfo(name = "listId")
    val listId: Int,         //here we store id of our list, to find it

    @ColumnInfo(name = "itemType")
    val itemType: String = "item",      // it's library for names of our items, if item значит выюбираем если нет смотрим в корзину и выбираем из нашей библиотеки

)  //нет serializable потому что будем вызывать из другого класса
