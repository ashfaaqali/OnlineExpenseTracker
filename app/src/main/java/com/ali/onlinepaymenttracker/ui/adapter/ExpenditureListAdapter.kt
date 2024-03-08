package com.ali.onlinepaymenttracker.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.ali.onlinepaymenttracker.data.model.Expenditure
import com.ali.onlinepaymenttracker.databinding.ExpenditureCardBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
        val dateFormatter = SimpleDateFormat("MMMM dd, yyyy", Locale.US)


        with(holder.binding){
            amountTv.text = "₹" + currentItem.amount.toString()
            noteTv.text = currentItem.note
            dateTv.text = dateFormatter.format(currentItem.dateInMills)
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