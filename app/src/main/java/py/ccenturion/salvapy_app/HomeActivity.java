package py.ccenturion.salvapy_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import py.ccenturion.salvapy_app.data.dto.AcercaDe;
import py.ccenturion.salvapy_app.data.dto.AcercaDeSalvaPy;
import py.ccenturion.salvapy_app.data.dto.DonantesDTO;
import py.ccenturion.salvapy_app.data.dto.Response;
import py.ccenturion.salvapy_app.services.RestAdapter;
import py.ccenturion.salvapy_app.services.ws.NoticiasWS;
import py.ccenturion.salvapy_app.services.ws.RegistroWS;
import retrofit2.Call;
import retrofit2.Callback;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private GoogleMap mMap;
    SharedPreferences sharedPreferences;
    NoticiasWS noticiasWS;
    String acercaDe = "";
    String acercaDeSalvaPy = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("SalvaPy");
        noticiasWS = RestAdapter.getClient("").create(NoticiasWS.class);
        sharedPreferences= getSharedPreferences("preferences", MODE_PRIVATE);
        setSupportActionBar(toolbar);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getAcercaDe();
        getAcercaDeSalvaPy();
    }

    private void getAcercaDe(){
        Call<Response<List<AcercaDe>>> responseCall= noticiasWS.getAcercaDe();
        responseCall.enqueue(new Callback<Response<List<AcercaDe>>>() {
            @Override
            public void onResponse(Call<Response<List<AcercaDe>>> call, retrofit2.Response<Response<List<AcercaDe>>> response) {
                if (response != null) {
                    if ( response.body().getCodigo().equals(200)){
                        acercaDe = response.body().getData().get(0).getDescripcion();
                    }
                    else {
                        Toast.makeText(HomeActivity.this,"Clave Invalida", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<Response<List<AcercaDe>>> call, Throwable t) {

            }
        });
    }
    private void getAcercaDeSalvaPy(){
        Call<Response<List<AcercaDeSalvaPy>>> responseCall= noticiasWS.getAcercaDeSalvaPy();
        responseCall.enqueue(new Callback<Response<List<AcercaDeSalvaPy>>>() {
            @Override
            public void onResponse(Call<Response<List<AcercaDeSalvaPy>>> call, retrofit2.Response<Response<List<AcercaDeSalvaPy>>> response) {
                if (response != null) {
                    if ( response.body().getCodigo().equals(200)){
                        acercaDeSalvaPy = response.body().getData().get(0).getDescripcion();
                    }
                    else {
                        Toast.makeText(HomeActivity.this,"Clave Invalida", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<Response<List<AcercaDeSalvaPy>>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.cerrarsesion) {
            cerrarSesion();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void cerrarSesion() {
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putBoolean("logueado", false);
        editor.apply();
        HomeActivity.this.finish();
        Intent intent=new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_information) {
            Intent intent= new Intent(HomeActivity.this, InfPersonalActivity.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.nav_pedidos) {
            Intent intent= new Intent(HomeActivity.this, DonacionActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_requisitos) {
            Intent intent= new Intent(HomeActivity.this, RequisitosActivity.class);
            intent.putExtra("acercade", acercaDe);
            startActivity(intent);

        } else if (id == R.id.nav_eventos) {
            Intent intent = new Intent(HomeActivity.this, EventosActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_noticias) {
            Intent intent = new Intent(HomeActivity.this, NoticiasActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_send) {
            Intent intent = new Intent(HomeActivity.this, AcercaDeActivity.class);
            intent.putExtra("acercade", acercaDeSalvaPy);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-25.299992,-57.612803);
        mMap.addMarker(new MarkerOptions().position(sydney).title("CENSSA"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17), 500, null);
    }
}
