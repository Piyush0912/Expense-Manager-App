package com.example.expansemanager

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*

class DashboardActivity : AppCompatActivity() {
    var sumExpense: Int =0;
    var sumIncome: Int = 0;
    private lateinit var totalincome : TextView
    private lateinit var totalexpense : TextView
    private lateinit var totalbalance : TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var userArrayList: ArrayList<userrecord>
    private lateinit var myAdapter: MyAdapter
    private lateinit var db : FirebaseFirestore
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseUser : FirebaseUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        totalincome = findViewById(R.id.total_income)
        totalbalance = findViewById(R.id.total_balance)
        totalexpense = findViewById(R.id.total_expense)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseUser = firebaseAuth.currentUser!!
        recyclerView = findViewById(R.id.history)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        userArrayList = arrayListOf()
        myAdapter = MyAdapter(userArrayList)
        recyclerView.adapter = myAdapter
        val alertDialogBuilder = AlertDialog.Builder(this)
        EventChangeListener()
        firebaseAuth.addAuthStateListener(FirebaseAuth.AuthStateListener {
            if(firebaseAuth.currentUser==null){
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
        })
    }

//    override fun onRestart() {
//        super.onRestart()
//        startActivity(Intent(this, DashboardActivity::class.java))
//    }

    private fun EventChangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("users").document(firebaseUser.uid).collection("Notes").orderBy("Date",Query.Direction.ASCENDING)
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if(error!=null){
                    Log.e("Firestore Error",error.message.toString())
                    return
                }
                for(dc: DocumentChange in value?.documentChanges!!){
                    if(dc.type == DocumentChange.Type.ADDED){
                        userArrayList.add(dc.document.toObject(userrecord::class.java))
                    }
                }
                for(i in userArrayList.indices){
                    val amount = Integer.parseInt(userArrayList.get(i).Amount.toString())
                    if( userArrayList.get(i).Type.equals("Expense")){
                        sumExpense = sumExpense + amount
                    }
                    else{
                        sumIncome = sumIncome + amount
                    }
                }
                totalincome.setText(sumIncome.toString())
                totalexpense.setText(sumExpense.toString())
                totalbalance.setText((sumIncome-sumExpense).toString())
                myAdapter.notifyDataSetChanged()
            }
        })

    }

    fun AddTransaction(view: View) {
        val intent = Intent(this,ADDTransactionActivity :: class.java)
        startActivity(intent)
    }

    fun Signout(view: View) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Log Out")
        builder.setMessage("Are you sure you want to Log Out")
        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            firebaseAuth.signOut()
        }
        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            dialog.cancel()
        }
        builder.show()
    }
}