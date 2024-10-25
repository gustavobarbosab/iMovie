package com.github.gustavobarbosab.imovies.data.movies.releases.remote

import com.github.gustavobarbosab.imovies.core.data.network.NetworkResponse
import com.github.gustavobarbosab.imovies.data.movies.releases.remote.response.ReleaseMovieResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.http.GET
import java.lang.reflect.Type


interface ReleaseMovieApi {

    @GET("/v1/releases")
    fun getReleases(): NetworkResponse<List<ReleaseMovieResponse>>
}

object MockReleaseMovieApi : ReleaseMovieApi {

    private const val MOCK_RELEASES = "{\n" +
            "  \"releases\": [\n" +
            "    {\n" +
            "      \"id\": 3165490,\n" +
            "      \"title\": \"Slow Horses\",\n" +
            "      \"type\": \"tv_series\",\n" +
            "      \"tmdb_id\": 95480,\n" +
            "      \"tmdb_type\": \"tv\",\n" +
            "      \"imdb_id\": \"tt5875444\",\n" +
            "      \"season_number\": 1,\n" +
            "      \"poster_url\": \"https://cdn.watchmode.com/posters/03165490_poster_w185.jpg\",\n" +
            "      \"source_release_date\": \"2022-04-01\",\n" +
            "      \"source_id\": 371,\n" +
            "      \"source_name\": \"AppleTV+\",\n" +
            "      \"is_original\": 1\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 3175997,\n" +
            "      \"title\": \"Luxe Listings Sydney\",\n" +
            "      \"type\": \"tv_series\",\n" +
            "      \"tmdb_id\": 128912,\n" +
            "      \"tmdb_type\": \"tv\",\n" +
            "      \"imdb_id\": \"tt14344354\",\n" +
            "      \"season_number\": 2,\n" +
            "      \"poster_url\": \"https://cdn.watchmode.com/posters/03175997_poster_w185.jpg\",\n" +
            "      \"source_release_date\": \"2022-04-01\",\n" +
            "      \"source_id\": 26,\n" +
            "      \"source_name\": \"Amazon Prime\",\n" +
            "      \"is_original\": 1\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 3179027,\n" +
            "      \"title\": \"The Outlaws\",\n" +
            "      \"type\": \"tv_series\",\n" +
            "      \"tmdb_id\": 136044,\n" +
            "      \"tmdb_type\": \"tv\",\n" +
            "      \"imdb_id\": \"tt11646832\",\n" +
            "      \"season_number\": 1,\n" +
            "      \"poster_url\": \"https://cdn.watchmode.com/posters/03179027_poster_w185.jpg\",\n" +
            "      \"source_release_date\": \"2022-04-01\",\n" +
            "      \"source_id\": 26,\n" +
            "      \"source_name\": \"Amazon Prime\",\n" +
            "      \"is_original\": 0\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 3171741,\n" +
            "      \"title\": \"Doug Unplugs\",\n" +
            "      \"type\": \"tv_series\",\n" +
            "      \"tmdb_id\": 112148,\n" +
            "      \"tmdb_type\": \"tv\",\n" +
            "      \"imdb_id\": \"tt11690802\",\n" +
            "      \"season_number\": 2,\n" +
            "      \"poster_url\": \"https://cdn.watchmode.com/posters/03171741_poster_w185.jpg\",\n" +
            "      \"source_release_date\": \"2022-04-01\",\n" +
            "      \"source_id\": 371,\n" +
            "      \"source_name\": \"AppleTV+\",\n" +
            "      \"is_original\": 1\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 1623745,\n" +
            "      \"title\": \"The Bubble\",\n" +
            "      \"type\": \"movie\",\n" +
            "      \"tmdb_id\": 765119,\n" +
            "      \"tmdb_type\": \"movie\",\n" +
            "      \"imdb_id\": \"tt13610562\",\n" +
            "      \"season_number\": null,\n" +
            "      \"poster_url\": \"https://cdn.watchmode.com/posters/01623745_poster_w185.jpg\",\n" +
            "      \"source_release_date\": \"2022-04-01\",\n" +
            "      \"source_id\": 203,\n" +
            "      \"source_name\": \"Netflix\",\n" +
            "      \"is_original\": 0\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 3175047,\n" +
            "      \"title\": \"The Boarding School: Las Cumbres\",\n" +
            "      \"type\": \"tv_series\",\n" +
            "      \"tmdb_id\": 97513,\n" +
            "      \"tmdb_type\": \"tv\",\n" +
            "      \"imdb_id\": \"tt11709206\",\n" +
            "      \"season_number\": 2,\n" +
            "      \"poster_url\": \"https://cdn.watchmode.com/posters/03175047_poster_w185.jpg\",\n" +
            "      \"source_release_date\": \"2022-04-01\",\n" +
            "      \"source_id\": 26,\n" +
            "      \"source_name\": \"Amazon Prime\",\n" +
            "      \"is_original\": 1\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 1645336,\n" +
            "      \"title\": \"Captain Nova\",\n" +
            "      \"type\": \"movie\",\n" +
            "      \"tmdb_id\": 881957,\n" +
            "      \"tmdb_type\": \"movie\",\n" +
            "      \"imdb_id\": \"tt14915608\",\n" +
            "      \"season_number\": null,\n" +
            "      \"poster_url\": \"https://cdn.watchmode.com/posters/01645336_poster_w185.jpg\",\n" +
            "      \"source_release_date\": \"2022-04-01\",\n" +
            "      \"source_id\": 203,\n" +
            "      \"source_name\": \"Netflix\",\n" +
            "      \"is_original\": 0\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 543512,\n" +
            "      \"title\": \"Love Me\",\n" +
            "      \"type\": \"tv_series\",\n" +
            "      \"tmdb_id\": 139628,\n" +
            "      \"tmdb_type\": \"tv\",\n" +
            "      \"imdb_id\": \"tt15233564\",\n" +
            "      \"season_number\": 1,\n" +
            "      \"poster_url\": \"https://cdn.watchmode.com/posters/0543512_poster_w185.jpg\",\n" +
            "      \"source_release_date\": \"2022-04-01\",\n" +
            "      \"source_id\": 157,\n" +
            "      \"source_name\": \"Hulu\",\n" +
            "      \"is_original\": 0\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 122505,\n" +
            "      \"title\": \"All Inclusive\",\n" +
            "      \"type\": \"movie\",\n" +
            "      \"tmdb_id\": 105894,\n" +
            "      \"tmdb_type\": \"movie\",\n" +
            "      \"imdb_id\": \"tt1065290\",\n" +
            "      \"season_number\": null,\n" +
            "      \"poster_url\": \"https://cdn.watchmode.com/posters/0122505_poster_w185.jpg\",\n" +
            "      \"source_release_date\": \"2022-04-01\",\n" +
            "      \"source_id\": 157,\n" +
            "      \"source_name\": \"Hulu\",\n" +
            "      \"is_original\": 0\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 131232,\n" +
            "      \"title\": \"Antz\",\n" +
            "      \"type\": \"movie\",\n" +
            "      \"tmdb_id\": 8916,\n" +
            "      \"tmdb_type\": \"movie\",\n" +
            "      \"imdb_id\": \"tt0120587\",\n" +
            "      \"season_number\": null,\n" +
            "      \"poster_url\": \"https://cdn.watchmode.com/posters/0131232_poster_w185.jpg\",\n" +
            "      \"source_release_date\": \"2022-04-01\",\n" +
            "      \"source_id\": 157,\n" +
            "      \"source_name\": \"Hulu\",\n" +
            "      \"is_original\": 0\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": 133594,\n" +
            "      \"title\": \"Armored\",\n" +
            "      \"type\": \"movie\",\n" +
            "      \"tmdb_id\": 4597,\n" +
            "      \"tmdb_type\": \"movie\",\n" +
            "      \"imdb_id\": \"tt0913354\",\n" +
            "      \"season_number\": null,\n" +
            "      \"poster_url\": \"https://cdn.watchmode.com/posters/0133594_poster_w185.jpg\",\n" +
            "      \"source_release_date\": \"2022-04-01\",\n" +
            "      \"source_id\": 157,\n" +
            "      \"source_name\": \"Hulu\",\n" +
            "      \"is_original\": 0\n" +
            "    }\n" +
            "  ]\n" +
            "}"

    override fun getReleases(): NetworkResponse<List<ReleaseMovieResponse>> {
        val gson = Gson()
        val listType: Type = object : TypeToken<List<ReleaseMovieResponse>>() {}.type
        val response = gson.fromJson<List<ReleaseMovieResponse>>(MOCK_RELEASES, listType)
        return NetworkResponse.Success(response)
    }
}