package com.k.kelimekaydedici;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class KelimelerActivity extends AppCompatActivity implements KelimeListener {

    private List<KelimeModel> kelimeModelList;
    private RecyclerView recyclerView;
    private String ata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelimeler);
        recyclerView=findViewById(R.id.kelimelerRec);
        ata=getIntent().getStringExtra("ata");

        loadKelimeKlasorData();

        KelimeListAdapter kelimeListAdapter = new KelimeListAdapter(kelimeModelList,this);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(kelimeListAdapter);
        Toast.makeText(this,kelimeModelList.size()+ ata +"", Toast.LENGTH_SHORT).show();

    }
    private void saveKelimeKlasorData() {
        SharedPreferences sharedPreferences = getSharedPreferences("kelimelik",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(kelimeModelList);
        editor.putString(ata,json);
        editor.apply();
    }
    private void loadKelimeKlasorData() {
        SharedPreferences sharedPreferences = getSharedPreferences("kelimelik",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(ata,null);
        Type type= new TypeToken<ArrayList<KelimeModel>>(){}.getType();
        kelimeModelList=gson.fromJson(json,type);
        if (kelimeModelList==null){
            kelimeModelList=new ArrayList<>();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.kelimeler_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.testYap){
            Intent intent = new Intent(KelimelerActivity.this,TestActivity.class);
            intent.putExtra("ata",ata);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onKelimeListener(KelimeModel kelimeModel, int position) {

    }
}
