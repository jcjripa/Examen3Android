package com.cibertec.androidexamen3.servicio;

        import com.cibertec.androidexamen3.entidad.Comprobante;
        import java.util.List;
        import retrofit2.Call;
        import retrofit2.http.Body;
        import retrofit2.http.GET;
        import retrofit2.http.POST;
        import retrofit2.http.Path;

public interface ServicioRest {

    @GET("listaComprobante")
    public abstract Call<List<Comprobante>> listaComprobante();

    @POST("comprobante")
    public abstract Call<Comprobante> registraComprobante(@Body Comprobante c);

    @GET("listaComprobante/{idPedido}")
    public abstract Call<List<Comprobante>> listaComprobantePorPedido(@Path("idPedido") int id);

    @GET("listaComprobantePorCliente/{idCliente}")
    public abstract Call<List<Comprobante>> listaComprobantePorCliente(@Path("idCliente") int id);



}
