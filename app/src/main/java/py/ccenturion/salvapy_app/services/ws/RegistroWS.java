package py.ccenturion.salvapy_app.services.ws;

import py.ccenturion.salvapy_app.data.dto.DonantesDTO;
import py.ccenturion.salvapy_app.data.dto.Response;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RegistroWS {

    @POST("registro/registrar-donante")
    Call<Response<DonantesDTO>> registrar(@Body DonantesDTO donantesDTO);

    @POST("registro/login")
    Call<Response<DonantesDTO>> login(@Body DonantesDTO donantesDTO);

    @POST("registro/actualizar-usuario")
    Call<Response<DonantesDTO>> actualizarDonante(@Body DonantesDTO donantesDTO);

    @POST("registro/obtener-usuario/{id}")
    Call<Response<DonantesDTO>> obtenerDonante(@Path("id") Integer id);

    @POST("registro/recuperar-contra/{cedula}")
    Call<Response<DonantesDTO>> recuperarContra(@Path("cedula") Integer cedula);
}
