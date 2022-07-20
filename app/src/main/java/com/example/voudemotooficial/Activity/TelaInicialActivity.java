package com.example.voudemotooficial.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.voudemotooficial.R;
import com.example.voudemotooficial.databinding.ActivityTelaInicialBinding;

public class TelaInicialActivity extends AppCompatActivity {
    private ActivityTelaInicialBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTelaInicialBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bntPassageiro.setOnClickListener(v ->
                startActivity(new Intent(TelaInicialActivity.this, LoginActivity.class)));
    }
    //fazer para o botão de mototaxista
}