package com.greattree.shoppingcartdemo.utils

import android.view.View

abstract class CustomerClickListener : View.OnClickListener {
    private var lastClickTime = 0L

    override fun onClick(v: View) {
        if (System.currentTimeMillis() - lastClickTime > FAST_CLICK_TIME) {
            onOneClick()
            lastClickTime = System.currentTimeMillis()
        }
    }

    abstract fun onOneClick()

    companion object {
        private const val FAST_CLICK_TIME = 1000
    }
}
