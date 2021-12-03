package com.example.proyectoemprededor.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectoemprededor.R;
import com.example.proyectoemprededor.model.Global;
import com.example.proyectoemprededor.model.inmigrantes;
import com.example.proyectoemprededor.model.persona;
import com.example.proyectoemprededor.viewholderadapter.inmigrantesContenidoAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import me.saket.bettermovementmethod.BetterLinkMovementMethod;

public class ShowDetail extends AppCompatActivity {

    private inmigrantes inmigrantes;
    private TextView titulocontenido, materialurl, actividadurl, documentourl;
    private RecyclerView recyclerViewContenido;
    private inmigrantesContenidoAdapter inmigrantesContenidoAdapter;
    private YouTubePlayerView youTubePlayerView;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        inicializarFirebase();
        //consulta la clase global
        Global global = (Global)getApplicationContext();
        //capturo el putextra de postion
        int position = getIntent().getExtras().getInt("position");

        //consulta la base de datos para obtener la unidad de progreso
        databaseReference.child("Persona").child(global.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    final String estado = dataSnapshot.child("estadoUnidad").getValue(String.class);
                    if(estado!=null) {
                        global.setEstadoUnidad(estado);
                    }else{
                    }
                   }else{
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Fallo la lectura: " + databaseError.getCode());

            }
        });

        //validar si el usuario puede ver la unidad
        //la unida debe ser mayor que la posisiòn
        if(position > Integer.parseInt(global.getEstadoUnidad())){
            // se crea un dialogo de alert
            AlertDialog.Builder mBuelder = new AlertDialog.Builder(this, R.style.fullscreenalert);
            View view =  getLayoutInflater().inflate(R.layout.full_alertdialogo, null);
            TextView salir =  view.findViewById(R.id.Mensaje);
            Button volver = view.findViewById(R.id.Volver);
            String unidad = "";
            if(Integer.parseInt(global.getEstadoUnidad())==0)
                unidad = "0";
            else
                unidad = String.valueOf((Integer.parseInt(global.getEstadoUnidad()))+1);
            salir.setText("Debes ver la Unidad " + unidad);
            mBuelder.setView(view);
            mBuelder.setCancelable(false);
            AlertDialog dialog = mBuelder.create();
            // activar el dialogo
            dialog.show();

            volver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), ShowInmigrantes.class);
                    startActivity(intent);
                    //oculatar la alerta de dialogo
                    dialog.dismiss();
                    finish();
                }
            });
        }else {
            //instancia la clase persona
            persona personaUnidad = new persona();
            personaUnidad.setId(global.getId());
            //actualizar el pregreso de persona
            if(Integer.parseInt(global.getEstadoUnidad()) == 0)
                databaseReference.child("Persona").child(personaUnidad.getId()).child("estadoUnidad").setValue("1");
            if(position >= Integer.parseInt(global.getEstadoUnidad())) {
                personaUnidad.setEstadoUnidad(String.valueOf(position+1));
                databaseReference.child("Persona").child(personaUnidad.getId()).child("estadoUnidad").setValue(personaUnidad.getEstadoUnidad());
            }

            setContentView(R.layout.activity_show_detail);
            youTubePlayerView = findViewById(R.id.youtube_player_view);
            recyclerViewContenido = findViewById(R.id.recviewcontenido);
            titulocontenido = findViewById(R.id.titleTextcontenido);
            materialurl = findViewById(R.id.materialurl);
            actividadurl = findViewById(R.id.actividaurl);
            documentourl = findViewById(R.id.documentourl);

            initData();
            initVideo();
            initRecyclerView();

            // clic de descarga del documento
            documentourl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(inmigrantes.getDocumentourl()));
                    startActivity(intent);
                }
            });

            //el boton de ver el link en el navegador actividad
            BetterLinkMovementMethod.linkify(Linkify.WEB_URLS, actividadurl)
                    .setOnLinkClickListener(new BetterLinkMovementMethod.OnLinkClickListener() {
                        @Override
                        public boolean onClick(TextView textView, String url) {
                            Toast.makeText(ShowDetail.this, "Website:" + url, Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    });

            //el boton de ver el link en el navegador material
            BetterLinkMovementMethod.linkify(Linkify.WEB_URLS, materialurl)
                    .setOnLinkClickListener(new BetterLinkMovementMethod.OnLinkClickListener() {
                        @Override
                        public boolean onClick(TextView textView, String url) {
                            Toast.makeText(ShowDetail.this, "Website:" + url, Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    });
        }

    }
    //opterner la informacion de inputText
    private void initData() {
        Intent inputText = getIntent();
        inmigrantes = (inmigrantes) inputText.getSerializableExtra("inmigrantes");
        titulocontenido.setText(inmigrantes.getTitulo());

        materialurl.setText(inmigrantes.getMaterialurl());
        actividadurl.setText(inmigrantes.getActividaurl());
        documentourl.setText(inmigrantes.getDocumentourl());
    }

    // cargar el recucler
    private void initRecyclerView() {
        inmigrantesContenidoAdapter = new inmigrantesContenidoAdapter(inmigrantes.getContenido());
        recyclerViewContenido.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewContenido.setAdapter(inmigrantesContenidoAdapter);
    }

    //cargar el video de youtube
    private void initVideo() {
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                String videoId = inmigrantes.getVideo();
                youTubePlayer.loadVideo(videoId, 0);
                youTubePlayer.pause();
            }
        });
    }

    //iniciar la base de datos
    private void inicializarFirebase() {

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

    }
}