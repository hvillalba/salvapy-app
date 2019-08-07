package py.ccenturion.salvapy_app.services.ws;

import java.util.List;

import py.ccenturion.salvapy_app.data.dto.AcercaDe;
import py.ccenturion.salvapy_app.data.dto.AcercaDeSalvaPy;
import py.ccenturion.salvapy_app.data.dto.Donacion;
import py.ccenturion.salvapy_app.data.dto.DonantesDTO;
import py.ccenturion.salvapy_app.data.dto.Evento;
import py.ccenturion.salvapy_app.data.dto.NoticiasDto;
import py.ccenturion.salvapy_app.data.dto.Response;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface NoticiasWS  {

    @GET("noticias/lista")
    Call<Response<List<NoticiasDto>>> getNoticias();

    @GET("noticias/evento")
    Call<Response<List<Evento>>> getEvento();

    @GET("noticias/donacion")
    Call<Response<List<Donacion>>> getDonacion();

    @GET("noticias/lista-acercade")
    Call<Response<List<AcercaDe>>> getAcercaDe();

    @GET("noticias/lista-acercade-salvapy")
    Call<Response<List<AcercaDeSalvaPy>>> getAcercaDeSalvaPy();
}
