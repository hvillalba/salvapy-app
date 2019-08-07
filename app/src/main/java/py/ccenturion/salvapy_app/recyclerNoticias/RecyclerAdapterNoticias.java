package py.ccenturion.salvapy_app.recyclerNoticias;

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
import py.ccenturion.salvapy_app.data.dto.NoticiasDto;

public class RecyclerAdapterNoticias extends RecyclerView.Adapter<CustomViewHolderNoticias> {
    List<NoticiasDto> lista =  new ArrayList<>();
    Context context;

    public RecyclerAdapterNoticias(List<NoticiasDto> lista, Context context){
        this.lista = lista;
        this.context = context;

    }

    @NonNull
    @Override
    public CustomViewHolderNoticias onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_noticias,viewGroup, false);
        CustomViewHolderNoticias cv = new CustomViewHolderNoticias(view);
        return cv;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolderNoticias holder, int i) {
        NoticiasDto noticiasDto = lista.get(holder.getAdapterPosition());
        holder.tvTitulo.setText(" " + noticiasDto.getTitulo());
        holder.tvDescripcion.setText("" + noticiasDto.getDescripcion());
        Picasso.with(context)
                .load(noticiasDto.getImagen())
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}
