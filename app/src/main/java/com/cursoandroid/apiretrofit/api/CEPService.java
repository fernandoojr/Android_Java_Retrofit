package com.cursoandroid.apiretrofit.api;

import com.cursoandroid.apiretrofit.model.CEP;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CEPService {

    @GET("14408140/json/")
    Call<CEP> recuperarCEP();

}
