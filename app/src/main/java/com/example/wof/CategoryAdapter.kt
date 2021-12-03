package com.example.wof

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

public var category = ""

class CategoryAdapter(var arrayList: ArrayList<String>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        holder.catTitle.text = arrayList[position]
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val catTitle: TextView = itemView.findViewById(R.id.categoryList)

        init {
            itemView.setOnClickListener {

                val position: Int = adapterPosition

                when (position) {
                    0 -> category = "Animals"
                    1 -> category = "Sports"
                    2 -> category = "Food"
                    3 -> category = "Countries"
                }

            }
        }
    }
}