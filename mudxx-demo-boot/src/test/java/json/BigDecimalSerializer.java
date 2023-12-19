package json;

import cn.hutool.core.util.ObjUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author laiw
 * @date 2023/9/22 16:34
 */
public class BigDecimalSerializer extends JsonSerializer<BigDecimal> {

    @Override
    public void serialize(BigDecimal value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (ObjUtil.isNotNull(value)) {
            // 返回到前端的数据若为数字类型,前端接收有可能丢失精度,所以需要转成字符串
            //jsonGenerator.writeString(value.stripTrailingZeros().toPlainString());
            // 去除小数点后的0
            jsonGenerator.writeNumber(value.stripTrailingZeros());
            // 保留2位小数，四舍五入
            //jsonGenerator.writeString(NumberUtil.decimalFormat("##0.00", value));
        } else {
            // 若为null，则写null
            jsonGenerator.writeNull();
        }
    }

}
