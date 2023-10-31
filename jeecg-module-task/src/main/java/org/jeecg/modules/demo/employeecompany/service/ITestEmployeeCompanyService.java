package org.jeecg.modules.demo.employeecompany.service;

import org.jeecg.modules.demo.employeecompany.entity.TestEmployeeCompany;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 员工和公司
 * @Author: jeecg-boot
 * @Date:   2023-10-31
 * @Version: V1.0
 */
public interface ITestEmployeeCompanyService extends IService<TestEmployeeCompany> {


    List<String> getByEmployeeID(String employeeId);

    List<String> queryByCompanyID(String companyId);
}
