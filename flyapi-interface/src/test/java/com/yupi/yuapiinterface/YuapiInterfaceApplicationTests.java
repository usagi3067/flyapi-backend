package com.dango.flyapiinterface;

import com.dango.flyapiclientsdk.client.FlyApiClient;
import com.dango.flyapiclientsdk.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class FlyapiInterfaceApplicationTests {

    @Resource
    private FlyApiClient flyApiClient;

    @Test
    void contextLoads() {
        String result = flyApiClient.getNameByGet("dango");
        User user = new User();
        user.setUsername("lidango");
        String usernameByPost = flyApiClient.getUsernameByPost(user);
        System.out.println(result);
        System.out.println(usernameByPost);
    }

}
