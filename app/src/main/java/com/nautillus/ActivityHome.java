package com.nautillus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class ActivityHome extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ClipboardManager clipboardManager;
    ImageView qrnautilus;
    EditText conteudo;
    Button buton_go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        qrnautilus  = (ImageView) findViewById(R.id.viewQrCode);
        conteudo = (EditText) findViewById(R.id.resultado);
        buton_go = (Button) findViewById(R.id.buton_go);

        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        if(conteudo.getText().toString().isEmpty()) {
            conteudo.setVisibility(View.INVISIBLE);
            buton_go.setVisibility(View.INVISIBLE);
        }

        Intent intent = getIntent();
        if (intent != null){
            Bundle params = intent.getExtras();
            if (params != null){
                conteudo.setVisibility(View.VISIBLE);
                buton_go.setVisibility(View.VISIBLE);
                
                conteudo.setText(params.getString("codigo"));

                Writer multiFormatWriter = new MultiFormatWriter();
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(conteudo.getText().toString(),
                            BarcodeFormat.QR_CODE, 200,200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    qrnautilus.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        }

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.btHome);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.btHome:
                        startActivity(new Intent(getApplicationContext(), ActivityHome.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.btScan:
                        startActivity(new Intent(getApplicationContext(), ActivityScanner.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.btNew:
                        Intent intent = new Intent(ActivityHome.this, ActivityCreate.class);
                            if(!conteudo.getText().toString().isEmpty()){
                                Bundle dados = new Bundle();
                                dados.putString("codigo", conteudo.getText().toString());
                                intent.putExtras(dados);
                            }
                        startActivity(intent);

                        return true;
                }
                return false;
            }
        });
    }
}
