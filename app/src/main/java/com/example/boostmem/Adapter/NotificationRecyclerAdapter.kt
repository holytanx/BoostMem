package com.example.boostmem.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.boostmem.R
import com.example.boostmem.models.notification
import kotlinx.android.synthetic.main.notification_view.view.*

class NotificationRecyclerAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private var notificationList: ArrayList<notification> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NotificationViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.notification_view,parent,false)
        )    }

    override fun getItemCount(): Int {
        return notificationList.size
    }
    fun submitList(set: List<notification>){
        notificationList = set as ArrayList<notification>
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is NotificationViewHolder -> {holder.bind(notificationList.get(position))}
        }    }
    class NotificationViewHolder constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView){

        val title = itemView.notiTitle
        val isActive = itemView.isActive


        fun bind (item : notification){
            title.setText(item.title)
            isActive.isChecked = item.isActive

        }
    }
}