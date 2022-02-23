package com.cursoandroid.apiretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cursoandroid.apiretrofit.api.CEPService;
import com.cursoandroid.apiretrofit.model.CEP;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Button btnRecuperar;
    private TextView txtResultado;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtResultado = findViewById(R.id.txtResultado);
        btnRecuperar = findViewById(R.id.btnRecuperar);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://viacep.com.br/ws/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        btnRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recuperarCEPRetrofit();
            }
        });
    }

    private void recuperarCEPRetrofit(){

        CEPService cepService = retrofit.create(CEPService.class);
        Call<CEP> call = cepService.recuperarCEP();

        call.enqueue(new Callback<CEP>() {
            @Override
            public void onResponse(Call<CEP> call, Response<CEP> response) {
                if(response.isSuccessful()){
                    CEP cep = response.body();
                    txtResultado.setText(cep.getLogradouro() +"/"+ cep.getBairro()+"/"+cep.getCep()+"/"+cep.getComplemento()+"/"+cep.getLocalidade()+"/"+cep.getUf());
                }
            }

            @Override
            public void onFailure(Call<CEP> call, Throwable t) {

            }
        });
    }


}
