package network;

import retrofit2.Call;
import retrofit2.http.GET;

public interface StackOverflowServices {

    @GET("/2.2/search?site=stackoverflow")
    Call<String> search();
}
