package com.greattree.shoppingcartdemo.view

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Pair
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.greattree.shoppingcartdemo.R
import com.greattree.shoppingcartdemo.base.BaseActivity
import com.greattree.shoppingcartdemo.data.DataAdapter
import com.greattree.shoppingcartdemo.data.db.ShoppingCartDatabase
import com.greattree.shoppingcartdemo.data.db.entity.SubCategory
import com.greattree.shoppingcartdemo.data.db.entity.Category
import com.greattree.shoppingcartdemo.repository.ShoppingCartRepository
import com.greattree.shoppingcartdemo.utils.Coroutines
import com.greattree.shoppingcartdemo.utils.CustomerClickListener
import com.greattree.shoppingcartdemo.viewModel.ShoppingCartViewModel
import com.greattree.shoppingcartdemo.viewModel.ShoppingCartViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_category.view.*
import kotlinx.android.synthetic.main.item_sub_category.view.*
import kotlinx.android.synthetic.main.layout_title_bar.*

class MainActivity : BaseActivity() {
    private lateinit var shoppingCartDatabase: ShoppingCartDatabase
    private lateinit var repository: ShoppingCartRepository
    private lateinit var myFactory: ShoppingCartViewModelFactory
    private lateinit var viewModel: ShoppingCartViewModel
    private var categoryAdapter: DataAdapter.Builder<Category>? = null
    private var categoryList = arrayListOf<Category>()
    private var subSubCategoryAdapter: DataAdapter.Builder<SubCategory>? = null
    private var subCategoryList = arrayListOf<SubCategory>()
    private var selectedPosition = -1
    private var showShoppingCart = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        shoppingCartDatabase = ShoppingCartDatabase(this)
        repository = ShoppingCartRepository(shoppingCartDatabase)
        myFactory = ShoppingCartViewModelFactory(repository)
        viewModel = ViewModelProvider(this, myFactory)[ShoppingCartViewModel::class.java]
        initView()
        initAdapter()
        initObserver()
        viewModel.getCategoryList()
    }

    private fun initView() {
        view_back.visibility = View.GONE
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initAdapter() {
        /**
         * 大分類recyclerView
         * */
        if (categoryAdapter == null) {
            categoryAdapter =
                DataAdapter.Builder<Category>().setData(categoryList)
                    .setLayoutId(R.layout.item_category)
                    .addBindView { itemView, itemData, position ->
                        itemView.tv_category.text = itemData.categoryName
                        setCategorySelectedColor(itemView, itemData, position)
                        itemView.setOnClickListener(object : CustomerClickListener() {
                            override fun onOneClick() {
                                selectedPosition = position
                                viewModel.subjectList.value?.run {
                                    categoryAdapter?.update(this)
                                    subSubCategoryAdapter?.update(itemData.subCategory)
                                }
                            }
                        })
                    }
        }
        rv_category.layoutManager = LinearLayoutManager(this)
        rv_category.setHasFixedSize(true)

        categoryAdapter?.run {
            rv_category.adapter = this.create()
        }

        /**
         * 子分類recyclerView
         * */
        if (subSubCategoryAdapter == null) {
            subSubCategoryAdapter = DataAdapter.Builder<SubCategory>().setData(subCategoryList)
                .setLayoutId(R.layout.item_sub_category)
                .addBindView { itemView, itemData, _ ->
                    itemView.iv.setBackgroundResource(R.drawable.test_pic)
                    itemView.tv_sub_category_name.text = itemData.subCategoryName
                    itemView.setOnClickListener(object : CustomerClickListener() {
                        override fun onOneClick() {
                            Intent(this@MainActivity, ProductListActivity::class.java).apply {
                                this.putExtra(
                                    "categoryId",
                                    if (selectedPosition < 0) viewModel.subjectList.value!![0].categoryId else  viewModel.subjectList.value!![selectedPosition].categoryId
                                )
                                this.putExtra("subCategoryId", itemData.subCategoryId)
                                this.putExtra("subCategoryName", itemData.subCategoryName)
                            }.also {
                                val p1:Pair<View,String> = Pair.create(itemView.iv, "image_productShareAnimation")
                                val p2:Pair<View,String> = Pair.create(itemView.tv_sub_category_name, "text_productShareAnimation")
                                val transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(this@MainActivity, p1, p2)
                                startActivity(it,transitionActivityOptions.toBundle())
                            }
                        }
                    })
                }
        }
        rv_subCategory.layoutManager = GridLayoutManager(this,3)
        rv_subCategory.setHasFixedSize(true)

        subSubCategoryAdapter?.run {
            rv_subCategory.adapter = this.create()
        }
    }

    private fun initObserver() {
        Coroutines.main {
            viewModel.subjectList.observe(this@MainActivity, Observer {
                categoryAdapter?.update(it)
            })
        }
    }

    private fun setCategorySelectedColor(itemView: View, itemData: Category, position: Int) {
        //預設選取第一個position
        if (selectedPosition == -1 && position == 0) {
            itemView.cl.setBackgroundColor(this@MainActivity.getColor(R.color.green))
            itemView.tv_category.setTextColor(this@MainActivity.getColor(R.color.white))
            subSubCategoryAdapter?.update(itemData.subCategory)
        } else {
            itemView.cl.setBackgroundColor(
                if (position == selectedPosition) this.getColor(
                    R.color.green
                ) else this.getColor(R.color.white)
            )

            itemView.tv_category.setTextColor(
                if (position == selectedPosition) this.getColor(
                    R.color.white
                ) else this.getColor(R.color.black)
            )
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_UP && cl_shopping_cart.visibility == View.VISIBLE) {
            if (!(ev.x > cl_shopping_cart.left &&
                        ev.x < cl_shopping_cart.right &&
                        ev.y > cl_shopping_cart.top &&
                        ev.y < cl_shopping_cart.bottom)
            ) {
                showShoppingCart = showShoppingCart(false)
                return true
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}