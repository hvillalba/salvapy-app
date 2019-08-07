package py.ccenturion.salvapy_app;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class RequisitosActivity extends AppCompatActivity {

    private TextView mitexto;
    String texto = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requisitos);
        texto = getIntent().getStringExtra("acercade");

        mitexto = findViewById(R.id.textRequisitos);

//        String texto = "* Tener entre 18 y 65 años.\n" +
//                "* Peso mínimo 50 Kg.\n" +
//                "* Un ayuno breve de 2 horas para donar sangre y 4 horas para donar plaquetas,\n" +
//                "* No estar enfermo el día que acuda a la donación.\n" +
//                "* No haber padecido Hepatitis tipo B, tipo C, VIH-SIDA, Sífilis, etc.\n" +
//                "* No tener múltiples parejas sexuales.\n" +
//                "* No haber recibido trasplantes de órganos.\n" +
//                "* No padecer epilepsia, tuberculosis, enfermedades severas del corazón o cáncer.\n" +
//                "* No usar drogas intravenosas o inhaladas.\n" +
//                "* No haber estado internado en instituciones penales o mentales.\n" +
//                "* Mujeres, no estar embarazadas o lactando.\n" +
//                "* En los últimos 12 meses, no haberse realizado tatuajes, perforaciones, acupuntura, transfusiones, cateterismos, endoscopías o contacto sexual con desconocidos.\n" +
//                "* En los últimos 6 meses, no haber tenido cirugía, accidente mayor, mononucleosis, toxoplasmosis o meningitis. En caso de mujeres, no haber tenido parto, cesárea o aborto.\n" +
//                "* En los últimos 28 días, no haber viajado a zonas con brotes epidemiológicos ni haber recibido cualquiera de las siguientes vacunas: tuberculosis, poliomielitis, sarampión, rubeola, parotiditis, fiebre amarilla, cólera o influenza.  Se aceptan donadores que hayan recibido toxoide tetánico o diftérico.\n" +
//                "* En las últimas 12 horas, no haber ingerido bebidas alcohólicas, narcóticos, marihuana o algún estupefaciente.";
        mitexto.setText(texto);
    }
}
