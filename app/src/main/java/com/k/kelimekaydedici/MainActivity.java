package com.k.kelimekaydedici;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
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
import android.widget.ImageButton;
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

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private FloatingActionButton ekleButton;
    private List<DilModel> dilModelList;
    private Toolbar toolbar;
    private ImageView imagePlus;
    private TextView dilEkle;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.dilRec);
        ekleButton=findViewById(R.id.ekleButon);
        dilEkle=findViewById(R.id.dilEkle);
        imagePlus=findViewById(R.id.imageplus);
        toolbar = findViewById(R.id.toolbar);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Diller");

        loadData();

        final DilAdapter dilAdapter = new DilAdapter(dilModelList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(dilAdapter);

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_ACTION_BAR);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_item);

        final EditText editText = dialog.findViewById(R.id.editText);
        Button cancel = dialog.findViewById(R.id.cancelButton);
        Button ok = dialog.findViewById(R.id.okButton);

        if(dilModelList.size()==0){
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
        TextView diltext=dialog.findViewById(R.id.dilText);
        diltext.setText("Dil Ekle");
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().toString().trim().equals("")){
                    dialog.cancel();
                } else {
                    dilModelList.add(new DilModel(editText.getText().toString()));
                    editText.setText("");
                    dialog.cancel();
                }
                if(!dilModelList.equals(null)){
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
        SharedPreferences sharedPreferences = getSharedPreferences("sh",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(dilModelList);
        editor.putString("task list",json);
        editor.apply();
    }
    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("sh",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list",null);
        Type type= new TypeToken<ArrayList<DilModel>>(){}.getType();
        dilModelList=gson.fromJson(json,type);
        if (dilModelList==null){
            dilModelList=new ArrayList<>();
        }
    }

}
