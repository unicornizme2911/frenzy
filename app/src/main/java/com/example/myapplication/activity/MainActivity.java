package com.example.myapplication.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.myapplication.R;
import com.example.myapplication.authentication.LoginActivity;
import com.example.myapplication.authentication.RegisterActivity;
import com.example.myapplication.fragments.DetailUserFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private int i = 1;
    private static final String[] PERMISSIONS = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Log.d(TAG, "onCreate: "+ String.valueOf(i));
        if (savedInstanceState == null && i ==1) {
            i++;
            Intent sw = new Intent(this, LoginActivity.class);
            startActivity(sw);
        }
        for(String permission : PERMISSIONS) {
            if (checkSelfPermission(permission) != getPackageManager().PERMISSION_GRANTED) {
                requestPermissions(PERMISSIONS, 1);
                break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode != 1) {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            return;
        }
        for(int grantResult : grantResults) {
            if(grantResult != getPackageManager().PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }
}