package www.sh.com.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import www.sh.com.Application;
import www.sh.com.pojo.vo.UserVo;
import www.sh.com.service.IUserService;

/**
 * @author liaojinneng
 * @date 2018/7/18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class UserServiceImplTest {
    @Autowired
    private IUserService iUserService;

    @Test
    public void findById() throws Exception {
        UserVo userVo = iUserService.findById(2L);
        System.out.println();

    }

}