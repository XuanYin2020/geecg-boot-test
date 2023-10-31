package org.jeecg.modules.demo.employeecompany.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.demo.employeecompany.entity.TestEmployeeCompany;
import org.jeecg.modules.demo.employeecompany.mapper.TestEmployeeCompanyMapper;
import org.jeecg.modules.demo.employeecompany.service.ITestEmployeeCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 员工和公司
 * @Author: jeecg-boot
 * @Date:   2023-10-31
 * @Version: V1.0
 */
@Service
@Slf4j
public class TestEmployeeCompanyServiceImpl extends ServiceImpl<TestEmployeeCompanyMapper, TestEmployeeCompany> implements ITestEmployeeCompanyService {

    @Autowired
    TestEmployeeCompanyMapper testEmployeeCompanyMapper;

    @Override
    public List<String> getByEmployeeID(String employeeId) {
        QueryWrapper<TestEmployeeCompany> companyQueryWrapper = new QueryWrapper<>();
        companyQueryWrapper.eq("employee_id", employeeId);
        List<TestEmployeeCompany> employeeCompanyList = testEmployeeCompanyMapper.selectList(companyQueryWrapper);

        log.info(employeeCompanyList.toString());
        List<String> reCompanyIdList = new ArrayList<>();
        employeeCompanyList.forEach(employeeCompany->reCompanyIdList.add(employeeCompany.getCompanyId()));
        log.info(reCompanyIdList.toString());

        return reCompanyIdList;
    }
}
