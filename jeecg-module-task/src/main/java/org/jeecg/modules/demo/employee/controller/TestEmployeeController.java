package org.jeecg.modules.demo.employee.controller;

import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONObject;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.company.entity.TestCompany;
import org.jeecg.modules.demo.employee.entity.TestEmployee;
import org.jeecg.modules.demo.employee.service.ITestEmployeeService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.demo.employeecompany.entity.TestEmployeeCompany;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: 人员信息
 * @Author: jeecg-boot
 * @Date:   2023-10-31
 * @Version: V1.0
 */
@Api(tags="人员信息")
@RestController
@RequestMapping("/employee/testEmployee")
@Slf4j
public class TestEmployeeController extends JeecgController<TestEmployee, ITestEmployeeService> {
	@Autowired
	private ITestEmployeeService testEmployeeService;

	 @Autowired
	 private RestTemplate restTemplate;

	/**
	 * 分页列表查询
	 *
	 * @param testEmployee
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "人员信息-分页列表查询")
	@ApiOperation(value="人员信息-分页列表查询", notes="人员信息-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<TestEmployee>> queryPageList(TestEmployee testEmployee,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<TestEmployee> queryWrapper = QueryGenerator.initQueryWrapper(testEmployee, req.getParameterMap());
		Page<TestEmployee> page = new Page<TestEmployee>(pageNo, pageSize);
		IPage<TestEmployee> pageList = testEmployeeService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param testEmployee
	 * @return
	 */
	@AutoLog(value = "人员信息-添加")
	@ApiOperation(value="人员信息-添加", notes="人员信息-添加")
	@RequiresPermissions("employee:test_employee:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody TestEmployee testEmployee) {
		testEmployeeService.save(testEmployee);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param testEmployee
	 * @return
	 */
	@AutoLog(value = "人员信息-编辑")
	@ApiOperation(value="人员信息-编辑", notes="人员信息-编辑")
	@RequiresPermissions("employee:test_employee:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody TestEmployee testEmployee) {
		testEmployeeService.updateById(testEmployee);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "人员信息-通过id删除")
	@ApiOperation(value="人员信息-通过id删除", notes="人员信息-通过id删除")
	@RequiresPermissions("employee:test_employee:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		testEmployeeService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "人员信息-批量删除")
	@ApiOperation(value="人员信息-批量删除", notes="人员信息-批量删除")
	@RequiresPermissions("employee:test_employee:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.testEmployeeService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "人员信息-通过id查询")
	@ApiOperation(value="人员信息-通过id查询", notes="人员信息-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<TestEmployee> queryById(@RequestParam(name="id",required=true) String id) {
		TestEmployee testEmployee = testEmployeeService.getById(id);
		if(testEmployee==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(testEmployee);
	}

	 /**
	  * 通过id,查询人员就职公司
	  *
	  * @param id
	  * @return
	  */
	 @ApiOperation(value="人员信息-通过id查询就职公司", notes="人员信息-通过id查询就职公司")
	 @GetMapping(value = "/queryEmployeeCompanyById")
	public Result<List<Object>> queryEmployeeCompanyById(@RequestParam(name="id",required=true) String id){
		 /**
		  * restTemplate 请求其他服务（带token认证，参数格式json）
		  */
		 log.info(id);
		 String url = "http://localhost:8080/jeecg-boot/employeecompany/testEmployeeCompany" +
				 "/queryByEmployeeID?id={id}";
		 //Header
		 HttpHeaders headers = new HttpHeaders();
		 headers.add("X-Access-Token",
					 "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9." +
							 "eyJleHAiOjE2OTg5MzY4MzEsInVzZXJuYW1lIjoiYWRtaW4ifQ." +
							 "sZJgLdH43YaUmGKMW0DqvpZG75lQ5EZ9UqoKBpQ4WUk");
		 HttpEntity httpEntity = new HttpEntity(headers);
		 //请求
		 ResponseEntity<Result> exchange = restTemplate.exchange(url, HttpMethod.GET, httpEntity,
				 												Result.class,id);
		 Result body = exchange.getBody();
		 Object companyIdObject = body.getResult();
		 List<String> companyIds = (List<String>)companyIdObject;
		 log.info("employeecompany远程调用数据信息：{}", companyIds.toString());

		 //根据company的id获得公司的信息
		 log.info("根据company的id获得公司的信息");
		 String url2 = "http://localhost:8080/jeecg-boot/company/testCompany/queryById?id={id}";
		 //TODO 请求
		 List<Object> reObject = new LinkedList<>();
		 companyIds.forEach(companyId->
				 reObject.add(restTemplate.exchange(url2, HttpMethod.GET, httpEntity, Result.class,companyId)
						 .getBody()
						 .getResult())
		 );
		 log.info("company远程调用数据信息：{}", reObject.toString());
		 return Result.OK(reObject);
	}


    /**
    * 导出excel
    *
    * @param request
    * @param testEmployee
    */
    @RequiresPermissions("employee:test_employee:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, TestEmployee testEmployee) {
        return super.exportXls(request, testEmployee, TestEmployee.class, "人员信息");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("employee:test_employee:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, TestEmployee.class);
    }

}
