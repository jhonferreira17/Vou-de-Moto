package com.example.voudemotooficial.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.voudemotooficial.Model.Usuario;
import com.example.voudemotooficial.databinding.ActivityCadastroMototaxiBinding;
import com.example.voudemotooficial.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        binding.btnLogin.setOnClickListener(v -> validarDados());

        binding.textRecuperarConta.setOnClickListener(v ->
                startActivity(new Intent(this, RecuperarContaActivity.class)));
        binding.textCadastro.setOnClickListener(v ->
                startActivity(new Intent(this,CadastroPassageiroActivity.class)));

        binding.textAbrirCadastroMototaxi.setOnClickListener(v ->
         startActivity(new Intent(this, CadastroMototaxiActivity.class)));
    }
//Validadndo os dados do passageiro
    private void validarDados() {
        String email = binding.editEmailPassageiro.getText().toString().trim();
        String senha = binding.editSenhaPassageiro.getText().toString().trim();

        if(!email.isEmpty()){
            if(!senha.isEmpty()){
                binding.progressBar.setVisibility(View.VISIBLE);
                Usuario usuario = new Usuario();
                usuario.setEmail( email);
                usuario.setSenha( senha);
               loginFirebase(usuario);
            }else{
                Toast.makeText(this, "Informe a sua senha", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this, "Informe o seu e-mail!", Toast.LENGTH_LONG).show();
        }
    }

    private void loginFirebase(Usuario usuario){
        mAuth.signInWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(task -> {

            if(task.isSuccessful()){
                finish();
                startActivity(new Intent(this, MapsActivityPassageiro.class));
            }else{
                binding.progressBar.setVisibility(View.GONE);
                String excecao = "";
                try {
                    throw task.getException();
                }catch ( FirebaseAuthInvalidUserException e ) {
                    excecao = "Usuário não está cadastrado.";
                }catch ( FirebaseAuthInvalidCredentialsException e ){
                    excecao = "E-mail e senha não correspondem a um usuário cadastrado";
                }catch (Exception e){
                    excecao = "Erro ao cadastrar usuário: "  + e.getMessage();
                    e.printStackTrace();
                }
                Toast.makeText(LoginActivity.this, excecao, Toast.LENGTH_LONG).show();
            }
        });
    }
}