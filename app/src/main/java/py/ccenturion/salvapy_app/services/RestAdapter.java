package py.ccenturion.salvapy_app.services;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import py.ccenturion.salvapy_app.util.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Gustavo on 8/17/2017.
 */
public class RestAdapter {
    private static Retrofit retrofit = null;
    static String token;

    public static Retrofit  getClient(String token) {
        if (token != null){
            //CustomPreferenceManager customPreferenceManager = new CustomPreferenceManager(context);
           token = token;
        }
        String BASE_URL = Constants.URL_PATH;

        Gson gson = new GsonBuilder()
                //.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .setDateFormat("dd-MM-yyyy hh:mm:ss")
                //.registerTypeAdapter(Date.class, ser)
                //.registerTypeAdapter(Date.class, deser)
                .registerTypeAdapter(Date.class, new DateTypeDeserializer())
                .create();
        // Retrofit
        if (retrofit == null) {
                retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .client(getHttpClient())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

   static JsonSerializer<Date> ser = new JsonSerializer<Date>() {
        @Override
        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext
                context) {
            return src == null ? null : new JsonPrimitive(src.getTime());
        }
    };

    static JsonDeserializer<Date> deser = new JsonDeserializer<Date>() {
        @Override
        public Date deserialize(JsonElement json, Type typeOfT,
                                JsonDeserializationContext context) throws JsonParseException {
            if (json.getAsString().contains("-")){
                String[] fecha = json.getAsString().split("\\-");
                Calendar calendar = Calendar.getInstance();
                calendar.set(Integer.parseInt(fecha[0]), Integer.parseInt(fecha[1]), Integer.parseInt(fecha[2]) );
                return json == null ? null : calendar.getTime();
            }else {
                return json == null ? null : new Date(json.getAsLong());
            }
        }
    };

    public static class DateTypeDeserializer implements JsonDeserializer<Date> {
        private final String[] DATE_FORMATS = new String[]{
//                "yyyy-MM-dd'T'HH:mm:ssZ",
//                "yyyy-MM-dd'T'HH:mm:ss",
                "yyyy-MM-dd",
                "dd-MM-yyyy hh:mm:ss",
                "yyyy-MM-dd hh:mm:ss",
                "dd-MM-yyyy"
//                "EEE MMM dd HH:mm:ss z yyyy",
//                "HH:mm:ss",
//                "MM/dd/yyyy HH:mm:ss aaa",
//                "yyyy-MM-dd'T'HH:mm:ss.SSSSSS",
//                "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS",
//                "yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'",
//                "MMM d',' yyyy H:mm:ss a"
        };

        @Override
        public Date deserialize(JsonElement jsonElement, Type typeOF, JsonDeserializationContext context) throws JsonParseException {
            for (String format : DATE_FORMATS) {
                Log.e("dateFormat", format);
                try {
                    if (jsonElement.getAsString().contains("-")){
                        return new SimpleDateFormat(format, Locale.US).parse(jsonElement.getAsString());
                    }else {
                        return jsonElement == null ? null : new Date(jsonElement.getAsLong());
                    }

                } catch (ParseException e) {
                    Log.e("ParseException", e.getMessage());
                }
            }
            throw new JsonParseException("Unparseable date: \"" + jsonElement.getAsString()
                    + "\". Supported formats: \n" + Arrays.toString(DATE_FORMATS));
        }
    }

    /**
     * Obtenemos el cliente HTTP
     * @return
     */
    private static OkHttpClient getHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + token)
                        .build();
                return chain.proceed(newRequest);
            }
        });
        // Establecemos el timeout
        builder.connectTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS);
        return builder.build();
    }

}
