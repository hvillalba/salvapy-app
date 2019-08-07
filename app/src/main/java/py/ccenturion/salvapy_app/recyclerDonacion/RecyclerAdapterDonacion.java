package py.ccenturion.salvapy_app.recyclerDonacion;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import py.ccenturion.salvapy_app.DonacionDetalleActivity;
import py.ccenturion.salvapy_app.R;
import py.ccenturion.salvapy_app.data.dto.Donacion;

public class RecyclerAdapterDonacion extends RecyclerView.Adapter<CustomViewHolderDonacion> {
    List<Donacion> lista =  new ArrayList<>();
    Context context;

    public RecyclerAdapterDonacion(List<Donacion> lista, Context context){
        this.lista = lista;
        this.context = context;

    }

    @NonNull
    @Override
    public CustomViewHolderDonacion onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_donacion,viewGroup, false);
        CustomViewHolderDonacion cv = new CustomViewHolderDonacion(view);
        return cv;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolderDonacion holder, int i) {
        Donacion noticiasDto = lista.get(holder.getAdapterPosition());
        holder.tvPaciente.setText("Paciente: " + noticiasDto.getPaciente());
        holder.tvSexo.setText("Sexo: " + noticiasDto.getSexo());
        holder.tvCantDonantes.setText("Cant. de Donantes: " + noticiasDto.getCantDonantes());
        holder.tvEdad.setText("Edad : " + noticiasDto.getEdad());
        holder.tvTipoSangre.setText("Tipo de Sangre : " + noticiasDto.getTipoSangre());
        //holder.tvDescripcion.setText("Descripcion: " + noticiasDto.getDescripcion());
        Picasso.with(context)
                .load(noticiasDto.getUrl())
                .into(holder.imageView);
        holder.imageView.setOnClickListener(e-> {
            Intent intent = new Intent(context, DonacionDetalleActivity.class);
            intent.putExtra("donacion",noticiasDto);
            context.startActivity(intent);
        });
        holder.tvTipoSangre.setOnClickListener(e-> {
            Intent intent = new Intent(context, DonacionDetalleActivity.class);
            intent.putExtra("donacion",noticiasDto);
            context.startActivity(intent);
        });
        holder.tvPaciente.setOnClickListener(e-> {
            Intent intent = new Intent(context, DonacionDetalleActivity.class);
            intent.putExtra("donacion",noticiasDto);
            context.startActivity(intent);
        });
        holder.tvCantDonantes.setOnClickListener(e-> {
            Intent intent = new Intent(context, DonacionDetalleActivity.class);
            intent.putExtra("donacion",noticiasDto);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}
