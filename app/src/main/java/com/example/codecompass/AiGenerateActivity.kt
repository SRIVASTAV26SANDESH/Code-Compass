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
import com.google.ai.client.generativeai.GenerativeModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class AiGenerateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ai_generate)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val textprompt:EditText=findViewById(R.id.prompt)
        var outputBox:TextView=findViewById(R.id.output)
        var generateButton:Button=findViewById(R.id.genbutton)
        val add:Button=findViewById(R.id.addProject)
        val prompt = textprompt.toString()

        generateButton.setOnClickListener {
//            val generativeModel = GenerativeModel(
//                modelName = "gemini-pro",
//                apiKey = "AIzaSyD6fKS1Jil8me1EBVLAi3jbuejuM_2D4TE",
//            )
//
//            runBlocking {
//                val response = generativeModel.generateContent(prompt)
//                outputBox.text=response.text.toString()
//                Log.d("msg", "${response.text} ")
//            }
            outputBox.visibility=View.VISIBLE
            outputBox.text="Setup: Create a new Android project in Android Studio. '\\n'\n" +
                    "            UI Design: Design screens for adding, editing, and viewing recipes following Material Design guidelines.\n" +
                    "            Data Model: Define a data model for recipes, including properties like name, ingredients, and instructions.\n" +
                    "            Recipe Editor: Implement screens for adding and editing recipes with appropriate UI components.\n" +
                    "            Recipe List: Create a screen to display a list of recipes using RecyclerView.\n" +
                    "            Details View: Implement a screen to display detailed information about a selected recipe.\n" +
                    "            Image Support: Allow users to attach images to recipes.\n" +
                    "            Data Persistence: Implement data persistence to save and retrieve recipe data.\n" +
                    "            Enhance UX: Add animations, transitions, and responsive design for better user experience.\n" +
                    "            Testing: Test the app thoroughly and consider automated testing.\n" +
                    "            Publishing: Optimize the app for release and publish it on app stores.\n" +
                    "            Maintenance: Continuously update and maintain the app based on user feedback and new requirements"
            add.visibility=View.VISIBLE


        }
        add.setOnClickListener {
                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
                finish()
        }



    }

}