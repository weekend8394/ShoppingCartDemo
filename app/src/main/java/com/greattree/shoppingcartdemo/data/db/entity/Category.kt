package com.greattree.shoppingcartdemo.data.db.entity

import com.google.firebase.firestore.PropertyName

data class Category(
    @set:PropertyName("id")
    @get:PropertyName("id")
    var categoryId: Int?,

    @set:PropertyName("name")
    @get:PropertyName("name")
    var categoryName: String?,

    @set:PropertyName("pic")
    @get:PropertyName("pic")
    var categoryPic: Int?,

    @set:PropertyName("subCategory")
    @get:PropertyName("subCategory")
    var subCategory: ArrayList<SubCategory>
)

data class SubCategory(
    @set:PropertyName("id")
    @get:PropertyName("id")
    var subCategoryId: Int?,

    @set:PropertyName("name")
    @get:PropertyName("name")
    var subCategoryName: String?,

    @set:PropertyName("pic")
    @get:PropertyName("pic")
    var subCategoryPic: Int?
)

data class ProductInfo(
    @set: PropertyName("name")
    @get: PropertyName("name")
    var productName: String,

    @set: PropertyName("price")
    @get: PropertyName("price")
    var productPrice: Int,

    @set: PropertyName("image")
    @get: PropertyName("image")
    var productImage: String,

    @set: PropertyName("description")
    @get: PropertyName("description")
    var productDescription: String
)