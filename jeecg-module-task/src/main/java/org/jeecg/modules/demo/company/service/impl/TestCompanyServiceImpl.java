package org.jeecg.modules.demo.company.service.impl;

import org.jeecg.modules.demo.company.entity.TestCompany;
import org.jeecg.modules.demo.company.entity.TestCompanyEmployee;
import org.jeecg.modules.demo.company.mapper.TestCompanyEmployeeMapper;
import org.jeecg.modules.demo.company.mapper.TestCompanyMapper;
import org.jeecg.modules.demo.company.service.ITestCompanyService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.List;
import java.util.Collection;

/**
 * @Description: 公司信息
 * @Author: jeecg-boot
 * @Date:   2023-11-01
 * @Version: V1.0
 */
@Service
public class TestCompanyServiceImpl extends ServiceImpl<TestCompanyMapper, TestCompany> implements ITestCompanyService {

	@Autowired
	private TestCompanyMapper testCompanyMapper;
	@Autowired
	private TestCompanyEmployeeMapper testCompanyEmployeeMapper;
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveMain(TestCompany testCompany, List<TestCompanyEmployee> testCompanyEmployeeList) {
		testCompanyMapper.insert(testCompany);
		if(testCompanyEmployeeList!=null && testCompanyEmployeeList.size()>0) {
			for(TestCompanyEmployee entity:testCompanyEmployeeList) {
				//外键设置
				entity.setCompanyId(testCompany.getId());
				testCompanyEmployeeMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateMain(TestCompany testCompany,List<TestCompanyEmployee> testCompanyEmployeeList) {
		testCompanyMapper.updateById(testCompany);
		
		//1.先删除子表数据
		testCompanyEmployeeMapper.deleteByMainId(testCompany.getId());
		
		//2.子表数据重新插入
		if(testCompanyEmployeeList!=null && testCompanyEmployeeList.size()>0) {
			for(TestCompanyEmployee entity:testCompanyEmployeeList) {
				//外键设置
				entity.setCompanyId(testCompany.getId());
				testCompanyEmployeeMapper.insert(entity);
			}
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delMain(String id) {
		testCompanyEmployeeMapper.deleteByMainId(id);
		testCompanyMapper.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delBatchMain(Collection<? extends Serializable> idList) {
		for(Serializable id:idList) {
			testCompanyEmployeeMapper.deleteByMainId(id.toString());
			testCompanyMapper.deleteById(id);
		}
	}
	
}
