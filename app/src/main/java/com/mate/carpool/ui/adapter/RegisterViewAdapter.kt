package com.mate.carpool.ui.adapter

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.mate.carpool.data.model.UserModel
import com.mate.carpool.data.model.WeekModel
import com.mate.carpool.databinding.ItemviewRegisterInfoBinding
import com.mate.carpool.databinding.ItemviewWeekBinding
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class RegisterViewAdapter @Inject constructor(@ApplicationContext private val context: Context): RecyclerView.Adapter<RegisterViewAdapter.ViewHolder>(){
    private val items = ArrayList<HashMap<String,String>>()

    inner class ViewHolder(private val binding: ItemviewRegisterInfoBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.etRegisterInput.width=context.applicationContext.resources.displayMetrics.widthPixels
        }
        fun bind(item: HashMap<String,String>){
            binding.textRegisterKind.text=item["kind"]
            binding.etRegisterInput.addTextChangedListener{
                item["input"]=it.toString()
            }
            when(items.indexOf(item)){
                0->{
                    binding.etRegisterInput.hint="예:메이트"
                }
                1->{
                    binding.etRegisterInput.hint="예:123a123"
                }
                2->{
                    binding.etRegisterInput.hint="예:미학과"
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemviewRegisterInfoBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getItems():ArrayList<String>{
        val list = arrayListOf<String>()
        for(item in this.items){
            if(item["input"].isNullOrBlank())
                list.add("")
            else
                list.add(item["input"]!!)
        }
        return list
    }

    fun addItem(item:HashMap<String,String>){
        this.items.reverse()
        this.items.add(item)
        this.items.reverse()
        Log.d("test",item.values.toString())
    }

}