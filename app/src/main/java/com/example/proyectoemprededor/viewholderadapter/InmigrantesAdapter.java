package com.example.proyectoemprededor.viewholderadapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoemprededor.R;
import com.example.proyectoemprededor.model.Global;
import com.example.proyectoemprededor.model.inmigrantes;
import com.example.proyectoemprededor.model.persona;
import com.example.proyectoemprededor.view.HomeActivity;
import com.example.proyectoemprededor.view.ShowDetail;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class InmigrantesAdapter extends FirebaseRecyclerAdapter<inmigrantes, InmigrantesAdapter.inmigrantesViewHolder> {

    public InmigrantesAdapter(@NonNull FirebaseRecyclerOptions<inmigrantes> options) {super(options);}

    @Override
    protected void onBindViewHolder(@NonNull inmigrantesViewHolder holder, int position, @NonNull inmigrantes inmigrantes)
    {
        //para lo datos de lista de acurdo a la posisiòn
        holder.titulo.setText(inmigrantes.getTitulo());
        Picasso.with(holder.img.getContext()).load(inmigrantes.getImagenurl()).into(holder.img);
        Log.d("inmigrantes"," data : "+ inmigrantes);
        int positio = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), ShowDetail.class);
                //inviar informaciòn inmigrantes y posisiòn
                intent.putExtra("inmigrantes", inmigrantes);
                intent.putExtra("position", positio);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public inmigrantesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        //cargar el layout
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cardviewinmigrantes,parent,false);
        return new inmigrantesViewHolder(view);
    }

    // crea un clase de viewholder para capturar los campos
    class inmigrantesViewHolder extends RecyclerView.ViewHolder
    {
        CircleImageView img;
        TextView titulo;
        public inmigrantesViewHolder(@NonNull View itemView)
        {
            super(itemView);
            img=itemView.findViewById(R.id.imagenlist);
            titulo=itemView.findViewById(R.id.titulo);
        }
    }
}
