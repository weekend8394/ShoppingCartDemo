package com.greattree.shoppingcartdemo.base

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_title_bar.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

open class BaseActivity : AppCompatActivity() ,CoroutineScope by MainScope() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hidden()
        hiddenStatusBar()
    }

    override fun onStart() {
        super.onStart()
        initView()
    }

    private fun initView(){
        tv_title?.text = "大樹店購系統"
    }

    private fun hiddenStatusBar() {
        window.decorView.setOnSystemUiVisibilityChangeListener {
            if (it and View.SYSTEM_UI_FLAG_FULLSCREEN == 0) {
                hidden()
            }
        }
    }

    private fun hidden() {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LOW_PROFILE or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }

    fun showShoppingCart(visible: Boolean):Boolean{
        if (visible) {
            val mShowAction = TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f
            )
            mShowAction.duration = 400
            cl_shopping_cart.startAnimation(mShowAction)
            cl_shopping_cart.visibility = View.VISIBLE
        } else {
            val mShowAction = TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f
            )
            mShowAction.duration = 400
            cl_shopping_cart.startAnimation(mShowAction)
            cl_shopping_cart.visibility = View.GONE
        }
        return  visible
    }
}