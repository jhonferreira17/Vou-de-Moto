package com.example.voudemotooficial.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.example.voudemotooficial.Adapter.RequisicoesAdapter;
import com.example.voudemotooficial.Config.Config;
import com.example.voudemotooficial.Helper.UsuarioFirebase;
import com.example.voudemotooficial.Model.Requisicao;
import com.example.voudemotooficial.Model.Usuario;
import com.example.voudemotooficial.R;
import com.example.voudemotooficial.databinding.ActivityRequisicoesBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RequisicoesActivity extends AppCompatActivity {
    private FirebaseAuth autenticacao;
    private DatabaseReference firebaseRef;
    private ActivityRequisicoesBinding binding;
    private RequisicoesAdapter adapter;
    private List<Requisicao> listaRequisicoes = new ArrayList<>();
    private Usuario motorista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRequisicoesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        inicializarComponentes();
    }

    private void inicializarComponentes(){
        motorista = UsuarioFirebase.getDadosUsuarioLogado();

        //configurações iniciais
        autenticacao = Config.getFirebaseAutenticacao();
        firebaseRef = Config.getFirebaseDatabase();

        //configurar RecyclerView
        adapter = new RequisicoesAdapter(listaRequisicoes, getApplicationContext(), motorista);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        binding.recyclerRequisicoes.setLayoutManager(layoutManager);
        binding.recyclerRequisicoes.setHasFixedSize(true);
        binding.recyclerRequisicoes.setAdapter(adapter);
        recuperarRequisicoes();
    }
    private void recuperarRequisicoes(){
        DatabaseReference requisicoes = firebaseRef.child("requisicoes");
        Query requicaoPesquisa = requisicoes.orderByChild("status")
                .equalTo(Requisicao.STATUS_AGUARDANDO);
        requicaoPesquisa.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getChildrenCount() > 0){
                    binding.textResultado.setVisibility(View.GONE);
                    binding.recyclerRequisicoes.setVisibility(View.VISIBLE);
                }else {
                    binding.textResultado.setVisibility(View.VISIBLE);
                    binding.recyclerRequisicoes.setVisibility(View.GONE);
                }
                for(DataSnapshot ds: snapshot.getChildren()){
                    Requisicao requisicao = ds.getValue(Requisicao.class);
                    listaRequisicoes.add(requisicao);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}