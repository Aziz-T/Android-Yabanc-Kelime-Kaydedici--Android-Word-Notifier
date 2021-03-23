package com.k.kelimekaydedici;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestActivity extends AppCompatActivity {
    private List<KelimeModel> kelimeModelList;
    private String ata;
    private Button buton1, buton2,buton3,buton4, nextButon;
    private TextView textView;
    private LinearLayout linearLayout;
    private int position=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ata=getIntent().getStringExtra("ata");
        loadKelimeKlasorData();
        textView=findViewById(R.id.soruText);
        buton1=findViewById(R.id.buton1);
        buton2=findViewById(R.id.buton2);
        buton3=findViewById(R.id.buton3);
        buton4=findViewById(R.id.buton4);
        nextButon=findViewById(R.id.ileriButon);
        linearLayout=findViewById(R.id.lineer);
        getKelime(position);
        calistir();

        nextButon.setEnabled(false);
        nextButon.setAlpha(0.7f);


        nextButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position++;
                if(position==kelimeModelList.size()){
                    Intent intent = new Intent(TestActivity.this,SkorActivity.class);
                    startActivity(intent);
                }
                getKelime(position);
                butondegistir(false);
            }
        });


    }

    private void butondegistir(Boolean durum) {

            for(int i = 0 ; i< linearLayout.getChildCount();i++) {
                if(durum){
                    linearLayout.getChildAt(i).setEnabled(false);
                    nextButon.setAlpha(1);
                    nextButon.setEnabled(true);
                }else{
                    linearLayout.getChildAt(i).setEnabled(true);
                    linearLayout.getChildAt(i).setBackground(ContextCompat.getDrawable(this,R.drawable.buton_back));
                    nextButon.setAlpha(0.7f);
                    nextButon.setEnabled(false);
                }
            }


    }

    private void calistir() {
        for(int i = 0 ; i< linearLayout.getChildCount();i++) {
            linearLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(((Button)view).getText().toString().equals(kelimeModelList.get(position).getCevirisi())){
                        ((Button)view).setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buton_back_doru));
                        butondegistir(true);
                    }else {
                        ((Button)view).setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.buton_back_yanlis));
                        butondegistir(true);
                    }
                }
            });
        }
    }


    private void getKelime(int position) {
        textView.setText(kelimeModelList.get(position).getKelime());
        int durum;
        Random rnd = new Random();
        durum=rnd.nextInt(5);
        if(durum==1) {
            buton1.setText(kelimeModelList.get(position).getCevirisi());
            buton2.setText(kelimeModelList.get(durum=rnd.nextInt(kelimeModelList.size())).getCevirisi());
            buton3.setText(kelimeModelList.get(rnd.nextInt(kelimeModelList.size()-1)).getCevirisi());
            buton4.setText(kelimeModelList.get(rnd.nextInt(kelimeModelList.size()-2)).getCevirisi());
        }
        if(durum==2){
            buton1.setText(kelimeModelList.get(rnd.nextInt(kelimeModelList.size())).getCevirisi());
            buton2.setText(kelimeModelList.get(position).getCevirisi());
            buton3.setText(kelimeModelList.get(rnd.nextInt(kelimeModelList.size()-1)).getCevirisi());
            buton4.setText(kelimeModelList.get(rnd.nextInt(kelimeModelList.size()-2)).getCevirisi());
        }
        if(durum==3){
            buton1.setText(kelimeModelList.get(rnd.nextInt(kelimeModelList.size())).getCevirisi());
            buton2.setText(kelimeModelList.get(rnd.nextInt(kelimeModelList.size()-1)).getCevirisi());
            buton3.setText(kelimeModelList.get(position).getCevirisi());
            buton4.setText(kelimeModelList.get(rnd.nextInt(kelimeModelList.size()-2)).getCevirisi());
        }
        if(durum==4){
            buton1.setText(kelimeModelList.get(rnd.nextInt(kelimeModelList.size())).getCevirisi());
            buton2.setText(kelimeModelList.get(rnd.nextInt(kelimeModelList.size()-1)).getCevirisi());
            buton3.setText(kelimeModelList.get(rnd.nextInt(kelimeModelList.size()-2)).getCevirisi());
            buton4.setText(kelimeModelList.get(position).getCevirisi());
        }


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
}
