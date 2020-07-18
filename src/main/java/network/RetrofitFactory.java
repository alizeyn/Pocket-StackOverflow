package network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

public class RetrofitFactory {

    public static String STACK_OVERFLOW_BASE_URL = "http://localhost:9595";

    private static RetrofitFactory retrofitFactory;
    private OkHttpClient client;
    private Retrofit stackOverflowRetrofit;

    private RetrofitFactory() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        client = new OkHttpClient().newBuilder()
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();
    }

    public static RetrofitFactory getInstance() {

        return retrofitFactory == null ?
        (retrofitFactory = new RetrofitFactory()) : retrofitFactory;
    }
    public SearchServices getSearchRetrofit() {
        if (stackOverflowRetrofit == null) {
            stackOverflowRetrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(STACK_OVERFLOW_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return stackOverflowRetrofit.create(SearchServices.class);
    }
}
