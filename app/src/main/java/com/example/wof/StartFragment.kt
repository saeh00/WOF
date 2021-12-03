package com.example.wof

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class StartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_start, container, false)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = context.let { CategoryAdapter(getCategoryList()) }

        val startButton = view.findViewById<Button>(R.id.startButton)

        startButton.setOnClickListener {
            if (category != ""){
                Navigation.findNavController(view).navigate(R.id.action_startGame)
            } else {
                Toast.makeText(activity, "Please choose category first", Toast.LENGTH_SHORT).show()
            }

        }

        return view
    }

    private fun getCategoryList(): ArrayList<String> {
        val catList = ArrayList<String>()
        val someList = resources.getStringArray(R.array.category)

        for (i in someList) {
            catList.add(i)
        }

        return catList
    }

}