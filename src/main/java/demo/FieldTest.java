package demo;

import net.slans.qd.sdk.pay.annotation.FieldObj;
import net.slans.qd.sdk.pay.utils.BeanUtils;

import java.io.Serializable;
import java.util.Map;

public class FieldTest {

    public static void main(String[] args) throws Exception {
        User user = new User();
        user.setName("goodchax");
        user.setAge(30);
        user.setPassword("123456");

        Map<String, String> map = BeanUtils.Obj2Map(user);
        String str = BeanUtils.map2SignStr(map);
        System.out.println(str);
    }


    public static class User implements Serializable {

        @FieldObj(name = "username")
        private String name;

        @FieldObj(name = "_age")
        private int age;

        @FieldObj(name = "pwd")
        private String password;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

}
