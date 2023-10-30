-- 注意：该页面对应的前台目录为views/employeecompany文件夹下
-- 如果你想更改到其他目录，请修改sql中component字段对应的值


INSERT INTO sys_permission(id, parent_id, name, url, component, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_route, is_leaf, keep_alive, hidden, hide_tab, description, status, del_flag, rule_flag, create_by, create_time, update_by, update_time, internal_or_external) 
VALUES ('2023103110333490560', NULL, '员工和公司', '/employeecompany/testEmployeeCompanyList', 'employeecompany/TestEmployeeCompanyList', NULL, NULL, 0, NULL, '1', 0.00, 0, NULL, 1, 0, 0, 0, 0, NULL, '1', 0, 0, 'admin', '2023-10-31 10:33:56', NULL, NULL, 0);

-- 权限控制sql
-- 新增
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2023103110333500561', '2023103110333490560', '添加员工和公司', NULL, NULL, 0, NULL, NULL, 2, 'employeecompany:test_employee_company:add', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2023-10-31 10:33:56', NULL, NULL, 0, 0, '1', 0);
-- 编辑
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2023103110333500562', '2023103110333490560', '编辑员工和公司', NULL, NULL, 0, NULL, NULL, 2, 'employeecompany:test_employee_company:edit', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2023-10-31 10:33:56', NULL, NULL, 0, 0, '1', 0);
-- 删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2023103110333500563', '2023103110333490560', '删除员工和公司', NULL, NULL, 0, NULL, NULL, 2, 'employeecompany:test_employee_company:delete', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2023-10-31 10:33:56', NULL, NULL, 0, 0, '1', 0);
-- 批量删除
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2023103110333500564', '2023103110333490560', '批量删除员工和公司', NULL, NULL, 0, NULL, NULL, 2, 'employeecompany:test_employee_company:deleteBatch', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2023-10-31 10:33:56', NULL, NULL, 0, 0, '1', 0);
-- 导出excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2023103110333500565', '2023103110333490560', '导出excel_员工和公司', NULL, NULL, 0, NULL, NULL, 2, 'employeecompany:test_employee_company:exportXls', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2023-10-31 10:33:56', NULL, NULL, 0, 0, '1', 0);
-- 导入excel
INSERT INTO sys_permission(id, parent_id, name, url, component, is_route, component_name, redirect, menu_type, perms, perms_type, sort_no, always_show, icon, is_leaf, keep_alive, hidden, hide_tab, description, create_by, create_time, update_by, update_time, del_flag, rule_flag, status, internal_or_external)
VALUES ('2023103110333500566', '2023103110333490560', '导入excel_员工和公司', NULL, NULL, 0, NULL, NULL, 2, 'employeecompany:test_employee_company:importExcel', '1', NULL, 0, NULL, 1, 0, 0, 0, NULL, 'admin', '2023-10-31 10:33:56', NULL, NULL, 0, 0, '1', 0);