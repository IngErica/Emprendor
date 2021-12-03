package com.example.proyectoemprededor.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectoemprededor.R;
import com.example.proyectoemprededor.model.Global;
import com.example.proyectoemprededor.model.persona;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity {

    private TextView name, correo;
    private CircleImageView foto;
    private CardView btnStudents, btnStudentsprogreso;
    private FloatingActionButton fab;
    private Global global;
    private persona persona;
    private FirebaseAuth auth;
    DatabaseReference databaseReference;
    GoogleSignInAccount signInAccount;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        name = findViewById(R.id.user_name);
        correo = findViewById(R.id.user_id);
        foto = findViewById(R.id.user_photo);
        btnStudents = findViewById(R.id.btnStudents);

        btnStudentsprogreso = findViewById(R.id.btnStudentsprogreso);

        progressBar = findViewById(R.id.spin_kit);
        // se instancia el metodo circle para el progrebar
        Circle Circle = new Circle();
        progressBar.setIndeterminateDrawable(Circle);
        progressBar.setVisibility(View.GONE);

        //inicar firebase
        inicializarFirebase();

        //validar el usuario logeoda
        signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if(signInAccount != null){
            global = (Global)getApplicationContext();
            global.setNombre(signInAccount.getDisplayName());
            global.setCorreo(signInAccount.getEmail());
            global.setFoto(signInAccount.getPhotoUrl().toString());
            global.setId(signInAccount.getId());
            // consulta si la persona existe
            databaseReference.child("Persona").child(signInAccount.getId()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if(dataSnapshot.exists()){
                        // valida la unidad de progreso
                        final String estado = dataSnapshot.child("estadoUnidad").getValue(String.class);
                        if(estado!=null) {
                            global.setEstadoUnidad(estado);
                        }else{
                            global.setEstadoUnidad("0");
                        }
                    }else{
                        //crear el usuario y iniciar la unidad de progreso en cero
                        global.setEstadoUnidad("0");
                        persona = new persona(global.getNombre(), global.getCorreo(), global.getFoto(), global.getId(), global.getEstadoUnidad());
                        databaseReference.child("Persona").child(persona.getId()).setValue(persona);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("Fallo la lectura: " + databaseError.getCode());

                }
            });

        }

        name.setText(global.getNombre());
        correo.setText(global.getCorreo());
        Picasso.with(this).load(global.getFoto()).into(foto);

        consultarFirebase();

        // boton de ver las unidades
        btnStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Ir a la otra actividad
                Intent intent = new Intent(getApplicationContext(), ShowInmigrantes.class);
                startActivity(intent);
            }
        });

        //boton de ver el pregreso
        btnStudentsprogreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainProgressBar.class);
                startActivity(intent);
            }
        });

        // Boton  de salir
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //salir de aplicacion y cerrar sesiòn
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


    }

    //actualizar automaticamente cuando hay un evento en la HomeActivity
    @Override
    protected void onStart() {
        super.onStart();
        consultarFirebase();
    }

    //no enviar de nuevo la consulta
    @Override
    protected void onStop() {
        super.onStop();
    }

    //deshabilitar el boton que hace click atras
    @Override
    public void onBackPressed() {

    }

    //metodo inicializa la base de datos firebase
    private void inicializarFirebase() {

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

    }

    //consulta la base de datos persona para obtener el progreso de la persona
    private void consultarFirebase(){
        progressBar.setVisibility(View.VISIBLE);
        databaseReference.child("Persona").child(signInAccount.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    final String estado = dataSnapshot.child("estadoUnidad").getValue(String.class);
                    if(estado!=null) {
                        global.setEstadoUnidad(estado);
                    }else{
                        global.setEstadoUnidad("0");
                    }
                }else{
                    global.setEstadoUnidad("0");
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Fallo la lectura: " + databaseError.getCode());

            }
        });
    }

}