package py.ccenturion.salvapy_app;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import py.ccenturion.salvapy_app.data.dto.NoticiasDto;
import py.ccenturion.salvapy_app.data.dto.Response;
import py.ccenturion.salvapy_app.recyclerNoticias.RecyclerAdapterNoticias;
import py.ccenturion.salvapy_app.services.RestAdapter;
import py.ccenturion.salvapy_app.services.ws.NoticiasWS;
import retrofit2.Call;
import retrofit2.Callback;

public class NoticiasActivity extends AppCompatActivity {
    RecyclerAdapterNoticias adapterEventos;
    NoticiasWS noticiasWS;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_noticias);
        recyclerView = findViewById(R.id.recyclerNoticias);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        noticiasWS = RestAdapter.getClient("").create(NoticiasWS.class);
        noticiasWS.getNoticias().enqueue(new Callback<Response<List<NoticiasDto>>>() {
            @Override
            public void onResponse(Call<Response<List<NoticiasDto>>> call, retrofit2.Response<Response<List<NoticiasDto>>> response) {
                adapterEventos = new RecyclerAdapterNoticias(response.body().getData(), NoticiasActivity.this);
                recyclerView.setAdapter(adapterEventos);
            }

            @Override
            public void onFailure(Call<Response<List<NoticiasDto>>> call, Throwable t) {
                Toast.makeText(NoticiasActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
