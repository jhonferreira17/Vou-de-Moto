package com.example.voudemotooficial.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.voudemotooficial.R;
import com.example.voudemotooficial.databinding.ActivityRecuperarContaBinding;
import com.google.firebase.auth.FirebaseAuth;

public class RecuperarContaActivity extends AppCompatActivity {
    private ActivityRecuperarContaBinding binding;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecuperarContaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        binding.btnRecuperaConta.setOnClickListener( v -> validarDados());
    }

    private void validarDados() {
        String email = binding.editEmailPassageiro.getText().toString().trim();

        if(!email.isEmpty()){
            binding.progressBar.setVisibility(View.VISIBLE);
            recuperarContaFirebase(email);
        }else{
            Toast.makeText(this, "Informe o seu e-mail!", Toast.LENGTH_LONG).show();
        }
    }

    private void recuperarContaFirebase(String email){
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                finish();
                Toast.makeText(this, "Já pode verificar seu e-mail!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this,LoginActivity.class));
            }else{
                Toast.makeText(this, "Ocorreu um erro", Toast.LENGTH_LONG).show();
            }
            binding.progressBar.setVisibility(View.GONE);
        });
    }

}