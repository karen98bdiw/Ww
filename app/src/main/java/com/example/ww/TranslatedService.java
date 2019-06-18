package com.example.ww;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TranslatedService {

    @GET("translate")
    Single<Translated> getText(@Query("key") String key,@Query("text") String text,@Query("lang") String lang);



}
