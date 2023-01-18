package com.mate.carpool.ui.screen.register

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mate.carpool.data.model.domain.item.WeekItem
import com.mate.carpool.databinding.ItemviewWeekBinding
import javax.inject.Inject

class WeekViewAdapter @Inject constructor()  : RecyclerView.Adapter<WeekViewAdapter.ViewHolder>(){
    private val items = ArrayList<WeekItem>()

    inner class ViewHolder(private val binding: ItemviewWeekBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WeekItem){
            binding.btnDay.text=item.weekName
            binding.btnDay.setOnClickListener {
                if(!item.weekFlag){
                    binding.btnDay.isSelected=true
                    binding.btnDay.setTextColor(Color.WHITE)
                    item.weekFlag=true
                }else{
                    binding.btnDay.isSelected=false
                    binding.btnDay.setTextColor(Color.BLUE)
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

    fun setItems(items:List<WeekItem>){
        this.items.clear()
        this.items.addAll(items)
    }

    fun getItems():ArrayList<WeekItem>{
        return this.items
    }


}