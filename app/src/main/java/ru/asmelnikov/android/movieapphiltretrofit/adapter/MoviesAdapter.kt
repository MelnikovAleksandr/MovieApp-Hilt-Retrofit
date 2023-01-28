package ru.asmelnikov.android.movieapphiltretrofit.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.asmelnikov.android.movieapphiltretrofit.databinding.MovieItemBinding
import ru.asmelnikov.android.movieapphiltretrofit.response.Result
import ru.asmelnikov.android.movieapphiltretrofit.utils.Constants.POSTER_BASE_URL
import ru.asmelnikov.android.movieapphiltretrofit.utils.loadImage
import javax.inject.Inject

class MoviesAdapter @Inject constructor() :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    private lateinit var binding: MovieItemBinding
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = MovieItemBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.set(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {
        fun set(item: Result) {
            binding.apply {
                movieName.text = item.original_title
                lang.text = item.original_language
                rate.text = item.vote_average.toString()
                movieDateRelease.text = item.release_date
                val moviePosterURL = POSTER_BASE_URL + item.poster_path
                imageMovie.loadImage(moviePosterURL)
            }
        }
    }

    private val differCallBack = object :
        DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

}