package com.example.projetointegrador;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Perfil_Vacina extends AppCompatActivity {


    TextView txtPerfilNome;       // nome do pet
    TextView txtPerfilSubtitulo;  // dose da vacina
    TextView txtPerfilNomeVac;    // nome da vacina
    TextView txtPerfildataVac;    // data de aplicação
    TextView txtPerfilHoraVac;    // hora de aplicação
    TextView txtPerfilLote;       // lote da vacina
    TextView txtPerfilFabricante; // fabricante
    TextView txtPerfilValVac;     // validade da vacina
    TextView txtPerfilVet;        // nome do veterinário
    TextView txtPerfilCRMV;       // CRMV do veterinário
    TextView txtPerfilNome_pro;   // nome da próxima vacina
    TextView txtPerfilData_pro;   // data da próxima vacina

    ImageButton btVoltarPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfil_vacina);


        txtPerfilNome      = findViewById(R.id.txtPerfilNome);
        txtPerfilSubtitulo = findViewById(R.id.txtPerfilSubtitulo);
        txtPerfilNomeVac   = findViewById(R.id.txtPerfilNomeVac);
        txtPerfildataVac   = findViewById(R.id.txtPerfildataVac);
        txtPerfilHoraVac   = findViewById(R.id.txtPerfilHoraVac);
        txtPerfilLote      = findViewById(R.id.txtPerfilLote);
        txtPerfilFabricante = findViewById(R.id.txtPerfilFabricante);
        txtPerfilValVac    = findViewById(R.id.txtPerfilValVac);
        txtPerfilVet       = findViewById(R.id.txtPerfilVet);
        txtPerfilCRMV      = findViewById(R.id.txtPerfilCRMV);
        txtPerfilNome_pro  = findViewById(R.id.txtPerfilNome_pro);
        txtPerfilData_pro  = findViewById(R.id.txtPerfilData_pro);
        btVoltarPerfil     = findViewById(R.id.btVoltarPerfil);


        btVoltarPerfil.setOnClickListener(v -> finish());

        //  Recupera os objetos enviados pela tela anterior
        Pet pet       = (Pet)    getIntent().getSerializableExtra("PET_OBJETO");
        Vacina vacina = (Vacina) getIntent().getSerializableExtra("VACINA_OBJ");


        if (pet == null) {
            Toast.makeText(this, "Erro ao carregar pet!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Preenche o nome do pet no topo
        txtPerfilNome.setText(pet.getNome());


        if (vacina == null) {
            Toast.makeText(this, "Vacina não encontrada!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // informações principais
        txtPerfilSubtitulo.setText("Dose: " + vacina.getDose());
        txtPerfilNomeVac.setText(vacina.getNomeVac());
        txtPerfildataVac.setText(vacina.getData_aplicacao());
        txtPerfilHoraVac.setText(vacina.getHora_aplicacao());

        //  detalhes da vacina
        txtPerfilLote.setText("Lote: " + vacina.getLote()
                + " | Validade: " + vacina.getData_val());


        if (vacina.getFabricante() != null && !vacina.getFabricante().isEmpty()) {
            txtPerfilFabricante.setVisibility(android.view.View.VISIBLE);
            txtPerfilFabricante.setText("Fabricante: " + vacina.getFabricante());
        }


        if (vacina.getData_val() != null && !vacina.getData_val().isEmpty()) {
            txtPerfilValVac.setVisibility(android.view.View.VISIBLE);
            txtPerfilValVac.setText("Validade: " + vacina.getData_val());
        }

        //  veterinário responsável
        txtPerfilVet.setText("Veterinário: " + vacina.getNome_vet());
        txtPerfilCRMV.setText("CRMV: " + vacina.getCRMV_vet());

        // próxima vacina
        txtPerfilNome_pro.setText("Próxima: " + vacina.getNome_pro_vac());
        txtPerfilData_pro.setText("Data: "    + vacina.getData_pro_vac());
    }
}