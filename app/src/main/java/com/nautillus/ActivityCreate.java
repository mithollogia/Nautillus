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
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class ActivityCreate extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ClipboardManager clipboardManager;
    ImageView qrnautilus;
    Button newQrCode;
    EditText conteudo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        conteudo    = (EditText) findViewById(R.id.entrada);
        newQrCode   = (Button) findViewById(R.id.btNewCode);
        qrnautilus  = (ImageView) findViewById(R.id.viewQrCode);

        Intent intent = getIntent();
        if (intent != null){
            Bundle params = intent.getExtras();
            if (params != null){
                conteudo.setText(params.getString("codigo"));
            }
        }

        newQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!conteudo.getText().toString().isEmpty()) {
                    Writer multiFormatWriter = new MultiFormatWriter();
                    try {
                        BitMatrix bitMatrix = multiFormatWriter.encode(conteudo.getText().toString(),
                                BarcodeFormat.QR_CODE, 200, 200);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                        qrnautilus.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(getApplicationContext(),"Digite o texto a ser gerado!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.btNew);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.btHome:
                        Intent intent = new Intent(ActivityCreate.this, ActivityHome.class);
                        overridePendingTransition(0,0);
                        if(!conteudo.getText().toString().isEmpty()){

                            Bundle dados = new Bundle();
                            dados.putString("codigo", conteudo.getText().toString());

                            intent.putExtras(dados);
                        }
                        startActivity(intent);
                        return true;

                    case R.id.btScan:
                        startActivity(new Intent(getApplicationContext(), ActivityScanner.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.btNew:
                        startActivity(new Intent(getApplicationContext(), ActivityCreate.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}
