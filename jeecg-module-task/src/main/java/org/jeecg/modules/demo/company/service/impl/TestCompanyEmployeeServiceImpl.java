package org.jeecg.modules.demo.company.service.impl;

import org.jeecg.modules.demo.company.entity.TestCompanyEmployee;
import org.jeecg.modules.demo.company.mapper.TestCompanyEmployeeMapper;
import org.jeecg.modules.demo.company.service.ITestCompanyEmployeeService;
import org.springframework.stereotype.Service;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 入职的员工
 * @Author: jeecg-boot
 * @Date:   2023-11-01
 * @Version: V1.0
 */
@Service
public class TestCompanyEmployeeServiceImpl extends ServiceImpl<TestCompanyEmployeeMapper, TestCompanyEmployee> implements ITestCompanyEmployeeService {
	
	@Autowired
	private TestCompanyEmployeeMapper testCompanyEmployeeMapper;
	
	@Override
	public List<TestCompanyEmployee> selectByMainId(String mainId) {
		return testCompanyEmployeeMapper.selectByMainId(mainId);
	}
}
