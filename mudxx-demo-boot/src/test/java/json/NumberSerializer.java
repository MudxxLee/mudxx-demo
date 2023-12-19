package json;


import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;

import java.lang.reflect.Type;
import java.math.BigDecimal;

/**
 * @author laiw
 * @date 2023/10/9 16:47
 */
public class NumberSerializer implements ObjectSerializer {
    public static final NumberSerializer instance = new NumberSerializer();

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {

        SerializeWriter out = serializer.out;

        if (object == null) {
            out.writeNull();
            return;
        }

        if ("java.math.BigDecimal".equalsIgnoreCase(fieldType.getTypeName())) {
            BigDecimal value = (BigDecimal) object;
            out.write(value.stripTrailingZeros().toPlainString());
        }

    }
}

