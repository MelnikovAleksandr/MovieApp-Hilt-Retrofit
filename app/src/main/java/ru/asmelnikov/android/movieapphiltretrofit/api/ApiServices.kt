package ru.asmelnikov.android.movieapphiltretrofit.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.asmelnikov.android.movieapphiltretrofit.response.MovieDetailsResponse
import ru.asmelnikov.android.movieapphiltretrofit.response.MoviesListResponse

interface ApiServices {

    @GET("movie/popular")
    fun getPopularMoviesList(@Query("page") page: Int): Call<MoviesListResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movieId: Int): Call<MovieDetailsResponse>
}