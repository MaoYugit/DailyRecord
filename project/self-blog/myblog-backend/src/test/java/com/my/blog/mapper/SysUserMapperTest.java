package com.my.blog.mapper;

import com.my.blog.entity.SysUser;
import org.junit.jupiter.api.Assertions; // 引入断言工具
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional; // 引入事务控制

import java.util.Date;

/**
 * 单元测试类
 * @ SpringBootTest: 这个注解非常重要！
 * 它会启动整个 Spring Boot 容器，读取 application.yml 的配置，
 * 连接数据库，把 Mapper 接口生成代理对象，这样我们才能测试。
 */
@SpringBootTest
public class SysUserMapperTest {

    // 注入我们要测试的 Mapper 接口
    @Autowired
    private SysUserMapper sysUserMapper;

    @Test
    @Transactional  // 【神器】测试结束后，自动回滚事务。也就是说，插入的数据会被撤销，不会污染数据库！
    public void testInsertAndSelect() {
        // =================================
        // 1. 准备数据(模拟一个新用户)
        // =================================
        SysUser user = new SysUser();
        user.setUsername("junit_test_user"); // 有了回滚，不用担心用户名重复，可以写死
        user.setPassword("123456");
        user.setEmail("test@example.com");
        user.setNickname("单元测试员");
        user.setRole(0);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setIsDeleted(0);

        System.out.println("插入前的主键 ID: " + user.getId()); // 应该是 null

        // =================================
        // 2. 执行插入(Insert)
        // =================================
        int rows = sysUserMapper.insert(user);
        System.out.println("插入影响行数: " + rows); // 应该是 1

        // 【断言 1】断言影响行数必须是 1，如果不是，测试直接爆红失败
        Assertions.assertEquals(1, rows, "插入数据失败，影响行数不为1");

        // 【断言 2】断言 ID 不为空 (MyBatis 应该回填 ID)
        Assertions.assertNotNull(user.getId(), "主键ID未回填");

        // 关键点：因为 XML 里配置了 useGeneratedKeys="true"，
        // 插入成功后，MyBatis 会把数据库生成的 ID 回填给 user 对象。
        System.out.println("生成的ID是: " + user.getId());

        // =================================
        // 3. 测试查询 (Select)
        // =================================
        // 用刚才生成的 ID 去数据库查一下，看能不能查出来
        SysUser foundUser = sysUserMapper.selectById(user.getId());
        System.out.println("查询结果: " + foundUser);

        // 【断言 3】断言查出来的对象不为空
        Assertions.assertNotNull(foundUser, "根据ID查询失败");

        // 【断言 4】断言查出来的用户名和存进去的一样
        Assertions.assertEquals(user.getUsername(), foundUser.getUsername(), "用户名不一致");

        System.out.println(">>> 测试完美通过！(且数据库不会留下垃圾数据) <<<");
    }
}