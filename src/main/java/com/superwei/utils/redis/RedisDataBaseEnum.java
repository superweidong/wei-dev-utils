package com.superwei.utils.redis;

/**
 * @author weidongge
 * @program wei-dev-utils
 * @description
 * @create 2019-11-21 14:42
 */
public enum RedisDataBaseEnum {
    DATABASE_0(0, "数据库0"),
    DATABASE_1(1, "数据库1"),
    DATABASE_2(2, "数据库2"),
    DATABASE_3(3, "数据库3"),
    DATABASE_4(4, "数据库4"),
    DATABASE_5(5, "数据库5"),
    DATABASE_6(6, "数据库6"),
    DATABASE_7(7, "数据库7"),
    DATABASE_8(8, "数据库8"),
    DATABASE_9(9, "数据库9"),
    DATABASE_10(10, "数据库10"),
    DATABASE_11(11, "数据库11"),
    DATABASE_12(12, "数据库12"),
    DATABASE_13(13, "数据库13"),
    DATABASE_14(14, "数据库14"),
    DATABASE_15(15, "数据库15");
    private int id ;
    private String name ;

    RedisDataBaseEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 根据id获取枚举对象.
     */
    public static RedisDataBaseEnum getById(int id) {
        for (RedisDataBaseEnum temp : RedisDataBaseEnum.values()) {
            if (temp.getId() == id) {
                return temp;
            }
        }
        return null;
    }

}

