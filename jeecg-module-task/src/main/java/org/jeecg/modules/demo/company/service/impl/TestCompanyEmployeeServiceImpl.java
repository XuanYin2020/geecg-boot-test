package org.jeecg.modules.demo.company.service.impl;

import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.XSlf4j;
import org.jeecg.modules.demo.company.entity.TestCompanyEmployee;
import org.jeecg.modules.demo.company.mapper.TestCompanyEmployeeMapper;
import org.jeecg.modules.demo.company.service.ITestCompanyEmployeeService;
import org.springframework.stereotype.Service;

import java.util.*;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 入职的员工
 * @Author: jeecg-boot
 * @Date:   2023-11-01
 * @Version: V1.0
 */
@Service
@Slf4j
public class TestCompanyEmployeeServiceImpl extends ServiceImpl<TestCompanyEmployeeMapper, TestCompanyEmployee> implements ITestCompanyEmployeeService {
	
	@Autowired
	private TestCompanyEmployeeMapper testCompanyEmployeeMapper;
	
	@Override
	public List<TestCompanyEmployee> selectByMainId(String mainId) {
		return testCompanyEmployeeMapper.selectByMainId(mainId);
	}

    @Override
    public List<TestCompanyEmployee> getAll() {
		List<TestCompanyEmployee> testCompanyEmployees = testCompanyEmployeeMapper.allTableValue();
		log.info(testCompanyEmployees.toString());
		return testCompanyEmployees;

    }

    @Override
    public void addOneRecord(TestCompanyEmployee testCompanyEmployee) {
		Map<String, Object> params = new HashMap<>();
		params.put("companyId", testCompanyEmployee.getId());
		params.put("employeeId",  testCompanyEmployee.getEmployeeId());
		params.put("takingTime",testCompanyEmployee.getTakingTime());
		params.put("partment", testCompanyEmployee.getPartment());
		Random random = new Random();
		int randomInt = random.nextInt();
		params.put("id", String.valueOf( Math.abs(randomInt) ) );
		log.info("id:"+Math.abs(randomInt) );
		testCompanyEmployeeMapper.addOneRecord(params);

    }

    @Override
    public void deleteOneRecord(TestCompanyEmployee testcompanyemployee) {
		String id = testcompanyemployee.getId();
		log.info("The id is:"+id);

		testCompanyEmployeeMapper.deleteOneRecord(id);

    }

    @Override
    public TestCompanyEmployee selectById(String id) {

		TestCompanyEmployee testCompanyEmployee = testCompanyEmployeeMapper.selectById(id);
		return testCompanyEmployee;
    }
}
