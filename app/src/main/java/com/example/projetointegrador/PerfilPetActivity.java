package com.example.projetointegrador;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class PerfilPetActivity extends AppCompatActivity {

    TextView txtPerfilNome, txtPerfilIdade, txtPerfilEspecie, txtPerfilPorte, txtPerfilResumo;

    Button btExcluirPet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_pet);

        txtPerfilNome = findViewById(R.id.txtPerfilNome);
        txtPerfilIdade = findViewById(R.id.txtPerfilIdade);
        txtPerfilEspecie = findViewById(R.id.txtPerfilEspecie);
        txtPerfilPorte = findViewById(R.id.txtPerfilPorte);
        txtPerfilResumo = findViewById(R.id.txtPerfilResumo);
        btExcluirPet = findViewById(R.id.btExcluirPet);

        // Pega o objeto (Pet) com todos os dados que foi enviado pelo Form3
        Pet pet = (Pet) getIntent().getSerializableExtra("PET_OBJETO");

        if (pet != null) {
            // Aqui mostra os dados do Pet
            txtPerfilNome.setText("Nome: " + pet.getNome());
            txtPerfilIdade.setText("Idade: " + pet.getIdade() + " anos");
            txtPerfilEspecie.setText("Espécie: " + pet.getEspecie() + " (" + pet.getSexo() + ")");
            txtPerfilPorte.setText("Porte: " + pet.getPorte());

            String resumoGeral =
                    "Pelo: " + pet.getPelo() + "\n" +
                    "Dócil: " + pet.getDocil() + "\n" +
                    "Castrado: " + pet.getCastrado() + "\n" +
                    "Enfermidade: " + pet.getEnfermidade() + " (" + pet.getObsEnfermidade() + ")\n" +
                    "Anotações: " + pet.getAnotacoes();

            txtPerfilResumo.setText(resumoGeral);

            btExcluirPet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new BancoControllerAnimais(PerfilPetActivity.this).excluirDados(pet.getId());
                    finish();
                }
            });
        }
    }
}