package com.k.kelimekaydedici;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class KelimeActivity extends AppCompatActivity implements KlasorListener {
    private EditText editKelime,editAnlam;
    private ImageButton imageFav;
    private TextView favtxt;

    private List<KelimeModel> kelimeModelList;
    private List<KelimeModel> kelimeModelFav;
    private List<KlasorModel> klasorModelList;
    private List<KelimeModel> kelimeModelKlasor;

    private KelimeModel kelimeModel;


    private String kelime, ceviri ;
    private int position;
    private int durum;
    private String yeniata;
    private boolean drm;

    private BottomSheetDialog dialog1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kelime);
        editKelime=findViewById(R.id.editKelime);
        editAnlam=findViewById(R.id.editAnlam);
        imageFav=findViewById(R.id.imageFav);
        favtxt=findViewById(R.id.favTxt);
        loadData();
        loadDataKlasor();



        kelime= getIntent().getStringExtra("kelime");
        ceviri=getIntent().getStringExtra("ceviri");
        position=getIntent().getIntExtra("position",0);

        editKelime.setText(kelime);
        editAnlam.setText(ceviri);

        kelimeModel=new KelimeModel(kelime,ceviri);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("degisenkelime",editKelime.getText().toString());
        intent.putExtra("degisenceviri",editAnlam.getText().toString());
        intent.putExtra("position",position);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.kelime_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.kategori){
            dialogAc();
        }
        return super.onOptionsItemSelected(item);

    }

    private void dialogAc() {
        dialogOlustur();
    }

    private void dialogOlustur() {
        dialog1 = new BottomSheetDialog(this);


        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setCancelable(true);
        dialog1.setContentView(R.layout.dialog_item);
        DialogAdapter adapter = new DialogAdapter(klasorModelList, (KlasorListener) this,"dialog",this);
        TextView olustur = (TextView) dialog1.findViewById(R.id.yeniOlustur);
        RecyclerView rec = (RecyclerView) dialog1.findViewById(R.id.klasorRec);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        rec.setLayoutManager(linearLayoutManager);
        rec.setAdapter(adapter);
        olustur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.cancel();
                ekleDialog();
            }
        });

        dialog1.show();
    }

    private void ekleDialog() {
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        //final Dialog dialog = new Dialog(this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.ekle_dialog_item);
        final EditText olustur = (EditText) dialog.findViewById(R.id.olusturEdit);
        Button cancel = (Button) dialog.findViewById(R.id.iptalButon);
        Button tamam = (Button) dialog.findViewById(R.id.ok_button);
        tamam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                klasorModelList.add(new KlasorModel(olustur.getText().toString()));
                yeniata=olustur.getText().toString();
                loadKelimeKlasorData();
                kelimeModelKlasor.add(new KelimeModel(editKelime.getText().toString(),editAnlam.getText().toString()));
                Toast.makeText(KelimeActivity.this, "Soru "+yeniata+" klasörüne eklendi.", Toast.LENGTH_SHORT).show();
                saveKelimeKlasorData();
                saveDataKlasor();
                dialog.cancel();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        dialog.show();
    }

    private void loadDataKlasor(){
        SharedPreferences sharedPreferences = getSharedPreferences("klasor",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("klasor list",null);
        Type type= new TypeToken<ArrayList<KlasorModel>>(){}.getType();
        klasorModelList=gson.fromJson(json,type);
        if (klasorModelList==null){
            klasorModelList=new ArrayList<>();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveData();
        saveKelimeKlasorData();
        saveDataKlasor();
    }

    private void saveDataKlasor() {
        SharedPreferences sharedPreferences = getSharedPreferences("klasor",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(klasorModelList);
        editor.putString("klasor list",json);
        editor.apply();
    }

    private void saveKelimeKlasorData() {
        SharedPreferences sharedPreferences = getSharedPreferences("kelimelik",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(kelimeModelKlasor);
        editor.putString(yeniata,json);
        editor.apply();
    }
    private void loadKelimeKlasorData() {
        SharedPreferences sharedPreferences = getSharedPreferences("kelimelik",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(yeniata,null);
        Type type= new TypeToken<ArrayList<KelimeModel>>(){}.getType();
        kelimeModelKlasor=gson.fromJson(json,type);
        if (kelimeModelKlasor==null){
            kelimeModelKlasor=new ArrayList<>();
        }
    }
    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("soru",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(kelimeModelList);
        editor.putString("soru list",json);
        editor.apply();
    }
    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("soru",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("soru list",null);
        Type type= new TypeToken<ArrayList<KelimeModel>>(){}.getType();
        kelimeModelList=gson.fromJson(json,type);
        if (kelimeModelList==null){
            kelimeModelList=new ArrayList<>();
        }
    }
    @Override
    public void onKlasorListener(KlasorModel klasorModel, int position) {
        yeniata=klasorModel.getIsim();
        loadKelimeKlasorData();
        Toast.makeText(this,"Soru "+ yeniata +" klasörüne eklendi.", Toast.LENGTH_SHORT).show();
        kelimeModelKlasor.add(new KelimeModel(editKelime.getText().toString(),editAnlam.getText().toString()));
        saveKelimeKlasorData();
        dialog1.cancel();
    }
}
