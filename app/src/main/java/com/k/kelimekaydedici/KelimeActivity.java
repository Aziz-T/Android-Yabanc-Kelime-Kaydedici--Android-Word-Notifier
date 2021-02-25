package com.k.kelimekaydedici;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class KelimeActivity extends AppCompatActivity {
    private EditText editKelime,editAnlam;
    private ImageButton imageFav;
    private TextView favtxt;

    private List<KelimeModel> kelimeModelList;
    private List<KelimeModel> kelimeModelFav;
 //   private List<KlasorModel> klasorModelList;
    private List<KelimeModel> kelimeModelKlasor;

    private KelimeModel kelimeModel;


    private String kelime, ceviri ;
    private int position;
    private int durum;
    private String ata;
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


}
