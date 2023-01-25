package com.mate.carpool.ui.screen.reserveDriver.adapterview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.mate.carpool.databinding.ItemviewPassengerInfoBinding
import com.mate.carpool.ui.widget.listener.OnItemClickListener
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class ReserveDriverViewAdapter @Inject constructor(@ActivityContext private val context: Context):RecyclerView.Adapter<ReserveDriverViewAdapter.ViewHolder>(), OnItemClickListener {
    private val items = ArrayList<HashMap<String,String>>()
    private var mListener : OnItemClickListener?= null

    inner class ViewHolder(private val binding:ItemviewPassengerInfoBinding) :RecyclerView.ViewHolder(binding.root) {
        init {
            binding.iconTripleCircle.setOnClickListener {
                setOnItemClickListener(it,adapterPosition)
            }
        }
        fun bind(item:HashMap<String,String>){
            binding.imgProfile.setImageURI(item["profile"]?.toUri())
            binding.textDriverName.text=item["name"]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemviewPassengerInfoBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(ticketDetailList: ArrayList<HashMap<String,String>>){
        this.items.clear()
        this.items.addAll(ticketDetailList)
    }

    fun getPassengerIdOnSelectedItem(pos:Int) : String{
        return this.items[pos]["passengerId"]!!
    }

    fun getStudentIdOnSelectedItem(pos: Int): Long = items[pos]["id"]!!.toLong()

    fun setItemClickListener(listener: OnItemClickListener){
        this.mListener=listener
    }

    override fun setOnItemClickListener(view: View, pos: Int) {
        mListener?.setOnItemClickListener(view,pos)
    }
}