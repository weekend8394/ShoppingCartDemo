package com.greattree.shoppingcartdemo.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.greattree.shoppingcartdemo.R
import com.greattree.shoppingcartdemo.base.BaseActivity
import com.greattree.shoppingcartdemo.data.DataAdapter
import com.greattree.shoppingcartdemo.data.db.ShoppingCartDatabase
import com.greattree.shoppingcartdemo.data.db.entity.Product
import com.greattree.shoppingcartdemo.repository.ShoppingCartRepository
import com.greattree.shoppingcartdemo.utils.CustomerClickListener
import com.greattree.shoppingcartdemo.viewModel.ShoppingCartViewModel
import com.greattree.shoppingcartdemo.viewModel.ShoppingCartViewModelFactory
import kotlinx.android.synthetic.main.activity_product_list.*
import kotlinx.android.synthetic.main.item_product.view.*
import kotlinx.android.synthetic.main.layout_title_bar.*

class ProductListActivity : BaseActivity() {
    private var categoryName = ""
    private var showShoppingCart = false
    private var productAdapter: DataAdapter.Builder<Product>? = null
    private var productList = arrayListOf<Product>()
    private lateinit var shoppingCartDatabase: ShoppingCartDatabase
    private lateinit var repository: ShoppingCartRepository
    private lateinit var myFactory: ShoppingCartViewModelFactory
    private lateinit var viewModel: ShoppingCartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)
        shoppingCartDatabase = ShoppingCartDatabase(this)
        repository = ShoppingCartRepository(shoppingCartDatabase)
        myFactory = ShoppingCartViewModelFactory(repository)
        viewModel = ViewModelProvider(this, myFactory)[ShoppingCartViewModel::class.java]

        initView()
        initListener()
        initIntent()
        initAdapter()
        initObserver()
        viewModel.getProductList()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun initView() {
        iv_product.setImageDrawable(this.getDrawable(R.drawable.test_pic))
    }

    private fun initListener() {
        view_back.setOnClickListener(object : CustomerClickListener() {
            override fun onOneClick() {
                onBackPressed()
            }
        })

        tv_shopping_cart.setOnClickListener {
            showShoppingCart(!showShoppingCart)
        }
    }

    private fun initIntent() {
        categoryName = intent.getStringExtra("subCategoryName") ?: ""
        tv_sub_category_name.text = categoryName
    }

    private fun initAdapter() {
        if (productAdapter == null) {
            productAdapter = DataAdapter.Builder<Product>().setData(productList)
                .setLayoutId(R.layout.item_product).addBindView { itemView, itemData, position ->
                    itemView.iv_product.setBackgroundResource(R.drawable.test_pic)
                    itemView.tv_product_name.text = itemData.productName
                }
        }
        rv_product.layoutManager = GridLayoutManager(this,4)
        rv_product.setHasFixedSize(true)
        rv_product.adapter = productAdapter?.create()
    }

    private fun initObserver(){
        viewModel.productList.observe(this, Observer {
            productAdapter?.update(it)
            it.size
        })
    }
}