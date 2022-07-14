package com.example.voudemotooficial.Activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.voudemotooficial.Config.Config;
import com.example.voudemotooficial.Model.Usuario;
import com.example.voudemotooficial.databinding.ActivityCadastroPassageiroBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

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
                        Usuario usuario = new Usuario();
                        usuario.setNome( nome );
                        usuario.setContato(contato);
                        usuario.setEmail( email);
                        usuario.setSenha( senha);
                        criarContaFirebase(usuario);
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

    private void criarContaFirebase(Usuario usuario){
        mAuth = Config.getFirebaseAutenticacao();
        mAuth.createUserWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()).addOnCompleteListener(task -> {

                    if(task.isSuccessful()){
                        finish();
                        startActivity(new Intent(this, MainActivity.class));
                        Toast.makeText(this, "Conta Criada Com Sucesso!", Toast.LENGTH_LONG).show();
                    }else{
                        binding.progressBar.setVisibility(View.GONE);

                        String excecao = "";
                        try {
                            throw task.getException();
                        }catch ( FirebaseAuthWeakPasswordException e){
                            excecao = "Digite uma senha mais forte!";
                        }catch ( FirebaseAuthInvalidCredentialsException e){
                            excecao= "Por favor, digite um e-mail válido";
                        }catch ( FirebaseAuthUserCollisionException e){
                            excecao = "Este conta já foi cadastrada";
                        }catch (Exception e){
                            excecao = "Erro ao cadastrar usuário: "  + e.getMessage();
                            e.printStackTrace();
                        }

                        Toast.makeText(CadastroPassageiroActivity.this,
                                excecao,
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

}
    