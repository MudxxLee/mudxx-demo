package http;

import okhttp3.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * application/json方式测试
 *
 * @author ouyangjun
 */
public class PostJsonTest {

    public static void main(String[] args) {
        // 请求地址
        String url = "http://localhost:9230/api/post/okhttp3/post/json";

        // JSON传值
        String json = "{\"fileName\":\"testName\", \"fileSize\":\"999\"}";
        httpMethod(url, json);
    }

    public static void httpMethod(String url, String json) {
        // 创建client对象 创建调用的工厂类 具备了访问http的能力
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS) // 设置超时时间
                .readTimeout(10, TimeUnit.SECONDS) // 设置读取超时时间
                .writeTimeout(10, TimeUnit.SECONDS) // 设置写入超时时间
                .build();

        // 创建请求的请求体
        RequestBody body = RequestBody.create(json, MediaType.get("application/json;charset=UTF-8"));

        // 创建request, 表单提交
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        // 创建一个通信请求
        try (Response response = client.newCall(request).execute()) {
            // 尝试将返回值转换成字符串并返回
            System.out.println("==>httpMethod 方式二请求返回结果: " + response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
