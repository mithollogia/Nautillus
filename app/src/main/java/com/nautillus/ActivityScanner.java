package com.nautillus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ActivityScanner extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.btScan);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.btHome:
                        startActivity(new Intent(getApplicationContext(), ActivityScanner.class));
                        overridePendingTransition(0,0);
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

        final Activity activity = this;

        IntentIntegrator integrator = new IntentIntegrator(activity);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("LOCALIZANDO QR-CODE");
        integrator.setBarcodeImageEnabled(true);
        integrator.setBeepEnabled(false);
        integrator.setCameraId(0);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null){
            if (result.getContents() != null){
                Intent intent = new Intent(ActivityScanner.this, ActivityHome.class);

                Bundle dados = new Bundle();
                dados.putString("codigo", result.getContents().toString());

                intent.putExtras(dados);
                startActivity(intent);
            }else{
                Toast.makeText(getApplicationContext(),"Não foi possivel realizar a leitura!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ActivityScanner.this, ActivityHome.class);
                startActivity(intent);
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
