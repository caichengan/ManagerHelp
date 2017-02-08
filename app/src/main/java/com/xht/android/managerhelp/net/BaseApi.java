package com.xht.android.managerhelp.net;

public class BaseApi {

    /*signingConfigs {
        release {
            storeFile file('D:/xht/xhtmanager.jks')
            keyAlias 'xhtkey'
            keyPassword 'xhtcca'
            storePassword 'xiaohoutaicca'
        }
    }*/

    //检查更新
	public static final String CHECK_VERSION_URL = "http://www.xiaohoutai.com.cn:8888/XHT/business/ApkmanagerController/updateApkVersion";

    //首页数据
    public static final String MAIN_URL = "http://www.xiaohoutai.com.cn:8888/XHT/employeeController/loadMainPageCount";

    //通讯录客户
    public static final String CONTACTSPOST_URL = "http://www.xiaohoutai.com.cn:8888/XHT/employeeController/loadMailListCustomer";
    //通讯录内部员工
    public static final String CONTACTS_GONGSI_URL = "http://www.xiaohoutai.com.cn:8888/XHT/homeOrderDetailController/AddressBookEmployeeDetail";

    //登录提交验证
    public static final String LOGIN_URL="http://www.xiaohoutai.com.cn:8888/XHT/employeeController/ManagerLogin";

    //修改账户
    public static final String MANAGER_ZH_URL="http://www.xiaohoutai.com.cn:8888/XHT/employeeController/managerModifyTelOrName";

    //修改密码
    public static final String MANAGER_MIMA_URL="http://www.xiaohoutai.com.cn:8888/XHT/employeeController/managerModifyPw";


    //访问本日订单
    public static final String ORDER_DAY_URL="http://www.xiaohoutai.com.cn:8888/XHT/employeeController/newIncreaseOrderToday";

    //访问本周
    public static final String ORDER_WEEk_URL="http://www.xiaohoutai.com.cn:8888/XHT/employeeController/newIncreaseOrderCurrentWeek";

    //访问本月
    public static final String ORDER_MONTH_URL="http://www.xiaohoutai.com.cn:8888/XHT/employeeController/newIncreaseOrderCurrentMonth";


    //访问本日收款量
    public static final String MONEY_DAY_URL="http://www.xiaohoutai.com.cn:8888/XHT/employeeController/receivablesToday";

    //访问本周
    public static final String MONEY_WEEk_URL="http://www.xiaohoutai.com.cn:8888/XHT/employeeController/receivablesCurrentWeek";

    //访问本月
    public static final String MONEY_MONTH_URL="http://www.xiaohoutai.com.cn:8888/XHT/employeeController/receivablesCurrentMonth";


    //访问本日步骤量
    public static final String STEP_DAY_URL="http://www.xiaohoutai.com.cn:8888/XHT/employeeController/permitToday";

    //访问本周
    public static final String STEP_WEEk_URL="http://www.xiaohoutai.com.cn:8888/XHT/employeeController/permitCurrentWeek";

    //访问本月
    public static final String STEP_MONTH_URL="http://www.xiaohoutai.com.cn:8888/XHT/employeeController/permitCurrentMonth";

    //访问本日低分预警
    public static final String WARNING_DAY_URL="http://www.xiaohoutai.com.cn:8888/XHT/employeeController/lowLevelWarningNotDeal?k=t";

    //访问本周
    public static final String WARNING_WEEk_URL="http://www.xiaohoutai.com.cn:8888/XHT/employeeController/lowLevelWarningNotDeal?k=w";

    //访问本月
    public static final String WARNING_MONTH_URL="http://www.xiaohoutai.com.cn:8888/XHT/employeeController/lowLevelWarningNotDeal?k=m";


    //访问本日业绩
    public static final String PERFORM_DAY_URL="http://www.xiaohoutai.com.cn:8888/XHT/employeeController/achievementPermit?k=t";

    //访问本周
    public static final String PERFORM_WEEK_URL="http://www.xiaohoutai.com.cn:8888/XHT/employeeController/achievementPermit?k=w";

    //访问本月
    public static final String PERFORM_MONTH_URL="http://www.xiaohoutai.com.cn:8888/XHT/employeeController/achievementPermit?k=m";

    //已处理
    public static final String WARNING_NONE_URL="http://www.xiaohoutai.com.cn:8888/XHT/employeeController/lowLevelWarningDealed";

    //点击确认
    public static final String WARNING_SURE_URL="http://www.xiaohoutai.com.cn:8888/XHT/employeeController/dealLowLevelWarningNotDeal";


    //点击添加通讯录成员
    public static final String ADD_CONTACTS_URL="http://www.xiaohoutai.com.cn:8888/XHT/employeeController/addNewContact";

    //上传图片
    public static final String LOAD_PICTURE_URL="";

    //获取所有的公司所有信息
    public static final String GET_MYCUSTOMER_URL="http://www.xiaohoutai.com.cn:8888/XHT/employeeController/loadAllCompanyInfo" ;



    //获取公司人员信息
    public static final String COMPLETE_NAME_URL="http://www.xiaohoutai.com.cn:8888/XHT/appCompanyController/getCompanyDataDetail";

    //获取某个客户的所有订单信息
    public static final String CUSTOMER_ORDER_URL="http://www.xiaohoutai.com.cn:8888/XHT/employeeController/loadCompanyOrder" ;


    //获取某个订单信息
    public static final String DETIALS_ORDER_URL="http://www.xiaohoutai.com.cn:8888/XHT/employeeController/loadCompanyOrderDetails" ;

       //获取报税记录
    public static final String DETIALS_TAX_URL="http://www.xiaohoutai.com.cn:8888/XHT/employeeController/loadCompanyTaxDetails" ;

    //获取通讯录
    public static final String CONTACT_URL="http://www.xiaohoutai.com.cn:8888/XHT/employeeController/loadCompanyContact" ;


    //获取d订单图片
    public static final String ORDER_PIC_URL="http://www.xiaohoutai.com.cn:8888/XHT/employeeController/loadOrderServiceFile" ;

    //获取人员头像和图片
    public static final String NUMBER_PIC_URL="" ;



}
