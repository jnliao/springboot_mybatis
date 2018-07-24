package www.sh.com.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import www.sh.com.mapper.OpenApiConfigMapper;
import www.sh.com.pojo.domain.OpenApiConfig;
import www.sh.com.service.IOpenApiConfigService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liaojinneng
 * @since 2018-07-20
 */
@Service
public class OpenApiConfigServiceImpl extends ServiceImpl<OpenApiConfigMapper, OpenApiConfig> implements IOpenApiConfigService {
	
}
