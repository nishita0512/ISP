package com.example.isp.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.*
import com.example.isp.databinding.DateChipMessageSingleRowBinding
import com.example.isp.databinding.SupportMessageSingleRowBinding
import com.example.isp.model.SupportMessage
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class SupportMessagesAdapter: ListAdapter<SupportMessage,RecyclerView.ViewHolder>(Diff)
{

    class AdminMessageViewHolder(private val binding: SupportMessageSingleRowBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(message: SupportMessage){
            binding.apply {
                val params = relativeLayoutMessageSingleRow.layoutParams as ConstraintLayout.LayoutParams
                params.apply {
                    horizontalBias = 0f
                    marginStart = 4
                    marginEnd = 32
                }
                relativeLayoutMessageSingleRow.apply{
                    layoutParams = params
                }

                val timeInString = SimpleDateFormat("hh:mm aa", Locale.getDefault()).format(Date(message.dateAndTime))
                txtTimeMessageSingleRow.text = timeInString
                txtMessageSingleRow.text = message.message
            }

        }
    }

    class UserMessageViewHolder(private val binding: SupportMessageSingleRowBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(message: SupportMessage){
            binding.apply {
                var params = relativeLayoutMessageSingleRow.layoutParams as ConstraintLayout.LayoutParams
                params.apply {
                    width = wrapBehaviorInParent
                    horizontalBias = 1f
                    marginStart = 32
                    marginEnd = 4
                }
                relativeLayoutMessageSingleRow.apply{
                    layoutParams = params
                }
                params = txtMessageSingleRow.layoutParams as ConstraintLayout.LayoutParams
                params.apply {
                    horizontalBias = 0.5f
                    marginStart = 16
                    marginEnd = 8
                }
                txtMessageSingleRow.apply{
                    layoutParams = params
                }
                params = txtTimeMessageSingleRow.layoutParams as ConstraintLayout.LayoutParams
                params.apply {
                    horizontalBias = 1f
                    marginStart = 16
                    marginEnd = 8
                }
                txtTimeMessageSingleRow.apply{
                    textAlignment = View.TEXT_ALIGNMENT_VIEW_END
                    layoutParams = params
                }
                innerConstraintLayoutMessageSingleRow.setBackgroundColor(Color.parseColor("#F1F1F1"))
                val timeInString = SimpleDateFormat("hh:mm aa", Locale.getDefault()).format(Date(message.dateAndTime))
                txtTimeMessageSingleRow.text = timeInString
                txtMessageSingleRow.text = message.message
            }
        }
    }

    class DateChipViewHolder(private val binding: DateChipMessageSingleRowBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(message: SupportMessage){
            binding.apply {

                val date = (SimpleDateFormat("dd", Locale.getDefault()).format(Date(message.dateAndTime))).toInt()
                val month = (SimpleDateFormat("MM", Locale.getDefault()).format(Date(message.dateAndTime))).toInt()
                val year = (SimpleDateFormat("yyyy", Locale.getDefault()).format(Date(message.dateAndTime))).toInt()
                val currentDate = SimpleDateFormat("dd",Locale.getDefault()).format(Calendar.getInstance().time.time).toInt()
                val currentMonth = SimpleDateFormat("MM",Locale.getDefault()).format(Calendar.getInstance().time.time).toInt()
                val currentYear = SimpleDateFormat("yyyy",Locale.getDefault()).format(Calendar.getInstance().time.time).toInt()

                var yesterdaysDate: Int = currentDate-1
                var yesterdaysMonth: Int = currentMonth
                var yesterdaysYear: Int = currentYear

                if(currentDate==1){
                    if(currentMonth==1||currentMonth==2||currentMonth==4||currentMonth==6||currentMonth==8||currentMonth==9||currentMonth==11){
                        yesterdaysDate = 31
                        when(currentMonth){
                            1->{
                                yesterdaysMonth = 12
                                yesterdaysYear = currentYear-1
                            }
                            else->{
                                currentMonth-1
                                yesterdaysYear = currentYear
                            }
                        }
                    }
                    else if(currentMonth==5||currentMonth==7||currentMonth==10||currentMonth==12){
                        yesterdaysDate = 30
                        yesterdaysMonth = currentMonth-1
                        yesterdaysYear = currentYear
                    }
                    else{
                        if((currentYear%400==0 || currentYear%100!=0) &&(currentYear%4==0)){
                            yesterdaysDate = 29
                            yesterdaysMonth = 2
                            yesterdaysYear = currentYear
                        }
                        else{
                            yesterdaysDate = 28
                            yesterdaysMonth = 2
                            yesterdaysYear = currentYear
                        }
                    }
                }

                val displayDate = when {
                    (currentYear==year) and (currentMonth==month) and (currentDate==date) -> {
                        "Today"
                    }
                    (year==yesterdaysYear) and (month==yesterdaysMonth) and (date==yesterdaysDate) -> {
                        "Yesterday"
                    }
                    else -> {
                        "$date/$month/$year"
                    }
                }

                chipDateSupportMessage.text = displayDate

            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val message = getItem(position)
        if(message.messageFrom=="admin"){
            return 0
        }
        else if (message.messageFrom=="user"){
            return 1
        }
        return 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType){
            0->{
                return AdminMessageViewHolder(
                    SupportMessageSingleRowBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            1->{
                return UserMessageViewHolder(
                    SupportMessageSingleRowBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else->{
                return DateChipViewHolder(
                    DateChipMessageSingleRowBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = getItem(position)
        when(getItemViewType(position)){
            0->{
                val viewHolder = holder as AdminMessageViewHolder
                viewHolder.bind(message)
            }
            1->{
                val viewHolder = holder as UserMessageViewHolder
                viewHolder.bind(message)
            }
            else->{
                val viewHolder = holder as DateChipViewHolder
                viewHolder.bind(message)
            }
        }

    }

    private object Diff : DiffUtil.ItemCallback<SupportMessage>() {
        override fun areItemsTheSame(oldItem: SupportMessage, newItem: SupportMessage): Boolean {
            return false
        }

        override fun areContentsTheSame(oldItem: SupportMessage, newItem: SupportMessage): Boolean {
            return oldItem == newItem
        }

    }

}