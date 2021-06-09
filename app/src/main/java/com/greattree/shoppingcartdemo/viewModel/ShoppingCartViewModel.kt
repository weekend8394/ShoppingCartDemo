package com.greattree.shoppingcartdemo.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.greattree.shoppingcartdemo.data.db.entity.Category
import com.greattree.shoppingcartdemo.data.db.entity.Product
import com.greattree.shoppingcartdemo.data.db.entity.SubCategory
import com.greattree.shoppingcartdemo.repository.ShoppingCartRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup

class ShoppingCartViewModel(private val repository: ShoppingCartRepository) : ViewModel(),
    CoroutineScope by MainScope() {
    var mCategoryList = MutableLiveData<ArrayList<Category>>()
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
            arrayList.add(Category(i, categoryName[i], "", getSubCategoryList(i)))
        }

        /**
         * database 上傳主類別和副類別
         * */
        //val docData = hashMapOf("category" to arrayList)
        //val ref = db.collection("category").document("category")
        //ref.set(docData)

        val categoryList = arrayListOf<Category>()
        db.collection("category")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    val categoryMap = document.data["category"] as ArrayList<HashMap<String, Any>>
                    for (categoryLists in categoryMap) {
                        Log.d("TAG", "categoryList : $categoryLists")
                        //transform to subCategory
                        val subCategoryMap = categoryLists["subCategory"] as ArrayList<HashMap<String, Any>>
                        val subCategoryList = arrayListOf<SubCategory>()
                        for (subCategoryMaps in subCategoryMap) {
                            subCategoryList.add(
                                SubCategory(
                                    (subCategoryMaps["id"] as Long).toInt(),
                                    subCategoryMaps["name"] as String,
                                    subCategoryMaps["pic"] as String
                                )
                            )
                        }

                        //transform to category
                        categoryList.add(
                            Category(
                                (categoryLists["id"] as Long).toInt(),
                                categoryLists["name"] as String,
                                categoryLists["pic"] as String,
                                subCategoryList
                            )
                        )
                    }
                }
                mCategoryList.value = categoryList
            }
            .addOnFailureListener {

            }
    }

    private fun getSubCategoryList(categoryId: Int): ArrayList<SubCategory> {
        val categoryDetailList = ArrayList<SubCategory>()
        when (categoryId) {
            //醫學美容
            0 -> {
                val arrayList =
                    getHtml("https://storego.greattree.com.tw/store/series/De95cc6823B0b30b")
                for (i in arrayList.indices) {
                    categoryDetailList.add(SubCategory(i, arrayList[i], ""))
                }
                return categoryDetailList
            }
            //婦嬰用品
            1 -> {
                val arrayList =
                    getHtml("https://storego.greattree.com.tw/store/series/6cC5C3d823bf3306")
                for (i in arrayList.indices) {
                    categoryDetailList.add(SubCategory(i, arrayList[i], ""))
                }
                return categoryDetailList
            }
            //紙尿褲
            2 -> {
                val arrayList =
                    getHtml("https://storego.greattree.com.tw/store/series/5755C34823B5730b")
                for (i in arrayList.indices) {
                    categoryDetailList.add(SubCategory(i, arrayList[i], ""))
                }
                return categoryDetailList
            }
            //嬰幼兒副食品
            3 -> {
                val arrayList =
                    getHtml("https://storego.greattree.com.tw/store/series/7b85c46823B1730e")
                for (i in arrayList.indices) {
                    categoryDetailList.add(SubCategory(i, arrayList[i], ""))
                }
                return categoryDetailList
            }
            //奶粉
            4 -> {
                val arrayList =
                    getHtml("https://storego.greattree.com.tw/store/series/57d5C42823B36305")
                for (i in arrayList.indices) {
                    categoryDetailList.add(SubCategory(i, arrayList[i], ""))
                }
                return categoryDetailList
            }
            //休閒食品
            5 -> {
                val arrayList =
                    getHtml("https://storego.greattree.com.tw/store/series/57B5c57823B31305")
                for (i in arrayList.indices) {
                    categoryDetailList.add(SubCategory(i, arrayList[i], ""))
                }
                return categoryDetailList
            }
            //保健食品
            6 -> {
                val arrayList =
                    getHtml("https://storego.greattree.com.tw/store/series/5095C4f823b1430e")
                for (i in arrayList.indices) {
                    categoryDetailList.add(SubCategory(i, arrayList[i], ""))
                }
                return categoryDetailList
            }
            //居家生活
            7 -> {
                val arrayList =
                    getHtml("https://storego.greattree.com.tw/store/series/BbA5C5B823Ba230b")
                for (i in arrayList.indices) {
                    categoryDetailList.add(SubCategory(i, arrayList[i], ""))
                }
                return categoryDetailList
            }
            //營養飲品
            8 -> {
                val arrayList =
                    getHtml("https://storego.greattree.com.tw/store/series/1445c24823b3B302")
                for (i in arrayList.indices) {
                    categoryDetailList.add(SubCategory(i, arrayList[i], ""))
                }
                return categoryDetailList
            }
            //外用藥品
            9 -> {
                val arrayList =
                    getHtml("https://storego.greattree.com.tw/store/series/7C65C26823b7130e")
                for (i in arrayList.indices) {
                    categoryDetailList.add(SubCategory(i, arrayList[i], ""))
                }
                return categoryDetailList
            }
            //醫材用品
            10 -> {
                val arrayList =
                    getHtml("https://storego.greattree.com.tw/store/series/eAe5ca9823bf0303")
                for (i in arrayList.indices) {
                    categoryDetailList.add(SubCategory(i, arrayList[i], ""))
                }
                return categoryDetailList
            }
            //口服用品
            11 -> {
                val arrayList =
                    getHtml("https://storego.greattree.com.tw/store/series/A195c80823b8330C")
                for (i in arrayList.indices) {
                    categoryDetailList.add(SubCategory(i, arrayList[i], ""))
                }
                return categoryDetailList
            }
            //健康照護
            12 -> {
                val arrayList =
                    getHtml("https://storego.greattree.com.tw/store/series/e9D5c30823B0e30a")
                for (i in arrayList.indices) {
                    categoryDetailList.add(SubCategory(i, arrayList[i], ""))
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

    private fun getHtml(url: String): ArrayList<String> {
        val arrayList = arrayListOf<String>()
        launch(Dispatchers.IO) {
            val connection = Jsoup.connect(url)
            val docs = connection.get()
            docs.getElementsByClass("list-container").select("list-box").attr("text")

            for (string in docs.getElementsByClass("list-box")) {
                arrayList.add(string.text())
            }
        }

//        Thread.sleep(2000)
        return arrayList
    }
}