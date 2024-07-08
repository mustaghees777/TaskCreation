package com.example.android.taskcreation;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.android.taskcreation.HomeActivity.HomebuttonActivity;
import com.example.android.taskcreation.Office.OfficebuttonActivity;
import com.example.android.taskcreation.Uni.UnibuttonActivity;

public class MainActivity extends AppCompatActivity {
ImageView unibutton,officebutton,homebutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        unibutton = findViewById(R.id.unibutton);
        officebutton = findViewById(R.id.officebutton);
        homebutton = findViewById(R.id.homebutton);

        unibutton.setOnClickListener(v -> {
            startActivity(new Intent(this, UnibuttonActivity.class));
        });
        officebutton.setOnClickListener(v -> {
            startActivity(new Intent(this, OfficebuttonActivity.class));
        });
        homebutton.setOnClickListener(v -> {
            startActivity(new Intent(this, HomebuttonActivity.class));
        });

    }
}