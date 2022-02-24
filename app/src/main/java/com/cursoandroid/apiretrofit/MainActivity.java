package com.cursoandroid.apiretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cursoandroid.apiretrofit.api.CEPService;
import com.cursoandroid.apiretrofit.api.DataService;
import com.cursoandroid.apiretrofit.model.CEP;
import com.cursoandroid.apiretrofit.model.Foto;
import com.cursoandroid.apiretrofit.model.Postagem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Button btnRecuperar;
    private TextView txtResultado;
    private Retrofit retrofit;
    private List<Foto> listaFotos = new ArrayList<>();
    DataService service;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtResultado = findViewById(R.id.txtResultado);
        btnRecuperar = findViewById(R.id.btnRecuperar);


        retrofit = new Retrofit.Builder()
                //.baseUrl("https://viacep.com.br/ws/")
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(DataService.class);

        btnRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //recuperarCEPRetrofit();
                //recuperarListaRetrofit();
                //salvarPostagem();
                //atualizarPostagem();
                //removerPostagem();
            }
        });
    }

    private void removerPostagem(){
        Call<Void> call = service.removerPostagem(2);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    txtResultado.setText("Status: "+response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private void atualizarPostagem(){

        Postagem postagem = new Postagem("1234", null, "Cordo da postagem");
        Call<Postagem> call = service.atualizarPostagem(2, postagem);
        call.enqueue(new Callback<Postagem>() {
            @Override
            public void onResponse(Call<Postagem> call, Response<Postagem> response) {
                if(response.isSuccessful()){
                    Postagem postagemResposta = response.body();
                    txtResultado.setText(
                            "Código: "+response.code()+
                                    "id: "+postagemResposta.getId()+
                                    "userId: "+postagemResposta.getUserId()+
                                    " titulo: "+postagemResposta.getTitle()+
                                    " body: "+postagemResposta.getBody()
                    );
                }
            }

            @Override
            public void onFailure(Call<Postagem> call, Throwable t) {

            }
        });

    }

    private void salvarPostagem(){

        Postagem postagem = new Postagem("1234", "Título da postagem", "Cordo da postagem");

        Call<Postagem> call = service.salvarPostagem(postagem);

        call.enqueue(new Callback<Postagem>() {
            @Override
            public void onResponse(Call<Postagem> call, Response<Postagem> response) {
                if(response.isSuccessful()){
                    Postagem postagemResposta = response.body();
                    txtResultado.setText(
                            "Código: "+response.code()+
                            "id: "+postagemResposta.getId()+
                            " titulo: "+postagemResposta.getTitle()
                    );
                }
            }

            @Override
            public void onFailure(Call<Postagem> call, Throwable t) {

            }
        });

    }

    private void recuperarListaRetrofit(){

        Call<List<Foto>> call = service.recuperarFotos();

        call.enqueue(new Callback<List<Foto>>() {
            @Override
            public void onResponse(Call<List<Foto>> call, Response<List<Foto>> response) {
                if(response.isSuccessful()){
                    listaFotos = response.body();

                    //listaFotos recebeu todas as fotos do JSON.
                }
            }

            @Override
            public void onFailure(Call<List<Foto>> call, Throwable t) {

            }
        });
    }

    private void recuperarCEPRetrofit(){

        CEPService cepService = retrofit.create(CEPService.class);
        Call<CEP> call = cepService.recuperarCEP("14408140");

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
