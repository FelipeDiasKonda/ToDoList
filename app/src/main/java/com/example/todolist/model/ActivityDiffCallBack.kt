import androidx.recyclerview.widget.DiffUtil
import com.example.todolist.model.ActivityModel

class ActivityDiffCallback : DiffUtil.ItemCallback<ActivityModel>() {
    override fun areItemsTheSame(oldItem: ActivityModel, newItem: ActivityModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ActivityModel, newItem: ActivityModel): Boolean {
        return oldItem == newItem
    }
}