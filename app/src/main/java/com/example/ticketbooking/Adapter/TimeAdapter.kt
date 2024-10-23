package com.example.ticketbooking.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.ticketbooking.R
import com.example.ticketbooking.databinding.ItemTimeBinding

class TimeAdapter(private val timeSlots: List<String>) :RecyclerView.Adapter<TimeAdapter.TimeViewholder>() {
    private var selectedPosition=-1
    private var lastSelectedPosition=-1
    inner class TimeViewholder(private val binding: ItemTimeBinding) :RecyclerView.ViewHolder(binding.root) {
        fun bind(time:String){
            binding.textViewTime.text=time
            if(selectedPosition==position){
                binding.textViewTime.setBackgroundResource(R.drawable.white_bg)
                binding.textViewTime.setTextColor(ContextCompat.getColor(itemView.context,R.color.black))
            }else{
                binding.textViewTime.setBackgroundResource(R.drawable.light_black_bg)
                binding.textViewTime.setTextColor(ContextCompat.getColor(itemView.context,R.color.white))
            }
            binding.root.setOnClickListener {
                val position=position
                if(position!=RecyclerView.NO_POSITION){
                    lastSelectedPosition=position
                    notifyItemChanged(lastSelectedPosition)
                    notifyItemChanged(selectedPosition)
                }
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimeAdapter.TimeViewholder {
        return TimeViewholder(ItemTimeBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: TimeAdapter.TimeViewholder, position: Int) {
        holder.bind(timeSlots[position])
    }

    override fun getItemCount(): Int {
        return timeSlots.size
    }
}