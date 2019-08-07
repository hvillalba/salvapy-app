package py.ccenturion.salvapy_app.data.dto;

import java.io.Serializable;
import java.util.Date;

public class Donacion implements Serializable {
    private Integer iddonacion;
    private String paciente;
    private String tipoSangre;
    private Integer edad;
    private Integer cantDonantes;
    private String sexo;
    private String descripcion;
    private String url;
    private Date fechaDesde;
    private Date fechaHasta;

    public Integer getIddonacion() {
        return iddonacion;
    }

    public void setIddonacion(Integer iddonacion) {
        this.iddonacion = iddonacion;
    }

    public String getPaciente() {
        return paciente;
    }

    public void setPaciente(String paciente) {
        this.paciente = paciente;
    }

    public String getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(String tipoSangre) {
        this.tipoSangre = tipoSangre;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Integer getCantDonantes() {
        return cantDonantes;
    }

    public void setCantDonantes(Integer cantDonantes) {
        this.cantDonantes = cantDonantes;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }
}
