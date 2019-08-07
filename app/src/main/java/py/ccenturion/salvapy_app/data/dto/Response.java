package py.ccenturion.salvapy_app.data.dto;

import java.io.Serializable;

public class Response<T> implements Serializable {

    private Integer codigo;
    private String mensaje;
    private T data;

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
