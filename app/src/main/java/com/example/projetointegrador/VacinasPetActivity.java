package com.example.projetointegrador;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VacinasPetActivity extends AppCompatActivity {

    LinearLayout containerVacinas;
    ImageButton btnCadasVac;
    ImageButton btVoltarPets;
    Pet pet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_minhas_vacinas);

        containerVacinas = findViewById(R.id.containerVacinas);
        btnCadasVac      = findViewById(R.id.btnCadasVac);
        btVoltarPets     = findViewById(R.id.btVoltarPets);

        pet = (Pet) getIntent().getSerializableExtra("PET_OBJETO");
        if (pet == null) { finish(); return; }

        btVoltarPets.setOnClickListener(v -> finish());

        btnCadasVac.setOnClickListener(v -> {
            Intent intent = new Intent(this, formVac1.class);
            intent.putExtra("PET_OBJETO", pet);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarVacinas();
    }

    private void carregarVacinas() {
        containerVacinas.removeAllViews();
        OkHttpClient client = new OkHttpClient();

        // Busca vacinas do Supabase filtrando pelo ID do pet
        Request request = new Request.Builder()
                .url(SupabaseConfig.URL + "/rest/v1/carteira_vacinacao"
                        + "?id_animal=eq." + pet.getId()
                        + "&select=id_registro,Data_aplicacao,Hora_aplicacao,Nome_vet,CRMV_vet,dose,Lote,Data_val,Fabricante,dose2,NomeRef,DataRef,Nome_pro_vac,Data_pro_vac,lembrar,ObsVac,Anota_vac,vacinas(id_vacina,nome_vacina)")
                .get()
                .addHeader("apikey",        SupabaseConfig.API_KEY)
                .addHeader("Authorization", "Bearer " + SupabaseConfig.API_KEY)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                    TextView tv = new TextView(VacinasPetActivity.this);
                    tv.setText("Erro de conexão: " + e.getMessage());
                    tv.setPadding(40, 20, 40, 20);
                    containerVacinas.addView(tv);
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body() != null ? response.body().string() : "[]";

                runOnUiThread(() -> {
                    try {
                        JsonArray array = JsonParser.parseString(json).getAsJsonArray();

                        if (array.size() == 0) {
                            TextView tv = new TextView(VacinasPetActivity.this);
                            tv.setText("Nenhuma vacina cadastrada para " + pet.getNome());
                            tv.setPadding(40, 20, 40, 20);
                            containerVacinas.addView(tv);
                            return;
                        }

                        for (JsonElement el : array) {
                            JsonObject obj = el.getAsJsonObject();

                            // Monta objeto Vacina com os dados do Supabase
                            Vacina v = new Vacina();

                            if (obj.has("vacinas") && !obj.get("vacinas").isJsonNull()) {
                                JsonObject vacObj = obj.get("vacinas").getAsJsonObject();
                                v.setNomeVac(vacObj.has("nome_vacina") ? vacObj.get("nome_vacina").getAsString() : "");
                            }


                            v.setDose(obj.has("dose") ? obj.get("dose").getAsString() : "");
                            v.setData_aplicacao(obj.has("Data_aplicacao") ? obj.get("Data_aplicacao").getAsString() : "");
                            v.setHora_aplicacao(obj.has("Hora_aplicacao") ? obj.get("Hora_aplicacao").getAsString() : "");
                            v.setNome_vet(obj.has("Nome_vet") ? obj.get("Nome_vet").getAsString() : "");
                            v.setCRMV_vet(obj.has("CRMV_vet") ? obj.get("CRMV_vet").getAsString() : "");
                            v.setLote(obj.has("Lote") ? obj.get("Lote").getAsString():"");
                            v.setData_val(obj.has("Data_val") ? obj.get("Data_val").getAsString():"");
                            v.setFabricante(obj.has("Fabricante") ? obj.get("Fabricante").getAsString():"");
                            v.setDose2(obj.has("dose2") ? obj.get("dose2").getAsString():"");
                            v.setNomeRef(obj.has("NomeRef") ? obj.get("NomeRef").getAsString():"");
                            v.setDataRef(obj.has("DataRef") ? obj.get("DataRef").getAsString():"");
                            v.setObsVac(obj.has("ObsVac") ? obj.get("ObsVac").getAsString():"");
                            v.setNome_pro_vac(obj.has("Nome_pro_vac") ? obj.get("Nome_pro_vac").getAsString():"");
                            v.setData_pro_vac(obj.has("Data_pro_vac") ? obj.get("Data_pro_vac").getAsString():"");
                            v.setLembrar(obj.has("lembrar") ? obj.get("lembrar").getAsString():"");
                            v.setAnota_vac(obj.has("Anota_vac") ? obj.get("Anota_vac").getAsString():"");

                            if (obj.has("id_registro") && !obj.get("id_registro").isJsonNull()) {
                                v.setId_vacina(obj.get("id_registro").getAsInt());
                            }

                            adicionarCardVacina(v);
                        }

                    } catch (Exception e) {
                        TextView tv = new TextView(VacinasPetActivity.this);
                        tv.setText("Erro ao carregar vacinas.");
                        tv.setPadding(40, 20, 40, 20);
                        containerVacinas.addView(tv);
                    }
                });
            }
        });
    }

    private void adicionarCardVacina(Vacina v) {
        View cardView = getLayoutInflater()
                .inflate(R.layout.item_pet, containerVacinas, false);

        TextView txtNome    = cardView.findViewById(R.id.txtNomeCard);
        TextView txtEspecie = cardView.findViewById(R.id.txtEspecieCard);

        txtNome.setText(v.getNomeVac());
        txtEspecie.setText("Data: " + v.getData_aplicacao());

        cardView.setOnClickListener(view -> {
            Intent intent = new Intent(VacinasPetActivity.this, Perfil_Vacina.class);
            intent.putExtra("VACINA_OBJ", v);
            intent.putExtra("PET_OBJETO", pet);
            startActivity(intent);
        });

        containerVacinas.addView(cardView);
    }
}