package com.mudxx.demo.web.stream.api;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author laiw
 * @date 2023/4/3 14:57
 */
@RequestMapping("/api/demo")
@RestController
public class DemoController {

    @GetMapping("/download")
    public ResponseEntity<byte[]> download(HttpServletResponse response) {

        // 1、创建OSSClient实例。
        OSSClient ossClient = new OSSClient(
                "oss-ap-southeast-1.aliyuncs.com",
                "LTAI5tRpsE1rQXvbwH3mke9n",
                "DusEfTexBU6OiZLwuvLTOPOEY7rtZv"
        );

        // 2、调用ossClient.getObject返回一个OSSObject实例，该实例包含文件内容及文件元信息。
        OSSObject ossObject = ossClient.getObject(
                "agilewing-billing-dev-t1-cost",
                "year=2023/month=03/part-00000-f3ee2908-bc6a-42d6-b78d-fed23caaae90-c000.snappy.parquet"
        );

        ResponseEntity<byte[]> responseEntity = null;

        // 3、调用ossObject.getObjectContent获取文件输入流，可读取此输入流获取其内容。
        try {
            InputStream content = ossObject.getObjectContent();

            if (content != null) {

                byte[] bytes = inputStreamToByteArray(content);

                // 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
                content.close();

                String contentDisposition = ContentDisposition
                        .builder("attachment")
                        .filename(URLEncoder.encode("阿里自然月成本-2023-10", "utf-8"))
                        .build()
                        .toString();

                responseEntity = ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(bytes);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 4、关闭OSSClient。
            ossClient.shutdown();
        }

        return responseEntity;
    }

    @GetMapping("/download1")
    public void download1(HttpServletResponse response) {

        // 1、创建OSSClient实例。
        OSSClient ossClient = new OSSClient(
                "oss-ap-southeast-1.aliyuncs.com",
                "LTAI5tRpsE1rQXvbwH3mke9n",
                "DusEfTexBU6OiZLwuvLTOPOEY7rtZv"
        );

        // 2、调用ossClient.getObject返回一个OSSObject实例，该实例包含文件内容及文件元信息。
        OSSObject ossObject = ossClient.getObject(
                "agilewing-billing-dev-t1-cost",
                "year=2023/month=03/part-00000-f3ee2908-bc6a-42d6-b78d-fed23caaae90-c000.snappy.parquet"
        );

        try {
            InputStream content = ossObject.getObjectContent();
            //文件名包含中文时需要进行中文编码，否则会出现乱码问题
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode("阿里自然月成本-2023-10", "utf-8"));
            ServletOutputStream servletOutputStream = response.getOutputStream();
            int len;
            //设置一个缓冲区，大小取决于文件内容的大小
            byte[] buffer = new byte[1024];
            //每次读入缓冲区的数据，直到缓冲区无数据
            while ((len = content.read(buffer)) != -1) {
                //输出缓冲区的数据
                servletOutputStream.write(buffer, 0, len);
            }
            servletOutputStream.close();
            content.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 4、关闭OSSClient。
            ossClient.shutdown();
        }

    }

    public static byte[] inputStreamToByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = inputStream.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

    public static String inputStreamToString(InputStream inputStream) {
        try {
            byte[] bytes = inputStreamToByteArray(inputStream);
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


}


