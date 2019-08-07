package py.ccenturion.salvapy_app;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import py.ccenturion.salvapy_app.data.dto.Evento;
import py.ccenturion.salvapy_app.data.dto.Response;
import py.ccenturion.salvapy_app.recycler.RecyclerAdapterEventos;
import py.ccenturion.salvapy_app.services.RestAdapter;
import py.ccenturion.salvapy_app.services.ws.NoticiasWS;
import retrofit2.Call;
import retrofit2.Callback;

public class EventosActivity extends AppCompatActivity {
    RecyclerAdapterEventos adapterEventos;
    NoticiasWS noticiasWS;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);
        recyclerView = findViewById(R.id.recyclerEventos);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        noticiasWS = RestAdapter.getClient("").create(NoticiasWS.class);
        noticiasWS.getEvento().enqueue(new Callback<Response<List<Evento>>>() {
            @Override
            public void onResponse(Call<Response<List<Evento>>> call, retrofit2.Response<Response<List<Evento>>> response) {
                adapterEventos = new RecyclerAdapterEventos(response.body().getData(), EventosActivity.this);
                recyclerView.setAdapter(adapterEventos);
            }

            @Override
            public void onFailure(Call<Response<List<Evento>>> call, Throwable t) {
                Toast.makeText(EventosActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
