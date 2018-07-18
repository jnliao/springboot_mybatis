package www.sh.com.conf.ds;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.spring.stat.BeanTypeAutoProxyCreator;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;

/**
 * <p>数据库数据源配置</p>
 * @author Administrator
 */
@Component
public class DruidProperties {
	private static final Logger logger = LoggerFactory.getLogger(DruidProperties.class);
	@Value("${mysql_url}")
	private String url;
	@Value("${mysql_username}")
    private String username;
	@Value("${mysql_password}")
    private String password;

	@Value("${spring.druid.datasource.initialSize}")
    private Integer initialSize;
	@Value("${spring.druid.datasource.minIdle}")
    private Integer minIdle;
	@Value("${spring.druid.datasource.maxActive}")
    private Integer maxActive;

    /**
     *  这两个参数根据实际情况进行调整
     */
	@Value("${spring.druid.datasource.maxWait}")
    private Integer maxWait;
	@Value("${spring.druid.datasource.timeBetweenEvictionRunsMillis}")
    private Integer timeBetweenEvictionRunsMillis;
    
	@Value("${spring.druid.datasource.minEvictableIdleTimeMillis}")
    private Integer minEvictableIdleTimeMillis;
	@Value("${spring.druid.datasource.maxEvictableIdleTimeMillis}")
    private Integer maxEvictableIdleTimeMillis;
	@Value("${spring.druid.datasource.validationQuery}")
    private String validationQuery;
	@Value("${spring.druid.datasource.testWhileIdle}")
    private Boolean testWhileIdle;
	@Value("${spring.druid.datasource.testOnBorrow}")
    private Boolean testOnBorrow;
	@Value("${spring.druid.datasource.testOnReturn}")
    private Boolean testOnReturn;
	@Value("${spring.druid.datasource.poolPreparedStatements}")
    private Boolean poolPreparedStatements;
	@Value("${spring.druid.datasource.maxPoolPreparedStatementPerConnectionSize}")
    private Integer maxPoolPreparedStatementPerConnectionSize;
    /**
     * 监控统计用的filter:stat  日志用的filter:log4j   防御sql注入的filter:wall
     */
	@Value("${spring.druid.datasource.filters}")
    private String filters;
	
	@Value("${spring.druid.datasource.removeAbandoned}")
    private Boolean removeAbandoned;
	@Value("${spring.druid.datasource.removeAbandonedTimeout}")
    private Integer removeAbandonedTimeout;
	@Value("${spring.druid.datasource.logAbandoned}")
    private Boolean logAbandoned;

    public void config(DruidDataSource dataSource) {

        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        //定义初始连接数
        dataSource.setInitialSize(initialSize);
        //最小空闲
        dataSource.setMinIdle(minIdle);
        //定义最大连接数
        dataSource.setMaxActive(maxActive);
        //最长等待时间
        dataSource.setMaxWait(maxWait);

        // 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);

        // 配置一个连接在池中最小生存的时间，单位是毫秒
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        dataSource.setMaxEvictableIdleTimeMillis(maxEvictableIdleTimeMillis);
        dataSource.setValidationQuery(validationQuery);
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestOnReturn(testOnReturn);

        // 打开PSCache，并且指定每个连接上PSCache的大小
        dataSource.setPoolPreparedStatements(poolPreparedStatements);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        
        dataSource.setRemoveAbandoned(removeAbandoned);
        dataSource.setRemoveAbandonedTimeout(removeAbandonedTimeout);
        dataSource.setLogAbandoned(logAbandoned);

        try {
            dataSource.setFilters(filters);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        //设置运行批量执行sql
        List<Filter> list = dataSource.getProxyFilters();
        WallFilter  wallFilter = (WallFilter)list.get(1);
        wallFilter.setConfig(wallConfig());
        
        
    }
    
    /**
     * druidServlet注册
     */
    @Bean
    public ServletRegistrationBean druidServletRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new StatViewServlet());
        registration.addUrlMappings("/druid/*");
        return registration;
    }

    /**
     * druid监控 配置URI拦截策略
     */
    @Bean
    public FilterRegistrationBean druidStatFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        //添加过滤规则.
        filterRegistrationBean.addUrlPatterns("/*");
        //添加不需要忽略的格式信息.
        filterRegistrationBean.addInitParameter(
                "exclusions","/static/*,*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid,/druid/*");
        //用于session监控页面的用户名显示 需要登录后主动将username注入到session里
        filterRegistrationBean.addInitParameter("principalSessionName","username");
        return filterRegistrationBean;
    }

    /**
     * druid数据库连接池监控
     */
    @Bean
    public DruidStatInterceptor druidStatInterceptor() {
        return new DruidStatInterceptor();
    }


    @Bean
    public JdkRegexpMethodPointcut druidStatPointcut(){
        JdkRegexpMethodPointcut druidStatPointcut = new JdkRegexpMethodPointcut();
        String patterns = "www.sh.com.service.*";
        //可以set多个
        druidStatPointcut.setPatterns(patterns);
        return druidStatPointcut;
    }

    /**
     * druid数据库连接池监控
     */
    @Bean
    public BeanTypeAutoProxyCreator beanTypeAutoProxyCreator() {
        BeanTypeAutoProxyCreator beanTypeAutoProxyCreator = new BeanTypeAutoProxyCreator();
        beanTypeAutoProxyCreator.setTargetBeanType(DruidDataSource.class);
        beanTypeAutoProxyCreator.setInterceptorNames("druidStatInterceptor");
        return beanTypeAutoProxyCreator;
    }

    /**
     * druid 为druidStatPointcut添加拦截
     * @return
     */
    @Bean
    public Advisor druidStatAdvisor() {
        return new DefaultPointcutAdvisor(druidStatPointcut(), druidStatInterceptor());
    }
    
    @Bean
    public WallConfig wallConfig() {
    	//设置运行批量执行sql
    	WallConfig wallConfig = new WallConfig();
    	wallConfig.setMultiStatementAllow(true);
        return wallConfig;
    }

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
