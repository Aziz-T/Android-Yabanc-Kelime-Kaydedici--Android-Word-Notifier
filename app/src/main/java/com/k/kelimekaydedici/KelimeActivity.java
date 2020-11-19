package com.k.kelimekaydedici;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class KelimeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<KelimeModel> kelimeModels;
    private FloatingActionButton ekleButton;
    private Toolbar toolbar;
    private String ata;
    private ImageView imagePlus;
    private TextView dilEkle;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelime);
        recyclerView=findViewById(R.id.kelimeRec);
        toolbar = findViewById(R.id.toolBar);
        ekleButton=findViewById(R.id.kelimeEkleButon);
        dilEkle=findViewById(R.id.dilEkle);
        imagePlus=findViewById(R.id.imageplus);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Kelimeler");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        ata=getIntent().getStringExtra("ata");
        loadData(ata);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);


        final KelimeAdapter kelimeAdapter = new KelimeAdapter(kelimeModels);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(kelimeAdapter);

        if(kelimeModels.size()==0){
            imagePlus.setAlpha(1f);
            imagePlus.setEnabled(true);
            dilEkle.setAlpha(1f);
            dilEkle.setEnabled(true);
        }else{
            imagePlus.setAlpha(0f);
            imagePlus.setEnabled(false);
            dilEkle.setAlpha(0f);
            dilEkle.setEnabled(false);
        }


        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.kelime_dialog);



        final EditText kelimeEdit = dialog.findViewById(R.id.kelimeEdit);
        final EditText anlamEdit = dialog.findViewById(R.id.anlamEdit);

        Button cancel = dialog.findViewById(R.id.cancelButon);
        Button ok = dialog.findViewById(R.id.okButon);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(kelimeEdit.getText().toString().trim().equals("")){
                    dialog.cancel();
                } else {
                   kelimeModels.add(new KelimeModel(kelimeEdit.getText().toString(),anlamEdit.getText().toString()));
                    kelimeEdit.setText("");
                    anlamEdit.setText("");
                    dialog.cancel();
                }
                if(kelimeModels!=null){
                    imagePlus.setAlpha(0f);
                    imagePlus.setEnabled(false);
                    dilEkle.setAlpha(0f);
                    dilEkle.setEnabled(false);
                }else{
                    imagePlus.setAlpha(1f);
                    imagePlus.setEnabled(true);
                    dilEkle.setAlpha(1f);
                    dilEkle.setEnabled(true);
                }
                saveData();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        ekleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("KELIME",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(kelimeModels);
        editor.putString(ata,json);
        editor.apply();
    }

    private void loadData(String ata1) {
        SharedPreferences sharedPreferences = getSharedPreferences("KELIME",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(ata1,null);
        Type type= new TypeToken<ArrayList<KelimeModel>>(){}.getType();
        kelimeModels=gson.fromJson(json,type);
        if (kelimeModels==null){
            kelimeModels=new ArrayList<>();
        }
    }
    protected void onPause(){
        super.onPause();
        saveData();
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
