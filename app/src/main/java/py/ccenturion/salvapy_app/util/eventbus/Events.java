package py.ccenturion.salvapy_app.util.eventbus;

/**
 * Created by Gustavo on 11/8/17.
 */

public class Events {



    public static class FinalizarOrden{
        public boolean success;
        public String message;

        public FinalizarOrden(boolean success, String message){
            this.success = success;
            this.message = message;
        }
    }

    /****************************************
     * EVENTOS DE LAS ANIMACIONES
     ****************************************/
    public static class showAnimationVale {
        public int status;// 1 = animacion de fade 2 = cerrar el dialog
        public showAnimationVale(int status) {
            this.status = status;
        }
    }

    public static class Login{
        public boolean habilitar;
        public String message;

        public Login(boolean success, String message){
            this.habilitar = success;
            this.message = message;
        }
    }

    public static class Ciudad{
        public String ciudad;

        public Ciudad(String ciudad){
            this.ciudad = ciudad;
        }
    }
    public static class Barrio{
        public String barrio;

        public Barrio(String ciudad){
            this.barrio = ciudad;
        }
    }
    public static class Profesion{
        public String profesion;

        public Profesion(String profesion) {
            this.profesion = profesion;
        }
    }
    public static class Institucion{
        public String institucion;
        public Institucion(String institucion){
            this.institucion = institucion;
        }
    }

    public static class Maps{
        public double lat;
        public double lon;
        public Maps(double lat, double lon){
            this.lat = lat;
            this.lon = lon;
        }
    }


}
