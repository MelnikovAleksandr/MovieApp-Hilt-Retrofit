package ru.asmelnikov.android.movieapphiltretrofit.repository

import dagger.hilt.android.scopes.ActivityScoped
import ru.asmelnikov.android.movieapphiltretrofit.api.ApiServices
import javax.inject.Inject

@ActivityScoped
class ApiRepository @Inject constructor(
    private val apiServices: ApiServices
) {
    fun getPopularMoviesList(page: Int) =
        apiServices.getPopularMoviesList(page)

    fun getMovieDetails(id: Int) = apiServices.getMovieDetails(id)
}