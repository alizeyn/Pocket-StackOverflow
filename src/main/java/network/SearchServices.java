package network;

import model.ParseResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface SearchServices {

    @GET("sonOfMan/search")
    Call<List<ParseResult>> searchStackOverflow(@Query("q") String query);
}
