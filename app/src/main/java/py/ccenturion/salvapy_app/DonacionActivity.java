package py.ccenturion.salvapy_app;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import py.ccenturion.salvapy_app.data.dto.Donacion;
import py.ccenturion.salvapy_app.data.dto.Response;
import py.ccenturion.salvapy_app.recyclerDonacion.RecyclerAdapterDonacion;
import py.ccenturion.salvapy_app.services.RestAdapter;
import py.ccenturion.salvapy_app.services.ws.NoticiasWS;
import retrofit2.Call;
import retrofit2.Callback;

public class DonacionActivity extends AppCompatActivity {
    RecyclerAdapterDonacion adapterEventos;
    NoticiasWS noticiasWS;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donacion);
        recyclerView = findViewById(R.id.rvDonacion);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        noticiasWS = RestAdapter.getClient("").create(NoticiasWS.class);
        noticiasWS.getDonacion().enqueue(new Callback<Response<List<Donacion>>>() {
            @Override
            public void onResponse(Call<Response<List<Donacion>>> call, retrofit2.Response<Response<List<Donacion>>> response) {
                adapterEventos = new RecyclerAdapterDonacion(response.body().getData(), DonacionActivity.this);
                recyclerView.setAdapter(adapterEventos);
            }

            @Override
            public void onFailure(Call<Response<List<Donacion>>> call, Throwable t) {
                Toast.makeText(DonacionActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
