package com.greattree.shoppingcartdemo.data.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "product")
@Parcelize
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val productName: String?,
    val description: String?,
    val priority: Int?,
    val productPrice: Int?
) : Parcelable