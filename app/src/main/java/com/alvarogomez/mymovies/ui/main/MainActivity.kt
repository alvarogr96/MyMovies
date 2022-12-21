package com.alvarogomez.mymovies.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.alvarogomez.mymovies.model.MovieDbClient
import com.alvarogomez.mymovies.R
import com.alvarogomez.mymovies.databinding.ActivityMainBinding
import com.alvarogomez.mymovies.model.Movie
import com.alvarogomez.mymovies.ui.detail.DetailActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val moviewAdapter = MoviesAdapter(emptyList()) { movie ->
            navigateTo(movie)
        }
        binding.recycler.adapter = moviewAdapter

        lifecycleScope.launch {
            val apiKey = getString(R.string.api_key)
            val popularMovies = MovieDbClient.service.listPopularMovies(apiKey)
            moviewAdapter.movies = popularMovies.results
            moviewAdapter.notifyDataSetChanged()
        }
    }

    fun navigateTo(movie: Movie) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_MOVIE, movie)
        startActivity(intent)
    }
}