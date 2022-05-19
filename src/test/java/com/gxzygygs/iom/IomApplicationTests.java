package com.gxzygygs.iom;

import com.gxzygygs.iom.generator.CodeGenerator;
import com.gxzygygs.iom.utils.AesCipherUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;



@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
class IomApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

//    private UserMapper userMapper;
    @Autowired
    CodeGenerator codeGenerator;
    @Test
    void contextLoads() {
//        List<User> users = userMapper.selectList(null);
        //获取当前项目最外层的工作目录
        String projectPath = System.getProperty("user.dir");
        System.out.print(projectPath);

    }
    @Test
    void generateCode() {

        codeGenerator.codeGenerator();
    }


    @Test
    void ASEUtilTest(){


        System.out.print(AesCipherUtil.enCrypto("123456"));

    }
    @Test
    void hset(){
        HashOperations ops =redisTemplate.opsForHash();
        ops.put("info","a","aa");
    }

    @Test
    void hget(){
        HashOperations ops =redisTemplate.opsForHash();
        System.out.printf(ops.get("info","a").toString());
    }

}
