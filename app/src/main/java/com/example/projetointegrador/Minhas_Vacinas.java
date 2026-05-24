package com.example.projetointegrador;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import java.util.List;

public class Minhas_Vacinas extends AppCompatActivity implements View.OnClickListener {

    LinearLayout containerVacinas;
    ImageButton btnCadasVac;
    ImageButton btVoltarPets;
    BancoControllerAnimais controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_minhas_vacinas);

        btVoltarPets = findViewById(R.id.btVoltarPets);
        btnCadasVac = findViewById(R.id.btnCadasVac);
        containerVacinas = findViewById(R.id.containerVacinas);

        btVoltarPets.setOnClickListener(this);
        btnCadasVac.setOnClickListener(this);

        controller = new BancoControllerAnimais(this);
        exibirPets(); // chama o método que busca e exibe a lista de pets
    }

    private void exibirPets() {
        containerVacinas.removeAllViews();
        List<Pet> listaPets = controller.listar(); // ← lista os pets

        for (final Pet pet : listaPets) {
            View cardView = getLayoutInflater().inflate(R.layout.item_pet, containerVacinas, false);

            TextView txtNome = cardView.findViewById(R.id.txtNomeCard);
            TextView txtEspecie = cardView.findViewById(R.id.txtEspecieCard);
            TextView txtId = cardView.findViewById(R.id.txtIdCard);

            txtNome.setText(pet.getNome());                        // ← nome do animal
            txtEspecie.setText("Idade: " + pet.getIdade() + " anos"); // ← idade
            txtId.setText("ID: " + pet.getId());

            cardView.setOnClickListener(v -> {
                //  ao clicar, abre tela de vacinas do pet
                Intent intent = new Intent(Minhas_Vacinas.this, VacinasPetActivity.class);
                // passa o objeto pet para a proxima tela saber de qual pet exibir a vacina
                intent.putExtra("PET_OBJETO", pet);
                startActivity(intent);
            });

            containerVacinas.addView(cardView);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        exibirPets();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnCadasVac) {
            Intent cadastrarVac = new Intent(this, formVac1.class);
            startActivity(cadastrarVac);
        }
        if (view.getId() == R.id.btVoltarPets) {
            Intent main = new Intent(this, MainActivity.class);
            startActivity(main);
        }
    }
}