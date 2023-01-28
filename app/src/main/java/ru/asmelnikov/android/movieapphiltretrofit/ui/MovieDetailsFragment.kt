package ru.asmelnikov.android.movieapphiltretrofit.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.asmelnikov.android.movieapphiltretrofit.R
import ru.asmelnikov.android.movieapphiltretrofit.databinding.FragmentMovieDetailsBinding
import ru.asmelnikov.android.movieapphiltretrofit.repository.ApiRepository
import ru.asmelnikov.android.movieapphiltretrofit.response.MovieDetailsResponse
import ru.asmelnikov.android.movieapphiltretrofit.utils.Constants.POSTER_BASE_URL
import ru.asmelnikov.android.movieapphiltretrofit.utils.loadImage
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding

    @Inject
    lateinit var apiRepository: ApiRepository

    private val args: MovieDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = args.movieId
        binding.apply {
            prgBarMovies.visibility = View.INVISIBLE
            apiRepository.getMovieDetails(id).enqueue(
                object : Callback<MovieDetailsResponse> {
                    override fun onResponse(
                        call: Call<MovieDetailsResponse>,
                        response: Response<MovieDetailsResponse>
                    ) {
                        prgBarMovies.visibility = View.GONE
                        when (response.code()) {
                            200 -> {
                                response.body().let {
                                    val moviePoster = POSTER_BASE_URL + it!!.poster_path
                                    tvMovieBudget.text = it.budget.toString()
                                    tvMovieOverview.text = it.overview
                                    tvMovieDateRelease.text = it.release_date
                                    tvMovieRating.text = it.vote_average.toString()
                                    tvMovieRevenue.text = it.revenue.toString()
                                    tvMovieRuntime.text = it.runtime.toString()
                                    tvMovieTagLine.text = it.tagline
                                    tvMovieTitle.text = it.title

                                    imgMovie.loadImage(moviePoster)
                                    imgMovieBack.loadImage(moviePoster)

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

                    override fun onFailure(call: Call<MovieDetailsResponse>, t: Throwable) {
                        prgBarMovies.visibility = View.GONE
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