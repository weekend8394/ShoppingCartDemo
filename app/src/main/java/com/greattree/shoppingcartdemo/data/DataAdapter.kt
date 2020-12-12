package com.greattree.shoppingcartdemo.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class DataAdapter<T> private constructor() : RecyclerView.Adapter<DataAdapter<T>.DataViewHolder>() {
    //數據
    private var mDataList: ArrayList<T>? = null

    //佈局id
    private var mLayoutId: Int? = null

    //綁定事件的lambda放發
    private var addBindView: ((itemView: View, itemData: T, position: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(mLayoutId!!, parent, false)
        return DataViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mDataList?.size ?: -1 //左側為null時返回-1
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        addBindView?.invoke(holder.itemView, mDataList?.get(position)!!, position)
    }

    /**
     * 數據更新
     */
    fun update(lists: ArrayList<T>) {
        mDataList = lists
        notifyDataSetChanged()
    }

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    /**
     * 建造者，用來完成adapter的數據組合
     */
    class Builder<B> {
        private var adapter: DataAdapter<B> = DataAdapter()
        /**
         * 設置數據
         */
        fun setData(lists: ArrayList<B>): Builder<B> {
            adapter.mDataList = lists
            return this
        }

        /**
         * 設置佈局id
         */
        fun setLayoutId(layoutId: Int): Builder<B> {
            adapter.mLayoutId = layoutId
            return this
        }

        /**
         * 綁定View和數據
         */
        fun addBindView(itemBind: ((itemView: View, itemData: B, position: Int) -> Unit)): Builder<B> {
            adapter.addBindView = itemBind
            return this
        }

        fun create(): DataAdapter<B> {
            return adapter
        }

        /**
         * 數據更新
         */
        fun update(lists: ArrayList<B>) {
            adapter.mDataList = lists
            adapter.notifyDataSetChanged()
        }

        fun notifyItemInserted(position: Int) {
            adapter.notifyItemInserted(position)
        }

        fun notifyItemChanged(position: Int) {
            adapter.notifyItemChanged(position)
        }
    }
}