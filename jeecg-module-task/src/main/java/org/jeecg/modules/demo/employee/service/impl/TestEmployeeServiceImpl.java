package org.jeecg.modules.demo.employee.service.impl;

import org.jeecg.modules.demo.employee.entity.TestEmployee;
import org.jeecg.modules.demo.employee.mapper.TestEmployeeMapper;
import org.jeecg.modules.demo.employee.service.ITestEmployeeService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 人员信息
 * @Author: jeecg-boot
 * @Date:   2023-10-31
 * @Version: V1.0
 */
@Service
public class TestEmployeeServiceImpl extends ServiceImpl<TestEmployeeMapper, TestEmployee> implements ITestEmployeeService {

}
