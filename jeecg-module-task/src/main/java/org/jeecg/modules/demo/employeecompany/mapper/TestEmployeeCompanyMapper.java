package org.jeecg.modules.demo.employeecompany.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.demo.employeecompany.entity.TestEmployeeCompany;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @Description: 员工和公司
 * @Author: jeecg-boot
 * @Date:   2023-10-31
 * @Version: V1.0
 */
@Repository
public interface TestEmployeeCompanyMapper extends BaseMapper<TestEmployeeCompany> {

}
