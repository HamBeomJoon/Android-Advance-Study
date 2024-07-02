package com.example.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val myListAdapter: MyListAdapter by lazy {
        MyListAdapter()
    }

    private val dataSet = arrayListOf<Monster>().apply {
        add(Monster("뿔버섯", 10, listOf(100, 10, 50)))
        add(Monster("스텀프", 23, listOf(200, 20, 100)))
        add(Monster("슬라임", 2, listOf(10, 1, 5)))
        add(Monster("주니어발록", 2500, listOf(10000, 1000, 50000)))
        add(Monster("이블아이", 100, listOf(1000, 200, 1000)))
        add(Monster("와일드카고", 50, listOf(2000, 250, 10000)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.recyclerview.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = myListAdapter
        }
        myListAdapter.submitList(dataSet)

        binding.fab.setOnClickListener {
            val list = myListAdapter.currentList.toMutableList()
            list.add(Monster("핑크빈", 200, listOf(999999999, 10, 50)))
            myListAdapter.submitList(list)
        }

        val itemTouchHelper = ItemTouchHelper(MyItemTouchHelperCallback(binding.recyclerview))
        itemTouchHelper.attachToRecyclerView(binding.recyclerview)
    }
}
