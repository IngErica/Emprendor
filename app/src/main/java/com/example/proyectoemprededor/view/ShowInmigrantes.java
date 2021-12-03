package com.example.proyectoemprededor.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toolbar;

import com.example.proyectoemprededor.R;
import com.example.proyectoemprededor.model.inmigrantes;
import com.example.proyectoemprededor.viewholderadapter.InmigrantesAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.firebase.database.FirebaseDatabase;

public class ShowInmigrantes extends AppCompatActivity {

    private RecyclerView recyclerView;
    private InmigrantesAdapter inmigrantesAdapter;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_inmigrantes);

        progressBar = findViewById(R.id.spin_kit);
        Circle Circle = new Circle();
        progressBar.setIndeterminateDrawable(Circle);
        progressBar.setVisibility(View.VISIBLE);

        recyclerView = findViewById(R.id.recview);

        recyclerView.setLayoutManager(new CustomLinearLayoutManager(this));

        // consultar la base de datos de firebase inmigrantes
        FirebaseRecyclerOptions<inmigrantes> options =
                new FirebaseRecyclerOptions.Builder<inmigrantes>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("inmigrantes"), inmigrantes.class)
                        .build();

        Log.d("Options"," data : "+options);

        //lo envia a la adapter viewholder
        inmigrantesAdapter = new InmigrantesAdapter(options);
        //carga la informaciòn en lista
        recyclerView.setAdapter(inmigrantesAdapter);
        progressBar.setVisibility(View.GONE);
    }

    //captura el boton de atras
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        finish();
    }


    //actualizar cualquer cambio en la base de datos
    @Override
    protected void onStart() {
        super.onStart();
        //progressBar.setVisibility(View.VISIBLE);
        inmigrantesAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        inmigrantesAdapter.stopListening();
       // progressBar.setVisibility(View.GONE);
    }

    public class CustomLinearLayoutManager extends LinearLayoutManager {
        public CustomLinearLayoutManager(Context context) {
            super(context);
        }

        public CustomLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        public CustomLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        //Generate constructors

        @Override
        public boolean supportsPredictiveItemAnimations() {
            return false;
        }
    }

}