package com.example.expansemanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var email : EditText
    lateinit var password : EditText
    lateinit var person : FirebaseUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firebaseAuth = FirebaseAuth.getInstance()
         email = findViewById(R.id.editTextTextEmailAddress)
         password = findViewById(R.id.editTextTextPassword)
        firebaseAuth.addAuthStateListener(FirebaseAuth.AuthStateListener {
            if(firebaseAuth.currentUser!=null){
                    startActivity(Intent(this,DashboardActivity::class.java))
                    finish()
            }
        })
    }

     fun Signup(view: View) {
        val intent = Intent(this,RegistrationActivity::class.java)
        startActivity(intent)
         finish()
    }
    fun Login(view: View) {
        var et_email = email.text.toString();
        var et_password = password.text.toString()
        if(et_email.isBlank()) {
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show()
        }
        firebaseAuth.signInWithEmailAndPassword(et_email,et_password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    val intent = Intent(this,DashboardActivity:: class.java)
                    startActivity(intent)
                    finish()
                }
                else{
                    Toast.makeText(this, "Email or password is incorrect ", Toast.LENGTH_SHORT).show()
                }
            }
    }
}