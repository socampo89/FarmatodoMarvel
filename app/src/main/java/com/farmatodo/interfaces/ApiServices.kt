package com.farmatodo.interfaces

import com.farmatodo.data.*
import io.reactivex.Observable
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.codec.binary.Hex
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiServices {

    @GET("v1/public/comics?limit=50")
    fun getComics(): Observable<ComicResult>

    @GET("v1/public/comics/{id}")
    fun getComic(@Path("id") comicId: Int): Observable<ComicResult>

    @GET("v1/public/characters?limit=50")
    fun getCharacters(): Observable<CharacterResult>

    @GET("v1/public/characters/{id}")
    fun getCharacter(@Path("id") characterId: Int): Observable<CharacterResult>

    @GET("v1/public/creators?limit=50")
    fun getCreators(): Observable<CreatorResult>

    @GET("v1/public/creators/{id}")
    fun getCreator(@Path("id") creatorId: Int): Observable<CreatorResult>

    @GET("v1/public/series?limit=50")
    fun getSeries(): Observable<SerieResult>

    @GET("v1/public/series/{id}")
    fun getSerie(@Path("id") serieId: Int): Observable<SerieResult>

    @GET("v1/public/stories?limit=50")
    fun getStories(): Observable<StoryResult>

    @GET("v1/public/stories/{id}")
    fun getStory(@Path("id") storyId: Int): Observable<StoryResult>

    @GET("v1/public/events?limit=50")
    fun getEvents(): Observable<EventResult>

    @GET("v1/public/events/{id}")
    fun getEvent(@Path("id") eventId: Int): Observable<EventResult>

    companion object {
        const val BASE_URL = "https://gateway.marvel.com/"
        const val PUBLIC_KEY = "fb72ef1c9ab6d246ea3607229e554524"
        private const val P_KEY = "072bd63b32ebef1fa992747586b98ede2aa548bb"
        const val TS = 1
        private const val HASH = TS.toString() + P_KEY + PUBLIC_KEY

        fun getHash(): String {
            return String(Hex.encodeHex(DigestUtils.md5(HASH)))
        }
    }
}
