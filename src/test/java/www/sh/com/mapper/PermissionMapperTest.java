package www.sh.com.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import www.sh.com.Application;
import www.sh.com.pojo.domain.Permission;

import java.util.List;

/**
 * @author liaojinneng
 * @date 2018/7/24
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class PermissionMapperTest {
    @Autowired
    private PermissionMapper permissionMapper;

    @Test
    public void findPermissionsForUser() throws Exception {
        String account = "aaa@163.com";
        List<Permission> permissionList = permissionMapper.findPermissionsForUser(account);
        System.out.println(666);
    }

}