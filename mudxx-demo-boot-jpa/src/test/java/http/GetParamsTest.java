package http;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * GET带参请求
 *
 * @author ouyangjun
 */
public class GetParamsTest {

    public static void main(String[] args) {
        // 请求地址
        String url = "http://localhost:9236/api/get1/okhttp3/get/params?param1=EEE&param2=PPP";

        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS) // 设置超时时间
                .readTimeout(10, TimeUnit.SECONDS) // 设置读取超时时间
                .writeTimeout(10, TimeUnit.SECONDS) // 设置写入超时时间
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println("返回code: " + response.code());

            final ResponseBody responseBody = response.body();
            if (responseBody != null) {
                final String string = responseBody.string();
                System.out.println("返回结果: " + string);
            }

        } catch (IOException e) {
            System.out.println("error-------");
            e.printStackTrace();
        }

        // 参数名称需和FileVO中的名称一一对应
        url = "http://localhost:9236/api/get/okhttp3/get/form?fileName=kkkk&fileSize=6699";
        request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()) {
            System.out.println("返回code: " + response.code());

            final ResponseBody responseBody = response.body();
            if (responseBody != null) {
                final String string = responseBody.string();
                System.out.println("返回结果: " + string);
            }

        } catch (IOException e) {
            System.out.println("error-------");
            e.printStackTrace();
        }

    }
}
