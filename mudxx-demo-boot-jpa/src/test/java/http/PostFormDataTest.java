package http;

import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * multipart/form-data方式测试
 *
 * @author ouyangjun
 */
public class PostFormDataTest {

    public static void main(String[] args) {
        // 请求地址
        String url = "http://localhost:9236/api/post/okhttp3/post/formData";

        // 请求参数
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("fileName", "GGGGG");
        paramsMap.put("fileSize", "77777");
        httpMethod(url, paramsMap);
    }

    public static void httpMethod(String url, Map<String, String> paramsMap) {
        // 创建client对象 创建调用的工厂类 具备了访问http的能力
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS) // 设置超时时间
                .readTimeout(30, TimeUnit.SECONDS) // 设置读取超时时间
                .writeTimeout(30, TimeUnit.SECONDS) // 设置写入超时时间
                .build();

        // 添加请求类型
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MediaType.parse("multipart/form-data"));

        //  创建请求的请求体
        for (String key : paramsMap.keySet()) {
            // 追加表单信息
            builder.addFormDataPart(key, paramsMap.get(key));
        }
        RequestBody body = builder.build();

        // 创建request, 表单提交
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        // 创建一个通信请求
        try (Response response = client.newCall(request).execute()) {
            // 尝试将返回值转换成字符串并返回
            System.out.println("==>返回结果: " + response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
