package com.example.projetointegrador;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import okhttp3.Request;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class Minhas_Vacinas extends AppCompatActivity implements View.OnClickListener {

    LinearLayout containerVacinas;
    ImageButton btnCadasVac;
    ImageButton btVoltarPets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_minhas_vacinas);


        btVoltarPets     = findViewById(R.id.btVoltarPets);
        btnCadasVac      = findViewById(R.id.btnCadasVac);
        containerVacinas = findViewById(R.id.containerVacinas);

        // Registra os listeners de clique
        btVoltarPets.setOnClickListener(this);
        btnCadasVac.setOnClickListener(this);
    }

    private void exibirPets() {
        containerVacinas.removeAllViews();
        OkHttpClient client = new OkHttpClient();

        // Monta a requisição GET buscando todos os animais da tabela "animais"
        Request request = new Request.Builder()
                .url(SupabaseConfig.URL + "/rest/v1/animais"
                        + "?select=id_animal,nome,especie,idade,sexo,"
                        + "porte,data_resgate,enfermidade,obs_enfermidade,"
                        + "localizacao_resgate,castrado,pelo,docil,anotacoes")
                .get()
                .addHeader("apikey", SupabaseConfig.API_KEY)        // chave de acesso
                .addHeader("Authorization", "Bearer " + SupabaseConfig.API_KEY)
                .build();


        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

                runOnUiThread(() ->
                        Toast.makeText(Minhas_Vacinas.this,
                                "Erro de conexão: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String json = response.body() != null ? response.body().string() : "[]";


                JsonArray array = JsonParser.parseString(json).getAsJsonArray();


                runOnUiThread(() -> {
                    containerVacinas.removeAllViews();

                    // Percorre cada animal retornado pelo Supabase
                    for (JsonElement el : array) {
                        JsonObject obj = el.getAsJsonObject();


                        String nome    = obj.has("nome")    ? obj.get("nome").getAsString()    : "";
                        String especie = obj.has("especie") ? obj.get("especie").getAsString() : "";


                        int idade = 0;
                        if (obj.has("idade") && !obj.get("idade").isJsonNull()) {
                            try {
                                idade = Integer.parseInt(obj.get("idade").getAsString());
                            } catch (NumberFormatException ignored) {} // ignora se não for número
                        }

                        // Cria o objeto Pet com os dados recebidos

                        Pet pet = new Pet(nome, idade,
                                obj.has("sexo")         ? obj.get("sexo").getAsString()         : "",
                                obj.has("data_resgate") ? obj.get("data_resgate").getAsString() : "",
                                obj.has("porte")        ? obj.get("porte").getAsString()        : "",
                                especie, "", "", "", "", "", "", "");

                        // Define o ID do pet
                        if (obj.has("id_animal") && !obj.get("id_animal").isJsonNull()) {
                            pet.setId(obj.get("id_animal").getAsInt());
                        }

                        adicionarCard(pet); // cria o card visual para esse pet
                    }
                });
            }
        });
    }

    private void adicionarCard(Pet pet) {

        View cardView = getLayoutInflater()
                .inflate(R.layout.item_pet, containerVacinas, false);


        TextView txtNome    = cardView.findViewById(R.id.txtNomeCard);
        TextView txtEspecie = cardView.findViewById(R.id.txtEspecieCard);

        // Preenche o card com os dados do pet
        txtNome.setText(pet.getNome());
        txtEspecie.setText("Idade: " + pet.getIdade() + " anos");

        // Ao clicar no card, abre a tela de vacinas daquele pet
        cardView.setOnClickListener(v -> {
            Intent intent = new Intent(Minhas_Vacinas.this, VacinasPetActivity.class);
            intent.putExtra("PET_OBJETO", pet); // passa o pet para a próxima tela
            startActivity(intent);
        });

        containerVacinas.addView(cardView); // adiciona o card na tela
    }

    @Override
    protected void onResume() {
        super.onResume();
        exibirPets();
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btVoltarPets) {
            Intent main = new Intent(this, MainActivity.class);
            startActivity(main);
        }
    }
}