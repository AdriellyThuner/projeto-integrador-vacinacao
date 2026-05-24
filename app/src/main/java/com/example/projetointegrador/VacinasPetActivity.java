package com.example.projetointegrador;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class VacinasPetActivity extends AppCompatActivity {

    LinearLayout containerVacinas;
    ImageButton btnCadasVac;
    ImageButton btVoltarPets;
    Pet pet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minhas_vacinas);

        containerVacinas = findViewById(R.id.containerVacinas); // conecta ao linearlayout pelo id
        btnCadasVac = findViewById(R.id.btnCadasVac);
        btVoltarPets = findViewById(R.id.btVoltarPets);

        pet = (Pet) getIntent().getSerializableExtra("PET_OBJETO"); // recupera o objeto pet

        if (pet == null) { finish(); return; }

        btVoltarPets.setOnClickListener(v -> finish());

        // botão + abre cadastro de vacina já com o pet
        btnCadasVac.setOnClickListener(v -> {
            Intent intent = new Intent(this, formVac1.class);
            intent.putExtra("PET_OBJETO", pet); // passa o objeto para saber a qual pet pertence a vacina
            startActivity(intent);
        });

        carregarVacinas();
    }

    private void carregarVacinas() {
        containerVacinas.removeAllViews();
        BancoControllerVacinas controller = new BancoControllerVacinas(this);
        List<Vacina> vacinas = controller.listarPorPet(pet.getId()); //busca todas as vacinas associadas ao ID do animal

        if (vacinas.isEmpty()) {
            TextView tv = new TextView(this);
            tv.setText("Nenhuma vacina cadastrada para " + pet.getNome());
            tv.setPadding(40, 20, 40, 20); // espaçamento
            containerVacinas.addView(tv);
            return;
        }

        for (Vacina v : vacinas) { //percorre cada vacina para criar o card
            View cardView = getLayoutInflater().inflate(R.layout.item_pet, containerVacinas, false);

            TextView txtNome = cardView.findViewById(R.id.txtNomeCard);
            TextView txtEspecie = cardView.findViewById(R.id.txtEspecieCard);
            TextView txtId = cardView.findViewById(R.id.txtIdCard);

            txtNome.setText(v.getNomeVac());
            txtEspecie.setText("Data: " + v.getDataVac());
            txtId.setText("Dose: " + v.getDose());

            cardView.setOnClickListener(view -> {
                Intent intent = new Intent(VacinasPetActivity.this, Perfil_Vacina.class

            ); //abrir a tela de detalhes da vacina

                intent.putExtra("VACINA_OBJ", v);
                intent.putExtra("PET_OBJETO", pet);
                startActivity(intent); });

            containerVacinas.addView(cardView);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarVacinas();
    }
}