package com.example.projetointegrador;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ImageButton btCarteiraVacinacao, btMeusAnimais, btCalendarioGeral, iconUsuario, btMeuPerfil;
    BottomNavigationView bottomNav;
    TextView txtQuantidadeAnimais;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // BOTOES
        btCarteiraVacinacao = findViewById(R.id.btCarteiraVacinacao);
        btMeusAnimais = findViewById(R.id.btMeusAnimais);
        btCalendarioGeral = findViewById(R.id.btCalendarioGeral);
        iconUsuario = findViewById(R.id.iconUsuario);
        btMeuPerfil = findViewById(R.id.btMeuPerfil);
        txtQuantidadeAnimais = findViewById(R.id.txtQuantidadeAnimais);

        btCarteiraVacinacao.setOnClickListener(this);
        btMeusAnimais.setOnClickListener(this);
        btCalendarioGeral.setOnClickListener(this);
        iconUsuario.setOnClickListener(this);
        btMeuPerfil.setOnClickListener(this);

        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_home); // marca o ícone como ativo


        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                return true;

            } else if (id == R.id.nav_search) {
                startActivity(new Intent(this, MeusAnimais.class));
                return true;

            } else if (id == R.id.nav_add) {
                startActivity(new Intent(this, Form1.class));
                return true;

            }  else if (id == R.id.nav_calendar) {
                startActivity(new Intent(this, CalendarActivity.class));
                return true;
            } else if (id == R.id.nav_profile) {
            startActivity(new Intent(this, InfoUsuario.class));
            return true;
        }

            return false;
        });
    }

    @Override
    public void onClick(View view) {
        // AO CLICAR EM NO BOTÃO "btCarteiraVacinacao" VAI PARA A TELA CARTEIRA E VACINACAO
        if (view.getId() == R.id.btCarteiraVacinacao){
            // Chama a tela MeusAnimais
            Intent tela1 = new Intent(this, MeusAnimais.class); // Mudar a tela!
            startActivity(tela1);
        }
        if (view.getId() == R.id.iconUsuario){
            // Chama a tela InfoUsuario
            Intent tela1 = new Intent(this, InfoUsuario.class);
            startActivity(tela1);
        }
        // AO CLICAR EM NO BOTÃO "btMeusAnimais" VAI PARA A TELA MEUS PETS/ANIMAIS
        if (view.getId() == R.id.btMeusAnimais){
            // Chama a tela MeusAnimais
            Intent pets = new Intent(this, MeusAnimais.class);
            startActivity(pets);
        }
        // AO CLICAR EM NO BOTÃO "btCalendarioGeral" VAI PARA A TELA CALENDARIO GERAL
        if (view.getId() == R.id.btCalendarioGeral){
            // Chama a tela calendar
            Intent tela2 = new Intent(this, CalendarActivity.class); // Mudar a tela!
            startActivity(tela2);
        }
        // AO CLICAR EM NO BOTÃO "btCalendarioGeral" VAI PARA A TELA MEU PERFIL
        if (view.getId() == R.id.btMeuPerfil){
            // Chamar a tela InfoUsuario
            Intent infoUsuario = new Intent(this, InfoUsuario.class);
            startActivity(infoUsuario);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Dispara a contagem automática toda vez que o usuário visualizar esta tela
        atualizarContadorSupabase();
    }

    private void atualizarContadorSupabase() {
        OkHttpClient client = new OkHttpClient();
        String url = SupabaseConfig.URL + "/rest/v1/animais?select=count";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("apikey", SupabaseConfig.API_KEY)
                .addHeader("Authorization", "Bearer " + SupabaseConfig.API_KEY)
                .addHeader("Prefer", "count=exact") // Exige a contagem exata do banco
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> txtQuantidadeAnimais.setText("0"));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    String strResposta = response.body().string();
                    // Limpa o JSON do Supabase deixando apenas o número puro
                    String apenasNumeros = strResposta.replaceAll("[^0-9]", "");

                    runOnUiThread(() -> {
                        if (!apenasNumeros.isEmpty()) {
                            txtQuantidadeAnimais.setText(apenasNumeros);
                        } else {
                            txtQuantidadeAnimais.setText("0");
                        }
                    });
                }
            }
        });
    }

}