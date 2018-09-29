package com.xn.hk.common.utils.snmp;
/**
 * 
 * @ClassName: EnumStatusItem
 * @Package: com.xn.hk.common.utils.snmp
 * @Description: 
 * @Author: wanlei
 * @Date: 2018年9月29日 下午4:42:40
 */
public enum EnumStatusItem {
  
  CPU("cpu"),
  DISK("disk"),
  NETWORKFLOW("workflow"),
  SYSUPTIME("systemRuntime"),
  TOTALMEMORY("total_RAM"),
  TOTALMEMORYUSED("total_RAM_used"),
  TOTALMEMORYFREE("total_RAM_free"),
  ;
  
  private String name;
  
  private EnumStatusItem(String name) {
    this.name = name;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }
  
  public static EnumStatusItem getStatusItemWithName (String name) {
    EnumStatusItem[] values = EnumStatusItem.values();
      for (EnumStatusItem value : values) {
          if (value.name.equals(name)) {
              return value;
          }
      }
      return null;
  }

}
