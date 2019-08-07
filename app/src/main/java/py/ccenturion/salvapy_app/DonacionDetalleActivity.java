package py.ccenturion.salvapy_app;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import py.ccenturion.salvapy_app.data.dto.Donacion;

public class DonacionDetalleActivity extends AppCompatActivity {


    private TextView tvPaciente;
    private TextView tvSexo;
    private TextView tvCantDonantes;
    private TextView tvEdad;
    private TextView tvTipoSangre;
    private TextView tvDescripcion;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donacion_detalle);
        Donacion donacion = (Donacion) getIntent().getSerializableExtra("donacion");
        tvPaciente = findViewById(R.id.tvPaciente);
        tvSexo = findViewById(R.id.tvSexo);
        tvTipoSangre = findViewById(R.id.tvTipoSangre);
        tvCantDonantes = findViewById(R.id.tvCantDonantes);
        tvEdad = findViewById(R.id.tvEdad);
        tvDescripcion = findViewById(R.id.tvDescripcion);
        imageView = findViewById(R.id.img);
        tvPaciente.setText("Paciente: " + donacion.getPaciente());
        tvSexo.setText("Sexo: " + donacion.getSexo());
        tvCantDonantes.setText("Cant. de Donantes: " + donacion.getCantDonantes());
        tvEdad.setText("Edad : " + donacion.getEdad());
        tvTipoSangre.setText("Tipo de Sangre : " + donacion.getTipoSangre());
        tvDescripcion.setText("Descripcion: " + donacion.getDescripcion());
        Picasso.with(this)
                .load(donacion.getUrl())
                .into(imageView);
    }
}
