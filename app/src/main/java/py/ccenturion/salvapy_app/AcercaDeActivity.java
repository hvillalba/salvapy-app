package py.ccenturion.salvapy_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import py.ccenturion.salvapy_app.data.dto.AcercaDeSalvaPy;
import py.ccenturion.salvapy_app.data.dto.Response;
import py.ccenturion.salvapy_app.dialog.DialogFullAnimationTrans;
import py.ccenturion.salvapy_app.services.RestAdapter;
import py.ccenturion.salvapy_app.services.ws.NoticiasWS;
import retrofit2.Call;
import retrofit2.Callback;

public class AcercaDeActivity extends AppCompatActivity {

    String acercaDe = "";
    TextView tvAcercaDe;
    NoticiasWS noticiasWS;
    private DialogFullAnimationTrans dialogFullAnimation;
    private Button btnAcepta;
    private Button btnNoAcepta;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca_de);
        sharedPreferences = getSharedPreferences("preferences", MODE_PRIVATE);
        noticiasWS = RestAdapter.getClient("").create(NoticiasWS.class);
        btnAcepta = findViewById(R.id.btnAceptar);
        btnNoAcepta = findViewById(R.id.btnNoAcepta);
        if (sharedPreferences.getBoolean("first", false)){
            btnNoAcepta.setVisibility(View.GONE);
            btnAcepta.setVisibility(View.GONE);
        }
        dialogFullAnimation =   new DialogFullAnimationTrans(this);
        dialogFullAnimation.show();
        acercaDe = getIntent().getStringExtra("acercade");
        if (acercaDe == null){
            acercaDe = "Acerca de ";
        }
        btnAcepta.setOnClickListener(e -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("acepto",true);
            editor.apply();
            Intent intent = new Intent(AcercaDeActivity.this, LoginActivity.class);
            startActivity(intent);
            AcercaDeActivity.this.finish();
        });
        btnNoAcepta.setOnClickListener(e -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("acepto",false);
            editor.apply();
            AcercaDeActivity.this.finish();
        });
        tvAcercaDe = findViewById(R.id.tvAcercaDe);
        tvAcercaDe.setText(acercaDe);
        getAcercaDeSalvaPy();
    }

    private void getAcercaDeSalvaPy(){
        Call<Response<List<AcercaDeSalvaPy>>> responseCall= noticiasWS.getAcercaDeSalvaPy();
        responseCall.enqueue(new Callback<Response<List<AcercaDeSalvaPy>>>() {
            @Override
            public void onResponse(Call<Response<List<AcercaDeSalvaPy>>> call, retrofit2.Response<Response<List<AcercaDeSalvaPy>>> response) {
                if (response != null) {
                    if ( response.body().getCodigo().equals(200)){
                        dialogFullAnimation.dismiss();
                        acercaDe = response.body().getData().get(0).getDescripcion();
                        tvAcercaDe.setText(acercaDe);
                    }
                    else {
                        dialogFullAnimation.dismiss();
                        Toast.makeText(AcercaDeActivity.this,"Error al consultar servicio", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<Response<List<AcercaDeSalvaPy>>> call, Throwable t) {
                dialogFullAnimation.dismiss();
                Toast.makeText(AcercaDeActivity.this,"Error al consultar servicio", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
