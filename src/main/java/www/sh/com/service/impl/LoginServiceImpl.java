package www.sh.com.service.impl;

import org.springframework.stereotype.Service;
import www.sh.com.common.ResultForWeb;
import www.sh.com.service.ILoginservice;

/**
 * @author liaojinneng
 * @date 2018/7/21
 */
@Service
public class LoginServiceImpl implements ILoginservice{


    @Override
    public ResultForWeb login(String account, String password) {
        return null;
    }

    @Override
    public ResultForWeb logout(String username) {
        return null;
    }
}
