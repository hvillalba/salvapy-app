package py.ccenturion.salvapy_app;

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

public class RecuperarContraActivity extends AppCompatActivity {
    RegistroWS registroWS;
    EditText edNroCedula;
    Button btnRecuperar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_contra);
        registroWS= RestAdapter.getClient("").create(RegistroWS.class);
        edNroCedula = findViewById(R.id.edNroCedula);
        btnRecuperar = findViewById(R.id.btnRecuperar);
        btnRecuperar.setOnClickListener(e -> {
            Call<Response<DonantesDTO>> responseCall= registroWS.recuperarContra(Integer.parseInt(edNroCedula.getText().toString()) );
            responseCall.enqueue(new Callback<Response<DonantesDTO>>() {
                @Override
                public void onResponse(Call<Response<DonantesDTO>> call, retrofit2.Response<Response<DonantesDTO>> response) {
                    if (response.body() != null){
                        if (response.body().getCodigo().equals(200)){
                            Toast.makeText(RecuperarContraActivity.this, "Se envio su nuevo password, por favor revise su mail",
                                    Toast.LENGTH_SHORT).show();
                            RecuperarContraActivity.this.finish();
                        }else {
                            Toast.makeText(RecuperarContraActivity.this, response.body().getMensaje(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Response<DonantesDTO>> call, Throwable t) {
                    Toast.makeText(RecuperarContraActivity.this, "Error : " + t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    RecuperarContraActivity.this.finish();
                }
            });

        });
    }
}
