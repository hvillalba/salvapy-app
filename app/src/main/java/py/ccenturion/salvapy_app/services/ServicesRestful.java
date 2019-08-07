package py.ccenturion.salvapy_app.services;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import py.ccenturion.salvapy_app.BuildConfig;
import py.ccenturion.salvapy_app.util.Constants;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by isabelinorolandolopezroman on 21/7/17.
 */

public interface ServicesRestful {
    //public static final String BASE_URL="http://192.81.130.21:8080/checkit/api/"
    class Creator {

        public static Retrofit newApisService(Context context) {

            //CustomPreferenceManager customPreferenceManager = new CustomPreferenceManager(context);
            String BASE_URL = Constants.URL_PATH;
           final String token = "";
                   //customPreferenceManager.getString(Constant.MONCHIS_TOKEN);
                   HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            OkHttpClient okHttpClient = null;
            logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY
                    : HttpLoggingInterceptor.Level.NONE);
            if (token.equals("")){
                 okHttpClient = new OkHttpClient.Builder()
                        .addInterceptor(logging)
                        .build();
            }else {
                 okHttpClient = new OkHttpClient.Builder()
                        .addInterceptor(logging)
                        .addInterceptor(new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request newRequest  = chain.request().newBuilder()
                                        .addHeader("Authorization", "Bearer " + token)
                                        .build();
                                return chain.proceed(newRequest);
                            }
                        })

                        .build();
            }

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .registerTypeAdapter(Date.class, ser)
                    .registerTypeAdapter(Date.class, deser)
                    //.registerTypeAdapter(Date.class, getUnixEpochDateTypeAdapter())
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            return retrofit;
        }
    }
    /**
     * Obtenemos el cliente HTTP
     * @return
     */
    static OkHttpClient getHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                        //.addHeader("Authorization", "Bearer " + token)
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

    JsonSerializer<Date> ser = new JsonSerializer<Date>() {
        @Override
        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext
                context) {
            return src == null ? null : new JsonPrimitive(src.getTime());
        }
    };

    JsonDeserializer<Date> deser = new JsonDeserializer<Date>() {
        @Override
        public Date deserialize(JsonElement json, Type typeOfT,
                                JsonDeserializationContext context) throws JsonParseException {
            if (json.getAsString().contains("-")){
                String[] fecha = json.getAsString().split("\\-");
                Calendar calendar = Calendar.getInstance();
                calendar.set(Integer.parseInt(fecha[0]), Integer.parseInt(fecha[1]), Integer.parseInt(fecha[2]));
                return json == null ? null : calendar.getTime();
            }else {
                return json == null ? null : new Date(json.getAsLong());
            }
        }
    };

    JsonDeserializer<String> deserString = new JsonDeserializer<String>() {
        @Override
        public String deserialize(JsonElement json, Type typeOfT,
                                  JsonDeserializationContext context) throws JsonParseException {
            if (json.getAsString() != null){
                return json.getAsString();
            }else {
                return json == null ? "" : json.getAsString();
            }
        }
    };
    JsonSerializer<String> serString = new JsonSerializer<String>() {
        @Override
        public JsonElement serialize(String src, Type typeOfSrc, JsonSerializationContext
                context) {
            return src == null ? null : new JsonPrimitive(src);
        }
    };
    final class UnixEpochDateTypeAdapter
            extends TypeAdapter<Date> {

        private static final TypeAdapter<Date> unixEpochDateTypeAdapter = new UnixEpochDateTypeAdapter();

        private UnixEpochDateTypeAdapter() {
        }

        static TypeAdapter<Date> getUnixEpochDateTypeAdapter() {
            return unixEpochDateTypeAdapter;
        }

        @Override
        public Date read(final JsonReader in) {
            try {
                return new Date(in.nextLong());
            }catch (Exception e){
                return new Date();
            }
            // this is where the conversion is performed

        }

        @Override
        @SuppressWarnings("resource")
        public void write(final JsonWriter out, final Date value){
            try {
                out.value(value.getTime());
            }catch (Exception e){
                try {
                    out.value(new Date().getTime());
                } catch (IOException e1) {
                }
            }
            // write back if necessary or throw UnsupportedOperationException

        }

    }




}
