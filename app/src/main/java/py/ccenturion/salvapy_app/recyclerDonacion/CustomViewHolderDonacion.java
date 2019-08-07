package py.ccenturion.salvapy_app.recyclerDonacion;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import py.ccenturion.salvapy_app.R;

public class CustomViewHolderDonacion extends RecyclerView.ViewHolder {
    TextView tvPaciente;
    TextView tvSexo;
    TextView tvTipoSangre;
    TextView tvCantDonantes;
    TextView tvEdad;
    TextView tvDescripcion;
    ImageView imageView;

    public CustomViewHolderDonacion(@NonNull View itemView) {
        super(itemView);
        try {
            tvPaciente = (TextView) itemView.findViewById(R.id.tvPaciente);
            tvSexo = (TextView) itemView.findViewById(R.id.tvSexo);
            tvTipoSangre = (TextView) itemView.findViewById(R.id.tvTipoSangre);
            tvCantDonantes = (TextView) itemView.findViewById(R.id.tvCantDonantes);
            tvEdad = (TextView) itemView.findViewById(R.id.tvEdad);
            //tvDescripcion = (TextView) itemView.findViewById(R.id.tvDescripcion);
            imageView = (ImageView) itemView.findViewById(R.id.img);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
