package org.jeecg.modules.demo.company.vo;

import java.util.List;
import org.jeecg.modules.demo.company.entity.TestCompany;
import org.jeecg.modules.demo.company.entity.TestCompanyEmployee;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelEntity;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 公司信息
 * @Author: jeecg-boot
 * @Date:   2023-11-01
 * @Version: V1.0
 */
@Data
@ApiModel(value="test_companyPage对象", description="公司信息")
public class TestCompanyPage {

	/**主键*/
	@ApiModelProperty(value = "主键")
    private java.lang.String id;
	/**创建人*/
	@ApiModelProperty(value = "创建人")
    private java.lang.String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
	@ApiModelProperty(value = "更新人")
    private java.lang.String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**所属部门*/
	@ApiModelProperty(value = "所属部门")
    private java.lang.String sysOrgCode;
	/**公司名字*/
	@Excel(name = "公司名字", width = 15)
	@ApiModelProperty(value = "公司名字")
    private java.lang.String name;
	/**公司类型*/
	@Excel(name = "公司类型", width = 15)
	@ApiModelProperty(value = "公司类型")
    private java.lang.String type;
	/**地址*/
	@Excel(name = "地址", width = 15)
	@ApiModelProperty(value = "地址")
    private java.lang.String address;
	/**负责人*/
	@Excel(name = "负责人", width = 15)
	@ApiModelProperty(value = "负责人")
    private java.lang.String ceo;
	/**员工数量*/
	@Excel(name = "员工数量", width = 15)
	@ApiModelProperty(value = "员工数量")
    private java.lang.Integer numberEmplyee;
	/**联系信息*/
	@Excel(name = "联系信息", width = 15)
	@ApiModelProperty(value = "联系信息")
    private java.lang.String contact;

	@ExcelCollection(name="入职的员工")
	@ApiModelProperty(value = "入职的员工")
	private List<TestCompanyEmployee> testCompanyEmployeeList;

}
