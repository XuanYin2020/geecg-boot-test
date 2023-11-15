package org.jeecg.modules.demo.company.service;

import org.jeecg.modules.demo.company.entity.TestCompanyEmployee;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @Description: 入职的员工
 * @Author: jeecg-boot
 * @Date:   2023-11-01
 * @Version: V1.0
 */
public interface ITestCompanyEmployeeService extends IService<TestCompanyEmployee> {

	/**
	 * 通过主表id查询子表数据
	 *
	 * @param mainId 主表id
	 * @return List<TestCompanyEmployee>
	 */
	public List<TestCompanyEmployee> selectByMainId(String mainId);

	/**
	 * 获得到所有的副表
	 */
	public List<TestCompanyEmployee> getAll();

	/**
	 * 添加一条记录
	 */
	public void addOneRecord(TestCompanyEmployee testCompanyEmployee);

	/**
	 * 根据id 删除一条记录
	 */
    public void deleteOneRecord(TestCompanyEmployee testcompanyemployee);

	/**
	 * 根据子表id查询数据
	 * @param id
	 * @return
	 */
	TestCompanyEmployee selectById(String id);

	void editOneReocrd(TestCompanyEmployee testcompanyemployee);
}
