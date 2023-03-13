package com.alvarogomez.mymovies.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import com.alvarogomez.mymovies.databinding.ActivityDetailBinding
import com.alvarogomez.mymovies.model.Movie
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_MOVIE = "DetailActivity:movie"
    }

    private val binding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val movie = intent.getParcelableExtra<Movie>(EXTRA_MOVIE)
        movie?.let {
            title =  it.title
            Glide
                .with(this)
                .load("https://image.tmdb.org/t/p/w780/${movie.backdrop_path}")
                .into(binding.backdrop)
            binding.summary.text = movie.overview
            bindDetailInfo(binding.detailInfo, it)
        }
    }

    private fun bindDetailInfo(detailInfo: AppCompatTextView, movie: Movie) {
        detailInfo.text = buildSpannedString {
            appendInfo("Original language: ", movie.original_language)
            appendInfo("Original title: ", movie.original_title)
            appendInfo("Release date: ", movie.release_date)
            appendInfo("Popularity: ", movie.popularity.toString())
            appendInfo("Vote Average: ", movie.vote_average.toString())
        }
    }

    private fun SpannableStringBuilder.appendInfo(title: String, value: String) {
        bold {
            append("$title: ")
        }
        appendLine(value)

    }
}