package com.example.app

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), TextWatcher, View.OnClickListener {
    lateinit var binding: ActivityMainBinding
    lateinit var talkAdapter: TalkAdapter
    var changeType = false
    var s = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        binding.talk.addTextChangedListener(this)

        talkAdapter = TalkAdapter()
        binding.recyclerView.adapter = talkAdapter
        binding.recyclerView.setHasFixedSize(false)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

    }


    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.submit_talk -> {
                submitTalk()
            }
        }
    }

    override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
        this.s = s.toString()
    }

    override fun afterTextChanged(s: Editable?) {

    }

    private fun submitTalk() {
        val talk = if (changeType) {
            Talk(s, "right")
        } else {
            Talk(s, "left")
        }

        talkAdapter.addItem(talk)
        binding.recyclerView.smoothScrollToPosition(talkAdapter.lst.size - 1)
        talkAdapter.notifyItemChanged(talkAdapter.lst.size - 1)
        changeType = !changeType
        binding.talk.text = null
    }
}