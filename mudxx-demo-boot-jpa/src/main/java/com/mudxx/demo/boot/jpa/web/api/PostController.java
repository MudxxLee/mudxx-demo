package com.mudxx.demo.boot.jpa.web.api;

import com.mudxx.demo.boot.jpa.web.vo.FileVO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/api/post")
@RestController
public class PostController {

    /**
     * 局限性: 该方式只适合键值对数据格式, 最后会把键值对拼接成一条语句
     * 访问地址: http://localhost:8080/okhttp3/post/xwwwform_urlencoded
     * 请求方式: POST
     * 传参方式(键值对): application/x-www-form-urlencoded
     *
     * @param fileVO
     * @return
     */
    @RequestMapping(value = "/okhttp3/post/xwwwform_urlencoded", method = RequestMethod.POST,
            consumes = {
                    MediaType.APPLICATION_FORM_URLENCODED_VALUE
            },
            produces = {
                    MediaType.APPLICATION_JSON_UTF8_VALUE
            })
    public String xWWWFormUrlencoded(FileVO fileVO) {
        System.out.println("==>/okhttp3/post/xwwwform_urlencoded: " + fileVO);

        // 处理业务逻辑
        return "/okhttp3/post/xwwwform_urlencoded SUCCESS!";
    }

    /**
     * 局限性: 该方式比较适合图片类文件上传, 如把图片转换成Base64格式, 再处理
     * 访问地址: http://localhost:8080/okhttp3/post/formData
     * 请求方式: POST
     * 传参方式(键值对): multipart/form-data
     *
     * @param fileVO
     * @return
     */
    @RequestMapping(value = "/okhttp3/post/formData", method = RequestMethod.POST,
            consumes = {
                    MediaType.MULTIPART_FORM_DATA_VALUE
            },
            produces = {
                    MediaType.APPLICATION_JSON_UTF8_VALUE
            })
    public String formData(FileVO fileVO) {
        System.out.println("==>/okhttp3/post/formData: " + fileVO);

        // 处理业务逻辑
        return "/okhttp3/post/formData SUCCESS!";
    }

    /**
     * 访问地址: http://localhost:8080/okhttp3/post/json
     * 请求方式: POST
     * 传参方式: application/json
     *
     * @param fileVO
     * @return
     */
    @RequestMapping(value = "/okhttp3/post/json", method = RequestMethod.POST,
            consumes = {
                    MediaType.APPLICATION_JSON_UTF8_VALUE
            },
            produces = {
                    MediaType.APPLICATION_JSON_UTF8_VALUE
            })
    public String json(@RequestBody FileVO fileVO) {
        System.out.println("==>/okhttp3/post/json: " + fileVO);

        // 处理业务逻辑
        return "/okhttp3/post/json SUCCESS!";
    }
}
