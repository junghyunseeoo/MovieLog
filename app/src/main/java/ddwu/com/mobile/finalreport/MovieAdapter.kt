package ddwu.com.mobile.finalreport

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ddwu.com.mobile.finalreport.databinding.ItemLayoutBinding

class MovieAdapter(
    private val context: Context,
    private var movieList: List<Movie>
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(movie: Movie)
        fun onItemLongClick(movie: Movie): Boolean
    }

    private var listener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class MovieViewHolder(val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                listener?.onItemClick(movieList[adapterPosition])
            }
            binding.root.setOnLongClickListener {
                listener?.onItemLongClick(movieList[adapterPosition]) ?: false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        holder.binding.mvTitle.text = movie.title
        holder.binding.mvDirector.text = "감독: ${movie.director}"
        holder.binding.mvReleaseDate.text = "개봉일: ${movie.releaseDate}"
        val imageResId = context.resources.getIdentifier(movie.imageName, "drawable", context.packageName)
        holder.binding.mvImage.setImageResource(imageResId)
    }

    override fun getItemCount(): Int = movieList.size

    fun updateList(newList: List<Movie>) {
        movieList = newList
        notifyDataSetChanged()
    }
}