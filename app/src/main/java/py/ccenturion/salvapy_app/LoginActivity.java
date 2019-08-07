package py.ccenturion.salvapy_app;

import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.utils.Utils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import py.ccenturion.salvapy_app.data.dto.DonantesDTO;
import py.ccenturion.salvapy_app.data.dto.Response;
import py.ccenturion.salvapy_app.services.RestAdapter;
import py.ccenturion.salvapy_app.services.ws.RegistroWS;
import retrofit2.Call;
import retrofit2.Callback;


public class LoginActivity extends AppCompatActivity {

    RegistroWS registroWS;
    EditText edDonante, edPassword;
    Button btnLogin, btnRegistrarse;
    SharedPreferences sharedPreferences;
    TextView tvContra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        registroWS= RestAdapter.getClient("").create(RegistroWS.class);
        edDonante= findViewById(R.id.edUserName);
        edPassword= findViewById(R.id.edPassword);
        btnLogin= findViewById(R.id.btnLogin);
        tvContra = findViewById(R.id.tvContra);
        tvContra.setOnClickListener(e -> {
            Intent intent = new Intent(LoginActivity.this, RecuperarContraActivity.class);
            startActivity(intent);
        });
        btnRegistrarse= findViewById(R.id.btnRegistrar);
        sharedPreferences = getSharedPreferences("preferences", MODE_PRIVATE);

        btnRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this, RegistrarDonanteActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edDonante.getText().toString().equals("")){
                    edDonante.setError("El campo no puede quedar vacio");
                    edDonante.requestFocus();
                    return;
                }
                if (edPassword.getText().toString().equals("")){
                    edDonante.setError("El campo no puede quedar vacio");
                    edDonante.requestFocus();
                    return;
                }
                FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        try{
                            String token = instanceIdResult.getToken();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("token",token);
                            editor.apply();
                            // Guardamos en Shared Preferences también el token de FCM para no tener que instanciar un nuevo objeto cuando se quiera consultar
                            login(token);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                });

            }
        });
    }

    private void login(String token){
        DonantesDTO donantesDTO = new DonantesDTO();
        donantesDTO.setNroDocumento(Integer.parseInt(edDonante.getText().toString()));
        donantesDTO.setPassword(edPassword.getText().toString());
        donantesDTO.setToken(token);
        Call<Response<DonantesDTO>> responseCall=registroWS.login(donantesDTO);
        responseCall.enqueue(new Callback<Response<DonantesDTO>>() {
            @Override
            public void onResponse(Call<Response<DonantesDTO>> call, retrofit2.Response<Response<DonantesDTO>> response) {
                if (response != null) {
                    if ( response.body().getCodigo().equals(200)){
                        Intent intent=new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);
                        Toast.makeText(LoginActivity.this,"Proceso Satisfactorio", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("logueado",true);
                        editor.apply();
                        SharedPreferences.Editor editor2 = sharedPreferences.edit();
                        editor2.putBoolean("first",true);
                        editor2.apply();
                        SharedPreferences.Editor editor3 = sharedPreferences.edit();
                        editor3.putInt("id",response.body().getData().getId());
                        editor3.apply();
                        SharedPreferences.Editor editor4 = sharedPreferences.edit();
                        editor4.putString("password",response.body().getData().getPassword());
                        editor4.apply();
                        LoginActivity.this.finish();
                    }
                    else {
                        Toast.makeText(LoginActivity.this,"Clave Invalida", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<Response<DonantesDTO>> call, Throwable t) {

            }
        });
    }
}
