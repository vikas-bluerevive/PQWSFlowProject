package com.example.pqwsflowproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.TextUtils
import android.util.Patterns
import androidx.fragment.app.FragmentActivity
import com.example.pqwsflowproject.databinding.ActivityLoginBinding
import com.example.pqwsflowproject.databinding.ActivityRegistorBinding

class RegistorActivity : FragmentActivity() {

    private lateinit var binding: ActivityRegistorBinding

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
        binding = ActivityRegistorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_registor)

        binding.materialButton3.setOnClickListener {

            if(isValidEmail(binding.editEmail.text.toString())){


            }else if(isValidPassword(binding.editPassword.text.toString())){


            }else if(isValidPhone(binding.editPhone.text.toString())){


            }else{



            }
        }

    }


    private fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        if (password.length < 11) return false
        if (password.filter { it.isDigit() }.firstOrNull() == null) return false
        if (password.filter { it.isLetter() }.filter { it.isUpperCase() }.firstOrNull() == null) return false
        if (password.filter { it.isLetter() }.filter { it.isLowerCase() }.firstOrNull() == null) return false
        //if (password.filter { !it.isLetterOrDigit() }.firstOrNull() == null) return false

        return true
    }

    private fun isValidPhone(phone: String): Boolean {
        if (phone.length < 10) return false
        return true
    }
}