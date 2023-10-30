package org.jeecg.modules.demo.company.controller;

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
import org.jeecg.modules.demo.company.entity.TestCompany;
import org.jeecg.modules.demo.company.service.ITestCompanyService;

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
 * @Description: 公司信息
 * @Author: jeecg-boot
 * @Date:   2023-10-31
 * @Version: V1.0
 */
@Api(tags="公司信息")
@RestController
@RequestMapping("/company/testCompany")
@Slf4j
public class TestCompanyController extends JeecgController<TestCompany, ITestCompanyService> {
	@Autowired
	private ITestCompanyService testCompanyService;
	
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
	 * @param testCompany
	 * @return
	 */
	@AutoLog(value = "公司信息-添加")
	@ApiOperation(value="公司信息-添加", notes="公司信息-添加")
	@RequiresPermissions("company:test_company:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody TestCompany testCompany) {
		testCompanyService.save(testCompany);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param testCompany
	 * @return
	 */
	@AutoLog(value = "公司信息-编辑")
	@ApiOperation(value="公司信息-编辑", notes="公司信息-编辑")
	@RequiresPermissions("company:test_company:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody TestCompany testCompany) {
		testCompanyService.updateById(testCompany);
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
	@RequiresPermissions("company:test_company:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		testCompanyService.removeById(id);
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
	@RequiresPermissions("company:test_company:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.testCompanyService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
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
    * 导出excel
    *
    * @param request
    * @param testCompany
    */
    @RequiresPermissions("company:test_company:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, TestCompany testCompany) {
        return super.exportXls(request, testCompany, TestCompany.class, "公司信息");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    @RequiresPermissions("company:test_company:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, TestCompany.class);
    }

}
