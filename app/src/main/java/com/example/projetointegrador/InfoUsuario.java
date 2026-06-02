package com.example.projetointegrador;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class InfoUsuario extends AppCompatActivity {

    TextView PerfilNome, PerfilCPF, PerfilTelefone;
    TextView PerfilCRM, PerfilONG, PerfilEmail, PerfilResumo;
    ImageButton btVoltarPets;
    String emailLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_info_usuario);

        PerfilNome     = findViewById(R.id.PerfilNome);
        PerfilCPF      = findViewById(R.id.PerfilCPF);
        PerfilTelefone = findViewById(R.id.PerfilTelefone);
        PerfilCRM      = findViewById(R.id.PerfilCRM);
        PerfilONG      = findViewById(R.id.PerfilONG);
        PerfilEmail    = findViewById(R.id.PerfilEmail);
        PerfilResumo   = findViewById(R.id.PerfilResumo);
        btVoltarPets   = findViewById(R.id.btVoltarPets);

        emailLogado = getIntent().getStringExtra("emailLogado");

        btVoltarPets.setOnClickListener(v -> finish());

        carregarDadosUsuario();
    }

    private void carregarDadosUsuario() {
        OkHttpClient client = new OkHttpClient();

        // Busca usuário pelo email na tabela usuarios do Supabase
        Request request = new Request.Builder()
                .url(SupabaseConfig.URL + "/rest/v1/usuarios"
                        + "?email=eq." + emailLogado
                        + "&select=nome,CPF,telefone,CRMV,ong,email")
                .get()
                .addHeader("apikey", SupabaseConfig.API_KEY)
                .addHeader("Authorization", "Bearer " + SupabaseConfig.API_KEY)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("SUPABASE_ERRO", e.getMessage());
                runOnUiThread(() ->
                        Toast.makeText(InfoUsuario.this,
                                "Erro de conexão: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body() != null ? response.body().string() : "[]";
                Log.d("SUPABASE_RESP", json);

                runOnUiThread(() -> {
                    try {
                        JsonArray array = JsonParser.parseString(json).getAsJsonArray();

                        if (array.size() == 0) {
                            PerfilNome.setText("Nenhum usuário encontrado");
                            PerfilResumo.setText("Realize o cadastro primeiro.");
                            return;
                        }

                        JsonObject obj = array.get(0).getAsJsonObject();

                        String nome     = obj.has("nome")     ? obj.get("nome").getAsString()     : "";
                        String cpf      = obj.has("CPF")      ? obj.get("CPF").getAsString()      : "";
                        String telefone = obj.has("telefone") ? obj.get("telefone").getAsString() : "";
                        String crm      = obj.has("CRMV")     ? obj.get("CRMV").getAsString()     : "";
                        String ong      = obj.has("ong")      ? obj.get("ong").getAsString()      : "";
                        String email    = obj.has("email")    ? obj.get("email").getAsString()    : "";

                        PerfilNome.setText(nome);
                        PerfilCPF.setText("CPF: " + cpf);
                        PerfilTelefone.setText("Telefone: " + telefone);
                        PerfilCRM.setText("CRMV: " + crm);
                        PerfilONG.setText("ONG: " + ong);
                        PerfilEmail.setText("E-mail: " + email);
                        PerfilResumo.setText("Usuário registrado com CRMV "
                                + crm + " vinculado à ONG " + ong);

                    } catch (Exception e) {
                        Log.e("SUPABASE_PARSE", e.getMessage());
                        PerfilNome.setText("Erro ao carregar dados.");
                    }
                });
            }
        });
    }
}