package py.ccenturion.salvapy_app.recycler;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import py.ccenturion.salvapy_app.R;
import py.ccenturion.salvapy_app.data.dto.Evento;

public class RecyclerAdapterEventos extends RecyclerView.Adapter<CustomViewHolderEventos> {
    List<Evento> lista =  new ArrayList<>();
    Context context;

    public RecyclerAdapterEventos(List<Evento> lista, Context context){
        this.lista = lista;
        this.context = context;

    }

    @NonNull
    @Override
    public CustomViewHolderEventos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_eventos,viewGroup, false);
        CustomViewHolderEventos cv = new CustomViewHolderEventos(view);
        return cv;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolderEventos holder, int i) {
        Evento noticiasDto = lista.get(holder.getAdapterPosition());
        holder.tvTitulo.setText(" " + noticiasDto.getTitulo());
        holder.tvDescripcion.setText("" + noticiasDto.getDescripcion());
        Picasso.with(context)
                .load(noticiasDto.getUrl())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}
