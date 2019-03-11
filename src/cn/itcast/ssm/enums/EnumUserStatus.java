/**
 * @Title: EnumUserStatus.java
 * @Package com.ea.p2p.constants
 * @Description: 常量或枚举包
 * Copyright: Copyright (c) 2011 
 * Company:深圳市宇商资产管理有限公司
 * 
 * @author Comsys-xianqiao.gu
 * @date 2016年4月14日 下午6:48:34
 * @version V1.0
 */


package cn.itcast.ssm.enums;

import java.util.LinkedHashMap;
import java.util.Map;



/**
 * 用户状态枚举
 * @author xianjiao.luo
 *
 */
public enum EnumUserStatus {
	/** 0-禁止登陆 */
	INVALID(0,"禁止登陆"),
    /** 1-有效 */
    VALID(1,"有效");
 
    private long code;
    private String msg;
    private boolean display;
    
    EnumUserStatus(long code, String msg) {
      this.code = code;
      this.msg = msg;
      this.display = true;
    }
    
    public long getCode() {
      return code;
    }
    
    public void setCode(long code) {
      this.code = code;
    }
    
    public String getMsg() {
      return msg;
    }
    
    public void setMsg(String msg) {
      this.msg = msg;
    }
    
    public boolean isDisplay() {
      return display;
    }
    
    public void setDisplay(boolean display) {
      this.display = display;
    }
    
    /**
     * 根据枚举的code返回枚举对象
     * @param code  枚举code值
     * @return  枚举对象
     */
    public static EnumUserStatus getEnumByCode(long code) {
      for (EnumUserStatus type : values()) {
          if (type.getCode()==code)
              return type;
      }
      return null;
    }
    
    /**
     * 将枚举转换成code-msg形式的集合
     * 可通过<code>EnumXXX.setDisplay(false);</code>的方式将不需要的枚举类型不转换成map
     * @return  转换后的map集合
     */
    public static Map<Long, String> toMap() {
      Map<Long, String> enumDataMap = new LinkedHashMap<Long, String>();
      for (EnumUserStatus type : EnumUserStatus.values()) {
          if (type.isDisplay()) enumDataMap.put(type.getCode(), type.getMsg());
      }
      return enumDataMap;
    }
    
    /**
     * 将枚举转换成code-code-msg形式的集合
     * 可通过<code>EnumXXX.setDisplay(false);</code>的方式将不需要的枚举类型不转换成map
     * @return  转换后的map集合
     */
    public static Map<Long, String> toMixMap() {
      Map<Long, String> enumDataMap = new LinkedHashMap<Long, String>();
      for (EnumUserStatus type : EnumUserStatus.values()) {
          if (type.isDisplay()) enumDataMap.put(type.getCode(), type.getCode() + "-" + type.getMsg());
      }
      return enumDataMap;
    }
}
