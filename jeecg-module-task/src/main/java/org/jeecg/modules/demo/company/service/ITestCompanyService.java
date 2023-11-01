package org.jeecg.modules.demo.company.service;

import org.jeecg.modules.demo.company.entity.TestCompanyEmployee;
import org.jeecg.modules.demo.company.entity.TestCompany;
import com.baomidou.mybatisplus.extension.service.IService;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @Description: 公司信息
 * @Author: jeecg-boot
 * @Date:   2023-11-01
 * @Version: V1.0
 */
public interface ITestCompanyService extends IService<TestCompany> {

	/**
	 * 添加一对多
	 *
	 * @param testCompany
	 * @param testCompanyEmployeeList
	 */
	public void saveMain(TestCompany testCompany,List<TestCompanyEmployee> testCompanyEmployeeList) ;
	
	/**
	 * 修改一对多
	 *
   * @param testCompany
   * @param testCompanyEmployeeList
	 */
	public void updateMain(TestCompany testCompany,List<TestCompanyEmployee> testCompanyEmployeeList);
	
	/**
	 * 删除一对多
	 *
	 * @param id
	 */
	public void delMain (String id);
	
	/**
	 * 批量删除一对多
	 *
	 * @param idList
	 */
	public void delBatchMain (Collection<? extends Serializable> idList);
	
}
