package ru.strorin.businesscardapp.data.network.endpoints;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.strorin.businesscardapp.data.network.DefaultResponse;
import ru.strorin.businesscardapp.data.network.dto.NewsDTO;

public interface NewsEndpoint {
    @GET("svc/topstories/v2/{section}.json")
    Single<Response<DefaultResponse<List<NewsDTO>>>> topStories(@Path("section") String section);
}
