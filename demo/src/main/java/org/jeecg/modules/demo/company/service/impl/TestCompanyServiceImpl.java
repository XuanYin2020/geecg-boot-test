package org.jeecg.modules.demo.company.service.impl;

import org.jeecg.modules.demo.company.entity.TestCompany;
import org.jeecg.modules.demo.company.mapper.TestCompanyMapper;
import org.jeecg.modules.demo.company.service.ITestCompanyService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 公司信息
 * @Author: jeecg-boot
 * @Date:   2023-10-31
 * @Version: V1.0
 */
@Service
public class TestCompanyServiceImpl extends ServiceImpl<TestCompanyMapper, TestCompany> implements ITestCompanyService {

}
