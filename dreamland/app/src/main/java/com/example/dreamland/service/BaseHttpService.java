package com.example.dreamland.service;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Pair;
import com.google.gson.*;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 主要的http请求服务
 */
public class BaseHttpService {

    public static BaseHttpService baseHttpService;

    /**
     * 后台spring boo地址
     * 个人开发时，手机和电脑连接同一局域网，填写电脑的ipv4地址
     * 后期上线后换成服务器的spring boot地址
     */
    public static String BASE_URL = "http://192.168.43.215:9000/";


    public static BaseHttpService getInstance() {
        if (baseHttpService == null) {
            baseHttpService = new BaseHttpService();
        }
        return baseHttpService;
    }

    public static String token = "";

    public static void setToken(String token) {
        BaseHttpService.token = token;
    }

    /**
     * 用于发起get请求
     *
     * @param url      请求的url
     * @param callBack 回调函数，将在子线程中发起请求，在主线程中执行回调函数
     * @param params   请求参数
     */
    public <T> void get(String url, CallBack callBack, Class<T> type, Pair<String, String>... params) {

        HttpUrl.Builder httpUrlBuilder = HttpUrl.parse(BASE_URL + url).newBuilder();
        if (params != null) {
            for (Pair<String, String> param :
                    params) {
                httpUrlBuilder.addQueryParameter(param.first, param.second);
            }
        }
        Request request = new Request.Builder().url(httpUrlBuilder.build())
                .addHeader("Authorization", token).build();
        HttpRequest(request, callBack, type);
    }

    /**
     * 发起post请求
     *
     * @param url
     * @param data
     * @param callback
     * @param type
     * @param <T>
     */
    public <T> void post(String url, Object data, CallBack callback, Class<T> type) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd hh:mm:ss").create();
        String data1 = gson.toJson(data);
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(data));
        Request request = new Request.Builder()
                .url(BASE_URL + url)
                .post(body)
                .addHeader("Authorization", token)
                .build();
        HttpRequest(request, callback, type);
    }

    public <T> void putByForm(String url, RequestBody body, CallBack callback, Class<T> type) {
        Request request = new Request.Builder()
                .url(BASE_URL + url)
                .put(body)
                .addHeader("Authorization", token)
                .build();
        HttpRequest(request, callback, type);
    }

    public <T> void postByForm(String url, RequestBody body, CallBack callback, Class<T> type) {
        Request request = new Request.Builder()
                .url(BASE_URL + url)
                .post(body)
                .addHeader("Authorization", token)
                .build();
        HttpRequest(request, callback, type);
    }

    /**
     * 发送put请求
     *
     * @param url
     * @param data
     * @param callback
     * @param type
     * @param <T>
     */
    public <T> void put(String url, Object data, CallBack callback, Class<T> type) {
        Gson gson = new Gson();
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), gson.toJson(data));
        Request request = new Request.Builder()
                .url(BASE_URL + url)
                .put(body)
                .addHeader("Authorization", token)
                .build();
        HttpRequest(request, callback, type);
    }

    /**
     * 自定义异步任务 用于发送http请求
     */
    public <T> void HttpRequest(Request request, CallBack callBack, Class<T> type) {
        Gson gson = createGson();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(5, TimeUnit.SECONDS).build();
            try {
                Response response = okHttpClient.newCall(request).execute();
                // 在这里将返回流转化为需要的范型数据并返回
                CustomerResponse customerResponse = new CustomerResponse();
                customerResponse.response = response;
                if (assertSuccessResponse(response) && type != null) {
                    String body = response.body().string();
                    customerResponse.data = gson.fromJson(body, (Type) type);
                }
                handler.post(() -> {
                    callBack.onSuccess(customerResponse);
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public class CustomerResponse<E> {
        Response response;
        E data;

        public Response getResponse() {
            return response;
        }

        public E getData() {
            return data;
        }
    }

    /**
     * 请求回调接口
     */
    public interface CallBack {
        void onSuccess(CustomerResponse result);
    }

    /**
     * 判断请求是否成功
     *
     * @param response
     * @return
     */
    public static boolean assertSuccessResponse(Response response) {
        return response.code() >= 200 && response.code() < 300;
    }

    /**
     * 格式化日期输出
     * MM/dd/yyyy HH:mm:ss
     *
     * @param date
     * @param format
     * @return
     */
    public static String dateFormat(Date date, String format) {
        SimpleDateFormat bjSdf = new SimpleDateFormat(format);     // 北京
        bjSdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return bjSdf.format(date);
    }

    public static Gson createGson() {
        GsonBuilder builder = new GsonBuilder();

        // Register an adapter to manage the date types as long values
        builder.registerTypeAdapter(Timestamp.class, new JsonDeserializer<Timestamp>() {
            @Override
            public Timestamp deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                try {
                    Date date = sdf.parse(json.getAsString());
                    return new Timestamp(date.getTime());
                } catch (ParseException e) {
                    throw new JsonParseException(e);
                }
            }
        });
        return builder.create();
    }
}
