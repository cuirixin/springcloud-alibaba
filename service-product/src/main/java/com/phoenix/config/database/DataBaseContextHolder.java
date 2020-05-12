package com.phoenix.config.database;

/**
 * 自定义用来管理调配数据源的类 用 ThreadLocal解决了多线程情况下数据源错乱的问题
 */
public class DataBaseContextHolder {

    public enum DataBaseType {
        MASTER, SLAVE
    }

    private static final ThreadLocal<DataBaseType> context = new ThreadLocal<>();

    public static void setDataBaseType(DataBaseType dataBaseType) {
        if (dataBaseType == null) {
            throw new NullPointerException();
        }
        context.set(dataBaseType);
    }

    public static DataBaseType getDataBaseType() {
        return context.get() == null ? DataBaseType.MASTER : context.get();
    }

    public static void clearDataBaseType() {
        context.remove();
    }
    

}