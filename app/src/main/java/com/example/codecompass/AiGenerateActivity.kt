package com.example.codecompass

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException


class AiGenerateActivity : AppCompatActivity() {

    private val BASE_URL = "https://api.groq.com/"
    private val GROQ_API_KEY = "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"
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ai_generate)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var outputBox:TextView=findViewById(R.id.output)
        var generateButton:Button=findViewById(R.id.genbutton)
        val add:Button=findViewById(R.id.addProject)

        generateButton.setOnClickListener {
            val msgText:TextView = findViewById(R.id.inputprompt)
            val userInput:String = msgText.text.toString()
            Log.d("TAG", "onCreate: $msgText")
            Log.d("TAG", "onCreate: $userInput")


            val msg="fitness"
            val text="As a professional application developer with over a decade of experience in building various software applications, I am well-versed in creating sophisticated solutions tailored to meet $userInput needs. Your task today is to build a roadmap for a specific type of application.Please provide the following details for me to generate a comprehensive roadmap:- Name: ________- Tech Stacks: ________- User Flow: ________- Design Inspiration: ___________________- Resources for APIs: ___________________- Basic or Must-Have Features: ________Additionally, please specify:- What is the best technology or framework for building this type of application?"
            Log.d("TAG", "onCreate: ${text.toString()}")
            //Retrofit Call
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val client = OkHttpClient()

                    val MEDIA_TYPE = "application/json".toMediaType()

                    val requestBody = """
        {
            "messages": [{"role": "user", "content": "$text"}],
            "model": "mixtral-8x7b-32768"
        }
    """.trimIndent()
                    val request = Request.Builder()
                        .url("https://api.groq.com/openai/v1/chat/completions")
                        .post(requestBody.toRequestBody(MEDIA_TYPE))
                        .header("Authorization", "Bearer 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")
                        .header("Content-Type", "application/json")
                        .build()

                    client.newCall(request).execute().use { response ->
                        if (!response.isSuccessful) {
                            throw IOException("Unexpected code $response")
                        }

                        val responseBody = response.body?.string()

                        // Update UI on the main thread
                        withContext(Dispatchers.Main) {
                            val jsonObject = responseBody?.let { it1 -> JSONObject(it1) }
                            val messageContent = jsonObject?.getJSONArray("choices")?.getJSONObject(0)
                                ?.getJSONObject("message")?.getString("content")
                            outputBox.text=messageContent.toString()
                            outputBox.visibility=View.VISIBLE

                            Log.d("TAG", "onCreate: $messageContent")
                        }
                    }
                } catch (e: Exception) {
                    Log.e("TAG", "Error: ${e.message}", e)
                }
            }
            add.visibility=View.VISIBLE
            }
        add.setOnClickListener {
                val intent = Intent(this, ProjectActivity::class.java)
                startActivity(intent)
                finish()
        }
    }
}
