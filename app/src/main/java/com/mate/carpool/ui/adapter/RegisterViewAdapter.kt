package com.mate.carpool.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.mate.carpool.data.model.RegisterItem
import com.mate.carpool.data.utils.ButtonCheckUtils.checkRegisterInfoIsCorrect
import com.mate.carpool.databinding.FragmentRegisterInfoBinding
import com.mate.carpool.databinding.ItemviewRegisterInfoBinding
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class RegisterViewAdapter @Inject constructor(@ActivityContext private val context:Context): RecyclerView.Adapter<RegisterViewAdapter.ViewHolder>(){
    private val items = ArrayList<RegisterItem>()
    private var button : FragmentRegisterInfoBinding ?= null
    private var isAllConditionsTrue : ArrayList<Boolean> = arrayListOf(true,true,true)

    inner class ViewHolder(private val binding: ItemviewRegisterInfoBinding) : RecyclerView.ViewHolder(binding.root) {
        private var pattern:String = ""

        init {
            binding.etRegisterInput.width=context.resources.displayMetrics.widthPixels
            binding.etRegisterInput.apply {
                doOnTextChanged { text, start, before, count ->
                    when(pattern) {
                        "이름"->{
                            isAllConditionsTrue[0] = checkRegisterInfoIsCorrect(text.toString(), "[^ㄱ-힣]",4,1)
                        }
                        "학번"->{
                            isAllConditionsTrue[1] = checkRegisterInfoIsCorrect(text.toString(), "[^0-9a-zA-Z]",15,1)
                        }
                        "학과"->{
                            isAllConditionsTrue[2] = checkRegisterInfoIsCorrect(text.toString(), "[^ㄱ-힣]",15,1)
                        }
                    }
                    button?.btnConfirm?.isSelected = isAllConditionsTrue[0]&&isAllConditionsTrue[1]&&isAllConditionsTrue[2]
                }
            }
        }
        fun bind(item: RegisterItem){
            binding.registerItem=item

            when(item.kind.get()){
                "이름"->{
                    binding.etRegisterInput.hint="예:메이트"
                    pattern = "이름"
                }
                "학번"->{
                    binding.etRegisterInput.hint="예:123a123"
                    pattern = "학번"
                }
                "학과"->{
                    binding.etRegisterInput.hint="예:미학과"
                    pattern = "학과"
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
            if(item.input.get().isNullOrBlank())
                list.add("")
            else
                list.add(item.input.get()!!)
        }
        return list
    }

    fun setItems(items:ArrayList<RegisterItem>){
        this.items.clear()
        this.items.addAll(items)
    }

    fun setButton(button: FragmentRegisterInfoBinding){
        this.button=button
    }


}