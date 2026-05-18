package com.example.projetointegrador;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Form3 extends AppCompatActivity implements View.OnClickListener{

    Button btProximo3;
    ImageButton btVoltar3, btFechar3;
    EditText txtPelo, txtAnotacoes;
    RadioGroup rgDocil;
    ScrollView form3;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_form3);

        // BOTOES
        btProximo3 = findViewById(R.id.btProximo3);
        btVoltar3 = findViewById(R.id.btVoltar3);
        btFechar3 = findViewById(R.id.btFechar3);

        btProximo3.setOnClickListener(this);
        btVoltar3.setOnClickListener(this);
        btFechar3.setOnClickListener(this);

        // CAMPOS
        txtPelo = findViewById(R.id.txtPelo);
        rgDocil = findViewById(R.id.rgDocil);
        txtAnotacoes = findViewById(R.id.txtAnotacoes);
        form3 = findViewById(R.id.form3);

    }
    @Override
    public void onClick(View view) {
        // se a origem do click foi no botão Tela1
        if (view.getId() == R.id.btProximo3){
            String pelo = txtPelo.getText().toString();
            String anotacoes = txtAnotacoes.getText().toString();

            // RADIO DOCIL
            int idDocil = rgDocil.getCheckedRadioButtonId();

            if (idDocil == -1) {
                Toast.makeText(this, "Selecione a enfermidade!", Toast.LENGTH_SHORT).show();
                return;
            }
            RadioButton rbDocil = findViewById(idDocil);
            String docil = rbDocil.getText().toString();

            //RECEBE DADOS DO FORM1 e FORM2
            Intent intent = getIntent();

            String nome = intent.getStringExtra("nome");
            int idade = intent.getIntExtra("idade", 0);
            String sexo = intent.getStringExtra("sexo");
            String data = intent.getStringExtra("data");
            String porte = intent.getStringExtra("porte");
            String especie = intent.getStringExtra("especie");
            String enfermidade = intent.getStringExtra("enfermidade");
            String obsEnfermidade = intent.getStringExtra("obsEnfermidade");
            String local = intent.getStringExtra("local");
            String castrado = intent.getStringExtra("castrado");

            Pet novoPet = new Pet(nome, idade, sexo, data, porte, especie, enfermidade, obsEnfermidade, local,
                          castrado, pelo, docil, anotacoes);

            //  SALVAR NO BANCO
            BancoControllerAnimais controller = new BancoControllerAnimais(this);
            boolean sucesso = controller.insereDados(novoPet);

            if (sucesso) {
                Toast.makeText(this, "Dados salvos com sucesso!", Toast.LENGTH_SHORT).show();

                Intent confirma = new Intent(this, MeusAnimais.class);
                startActivity(confirma);

                /*/ passando o objeto inteiro
                Intent perfilPet = new Intent(this, PerfilPetActivity.class);
                perfilPet.putExtra("PET_OBJETO", novoPet);
                //startActivity(perfilPet); */

                finish(); // Fecha a tela de formulário atual
            } else {
                Toast.makeText(this, "Erro ao salvar no banco.", Toast.LENGTH_SHORT).show();
            }
        }

        // VOLTA PARA O FORM2
        if (view.getId() == R.id.btVoltar3){
            // Chamar a tela MeusAnimais
            Intent form2 = new Intent(this, Form2.class);
            startActivity(form2);
        }
        // VOLTA PARA A TELA MEUS PETS/ANIMAIS
        if (view.getId() == R.id.btFechar3){
            // Chamar a tela MeusAnimais
            Intent main = new Intent(this, MeusAnimais.class);
            startActivity(main);
        }


    }
}
