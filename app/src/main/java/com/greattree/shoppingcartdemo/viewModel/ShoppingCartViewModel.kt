package com.greattree.shoppingcartdemo.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.greattree.shoppingcartdemo.data.db.entity.SubCategory
import com.greattree.shoppingcartdemo.data.db.entity.Product
import com.greattree.shoppingcartdemo.data.db.entity.Category
import com.greattree.shoppingcartdemo.repository.ShoppingCartRepository

class ShoppingCartViewModel(private val repository: ShoppingCartRepository) : ViewModel() {
    var subjectList = MutableLiveData<ArrayList<Category>>()
    var productList = MutableLiveData<ArrayList<Product>>()
    val db = Firebase.firestore

    suspend fun insertNote(product: Product) = repository.insertProduct(product)

    suspend fun updateNote(product: Product) = repository.updateProduct(product)

    suspend fun deleteNote(product: Product) = repository.deleteProduct(product)

    suspend fun deleteNoteById(id: Int) = repository.deleteProductById(id)

    suspend fun clearNote() = repository.clearProduct()

    fun getAllNotes() = repository.getAllProducts()

    fun getCategoryList() {
        val arrayList = ArrayList<Category>()


        val categoryName = arrayOf(
            "醫學美容",
            "婦嬰用品",
            "紙尿褲",
            "嬰兒副食品",
            "奶粉",
            "休閒食品",
            "保健食品",
            "居家生活",
            "營養飲品",
            "外用藥品",
            "醫材用品",
            "口服用品",
            "健康照護"
        )

        for (i in 0..12) {
            arrayList.add(Category(i, categoryName[i], 0, getSubCategoryList(i)))
        }
        val docData = hashMapOf("category" to arrayList)
        val ref = db.collection("category").document("category")
        ref.set(docData)

        db.collection("category")
            .get()
            .addOnSuccessListener { result->
                for (document in result){
                    subjectList.value = document.data["category"] as ArrayList<Category>
                    Log.d("TAG", "getCategoryList: " + document.data)
                }
            }
            .addOnFailureListener {

            }
    }

    private fun getSubCategoryList(categoryId: Int): ArrayList<SubCategory> {
        val categoryDetailList = ArrayList<SubCategory>()
        when (categoryId) {
            0 -> {
                val arrayList = arrayOf("臉部保養","洗沐美體/美髮","美齒清潔保養","牙刷/電動牙刷","膚質功能","BIO-OIL百落","Sensimin舒逸敏","Palmer's帕瑪氏","蔻羅蘭","艾瑪絲AROMASE","歐露兒ORRER","自然匯In")
                for (i in arrayList.indices){
                    categoryDetailList.add(SubCategory(i, arrayList[i], 0))
                }
                return categoryDetailList
            }
            1 -> {
                for (i in 0..2){
                    categoryDetailList.add(SubCategory(i, "孕產媽咪專區", 0))
                }
                return categoryDetailList
            }
            2 -> {
                for (i in 0..2){
                    categoryDetailList.add(SubCategory(i, "紙尿褲", 0))
                }
                return categoryDetailList
            }
            3 -> {
                for (i in 0..2){
                    categoryDetailList.add(SubCategory(i, "嬰兒副食品", 0))
                }
                return categoryDetailList
            }
            4 -> {
                for (i in 0..2){
                    categoryDetailList.add(SubCategory(i, "奶粉", 0))
                }
                return categoryDetailList
            }
            5 -> {
                for (i in 0..2){
                    categoryDetailList.add(SubCategory(i, "休閒食品", 0))
                }
                return categoryDetailList
            }
            6 -> {
                for (i in 0..2){
                    categoryDetailList.add(SubCategory(i, "保健食品", 0))
                }
                return categoryDetailList
            }
            7 -> {
                for (i in 0..2){
                    categoryDetailList.add(SubCategory(i, "居家生活", 0))
                }
                return categoryDetailList
            }
            8 -> {
                for (i in 0..2){
                    categoryDetailList.add(SubCategory(i, "營養飲品", 0))
                }
                return categoryDetailList
            }
            9 -> {
                for (i in 0..2){
                    categoryDetailList.add(SubCategory(i, "外用藥品", 0))
                }
                return categoryDetailList
            }
            10 -> {
                val arrayList = arrayOf("傷口護理","專業護具","頭/頸/肩部位","肘/腕/掌/指部位","專業鞋墊/機能襪","醫療彈性襪","運動機能襪","口罩/耳塞")

                for (i in arrayList.indices){
                    categoryDetailList.add(SubCategory(i, "居家生活", 0))
                }
                return categoryDetailList
            }
            11 -> {
                for (i in 0..2){
                    categoryDetailList.add(SubCategory(i, "口服用品", 0))
                }
                return categoryDetailList
            }
            12 -> {
                for (i in 0..2){
                    categoryDetailList.add(SubCategory(i, "健康照護", 0))
                }
                return categoryDetailList
            }
            else -> {
                return categoryDetailList
            }
        }
    }

    fun getProductList() {
        val testProductList = ArrayList<Product>()
        val productName = arrayOf(
            "3M FUTURO可調式護腕Black黑",
            "3M FUTURO網球/高爾夫球專用護肘",
            "3M FUTURO可調式高度支撐護腕",
            "天祿 多功能保護帶",
            "天祿 任意黏之指環護腕帶",
            "muva 遠紅外線專業支撐護腕SA2701",
            "3M FUTURO可調式護腕Black黑",
            "3M FUTURO網球/高爾夫球專用護肘",
            "3M FUTURO可調式高度支撐護腕",
            "天祿 多功能保護帶",
            "天祿 任意黏之指環護腕帶",
            "muva 遠紅外線專業支撐護腕SA2701",
            "3M FUTURO可調式護腕Black黑",
            "3M FUTURO網球/高爾夫球專用護肘",
            "3M FUTURO可調式高度支撐護腕",
            "天祿 多功能保護帶",
            "天祿 任意黏之指環護腕帶",
            "muva 遠紅外線專業支撐護腕SA2701"
        )

        val productPrice = arrayOf(
            270,
            500,
            1080,
            587,
            580,
            289,
            270,
            500,
            1080,
            580,
            580,
            289,
            270,
            500,
            1080,
            580,
            580,
            289
        )

        for (i in productName.indices) {
            testProductList.add(Product(i, productName[i], "阿拉拉拉拉", 0, productPrice[i]))
        }
        productList.value = testProductList
    }
}