package com.k.kelimekaydedici;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements KelimeListener{
    public static final int REQUEST_CODE=0;
    public static final int REQUEST_CODE_DUZENLE=1;

    private RecyclerView recyclerView;
    private List<KelimeModel> kelimeModels;
    private FloatingActionButton ekleButton;
    private KelimeModel kelimeModel;
    private KelimeListAdapter kelimeListAdapter;

    private int position;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.kelimeRec);
        ekleButton = findViewById(R.id.ekleButon);
        loadDataKelime();

        drawerLayout=findViewById(R.id.drawer);
        drawerToggle= new ActionBarDrawerToggle(this,drawerLayout,R.string.Ac,R.string.Kapa);
        drawerToggle.setDrawerIndicatorEnabled(true);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSorular(kelimeModels,kelimeModel,0);


        ekleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,EkleActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
        NavigationView nav_view = findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id== R.id.favoriler){

                }if(id== R.id.kategoriler){

                }
                return true;
            }
        });


    }
    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    private void getSorular(List<KelimeModel> kelimeModels, KelimeModel kelimeModel, int position) {
        if(kelimeModel!=null){
            if(position==kelimeModels.size()+1){
                kelimeModels.add(kelimeModel);
            }else{
                kelimeModels.get(position).setKelime(kelimeModel.getKelime());
                kelimeModels.get(position).setCevirisi(kelimeModel.getCevirisi());
                kelimeListAdapter.notifyItemChanged(position);
                kelimeListAdapter.notifyDataSetChanged();
            }

        }
        kelimeListAdapter = new KelimeListAdapter(kelimeModels,this);
        RecyclerView.LayoutManager gridlayout = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridlayout);
        recyclerView.setAdapter(kelimeListAdapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveDataKelime();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE) {
                if (data != null){
                    kelimeModel= new KelimeModel(data.getStringExtra("kelime"),data.getStringExtra("cevirisi"));
                    getSorular(kelimeModels,kelimeModel,kelimeModels.size()+1);
                }
            }if (requestCode == REQUEST_CODE_DUZENLE) {
                if (data != null){
                    position = data.getIntExtra("position",0);
                    kelimeModel= new KelimeModel(data.getStringExtra("degisenkelime"),data.getStringExtra("degisenceviri"));
                    getSorular(kelimeModels,kelimeModel,position);
                }
            }
        }
    }

    private void saveDataKelime() {
        SharedPreferences sharedPreferences = getSharedPreferences("kelime",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(kelimeModels);
        editor.putString("kelime list",json);
        editor.apply();
    }
    private void loadDataKelime() {
        SharedPreferences sharedPreferences = getSharedPreferences("kelime",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("kelime list",null);
        Type type= new TypeToken<ArrayList<KelimeModel>>(){}.getType();
        kelimeModels=gson.fromJson(json,type);
        if (kelimeModels==null){
            kelimeModels=new ArrayList<>();
        }
    }

    @Override
    public void onKelimeListener(KelimeModel kelimeModel, int position) {
        Intent intent = new Intent(MainActivity.this, KelimeActivity.class);
        intent.putExtra("kelime",kelimeModel.getKelime());
        intent.putExtra("ceviri",kelimeModel.getCevirisi());
        intent.putExtra("position",position);
        startActivityForResult(intent,REQUEST_CODE_DUZENLE);
    }
}
