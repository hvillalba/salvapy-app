package py.ccenturion.salvapy_app;

import android.app.DatePickerDialog;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import py.ccenturion.salvapy_app.adapter.ArrayAdapterSpinner;
import py.ccenturion.salvapy_app.data.dto.DonantesDTO;
import py.ccenturion.salvapy_app.data.dto.Response;
import py.ccenturion.salvapy_app.services.RestAdapter;
import py.ccenturion.salvapy_app.services.ws.RegistroWS;
import retrofit2.Call;
import retrofit2.Callback;

public class RegistrarDonanteActivity extends AppCompatActivity implements View.OnClickListener {

    RegistroWS registroWS;
    EditText edNombre, edApellido, edFecha, edPassword, edDocumento, edNroCelular, edEmail;
    Button btnRegistrar;
    Button btnFecha;
    Spinner spSexo;
    Spinner spEstadoCivil;
    Spinner spTipoSangre;
    String item;
    List<String> lista_Sexo = Arrays.asList("SEXO","MASCULINO","FEMENINO");
    List<String> lista_EstadoCivil = Arrays.asList("ESTADO CIVIL","SOLTERO","CASADO","DIVORCIADO", "VIUDO");
    List<String> lista_TipoSangre = Arrays.asList("A+","A-","B+","B-", "AB+","AB-", "0+", "0-");
    private int dia, mes, ano;
    DatePickerDialog datePickerDialog;
    private Date fechaNacimiento;
    String sexo = "";
    String estadoCivil = "";
    String tipoSangre = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_donante);
        registroWS = RestAdapter.getClient("").create(RegistroWS.class);
        edNombre = findViewById(R.id.edNombre);
        edApellido = findViewById(R.id.edApellido);
        edEmail = findViewById(R.id.edEmail);
        edDocumento = findViewById(R.id.edDocumento);
        edFecha = findViewById(R.id.edFecha);
        spSexo = (Spinner) findViewById(R.id.spSexo);
        spTipoSangre = (Spinner) findViewById(R.id.spTipoSangre);
        spEstadoCivil = (Spinner) findViewById(R.id.spEstadoCivil);
        edPassword = findViewById(R.id.edPassword);
        //edNroCelular = findViewById(R.id.edNroCelular);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        btnFecha = (Button) findViewById(R.id.btnFecha);



        btnRegistrar.setOnClickListener(new View.OnClickListener(   ) {
            @Override
            public void onClick(View view) {

                validarCampos();
                DonantesDTO donantesDTO = new DonantesDTO();
                donantesDTO.setApellido(edApellido.getText().toString());
                donantesDTO.setNombre(edNombre.getText().toString());
                donantesDTO.setNroDocumento(Integer.parseInt(edDocumento.getText().toString()));
                donantesDTO.setPassword(edPassword.getText().toString());
                //donantesDTO.setNroCelular(edNroCelular.getText().toString());
                donantesDTO.setFechaNacimiento(fechaNacimiento);
                donantesDTO.setEmail(edEmail.getText().toString());
                donantesDTO.setEstadoCivil(estadoCivil);
                donantesDTO.setSexo(sexo);
                donantesDTO.setTipoSangre(tipoSangre);


                Call<Response<DonantesDTO>> responseCall = registroWS.registrar(donantesDTO);
                responseCall.enqueue(new Callback<Response<DonantesDTO>>() {
                    @Override
                    public void onResponse(Call<Response<DonantesDTO>> call, retrofit2.Response<Response<DonantesDTO>> response) {
                        if (response.body() != null){
                            if (response.body().getCodigo().equals(200)){
                                Toast.makeText(RegistrarDonanteActivity.this, "Proceso Satisfactorio", Toast.LENGTH_SHORT).show();
                                RegistrarDonanteActivity.this.finish();
                            }else {
                                Toast.makeText(RegistrarDonanteActivity.this,
                                        "Advertencia, " + response.body().getMensaje(),
                                        Toast.LENGTH_LONG).show();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<Response<DonantesDTO>> call, Throwable t) {
                        Toast.makeText(RegistrarDonanteActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }

        });

        btnFecha.setOnClickListener(this);


        ArrayAdapterSpinner adapter = new ArrayAdapterSpinner(this, R.layout.item_tiposangre,lista_Sexo);
        spSexo.setAdapter(adapter);
        spSexo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sexo = (String) spSexo.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapterSpinner adapter2 = new ArrayAdapterSpinner(this, R.layout.item_tiposangre, lista_EstadoCivil);
                spEstadoCivil.setAdapter(adapter2);

                spEstadoCivil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        estadoCivil = (String) spEstadoCivil.getSelectedItem();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

        ArrayAdapterSpinner adapter1 = new ArrayAdapterSpinner(this, R.layout.item_tiposangre, lista_TipoSangre);
                spTipoSangre.setAdapter(adapter1);
                spTipoSangre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        tipoSangre = (String) spTipoSangre.getSelectedItem();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

    }

    private void validarCampos() {
        if (edEmail.getText() ==null || edEmail.getText().toString().equals("")){
            edEmail.setError("Debe agregar un mail");
            edEmail.requestFocus();
            return;
        }
        if (edPassword.getText() ==null || edPassword.getText().toString().equals("")){
            edPassword.setError("No puede dejar vacio el campo de password");
            edPassword.requestFocus();
            return;
        }
        if (edNombre.getText() ==null || edNombre.getText().toString().equals("")){
            edNombre.setError("No puede dejar vacio el campo de password");
            edNombre.requestFocus();
            return;
        }
        if (edDocumento.getText() ==null || edDocumento.getText().toString().equals("")){
            edDocumento.setError("No puede dejar vacio el campo de password");
            edDocumento.requestFocus();
            return;
        }
        if (tipoSangre == null || tipoSangre.equals("")){
            Toast.makeText(RegistrarDonanteActivity.this, "Debe elegir un tipo de Sangre"
                    ,Toast.LENGTH_LONG).show();
            return;
        }
    }

    @Override
    public void onClick(View view) {
    if (view==btnFecha){
        final Calendar c = Calendar.getInstance();
        dia= c.get(Calendar.DAY_OF_MONTH);
        mes= c.get(Calendar.MONTH);
        ano= c.get(Calendar.YEAR);
        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
        System.out.println(c.getTime());
// Output "Wed Sep 26 14:23:28 EST 2012"

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                edFecha.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
                c.set(year,monthOfYear, dayOfMonth);
                String formatted = format1.format(c.getTime());
                try {
                    fechaNacimiento = format1.parse(formatted);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        }
        ,dia,mes,ano);
        datePickerDialog.show();
    }

//
//        spTipoSangre.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.lista_TipoSangre, android.R.layout.simple_spinner_item);
//                spTipoSangre.setAdapter(adapter1);
//
//            }
//        });
    }

}


