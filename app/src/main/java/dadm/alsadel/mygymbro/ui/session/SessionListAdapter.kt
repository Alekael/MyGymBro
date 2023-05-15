package dadm.alsadel.mygymbro.ui.session

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dadm.alsadel.mygymbro.domain.model.Session
import dadm.alsadel.mygymbro.databinding.SessionItemBinding

class SessionListAdapter() :
    ListAdapter<Session, SessionListAdapter.ViewHolder>(QuotationDiff) {

    class ViewHolder(val binding: SessionItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
        }

        fun bind(session: Session) {
            binding.tvDay.text = session.day!![0].toString()
            binding.tvSessionNumber.text = "Session " + session.number
            binding.tvHour.text = session.day + ", " + session.hour
        }
    }

    object QuotationDiff : DiffUtil.ItemCallback<Session>() {
        override fun areItemsTheSame(oldItem: Session, newItem: Session): Boolean = oldItem == newItem
        override fun areContentsTheSame(oldItem: Session, newItem: Session): Boolean = oldItem.number == newItem.number
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(SessionItemBinding.inflate(LayoutInflater.from(parent.context), parent,
            false) )
    }


}

