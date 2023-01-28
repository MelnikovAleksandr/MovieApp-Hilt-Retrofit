package ru.asmelnikov.android.movieapphiltretrofit.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.asmelnikov.android.movieapphiltretrofit.R
import ru.asmelnikov.android.movieapphiltretrofit.adapter.MoviesAdapter
import ru.asmelnikov.android.movieapphiltretrofit.databinding.FragmentMoviesBinding
import ru.asmelnikov.android.movieapphiltretrofit.repository.ApiRepository
import ru.asmelnikov.android.movieapphiltretrofit.response.MoviesListResponse
import javax.inject.Inject

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private lateinit var binding: FragmentMoviesBinding

    @Inject
    lateinit var apiRepository: ApiRepository

    @Inject
    lateinit var moviesAdapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            progressBarMovies.visibility = View.VISIBLE
            apiRepository.getPopularMoviesList(1).enqueue(
                object : Callback<MoviesListResponse> {
                    override fun onResponse(
                        call: Call<MoviesListResponse>,
                        response: Response<MoviesListResponse>
                    ) {
                        progressBarMovies.visibility = View.GONE
                        when (response.code()) {
                            200 -> {
                                response.body().let { itBody ->
                                    if (itBody?.results!!.isNotEmpty()) {
                                        moviesAdapter.differ.submitList(itBody.results)
                                    }
                                    recyclerMovies.apply {
                                        layoutManager = LinearLayoutManager(requireContext())
                                        adapter = moviesAdapter
                                    }
                                    moviesAdapter.setOnItemClickListener {
                                        val direction =
                                            MoviesFragmentDirections.actionMoviesFragmentToMovieDetailsFragment(
                                                it.id
                                            )
                                        findNavController().navigate(direction)
                                    }
                                }
                            }
                            401 -> {
                                Toast.makeText(
                                    requireContext(),
                                    R.string.invalid_api_key,
                                    Toast.LENGTH_LONG
                                )
                                    .show()
                            }
                            404 -> {
                                Toast.makeText(
                                    requireContext(),
                                    R.string.invalid_request,
                                    Toast.LENGTH_LONG
                                )
                                    .show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<MoviesListResponse>, t: Throwable) {
                        progressBarMovies.visibility = View.GONE
                        Toast.makeText(
                            requireContext(),
                            R.string.onFailure,
                            Toast.LENGTH_LONG
                        )
                            .show()
                    }
                }
            )
        }
    }
}