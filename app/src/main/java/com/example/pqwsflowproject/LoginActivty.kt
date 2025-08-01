package com.example.pqwsflowproject

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.TextUtils
import android.util.Patterns
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

        val prefs =
            PreferenceManager.getDefaultSharedPreferences(this)
        val yourLocked: Boolean = prefs.getBoolean("locked", false)
        if(yourLocked){
            setTheme(R.style.Theme_PQWSFlowProject3)
        }else{
            setTheme(R.style.Theme_PQWSFlowProject2)
        }
        
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

    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        if (password.length < 8) return false
        if (password.filter { it.isDigit() }.firstOrNull() == null) return false
        if (password.filter { it.isLetter() }.filter { it.isUpperCase() }.firstOrNull() == null) return false
        if (password.filter { it.isLetter() }.filter { it.isLowerCase() }.firstOrNull() == null) return false
        if (password.filter { !it.isLetterOrDigit() }.firstOrNull() == null) return false

        return true
    }

}