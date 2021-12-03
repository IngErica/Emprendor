package com.example.proyectoemprededor.viewholderadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectoemprededor.R;
import com.example.proyectoemprededor.model.contenido;
import com.squareup.picasso.Picasso;

import java.util.List;

public class inmigrantesContenidoAdapter extends RecyclerView.Adapter<inmigrantesContenidoAdapter.ViewHolderContenido> {

    //private static final String TAG = "Contenido";
    List<contenido> contenidoList;

    public inmigrantesContenidoAdapter(List<contenido> contenidoList) {
        this.contenidoList = contenidoList;
    }

    @NonNull
    @Override
    public ViewHolderContenido onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_contenido, parent, false);
        return new ViewHolderContenido(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderContenido holder, int position) {
        contenido contenido = contenidoList.get(position);
        holder.titleTextView.setText(contenido.getName());
        Picasso.with(holder.imagenlist.getContext()).load(contenido.getImagen()).into(holder.imagenlist);
        holder.descripcion.setText(contenido.getDescripcion());
        boolean isExpanded = contenidoList.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return contenidoList.size();
    }

    public class ViewHolderContenido extends RecyclerView.ViewHolder {
        //private static final String TAG = "ContenidoViewHolder";

        TextView titleTextView, descripcion;
        ImageView imagenlist;
        ConstraintLayout expandableLayout;

        public ViewHolderContenido(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            imagenlist = itemView.findViewById(R.id.imagenlist);
            descripcion=itemView.findViewById(R.id.textView);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);

            titleTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    contenido contenido = contenidoList.get(getAdapterPosition());
                    contenido.setExpanded(!contenido.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}

