package com.smartflow.ievent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement // 启注解事务管理，等同于xml配置方式的 <tx:annotation-driven />
@SpringBootApplication
//@EnableCaching
public class IeventApplication {

    public static void main(String[] args) {
        SpringApplication.run(IeventApplication.class, args);
//        GetPropertNameByIdUtil getPropertNameByIdUtil = new GetPropertNameByIdUtil();
//       String a =  getPropertNameByIdUtil.getImpactDegreeValueByKey(1);
//       System.out.println(a);
    }

}
