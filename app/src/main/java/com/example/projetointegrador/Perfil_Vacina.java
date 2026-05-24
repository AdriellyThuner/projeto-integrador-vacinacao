package com.example.projetointegrador;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class Perfil_Vacina extends AppCompatActivity {

    TextView txtPerfilNome,  txtPerfilNomeVac, txtPerfildataVac, txtPerfilvetVac, txtPerfilDose, txtResumo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_vacina);



        txtPerfilNome = findViewById(R.id.txtPerfilNome);

        txtPerfilNomeVac = findViewById(R.id.txtPerfilNomeVac);
        txtPerfildataVac = findViewById(R.id.txtPerfildataVac);
        txtPerfilvetVac= findViewById(R.id.txtPerfilvetVac);
        txtPerfilDose=findViewById(R.id.txtPerfilDose);
        txtResumo = findViewById(R.id.txtResumo);


        // Recupera o objeto Animal enviado pela Form3
        Pet pet = (Pet) getIntent().getSerializableExtra("PET_OBJETO");

        if (pet == null) {
            Toast.makeText(this, "Erro ao carregar pet!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        txtPerfilNome.setText("Nome: " + pet.getNome());




        Vacina vacina = (Vacina) getIntent().getSerializableExtra("VACINA_OBJ");

        if (pet != null) {

            txtPerfilNome.setText("Nome: " + pet.getNome());


            // SE EXISTIR VACINA
            if (vacina != null) {

                txtPerfilNomeVac.setText("Nome da vacina: " + vacina.getNomeVac());

                txtPerfildataVac.setText("Data: " + vacina.getDataVac());

                txtPerfilvetVac.setText("Veterinário: " + vacina.getVetVac());

                txtPerfilDose.setText("Dose: " + vacina.getDose());

                txtResumo.setText(
                        "\n===== VACINA =====" +
                                "\nNome: " + vacina.getNomeVac() +
                                "\nData da Aplicação: " + vacina.getDataVac() +
                                "\nHorário da Vacina: " + vacina.getHoraVac() +
                                "\nVeterinário: " + vacina.getVetVac() +
                                "\nCRM:: " + vacina.getCrmVac() +
                                "\nDose: " + vacina.getDose() +
                                "\n===== DETALHES DA VACINA =====" +
                                "\nLote: " + vacina.getDetVac() +
                                "\nValidade da Vacina: " + vacina.getDetVac1() +
                                "\nFabricante da Vacina: " + vacina.getDetVac2() +
                                "\n===== PROXIMA VACINA =====" +
                                "\nPossui reforço?: " + vacina.getDose2() +
                                "\nVacina de Reforço: " + vacina.getRefVac() +
                                "\nData da Proxima aplicação: " + vacina.getDataref() +
                                "\n===== OBS =====" +
                                "\nOBS: " + vacina.getObsVac() +
                                "\nPróxima: " + vacina.getProVac()
                );

            } else {

                Toast.makeText(this,
                        "Vacina não encontrada!",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}