package cn.itcast.Dao;

import cn.itcast.domain.Customer;
import cn.itcast.utils.CommonUtils;
import org.junit.jupiter.api.Test;

public class CustmoerTest {
    @Test
    /**
     * 添加 300 个记录
     */
    public void fun1() {
        CustomerDao customerDao = new CustomerDao();
        for (int i = 0; i <= 300; i++) {
            Customer c = new Customer();
            c.setCid(CommonUtils.uuid());
            c.setCname("cstm" + i);
            c.setBirthday("2020-10-13");
            c.setGender(i%2==0?"男":"女");
            c.setCellphone("139" + i);
            c.setEmail("cstm_" + i + "@qq.com");
            customerDao.add(c);
        }
    }
}
