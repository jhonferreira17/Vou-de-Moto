package com.example.voudemotooficial.Activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.voudemotooficial.databinding.ActivityCadastroPassageiroBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CadastroPassageiroActivity extends AppCompatActivity {

    private ActivityCadastroPassageiroBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCadastroPassageiroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        binding.btnCriarConta.setOnClickListener(v -> validarDados());
    }

    private void validarDados() {
        String nome = binding.editNomePassageiro.getText().toString().trim();
        String contato = binding.editContatoPassageiro.getText().toString().trim();
        String email = binding.editEmailPassageiro.getText().toString().trim();
        String senha = binding.editSenhaPassageiro.getText().toString().trim();

        if(!nome.isEmpty()){
            if(!contato.isEmpty()){
                if(!email.isEmpty()){
                    if(!senha.isEmpty()){
                        binding.progressBar.setVisibility(View.VISIBLE);
                        criarContaFirebase(email, senha);
                    }else{
                        Toast.makeText(this, "Informe a sua senha", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(this, "Informe o seu e-mail!", Toast.LENGTH_LONG).show();
                }
            }else{
                Toast.makeText(this, "Informe o seu contato", Toast.LENGTH_LONG).show();
            }
        }else{
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
                        binding.progressBar.setVisibility(View.GONE);
                        Toast.makeText(this, "Ocorreu um erro", Toast.LENGTH_LONG).show();
                    }
                });
    }

}
    