package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.HelperClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class YourViewModel : ViewModel() {
    private val helper = HelperClass()

    suspend fun sendRequest(userRequest: String): String {
        return helper.sendRequest(userRequest)
    }
}

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: MessageAdapter
    private lateinit var viewModel: YourViewModel
    private val messages = ArrayList<Message>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mText: EditText = binding.messageText

        viewModel = ViewModelProvider(this).get(YourViewModel::class.java)
        initRcView()

        binding.bSend.setOnClickListener {
            val userInputMessage = Message()
            userInputMessage.role = "you"
            userInputMessage.messageText = mText.text.toString()
            messages.add(userInputMessage)

            viewModel.viewModelScope.launch {
                try {
                    val response = viewModel.sendRequest(mText.text.toString())
                    val serverResponseMessage = Message()
                    serverResponseMessage.role = "yandexGPT"
                    serverResponseMessage.messageText = response
                    messages.add(serverResponseMessage)
                    adapter.submitList(messages)
                    mText.setText("")
                } catch (e: Exception) {
                    // Обработка ошибки здесь
                }
            }
            mText.setText("")
        }
    }

    private fun initRcView() = with(binding){
        adapter = MessageAdapter()
        rcView.layoutManager = LinearLayoutManager(this@MainActivity)
        rcView.adapter = adapter
    }
}