package com.example.myapplication

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

class HelperClass {

    fun sendRequest(userRequest: String): String {
        val gson = Gson()

        val prompt = mapOf(
            "modelUri" to "gpt://b1g2fdv1id9rtuuqu89c/yandexgpt-lite",
            "completionOptions" to mapOf(
                "stream" to false,
                "temperature" to 0.6,
                "maxTokens" to "2000"
            ),
            "messages" to listOf(
                mapOf(
                    "role" to "user",
                    "text" to userRequest
                )
            )
        )

        val url = "https://llm.api.cloud.yandex.net/foundationModels/v1/completion"
        val apiKey = "AQVN2M0pQshC5tIumbugNLY64P5u70EnHRnreZlA"

        val client = OkHttpClient()

        val requestBody = gson.toJson(prompt)
            .toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url(url)
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "Api-Key $apiKey")
            .post(requestBody)
            .build()

        val response = client.newCall(request).execute()
        val resultData = gson.fromJson(response.body?.string(), Map::class.java)
        val textResponse = (resultData["result"] as Map<*, *>)["alternatives"]
            .let { (it as List<*>)[0] as Map<*, *> }
            .let { it["message"] as Map<*, *> }
            .let { it["text"] as String }

        return textResponse
    }

}