package json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class FastJsonTest {
    private int a;
//    @JSONField(serializeUsing = NumberSerializer.class)
    private BigDecimal b;
    private String c;
    private List<Object> list;

    public static void main(String[] args) {
        FastJsonTest f = new FastJsonTest();
        f.setB(new BigDecimal("0.00000000"));
//        String json1 = JSON.toJSONString(f);
//        System.out.println("json1" + json1);
//
//        String json2 = JSON.toJSONString(f, SerializerFeature.WriteMapNullValue);
//        System.out.println("json2" + json2);

        SerializeConfig config = new SerializeConfig();
        ObjectSerializer serializer = config.createJavaBeanSerializer(NumberSerializer.class);
        String json3 = JSON.toJSONString(f, (SerializeConfig) serializer);
        System.out.println("json2" + json3);

    }
}

