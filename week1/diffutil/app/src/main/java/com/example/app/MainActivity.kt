package com.example.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app.databinding.ActivityMainBinding

class
MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val myListAdapter: MyListAdapter by lazy {
        MyListAdapter()
    }

    private val dataSet = arrayListOf<Monster>().apply {
        add(Monster("타일런트", Race.Zombie, 10, listOf(100, 10, 50), false))
        add(Monster("조커", Race.Human, 23, listOf(200, 20, 100), false))
        add(Monster("그렘린", Race.Goblin, 2, listOf(10, 1, 5), true))
        add(Monster("리오레우스", Race.Dragon, 2500, listOf(10000, 1000, 50000), false))
        add(Monster("사우론", Race.Human, 100, listOf(1000, 200, 1000), false))
        add(Monster("리바이어던", Race.Dragon, 50, listOf(2000, 250, 10000), true))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.recyclerview.apply {
            layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = myListAdapter
        }
        myListAdapter.submitList(dataSet)

        binding.fab.setOnClickListener {
            myListAdapter.submitList(dataSet.shuffled())
        }

        val itemTouchHelper = ItemTouchHelper(MyItemTouchHelperCallback(binding.recyclerview))
        itemTouchHelper.attachToRecyclerView(binding.recyclerview)
    }
}
