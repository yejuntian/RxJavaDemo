package rxjava.own.com.rxjavademo.service;

import retrofit2.http.GET;
import rx.Observable;
import rxjava.own.com.rxjavademo.service.entity.Translation;

public interface Api {
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20world")
    Observable<Translation> getCall();

}
