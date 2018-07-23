package www.sh.com.service;

import www.sh.com.common.ResultForWeb;

/**
 * @author liaojinneng
 * @date 2018/7/21
 */
public interface ILoginservice {
    /**
     * 登陆
     */
    ResultForWeb login(String account, String password);

    /**
     * 退出
     */
    ResultForWeb logout(String accessToken);
}
