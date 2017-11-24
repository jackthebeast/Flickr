package it.jgrassi.flickr.data;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by j.grassi on 24/11/2017.
 */

public class PostFactory {
    public final static String BASE_URL = "https://api.flickr.com/services/feeds/";

    public static Retrofit create(){

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request request  = chain.request().newBuilder()
                        .build();
                Response response = chain.proceed(request);
                if (response.code() == 200) {
                    String body = response.body().string();
                    body = body.replaceAll("\\)$", "");
                    body = body.replaceAll("^jsonFlickrFeed\\(", "");
                    MediaType contentType = response.body().contentType();
                    ResponseBody newBody = ResponseBody.create(contentType, body);
                    return response.newBuilder().body(newBody).build();
                }
                return response;
            }
        }).build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
