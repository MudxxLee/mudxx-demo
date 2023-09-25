package http;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * application/x-www-form-urlencoded方式测试
 * @author ouyangjun
 */
public class PostXwwwFormTest {

    public static void main(String[] args) {
        // 请求地址
        String url = "http://localhost:9236/api/post/okhttp3/post/xwwwform_urlencoded";

        // 传值方式一: 键值
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("fileName", "testName");
        paramsMap.put("fileSize", "999");
        httpMethodOne(url, paramsMap);

        // 传值方式二: 把键值对参数拼接起来
        String params = "fileName=TTTT&fileSize=666";
        httpMethodTwo(url, params);
    }

    // 方式一
    public static void httpMethodOne(String url, Map<String, String> paramsMap) {
        // 创建client对象 创建调用的工厂类 具备了访问http的能力
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS) // 设置超时时间
                .readTimeout(10, TimeUnit.SECONDS) // 设置读取超时时间
                .writeTimeout(10, TimeUnit.SECONDS) // 设置写入超时时间
                .build();

        // 创建请求的请求体
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : paramsMap.keySet()) {
            // 追加表单信息
            builder.add(key, paramsMap.get(key));
        }
        RequestBody body = builder.build();

        // 创建request, 表单提交
        Request request = new Request.Builder()
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .url(url)
                .post(body)
                .build();

        // 创建一个通信请求
        try (Response response = client.newCall(request).execute()) {
            // 尝试将返回值转换成字符串并返回
            System.out.println("==>httpMethodOne 方式二请求返回结果: " + response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 方式二: 把键值对参数拼接起来
    public static void httpMethodTwo(String url, String strParams) {
        // 创建client对象 创建调用的工厂类 具备了访问http的能力
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS) // 设置超时时间
                .readTimeout(10, TimeUnit.SECONDS) // 设置读取超时时间
                .writeTimeout(10, TimeUnit.SECONDS) // 设置写入超时时间
                .build();

        //  创建请求的请求体, 把参数拼接起来
        RequestBody body = RequestBody.create(strParams, MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"));

        // 创建request, 表单提交
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        // 创建一个通信请求
        try (Response response = client.newCall(request).execute()) {
            // 尝试将返回值转换成字符串并返回
            System.out.println("==>httpMethodTwo 方式二请求返回结果: " + response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
