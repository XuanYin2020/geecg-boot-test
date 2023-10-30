import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
//列表数据
export const columns: BasicColumn[] = [
   {
    title: '员工id',
    align:"center",
    dataIndex: 'employeeId_dictText'
   },
   {
    title: '公司id',
    align:"center",
    dataIndex: 'companyId_dictText'
   },
];
//查询数据
export const searchFormSchema: FormSchema[] = [
];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '员工id',
    field: 'employeeId',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"test_employee,name,id"
     },
  },
  {
    label: '公司id',
    field: 'companyId',
    component: 'JDictSelectTag',
    componentProps:{
        dictCode:"test_company,name,id"
     },
  },
	// TODO 主键隐藏字段，目前写死为ID
	{
	  label: '',
	  field: 'id',
	  component: 'Input',
	  show: false
	},
];



/**
* 流程表单调用这个方法获取formSchema
* @param param
*/
export function getBpmFormSchema(_formData): FormSchema[]{
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}