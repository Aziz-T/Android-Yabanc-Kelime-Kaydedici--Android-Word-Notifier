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

public class TurActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton ekleButton;
    private List<TurModel> turModelList;
    private Toolbar toolbar;
    private String ata;
    private ImageView imagePlus;
    private TextView dilEkle;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tur);
        recyclerView=findViewById(R.id.turRec);
        ekleButton=findViewById(R.id.turButon);


        ata= getIntent().getStringExtra("ata");

        toolbar = findViewById(R.id.tolbar);
        dilEkle=findViewById(R.id.dilEkle);
        imagePlus=findViewById(R.id.imageplus);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Kelime Türleri");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        loadData(ata);

        TurAdapter turAdapter = new TurAdapter(turModelList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(turAdapter);



        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_item);

        if(turModelList.size()==0){
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


        final EditText editText = dialog.findViewById(R.id.editText);
        Button cancel = dialog.findViewById(R.id.cancelButton);
        Button ok = dialog.findViewById(R.id.okButton);
        TextView diltext=dialog.findViewById(R.id.dilText);
        diltext.setText("Kelime Türü Ekle");
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().toString().trim().equals("")){
                    dialog.cancel();
                } else {
                    turModelList.add(new TurModel(editText.getText().toString(),ata));
                    editText.setText("");
                    dialog.cancel();
                }
                if(turModelList!=null){
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
    protected void onPause(){
        super.onPause();
        saveData();
    }
    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("TUR",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(turModelList);
        editor.putString(ata,json);
        editor.apply();
    }
    private void loadData(String ata1) {
        SharedPreferences sharedPreferences = getSharedPreferences("TUR",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(ata1,null);
        Type type= new TypeToken<ArrayList<TurModel>>(){}.getType();
        turModelList=gson.fromJson(json,type);
        if (turModelList==null){
            turModelList=new ArrayList<>();
        }
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
