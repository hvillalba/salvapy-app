package py.ccenturion.salvapy_app.recycler;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import py.ccenturion.salvapy_app.R;

public class CustomViewHolderEventos extends RecyclerView.ViewHolder {
    TextView tvTitulo;
    TextView tvDescripcion;
    ImageView imageView;

    public CustomViewHolderEventos(@NonNull View itemView) {
        super(itemView);
        try {
            tvTitulo = (TextView) itemView.findViewById(R.id.tvTitulo);
            tvDescripcion = (TextView) itemView.findViewById(R.id.tvDescripcion);
            imageView = (ImageView) itemView.findViewById(R.id.img);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
