package network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

public class RetrofitFactory {

    public static String STACK_OVERFLOW_BASE_URL = "";

    private static RetrofitFactory retrofitFactory;
    private OkHttpClient client;
    private Retrofit stackOverflowRetrofit;
    //todo: add github to search services
    //private Retrofit githubRetrofit;

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
    public StackOverflowServices getStackOverflowRetrofit() {
        if (stackOverflowRetrofit == null) {
            stackOverflowRetrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(STACK_OVERFLOW_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return stackOverflowRetrofit.create(StackOverflowServices.class);
    }
}
