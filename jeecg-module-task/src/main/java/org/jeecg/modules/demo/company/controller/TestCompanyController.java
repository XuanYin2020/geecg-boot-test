package org.jeecg.modules.demo.company.controller;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.modules.demo.employee.entity.TestEmployee;
import org.jeecg.modules.demo.employee.service.ITestEmployeeService;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.vo.LoginUser;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.demo.company.entity.TestCompanyEmployee;
import org.jeecg.modules.demo.company.entity.TestCompany;
import org.jeecg.modules.demo.company.vo.TestCompanyPage;
import org.jeecg.modules.demo.company.service.ITestCompanyService;
import org.jeecg.modules.demo.company.service.ITestCompanyEmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;


 /**
 * @Description: 公司信息
 * @Author: jeecg-boot
 * @Date:   2023-11-01
 * @Version: V1.0
 */
@Api(tags="公司信息")
@RestController
@RequestMapping("/company/testCompany")
@Slf4j
public class TestCompanyController {
	@Autowired
	private ITestCompanyService testCompanyService;
	@Autowired
	private ITestCompanyEmployeeService testCompanyEmployeeService;
	 @Autowired
	 private ITestEmployeeService testEmployeeService;
	/**
	 * 分页列表查询
	 *
	 * @param testCompany
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "公司信息-分页列表查询")
	@ApiOperation(value="公司信息-分页列表查询", notes="公司信息-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<TestCompany>> queryPageList(TestCompany testCompany,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<TestCompany> queryWrapper = QueryGenerator.initQueryWrapper(testCompany, req.getParameterMap());
		Page<TestCompany> page = new Page<TestCompany>(pageNo, pageSize);
		IPage<TestCompany> pageList = testCompanyService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param testCompanyPage
	 * @return
	 */
	@AutoLog(value = "公司信息-添加")
	@ApiOperation(value="公司信息-添加", notes="公司信息-添加")
    //@RequiresPermissions("company:test_company:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody TestCompanyPage testCompanyPage) {
		TestCompany testCompany = new TestCompany();
		BeanUtils.copyProperties(testCompanyPage, testCompany);//拷贝属性
		log.info("公司信息-添加");
		testCompany.setNumberEmplyee(testCompanyPage.getTestCompanyEmployeeList().size());
		testCompanyService.saveMain(testCompany, testCompanyPage.getTestCompanyEmployeeList());
		return Result.OK("添加成功！");
	}

	 /**
	  * 添加新的员工
	  */
	 @AutoLog(value = "添加新的员工")
	 @ApiOperation(value="添加新的员工", notes="添加新的员工")
	 //@RequiresPermissions("company:test_company:edit")
	 @RequestMapping(value = "/addOneEmployee", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String>  addOneEmployee(@RequestBody TestCompanyEmployee testcompanyemployee){
		 log.info("添加新的员工");
		 log.info(testcompanyemployee.toString());
		 testCompanyEmployeeService.addOneRecord(testcompanyemployee);
		 return Result.OK("添加成功!");
	}

	 /**
	  * 删除指定员工
	  * 根据id删除company employee中的数据
	  */
	 @AutoLog(value = "删除指定员工")
	 @ApiOperation(value="删除指定员工", notes="删除指定员工")
	 //@RequiresPermissions("company:test_company:edit")
	 @RequestMapping(value = "/deleteOneEmployee", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> deleteOneEmployee(@RequestBody TestCompanyEmployee testcompanyemployee){
		 log.info("删除指定Id员工");
		 testCompanyEmployeeService.deleteOneRecord(testcompanyemployee);
		 return Result.OK("删除成功!");
	 }
	/**
	 *  编辑
	 *
	 * @param testCompanyPage
	 * @return
	 */
	@AutoLog(value = "公司信息-编辑")
	@ApiOperation(value="公司信息-编辑", notes="公司信息-编辑")
    //@RequiresPermissions("company:test_company:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody TestCompanyPage testCompanyPage) {
		log.info("公司信息-编辑");
		log.info(testCompanyPage.toString());
		TestCompany testCompany = new TestCompany();
		BeanUtils.copyProperties(testCompanyPage, testCompany);
		TestCompany testCompanyEntity = testCompanyService.getById(testCompany.getId());
		if(testCompanyEntity==null) {
			return Result.error("未找到对应数据");
		}
		//更新员工的信息
		testCompany.setNumberEmplyee(testCompanyPage.getTestCompanyEmployeeList().size());//设置员工人数
		testCompanyService.updateMain(testCompany, testCompanyPage.getTestCompanyEmployeeList());

		//目的：公司添加了员工之后，员工页面直接展示
		// 争对当前的testCompany，更新相关employee的信息
		// 更新employee信息的就职公司信息:更新company id和company name
		List<TestCompanyEmployee> allCompanyEmployee= testCompanyPage.getTestCompanyEmployeeList();
		for(TestCompanyEmployee curCompanyEmployee:allCompanyEmployee){
			//带更新的employee，更新companyid和name
			TestEmployee curEmployee =testEmployeeService.getById(curCompanyEmployee.getEmployeeId());
			if(curEmployee==null){
				continue;
			}
			String curCompanyIds = curEmployee.getCompanyIds();
			if(curCompanyIds!=null && !curCompanyIds.equals("")){
				//curEmployee.setCompanyIds(curCompanyIds+","+testCompany.getId());
				curEmployee.setCompanyName(testCompany.getName());
			}else{
				curEmployee.setCompanyIds(testCompany.getId());
				curEmployee.setCompanyName(testCompany.getName());
			}

			testEmployeeService.updateById(curEmployee);
		}
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "公司信息-通过id删除")
	@ApiOperation(value="公司信息-通过id删除", notes="公司信息-通过id删除")
    //@RequiresPermissions("company:test_company:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		testCompanyService.delMain(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "公司信息-批量删除")
	@ApiOperation(value="公司信息-批量删除", notes="公司信息-批量删除")
    //@RequiresPermissions("company:test_company:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.testCompanyService.delBatchMain(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功！");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "公司信息-通过id查询")
	@ApiOperation(value="公司信息-通过id查询", notes="公司信息-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<TestCompany> queryById(@RequestParam(name="id",required=true) String id) {
		TestCompany testCompany = testCompanyService.getById(id);
		if(testCompany==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(testCompany);

	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "入职的员工通过主表ID查询")
	@ApiOperation(value="入职的员工主表ID查询", notes="入职的员工-通主表ID查询")
	@GetMapping(value = "/queryTestCompanyEmployeeByMainId")
	public Result<List<TestCompanyEmployee>> queryTestCompanyEmployeeListByMainId(@RequestParam(name="id",required=true) String id) {
		List<TestCompanyEmployee> testCompanyEmployeeList = testCompanyEmployeeService.selectByMainId(id);
		return Result.OK(testCompanyEmployeeList);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param testCompany
    */
    //@RequiresPermissions("company:test_company:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, TestCompany testCompany) {
      // Step.1 组装查询条件查询数据
      QueryWrapper<TestCompany> queryWrapper = QueryGenerator.initQueryWrapper(testCompany, request.getParameterMap());
      LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

      //配置选中数据查询条件
      String selections = request.getParameter("selections");
      if(oConvertUtils.isNotEmpty(selections)) {
         List<String> selectionList = Arrays.asList(selections.split(","));
         queryWrapper.in("id",selectionList);
      }
      //Step.2 获取导出数据
      List<TestCompany> testCompanyList = testCompanyService.list(queryWrapper);

      // Step.3 组装pageList
      List<TestCompanyPage> pageList = new ArrayList<TestCompanyPage>();
      for (TestCompany main : testCompanyList) {
          TestCompanyPage vo = new TestCompanyPage();
          BeanUtils.copyProperties(main, vo);
          List<TestCompanyEmployee> testCompanyEmployeeList = testCompanyEmployeeService.selectByMainId(main.getId());
          vo.setTestCompanyEmployeeList(testCompanyEmployeeList);
          pageList.add(vo);
      }

      // Step.4 AutoPoi 导出Excel
      ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
      mv.addObject(NormalExcelConstants.FILE_NAME, "公司信息列表");
      mv.addObject(NormalExcelConstants.CLASS, TestCompanyPage.class);
      mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("公司信息数据", "导出人:"+sysUser.getRealname(), "公司信息"));
      mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
      return mv;
    }

    /**
    * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("company:test_company:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
      Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
      for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
          // 获取上传文件对象
          MultipartFile file = entity.getValue();
          ImportParams params = new ImportParams();
          params.setTitleRows(2);
          params.setHeadRows(1);
          params.setNeedSave(true);
          try {
              List<TestCompanyPage> list = ExcelImportUtil.importExcel(file.getInputStream(), TestCompanyPage.class, params);
              for (TestCompanyPage page : list) {
                  TestCompany po = new TestCompany();
                  BeanUtils.copyProperties(page, po);
                  testCompanyService.saveMain(po, page.getTestCompanyEmployeeList());
              }
              return Result.OK("文件导入成功！数据行数:" + list.size());
          } catch (Exception e) {
              log.error(e.getMessage(),e);
              return Result.error("文件导入失败:"+e.getMessage());
          } finally {
              try {
                  file.getInputStream().close();
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
      return Result.OK("文件导入失败！");
    }

}
