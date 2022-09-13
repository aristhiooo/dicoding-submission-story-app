package id.co.iconpln.dicodingintermediate_storyapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.co.iconpln.dicodingintermediate_storyapp.databinding.ItemStoryListBinding
import id.co.iconpln.dicodingintermediate_storyapp.repository.remote.responsemodel.ListStoryItem
import id.co.iconpln.dicodingintermediate_storyapp.ui.adapter.StoryListAdapter.ViewHolder

class StoryListAdapter(private val onItemClicked: (ListStoryItem) -> Unit) : ListAdapter<ListStoryItem, ViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemStoryListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        Glide.with(holder.binding.root).load(item.photoUrl).into(holder.binding.ivItemPhoto)
        holder.binding.tvItemName.text = item.name
        holder.binding.root.setOnClickListener {
            onItemClicked(item)
        }
    }

    class ViewHolder(val binding: ItemStoryListBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<ListStoryItem> =
            object : DiffUtil.ItemCallback<ListStoryItem>() {
                override fun areItemsTheSame(
                    oldItem: ListStoryItem,
                    newItem: ListStoryItem
                ): Boolean {
                    return oldItem.id == newItem.id && oldItem.name == newItem.name
                }

                override fun areContentsTheSame(
                    oldItem: ListStoryItem,
                    newItem: ListStoryItem
                ): Boolean {
                    return oldItem == newItem
                }

            }
    }
}