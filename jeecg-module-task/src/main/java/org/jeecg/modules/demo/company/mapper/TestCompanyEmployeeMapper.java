package org.jeecg.modules.demo.company.mapper;

import java.util.List;
import org.jeecg.modules.demo.company.entity.TestCompanyEmployee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 入职的员工
 * @Author: jeecg-boot
 * @Date:   2023-11-01
 * @Version: V1.0
 */
public interface TestCompanyEmployeeMapper extends BaseMapper<TestCompanyEmployee> {

	/**
	 * 通过主表id删除子表数据
	 *
	 * @param mainId 主表id
	 * @return boolean
	 */
	public boolean deleteByMainId(@Param("mainId") String mainId);

  /**
   * 通过主表id查询子表数据
   *
   * @param mainId 主表id
   * @return List<TestCompanyEmployee>
   */
	public List<TestCompanyEmployee> selectByMainId(@Param("mainId") String mainId);
}
