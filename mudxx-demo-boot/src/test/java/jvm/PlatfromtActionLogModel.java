package jvm;

import java.util.HashMap;
import java.util.Map;

public enum PlatfromtActionLogModel {
    /**
     * 模块
     */
    USER("用户管理", "用户管理"),
    ROLE("角色管理", "角色管理"),
    AUTHORITY("权限管理", "权限管理"),
    ACCOUNT_SET("账套管理", "账套管理"),
    ACCOUNT_SET_AUTHORITY("账套权限", "账套权限"),
    CDN_CERT("AgileCDN证书", "AgileCDN证书"),
    SUPPLIER("供应商", "供应商"),
    BILLING_PRODUCT_SKU("账单模块-SKU", "账单模块-SKU"),
    BILLING_PRODUCT_PRICING("账单模块-定价", "账单模块-定价"),

    AGILEWING_SERVICECODE("Agilewing服务", "Agilewing服务"),
    BILLING_CREDIT("Credit模块", "Credit模块"),
    BILLING_WALLET("账单明细模块", "账单明细模块"),
    BILLING_DDOS("ddos同步数据到billing", "ddos同步数据到billing"),
    SUPPORT("支持中心", "支持中心"),
    ACTIVITY("活动管理", "活动管理"),
    MESSAGE("消息管理", "消息管理"),
    ACCOUNT_MESSAGE("官网留言管理", "官网留言管理"),
    MESSAGE_SECTION("消息栏目管理", "消息栏目管理"),
    AGILECDN_PARTNER("AgileCDN合作伙伴管理", "AgileCDN合作伙伴管理"),
    CDN_MANAGE("AgileCDN管理", "AgileCDN管理"),
    AGILEWING_RESOURCE("Agilewing资源管理", "Agilewing资源管理"),
    AGILECDN("AgileCDN", "AgileCDN"),
    ACCOUNT_CDN_PLAN("AgileCDN账套套餐", "AgileCDN账套套餐"),
    ACCOUNT_CDN_PLAN_DAY("AgileCDN账套套餐流水", "AgileCDN账套套餐流水"),
    INTERNAL_CDN_OVERVIEW("AgileCDN概况", "AgileCDN概况"),
    CDN_PLAN("AgileCDN套餐", "AgileCDN套餐"),
    SOLO_ORDER("SOLO支付记录", "SOLO支付记录"),
    SOLO_ACCOUNT_SET("SOLO用户管理", "SOLO用户管理"),
    SOLO_TRAFFIC("SOLO流量抵扣", "SOLO流量抵扣"),
    CMS_ARTICLE("CMS文章管理", "CMS文章管理"),
    CMS_PRODUCT("CMS产品管理", "CMS产品管理"),
    CMS_SECTION("CMS栏目管理", "CMS栏目管理"),

    EXTERNAL_MENU("菜单管理", "菜单管理"),

    L7_BILL_EXCHANGE_CODE("礼包礼包兑换券管理", "礼包礼包兑换券管理"),
    L7_BILL_GIFT("礼包管理", "礼包管理"),

	L7_BILL_CONTRACT_STANDARD("合同管理","合同管理"),
	L7_BILL_CUSTOMER("客户管理","客户管理"),
	L7_BILL_DEAD_MESSAGE("死信消息管理","死信消息管理"),
	L7_BILL_DICTIONARY("字典管理","字典管理"),
	L7_BILL_MAIL_TEMPLATE("邮件模板管理","邮件模板管理"),
	L7_BILL_CALCULATE_BILL("生成账单管理","生成账单管理"),
	L7_BILL_RECHARGE_DISCOUNTS("充值折扣管理","充值折扣管理"),
	L7_BILL_UPLOAD_FILE("附件管理","附件管理"),

    /*QUERY*/
    QUERY_INTERNAL_USER_ROLE("总部用户|角色查询", "总部用户|角色查询"),
    QUERY_AUDIT_LOG("操作日志查询", "操作日志查询"),


    /*SYS*/
    SYS_INTERNAL_MENU("总部菜单权限管理", "总部菜单权限管理"),
    SYS_INTERNAL_ROLE("总部角色管理", "总部角色管理"),
    SYS_INTERNAL_ROLE_MENU("总部角色|菜单管理", "总部角色|菜单管理"),
    SYS_INTERNAL_USER("总部用户管理", "总部用户管理"),
    SYS_AUDIT_LOG("操作日志管理", "操作日志管理"),

    ;

    /**
     * 中文秒描述
     */
    private final String desc;
    /**
     * 英文描述
     */
    private final String descEn;

    PlatfromtActionLogModel(String desc, String descEn) {
        this.desc = desc;
        this.descEn = descEn;
    }

    public String getDesc() {
        return desc;
    }

    public String getDescEn() {
        return descEn;
    }

    public static Map<String, PlatfromtActionLogModel> cache = new HashMap<>();

    static {
        for (PlatfromtActionLogModel model : PlatfromtActionLogModel.values()) {
            cache.put(model.name(), model);
        }
    }

    /**
     * 匹配name拼接中文描述
     *
     * @return
     */
    public static String spliceDescByMatchingName(String name) {
        if (cache.containsKey(name)) {
            PlatfromtActionLogModel model = cache.get(name);
            return model.getDesc() + "(" + model.name() + ")";
        } else {
            return name;
        }
    }

    /**
     * 匹配name拼接英文描述
     *
     * @return
     */
    public static String spliceDescEnByMatchingName(String name) {
        if (cache.containsKey(name)) {
            PlatfromtActionLogModel model = cache.get(name);
            return model.getDescEn() + "(" + model.name() + ")";
        } else {
            return name;
        }
    }
}
