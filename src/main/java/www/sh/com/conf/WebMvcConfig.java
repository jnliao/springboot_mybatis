package www.sh.com.conf;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import www.sh.com.intercepter.ApiLogInterceptor;
import www.sh.com.intercepter.LoginInterceptor;
import www.sh.com.intercepter.OpenApiInterceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private ApiLogInterceptor apiLogInterceptor;
    @Autowired
    private LoginInterceptor loginInterceptor;
    @Autowired
    private OpenApiInterceptor openApiInterceptor;



    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiLogInterceptor).addPathPatterns("/**");
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/v2/**","/css/**", "/js/**", "/fonts/**", "/index")
                .excludePathPatterns("/login/**")
                .excludePathPatterns("/swagger-ui.html","/swagger-resources/**")
                .excludePathPatterns("/druid/**")
                .excludePathPatterns("/bigClass/outInterface/**");
        
        registry.addInterceptor(openApiInterceptor).addPathPatterns("/bigClass/outInterface/**");
        super.addInterceptors(registry);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        //添加fastjson 的配置信息 比如 是否要格式化 返回的json数据
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue);
        fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
        // 解决乱码的问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastMediaTypes);
        converters.add(fastJsonHttpMessageConverter);
    }
}
