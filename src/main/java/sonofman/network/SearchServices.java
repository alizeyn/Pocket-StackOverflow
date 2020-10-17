package sonofman.network;

import sonofman.model.ParseResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface SearchServices {

    @GET("sonOfMan/gsearch")
    Call<List<ParseResult>> searchStackOverflow(@Query("query") String query);
}
