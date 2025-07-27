package com.example.pqwsflowproject

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.pqwsflowproject.databinding.ActivityLoginBinding
import com.example.pqwsflowproject.databinding.ActivityMainBinding
import com.example.pqwsflowproject.ui.theme.PQWSFlowProjectTheme

class LoginActivty: FragmentActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.materialButton3.setOnClickListener{
            val intent =     Intent(binding.materialButton3.context, MainActivity::class.java)

            ContextCompat.startActivity(binding.materialButton3.context, intent, null)

        }
   /* setContent {
        PQWSFlowProjectTheme {
            // A surface container using the 'background' color from the theme
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Greeting("Android")
            }
        }
    }*/
}

}