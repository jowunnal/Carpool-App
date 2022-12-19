package com.mate.carpool.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mate.carpool.data.model.WeekModel
import com.mate.carpool.databinding.ItemviewWeekBinding

class WeekViewAdapter  : RecyclerView.Adapter<WeekViewAdapter.ViewHolder>(){
    private val items = ArrayList<WeekModel>()

    inner class ViewHolder(private val binding: ItemviewWeekBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WeekModel){
            binding.btnDay.text=item.weekName
            binding.btnDay.setOnClickListener {
                if(!item.weekFlag){
                    binding.btnDay.isSelected=true
                    binding.btnDay.setTextColor(Color.WHITE)
                    item.weekFlag=true
                }else{
                    binding.btnDay.isSelected=false
                    binding.btnDay.setTextColor(Color.GRAY)
                    item.weekFlag=false
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemviewWeekBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(items:List<WeekModel>){
        this.items.clear()
        this.items.addAll(items)
    }

}