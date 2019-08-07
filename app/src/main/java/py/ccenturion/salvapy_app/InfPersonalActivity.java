package py.ccenturion.salvapy_app;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import py.ccenturion.salvapy_app.data.dto.DonantesDTO;
import py.ccenturion.salvapy_app.data.dto.Response;
import py.ccenturion.salvapy_app.services.RestAdapter;
import py.ccenturion.salvapy_app.services.ws.RegistroWS;
import retrofit2.Call;
import retrofit2.Callback;

public class InfPersonalActivity extends AppCompatActivity {

    RegistroWS actRegistroWS;
    EditText edNombre, edApellido, edFecha, edPassword, edDocumento, edNroCelular;
    Button btnActualizar;
    SharedPreferences sharedPreferences;
    private DonantesDTO donante;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inf_personal);
        sharedPreferences = getSharedPreferences("preferences", MODE_PRIVATE);
        actRegistroWS = RestAdapter.getClient("").create(RegistroWS.class);
        edNombre = findViewById(R.id.edNombre);
        edApellido = findViewById(R.id.edApellido);
        edDocumento = findViewById(R.id.edDocumento);
        edFecha = findViewById(R.id.edFecha);
        edPassword = findViewById(R.id.edPassword);
//        edNroCelular = findViewById(R.id.edNroCelular);
        btnActualizar = findViewById(R.id.btnActualizar);
        btnActualizar.setOnClickListener(e ->{
            actualizarDatos();
        });
        setData();
    }

    private void actualizarDatos() {
        donante.setPassword(edPassword.getText().toString());
        Call<Response<DonantesDTO>> response = actRegistroWS.actualizarDonante(donante);
        response.enqueue(new Callback<Response<DonantesDTO>>() {
            @Override
            public void onResponse(Call<Response<DonantesDTO>> call, retrofit2.Response<Response<DonantesDTO>> response) {
                if (response.body() != null){
                    if (response.body().getCodigo().equals(200)){
                        SharedPreferences.Editor editor2 = sharedPreferences.edit();
                        editor2.putString("password",edPassword.getText().toString());
                        editor2.apply();
                        InfPersonalActivity.this.finish();
                        Toast.makeText(InfPersonalActivity.this,
                                "Proceso satisfactorio",
                                Toast.LENGTH_LONG).show();
                    }else {

                        Toast.makeText(InfPersonalActivity.this,
                                "Error: " + response.body().getMensaje(),
                                Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Response<DonantesDTO>> call, Throwable t) {
                Toast.makeText(InfPersonalActivity.this,
                        "Error: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setData() {
       Call<Response<DonantesDTO>> response = actRegistroWS.obtenerDonante(sharedPreferences.getInt("id",0));
       response.enqueue(new Callback<Response<DonantesDTO>>() {
           @Override
           public void onResponse(Call<Response<DonantesDTO>> call, retrofit2.Response<Response<DonantesDTO>> response) {
               if (response.body() != null){
                   if (response.body().getCodigo().equals(200)){
                       edDocumento.setText(response.body().getData().getNroDocumento().toString());
                       edNombre.setText(response.body().getData().getNombre());
                       edApellido.setText(response.body().getData().getApellido());
                       edPassword.setText(sharedPreferences.getString("password",""));
                       donante = new DonantesDTO();
                       donante.setNombre(edNombre.getText().toString());
                       donante.setApellido(edApellido.getText().toString());
                       donante.setNroDocumento(Integer.parseInt(edDocumento.getText().toString()));
                   }
               }
           }

           @Override
           public void onFailure(Call<Response<DonantesDTO>> call, Throwable t) {

           }
       });

    }
}
