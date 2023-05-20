package com.astroexpress.user.api;

import com.astroexpress.user.utility.AllStaticFields;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitAPIService {

    private static final String BASE_URL = "http://secure.astroexpress.co.in";
//    private static final String BASE_URL = "http://api.astroexpress.co.in";
//    private static final String CALL_URL = "https://kpi.knowlarity.com";

    public static MyAPIs getApiClient() {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();

        if (AllStaticFields.uName != null && AllStaticFields.uPass != null) {
            clientBuilder.addInterceptor(new BasicAuthInterceptor(AllStaticFields.uName, AllStaticFields.uPass));
        }
        OkHttpClient client = clientBuilder.addNetworkInterceptor(httpLoggingInterceptor)
                .connectTimeout(5, TimeUnit.MINUTES) // connect timeout
                .writeTimeout(5, TimeUnit.MINUTES) // write timeout
                .readTimeout(5, TimeUnit.MINUTES) // read timeout
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit.create(MyAPIs.class);
    }

//    public static MyAPIs getCallApi() {
//
//        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(new Interceptor() {
//                    @NotNull
//                    @Override
//                    public okhttp3.Response intercept(@NotNull Chain chain) throws IOException {
//                        Request original = chain.request();
//
//                        Request request = original.newBuilder()
//                                .header("Authorization", "92b55a1c-7449-4e2f-958a-96fecdc5bd8b")
//                                .header("x-api-key", "1yWupUw5p26qOU7Vc9Un85GoDw09X0mP8c4CYlW5")
//                                .header("Content-Type", "application/json")
//                                .method(original.method(), original.body())
//                                .build();
//                        okhttp3.Response response = chain.proceed(request);
//                        return response;
//                    }
//                })
//                .addNetworkInterceptor(httpLoggingInterceptor)
//                .connectTimeout(5, TimeUnit.MINUTES) // connect timeout
//                .writeTimeout(5, TimeUnit.MINUTES) // write timeout
//                .readTimeout(5, TimeUnit.MINUTES) // read timeout
//                .build();
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(CALL_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
//                .build();
//
//        return retrofit.create(MyAPIs.class);
//    }

}
