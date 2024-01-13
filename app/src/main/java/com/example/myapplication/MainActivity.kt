package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.HelperClass

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mText: EditText = binding.messageText

        val helper = HelperClass()
        val messages = ArrayList<Message>()
        binding.bSend.setOnClickListener {
            val message = Message("user", mText.text.toString())
            val response = helper.sendRequest(mText.text.toString())
            messages.add(message)
            adapter.submitList(messages)
            message.role = "yandexGPT"
            message.messageText = response
            messages.add(message)
            mText.setText("")
            adapter.submitList(messages)
        }
        initRcView()
    }

    private fun initRcView() = with(binding){
        adapter = MessageAdapter()
        rcView.layoutManager = LinearLayoutManager(this@MainActivity)
        rcView.adapter = adapter
    }
}