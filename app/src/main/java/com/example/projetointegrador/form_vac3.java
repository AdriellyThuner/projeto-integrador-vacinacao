package com.example.projetointegrador;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class form_vac3 extends AppCompatActivity implements View.OnClickListener {

    Button btnProximo3;
    ImageButton btVoltar3, btFechar3;
    EditText txtNomedose, txtDataDose, txtProVac;
    CheckBox checkbox;
    ScrollView formVac3;
    Pet pet;
    Vacina vacinaDados;
    BancoControllerVacinas controller;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form_vac3);

        controller = new BancoControllerVacinas(this);

        if (getIntent().hasExtra("VACINA_OBJ")) { //verifica se o objeto vacina chegou
            vacinaDados = (Vacina) getIntent().getSerializableExtra("VACINA_OBJ"); // recupera os dados

        }
        pet = (Pet) getIntent().getSerializableExtra("PET_OBJETO");


        // BOTOES
        btnProximo3 = findViewById(R.id.btnProximo3);
        btVoltar3 = findViewById(R.id.btVoltar3);
        btFechar3 = findViewById(R.id.btFechar3);

        btnProximo3.setOnClickListener(this);
        btVoltar3.setOnClickListener(this);
        btFechar3.setOnClickListener(this);

        // CAMPOS
        txtNomedose = findViewById(R.id.txtNomedose);
        txtDataDose = findViewById(R.id.txtDataDose);
        txtProVac = findViewById(R.id.txtProVac);
        checkbox = findViewById(R.id.checkbox);
        formVac3 = findViewById(R.id.formVac3);

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btnProximo3) {
            String nomeDose = txtNomedose.getText().toString();
            String dataDose = txtDataDose.getText().toString();
            String proVac = txtProVac.getText().toString();
            String statusCheckbox = checkbox.isChecked() ? "Sim" : "Não";



            if (vacinaDados != null) { //verifica se o dados chegaram antes de preencher
                vacinaDados.setNomeDose(nomeDose);
                vacinaDados.setDataDose(dataDose);
                vacinaDados.setChecklist(statusCheckbox);
                vacinaDados.setProVac(proVac);

                if (pet != null) {
                    vacinaDados.setPetId(pet.getId()); // Vincula o ID do animal antes de salvar no banco
                }



                boolean resultado = controller.insereDados(vacinaDados);

                if (resultado) {

                    Toast.makeText(this,
                            "Dados salvos com sucesso!",
                            Toast.LENGTH_SHORT).show();

                    Intent tela =
                            new Intent(this, PerfilPetActivity.class);

                    // envia o PET
                    tela.putExtra("PET_OBJETO", pet); // passa o objeto pet para a tela de perfil

                    // envia a VACINA
                    tela.putExtra("VACINA_OBJ", vacinaDados); // passa o objeto vacina para a tela de perfil



                    startActivity(tela);

                    finish();
                } else {
                    Toast.makeText(this, "Erro ao salvar no banco.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Erro interno: Dados do formulário não localizados.", Toast.LENGTH_SHORT).show();
            }
        }


        if (view.getId() == R.id.btVoltar3) {
            Intent form2 = new Intent(this, formVac2.class);
            form2.putExtra("VACINA_OBJ", vacinaDados);
            form2.putExtra("PET_OBJETO",pet);
            startActivity(form2); // CORREÇÃO: Passado o objeto correto da intent
            finish();
        }

        if (view.getId() == R.id.btFechar3) {
            Intent main = new Intent(this, MainActivity.class);
            main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(main);
            finish();
        }
    }
}
