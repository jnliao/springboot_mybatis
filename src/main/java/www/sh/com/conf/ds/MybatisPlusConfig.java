package www.sh.com.conf.ds;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MybatisPlus配置
 *
 * @author Administrator
 */
@Configuration
@MapperScan(basePackages = MybatisPlusConfig.PACKAGE)
public class MybatisPlusConfig {
	static final String PACKAGE = "www.sh.com.mapper";
	
    @Autowired
    private DruidProperties druidProperties;

    private DruidDataSource dataSourceCommon(){
        DruidDataSource dataSource = new DruidDataSource();
        druidProperties.config(dataSource);
        return dataSource;
    }

    /**
     * 连接数据源
     */
    @Bean
    public DruidDataSource singleDatasource() {
        return dataSourceCommon();
    }

    /**
     * mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
    
    /**
     * mybatis-plus乐观锁插件
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }
    
}
