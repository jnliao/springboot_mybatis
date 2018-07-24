package www.sh.com.service.impl;

import www.sh.com.pojo.domain.Module;
import www.sh.com.mapper.ModuleMapper;
import www.sh.com.service.IModuleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统模块表 服务实现类
 * </p>
 *
 * @author liaojinneng
 * @since 2018-07-24
 */
@Service
public class ModuleServiceImpl extends ServiceImpl<ModuleMapper, Module> implements IModuleService {
	
}
