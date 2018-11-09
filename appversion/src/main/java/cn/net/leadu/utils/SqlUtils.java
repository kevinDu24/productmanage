package cn.net.leadu.utils;

import java.lang.reflect.Field;

/**
 * Created by PengChao on 16/9/25.
 */
public class SqlUtils {
    /**
     * excel导入构造通用的sql语句
     * @param clazz
     * @param table
     * @return
     */
    public static String generate(Class clazz,String table){
        Field[] fields = clazz.getDeclaredFields();
        String prefix = "insert into "+ table;
        String cols = " (id,";
        String vals = " values(nextval('hibernate_sequence'),";
        for(int i =0;i<fields.length;i++){
            if(i==fields.length-1){
                cols = cols + fields[i].getName() + ")";
                vals = vals + ":" + fields[i].getName() + ")";
            }else {
                cols = cols + fields[i].getName() + ",";
                vals = vals + ":" + fields[i].getName() + ",";
            }
        }
        return prefix + cols + vals;

    }

    /**
     * sim卡导入,sql语句构造
     * @return
     */
    public static String simCardSql(){
        String sql = "insert into sim_card (id,seq,sim_card_num,sim_card_imei,provider_name,sim_card_type)" +
                "values(nextval('hibernate_sequence'),:xuhao,:SIMkahao,:chuanhao,:suoshujigou,:kapianleixing)";
        return sql;
    }

    /**
     * 安装工人导入,sql语句构造
     * @return
     */
    public static String installPersonSql(){
        String sql = "insert into install_person (id,name,phone_num,card_id,provider_name,provider_property,addr,create_date)" +
                "values(nextval('hibernate_sequence'),:xingming,:shoujikahao,:shenfenzhenghao,:anzhuangfuwushangquancheng,:anzhuangfuwushangshuxing,:anzhuangdiqu, now())";
        return sql;
    }
}
