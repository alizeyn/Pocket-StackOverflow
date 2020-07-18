package network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchServices {

    @GET("sonOfMan/search")
    Call<String> searchStackOverflow(@Query("q") String query);
}
