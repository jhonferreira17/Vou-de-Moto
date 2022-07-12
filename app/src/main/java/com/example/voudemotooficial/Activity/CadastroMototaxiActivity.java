package com.example.voudemotooficial.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.voudemotooficial.R;
import com.example.voudemotooficial.databinding.ActivityCadastroMototaxiBinding;
import com.google.firebase.auth.FirebaseAuth;

public class CadastroMototaxiActivity extends AppCompatActivity {
    private ActivityCadastroMototaxiBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityCadastroMototaxiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        binding.buttonCadastrarMototaxi.setOnClickListener(v -> validarDadosMototaxi());
    }

    private void validarDadosMototaxi(){
        String nomeMototaxi = binding.editNomeMototaxi.getText().toString().trim();
        String contatoMototaxi = binding.editContatoMototaxi.getText().toString().trim();
        String emailMototaxi = binding.editEmailMototaxi.getText().toString().trim();
        String senhalMototaxi = binding.editSenhaMototaxi.getText().toString().trim();
        String cnhMototaxi = binding.editCNHMototaxi.getText().toString().trim();
        String numeroDeSerieMototaxi = binding.editNumeroDeSerie.getText().toString().trim();
        String placaDaMoto = binding.editPlacaDaMoto.getText().toString().trim();
        String modeloMoto = binding.editModeloDaMoto.getText().toString().trim();
        String corDaMoto = binding.editCorDaMoto.getText().toString().trim();

        if (!nomeMototaxi.isEmpty()){
            if (!contatoMototaxi.isEmpty()){
                if (!emailMototaxi.isEmpty()){
                    if (!senhalMototaxi.isEmpty()){
                        if (!cnhMototaxi.isEmpty()){
                            if (!numeroDeSerieMototaxi.isEmpty()){
                                if (!placaDaMoto.isEmpty()){
                                    if (!modeloMoto.isEmpty()){
                                        if (!corDaMoto.isEmpty()){
                                            binding.progressBar2.setVisibility(View.VISIBLE);
                                            criarContaFirebase(emailMototaxi, senhalMototaxi);
                                        }else{//fim cor moto
                                            Toast.makeText(this, "Informe a cor da sua moto!", Toast.LENGTH_LONG).show();
                                        }
                                    }else{// fim modelo da moto
                                        Toast.makeText(this, "Informe o modelo de sua moto!", Toast.LENGTH_LONG).show();
                                    }
                                }else{//fim placa da moto
                                    Toast.makeText(this, "Informe a placa da moto!", Toast.LENGTH_LONG).show();
                                }
                            }else{// fim numero de serie
                                Toast.makeText(this, "Informe o numero de serie da moto!", Toast.LENGTH_LONG).show();
                            }
                        }else{//fim CNH
                            Toast.makeText(this, "Informe o sua cnh!", Toast.LENGTH_LONG).show();
                        }
                    }else{// fim senha mototaxi
                        Toast.makeText(this, "Informe o sua senha!", Toast.LENGTH_LONG).show();
                    }
                }else{//fim email mototaxi
                    Toast.makeText(this, "Informe o seu e-mail!", Toast.LENGTH_LONG).show();
                }
            }else{ // fim contato mototaxi
                Toast.makeText(this, "Informe o seu contato!", Toast.LENGTH_LONG).show();
            }
        }else{//fim nome mototaxi
            Toast.makeText(this, "Informe o seu nome!", Toast.LENGTH_LONG).show();
        }
    }

    private void criarContaFirebase(String email, String senha){
        mAuth.createUserWithEmailAndPassword(
                email, senha).addOnCompleteListener(task -> {

            if(task.isSuccessful()){
                finish();
                startActivity(new Intent(this, MainActivity.class));
                Toast.makeText(this, "Conta Criada Com Sucesso!", Toast.LENGTH_LONG).show();
            }else{
                binding.progressBar2.setVisibility(View.GONE);
                Toast.makeText(this, "Ocorreu um erro", Toast.LENGTH_LONG).show();
            }
        });
    }
}