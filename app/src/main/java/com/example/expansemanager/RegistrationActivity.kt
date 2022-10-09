package com.example.expansemanager

import android.app.Person
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.util.Optional.empty

class RegistrationActivity : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var email : EditText
    lateinit var password : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        firebaseAuth = FirebaseAuth.getInstance()
        email = findViewById(R.id.editTextTextEmailAddress)
        password = findViewById(R.id.editTextTextPassword)
    }

     fun Signin(view: View) {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
         finish()
    }

    fun Register(view: View) {
        var et_email = email.text.toString();
        var et_password = password.text.toString()
        if (et_email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(et_email).matches()) {
            Toast.makeText(this, "Please Enter Valid Email !", Toast.LENGTH_SHORT).show();
            return;
        }
            if(et_password.length<8){
                Toast.makeText(this, "Please Enter Password more than 8 character", Toast.LENGTH_SHORT).show()
                return;
            }
        firebaseAuth.createUserWithEmailAndPassword(et_email,et_password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    Toast.makeText(this, "successfull", Toast.LENGTH_SHORT).show()
                    val inent = Intent(this,DashboardActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(this, "Email Already exist", Toast.LENGTH_SHORT).show()
                }
            }
    }
}