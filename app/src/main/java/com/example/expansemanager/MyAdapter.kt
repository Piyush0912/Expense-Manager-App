package com.example.expansemanager

import android.graphics.ColorSpace
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.auth.User
import java.lang.reflect.Type

class MyAdapter(private val userList: ArrayList<userrecord>): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.oneitem,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user : userrecord = userList[position]
        holder.Amount.text = user.Amount
        holder.Note.text = user.Note
        holder.Date.text = user.Date
        val priority = user.Type
        if(priority.equals("Expense")){
            holder.priority.setBackgroundResource(R.drawable.red_shape)
        }
        else{
            holder.priority.setBackgroundResource(R.drawable.green_shape)
        }
 }
    override fun getItemCount(): Int {
        return userList.size
    }
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val priority: View = itemView.findViewById(R.id.view)
        val Amount :TextView = itemView.findViewById(R.id.amount_one)
        val Note :TextView = itemView.findViewById(R.id.note_one)
        val Date : TextView = itemView.findViewById(R.id.date_one)
    }
}