package com.example.voudemotooficial.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.voudemotooficial.Config.Config;
import com.example.voudemotooficial.Model.Usuario;
import com.example.voudemotooficial.R;
import com.example.voudemotooficial.databinding.ActivityLoginBinding;
import com.example.voudemotooficial.databinding.ActivityLoginMototaxistaBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginMototaxistaActivity extends AppCompatActivity {
    private ActivityLoginMototaxistaBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseAuth autenticacao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginMototaxistaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        autenticacao = Config.getFirebaseAutenticacao();
        autenticacao.signOut();
        mAuth = FirebaseAuth.getInstance();

        //Configurando Onclick dos Button
        binding.btnLoginMototaxista.setOnClickListener(v -> validarDados());

        binding.textRecuperarConta.setOnClickListener(v ->
                startActivity(new Intent(this, RecuperarContaActivity.class)));

        binding.textCadastroMototaxista.setOnClickListener(v ->
                startActivity(new Intent(this,CadastroMototaxiActivity.class)));
    }


    private void validarDados() {
        String email = binding.editEmailMototaxista.getText().toString().trim();
        String senha = binding.editSenhaMototaxista.getText().toString().trim();

        if(!email.isEmpty()){
            if(!senha.isEmpty()){
                binding.progressBarMototaxista.setVisibility(View.VISIBLE);
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
        mAuth = Config.getFirebaseAutenticacao();
        mAuth.signInWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                finish();
                startActivity(new Intent(LoginMototaxistaActivity.this, MapsActivityMototaxista.class));
            }else{
                binding.progressBarMototaxista.setVisibility(View.GONE);
                String excecao = "";
                try {
                    throw task.getException();
                }catch ( FirebaseAuthInvalidUserException e ) {
                    excecao = "Usuário não está cadastrado.";
                }catch ( FirebaseAuthInvalidCredentialsException e ){
                    excecao = "E-mail e senha não correspondem a um usuário cadastrado";
                }catch (Exception e){
                    excecao = "Erro ao logar usuário: "  + e.getMessage();
                    e.printStackTrace();
                }
                Toast.makeText(LoginMototaxistaActivity.this, excecao, Toast.LENGTH_LONG).show();
            }
        });
    }
}
