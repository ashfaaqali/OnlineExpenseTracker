package com.onlineexpensetracker.app.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.ali.onlinepaymenttracker.databinding.ExpenditureCardBinding
import com.onlineexpensetracker.app.data.model.Expenditure

class ExpenditureListAdapter(private val viewModelStoreOwner: ViewModelStoreOwner) :
    RecyclerView.Adapter<ExpenditureListAdapter.MyViewHolder>() {
    private var expenditureList = emptyList<Expenditure>()

    inner class MyViewHolder(val binding: ExpenditureCardBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding =
            ExpenditureCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = expenditureList[position]

        with(holder.binding){
            amount.text = currentItem.amount.toString()
            note.text = currentItem.note
            date.text = currentItem.date
        }
    }

    override fun getItemCount(): Int {
        return expenditureList.size
    }

    fun setData(expenditure: List<Expenditure>){
        this.expenditureList = expenditure
        notifyDataSetChanged()
    }
}