package com.example.expansemanager

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ADDTransactionActivity : AppCompatActivity() {
    private lateinit var db : FirebaseFirestore
    lateinit var Amount : EditText
    lateinit var Note : EditText
    lateinit var et_expense: CheckBox
    lateinit var et_income : CheckBox
    lateinit var type : String
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseUser: FirebaseUser
    lateinit var id : String
    lateinit var curr_date : String
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addtransaction)
        Amount = findViewById(R.id.user_Amount_add)
        Note = findViewById(R.id.user_Note_add)
         et_expense = findViewById(R.id.expense_check_box)
         et_income  = findViewById(R.id.income_check_box)
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH::mm")
        curr_date = LocalDateTime.now().format(formatter)
        db =FirebaseFirestore.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseUser = firebaseAuth.currentUser!!
    }
    fun expense(view: View) {
        type = "Expense";
        et_income.isChecked = false
    }
    fun income(view: View) {
        type = "Income"
        et_expense.isChecked = false
    }
    fun ADD(view: View) {
        var et_Amount = Amount.text.toString()
        var et_Note = Note.text.toString()
        if(et_Amount.isEmpty()){
            Toast.makeText(this, "Please Enter Amount", Toast.LENGTH_SHORT).show()
            return;
        }
        if(type.isEmpty()){
            Toast.makeText(this, "Select Transaction Type", Toast.LENGTH_SHORT).show()
            return;
        }
        val user : MutableMap<String,String> = HashMap()
        id = UUID.randomUUID().toString()
        user["id"] = id
        user["Amount"] = et_Amount
        user["Note"] = et_Note
        user["Type"] = type
        user["Date"] = curr_date
        db.collection("users").document(firebaseAuth.uid!!).collection("Notes").document(id)
            .set(user)
            .addOnSuccessListener {
                Toast.makeText(this, "Record Added Successfully", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, DashboardActivity::class.java))
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
    }
}