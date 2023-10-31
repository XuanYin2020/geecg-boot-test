package org.jeecg.modules.demo.employeecompany.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.employeecompany.entity.TestEmployeeCompany;
import org.jeecg.modules.demo.employeecompany.service.ITestEmployeeCompanyService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: 员工和公司
 * @Author: jeecg-boot
 * @Date:   2023-10-31
 * @Version: V1.0
 */
@Api(tags="员工和公司")
@RestController
@RequestMapping("/employeecompany/testEmployeeCompany")
@Slf4j
public class TestEmployeeCompanyController extends JeecgController<TestEmployeeCompany, ITestEmployeeCompanyService> {
	@Autowired
	private ITestEmployeeCompanyService testEmployeeCompanyService;
	
	/**
	 * 分页列表查询
	 *
	 * @param testEmployeeCompany
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "员工和公司-分页列表查询")
	@ApiOperation(value="员工和公司-分页列表查询", notes="员工和公司-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<TestEmployeeCompany>> queryPageList(TestEmployeeCompany testEmployeeCompany,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<TestEmployeeCompany> queryWrapper = QueryGenerator.initQueryWrapper(testEmployeeCompany, req.getParameterMap());
		Page<TestEmployeeCompany> page = new Page<TestEmployeeCompany>(pageNo, pageSize);
		IPage<TestEmployeeCompany> pageList = testEmployeeCompanyService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param testEmployeeCompany
	 * @return
	 */
	@AutoLog(value = "员工和公司-添加")
	@ApiOperation(value="员工和公司-添加", notes="员工和公司-添加")
	@RequiresPermissions("employeecompany:test_employee_company:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody TestEmployeeCompany testEmployeeCompany) {
		testEmployeeCompanyService.save(testEmployeeCompany);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param testEmployeeCompany
	 * @return
	 */
	@AutoLog(value = "员工和公司-编辑")
	@ApiOperation(value="员工和公司-编辑", notes="员工和公司-编辑")
	@RequiresPermissions("employeecompany:test_employee_company:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody TestEmployeeCompany testEmployeeCompany) {
		testEmployeeCompanyService.updateById(testEmployeeCompany);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "员工和公司-通过id删除")
	@ApiOperation(value="员工和公司-通过id删除", notes="员工和公司-通过id删除")
	@RequiresPermissions("employeecompany:test_employee_company:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		testEmployeeCompanyService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "员工和公司-批量删除")
	@ApiOperation(value="员工和公司-批量删除", notes="员工和公司-批量删除")
	@RequiresPermissions("employeecompany:test_employee_company:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.testEmployeeCompanyService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "员工和公司-通过id查询")
	@ApiOperation(value="员工和公司-通过id查询", notes="员工和公司-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<TestEmployeeCompany> queryById(@RequestParam(name="id",required=true) String id) {
		TestEmployeeCompany testEmployeeCompany = testEmployeeCompanyService.getById(id);
		if(testEmployeeCompany==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(testEmployeeCompany);
	}

	 /**
	  * 通过employee的id, 查询就职公司的id
	  *
	  * @param employeeId
	  * @return
	  */
	@ApiOperation(value = "员工和公司-通过员工的id查询", notes = "员工和公司-通过员工的id查询")
	@GetMapping(value="/queryByEmployeeID")
	public Result<List<String>> queryByEmployeeID(@RequestParam(name="id",required=true) String employeeId){
		log.info("员工和公司-通过员工的id查询",employeeId);
		List<String> companyId =  testEmployeeCompanyService.getByEmployeeID(employeeId);
		return Result.ok(companyId);
	}
	 /**
	  * 通过company的id, 查询就职公司员工的id
	  *
	  * @param companyId
	  * @return
	  */
	 @ApiOperation(value = "员工和公司-通过公司的id查询", notes = "员工和公司-通过公司的id查询")
	 @GetMapping(value="/queryByCompanyID")
	 public Result<List<String>> queryByCompanyID(@RequestParam(name="id",required=true) String companyId){
		 log.info("员工和公司-通过公司的id查询",companyId);
		 List<String> employeeId =  testEmployeeCompanyService.queryByCompanyID(companyId);
		 return Result.ok(employeeId);
	 }

    /**
    * 导出excel
    *
    * @param request
    * @param testEmployeeCompany
    */
    @RequiresPermissions("employeecompany:test_employee_company:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, TestEmployeeCompany testEmployeeCompany) {
        return super.exportXls(request, testEmployeeCompany, TestEmployeeCompany.class, "员工和公司");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("employeecompany:test_employee_company:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, TestEmployeeCompany.class);
    }

}
