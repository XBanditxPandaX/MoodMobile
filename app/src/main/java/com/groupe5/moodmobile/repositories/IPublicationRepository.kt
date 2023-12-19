package com.groupe5.moodmobile.repositories

import com.groupe5.moodmobile.dtos.Publication.Input.DtoInputPubLike
import com.groupe5.moodmobile.dtos.Publication.Input.DtoInputPublicationInformation
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface IPublicationRepository {
    @GET("api/v1/publication/{id}")
    fun getPublicationInformation(@Path("id") id: Int): Call<DtoInputPublicationInformation>
    @POST("api/v1/publication/like")
    fun setPublicationLike(@Body dto: DtoInputPubLike): Call<Void>
}