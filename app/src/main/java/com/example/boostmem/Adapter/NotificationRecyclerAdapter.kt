package com.example.boostmem.Adapter

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.RecyclerView
import com.example.boostmem.Database.Models.NotificationModel
import com.example.boostmem.DeckViewModel
import com.example.boostmem.R
import kotlinx.android.synthetic.main.notification_view.view.*


class NotificationRecyclerAdapter internal constructor(context: Context,var clickListener: OnNotiItemClickListener, var oncheck: OnCheckSwitchChangeListener): RecyclerView.Adapter<NotificationRecyclerAdapter.NotificationViewHolder>(){
    var context = context as (AppCompatActivity)
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var notiSet = mutableListOf<NotificationModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val itemView = inflater.inflate(R.layout.notification_view,parent,false)
        return NotificationViewHolder(itemView,context)
    }


    override fun getItemCount() = notiSet.size
    fun getNotiAtPosition(position: Int): NotificationModel {
        return notiSet[position]
    }
    internal fun setNoti(notiSet: MutableList<NotificationModel>) {
        this.notiSet = notiSet
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val current = notiSet[position]
        lateinit var intent:Intent
        val fragmentManager: FragmentManager =
            context.supportFragmentManager // this is basically context of the class

        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        val popupMenu = PopupMenu(context,holder.itemView)
        popupMenu.inflate(R.menu.menu_item)
        popupMenu.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.delete -> {
                    holder.itemView.setOnClickListener{
                        intent = Intent("delete")
                        intent.putExtra("deleteNoti", current)
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)

                    }
                    true}
                R.id.edit -> {
                    holder.itemView.setOnClickListener{
                        intent = Intent("edit")
                        intent.putExtra("editNoti", current)
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)

                    }

                    true}
                else ->  false

            }
        }
        try{
            val fieldMPopup = PopupMenu::class.java.getDeclaredField("mPopup")
            fieldMPopup.isAccessible = true
            val mPopup = fieldMPopup.get(popupMenu)
            mPopup.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(mPopup,true)
        } catch (
            e: Exception
        ){
            Log.d("Main",e.toString())
        }
        holder.itemView.setOnClickListener {
            popupMenu.show()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            holder.initialize(current,current.isActive,this.clickListener,this.oncheck)
        }

    }



    class NotificationViewHolder constructor(
        itemView: View, var contxt: Context
    ): RecyclerView.ViewHolder(itemView){
        var title = itemView.noti_textview
        var time = itemView.time_textview
        var isActive = itemView.isActive

        @RequiresApi(Build.VERSION_CODES.M)
        fun initialize(item :NotificationModel, check: Boolean, action:OnNotiItemClickListener, action1:OnCheckSwitchChangeListener){
            title.text = item.deckName
            time.text = item.time
            isActive.isChecked = check
            val listener2: CompoundButton.OnCheckedChangeListener =  CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                action1.onCheckedChanged(isActive,isChecked)
                val pos = item.notificationID

            }
            isActive.setOnCheckedChangeListener (listener2)

            val listener1 : PopupMenu.OnMenuItemClickListener = PopupMenu.OnMenuItemClickListener {
                when (it.itemId) {
                    R.id.delete -> {
                        true
                    }
                    R.id.edit -> {
                        true
                    }
                    else -> false

                }
            }
//            itemView.setOnClickListener {
//                val id = item.notificationID
//                var act = ""
//                val popupMenu = PopupMenu(contxt,it)
//                popupMenu.setOnMenuItemClickListener {
//                    when(it.itemId){
//                    R.id.delete -> {
//                        true}
//                    R.id.edit -> {
//                            true}
//                    else ->  false
//
//                }
//                }
//                popupMenu.inflate(R.menu.menu_item)
//                try{
//                    val fieldMPopup = PopupMenu::class.java.getDeclaredField("mPopup")
//                    fieldMPopup.isAccessible = true
//                    val mPopup = fieldMPopup.get(popupMenu)
//                    mPopup.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
//                        .invoke(mPopup,true)
//                } catch (
//                    e: Exception
//                ){
//                    Log.d("Main",e.toString())
//                }finally {
//                    popupMenu.show()
//
//                }
//
//            }

        }

        private fun deleteNoti(id: Long) {
            val deckViewModel = DeckViewModel(contxt as Application)
            Toast.makeText(contxt,"Hello",Toast.LENGTH_LONG).show()
        }
        private fun editNoti(id:Long){
            val deckViewModel = DeckViewModel(contxt as Application)
            Toast.makeText(contxt,"Hello",Toast.LENGTH_LONG).show()

        }

    }

}

interface OnCheckSwitchChangeListener : CompoundButton.OnCheckedChangeListener {
    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {

    }
    fun position(notiID : Long)


}

interface OnNotiItemClickListener :  View.OnCreateContextMenuListener {
    fun onItemClick(item:NotificationModel, position: Int, action : String)
    fun position(notiID : Long, act : String)


}
