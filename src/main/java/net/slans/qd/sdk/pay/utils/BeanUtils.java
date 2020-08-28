package net.slans.qd.sdk.pay.utils;

import net.slans.qd.sdk.pay.annotation.FieldObj;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.*;

public class BeanUtils {

    /**
     * 对象转map
     * @param obj
     * @return
     * @throws Exception
     */
    public static Map<String, String> Obj2Map(Object obj) throws Exception{
        Map<String, String> map = new HashMap<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            FieldObj fo = field.getAnnotation(FieldObj.class);
            if (fo == null) {
                continue;
            }
            field.setAccessible(true);

            String name = fo.name();
            if (StringUtils.isAnyBlank(name)) {
                throw new MissingFormatArgumentException(field.getName());
            }

            map.put(name, field.get(obj).toString());
        }
        return map;
    }

    /**
     * map 转换成签名字符串
     * @param map
     * @return
     */
    public static String map2SignStr(Map<String, String> map) {
        List<String> list = new ArrayList<>(map.keySet());
        Object[] fieldNames = list.toArray();
        Arrays.sort(list.toArray());

        StringBuffer sb = new StringBuffer();
        for (Object fieldName : fieldNames) {
            String val = map.get(fieldName);
            if (StringUtils.isAnyBlank(val)) {
                continue;
            }
            //
            sb.append("&" + fieldName + "=" + val);
        }
        return sb.toString().substring(1);
    }

}
