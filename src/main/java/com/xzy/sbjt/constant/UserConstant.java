package com.xzy.sbjt.constant;

/**
 * @author xzy
 * @date 2020-12-12 15:03
 * 说明：用户相关的常量
 */
public class UserConstant {
    /**
     * 逻辑删除
     */
    public static final Integer NOT_DELETED = 0;
    public static final Integer IS_DELETED = 1;
    /**
     * 锁定
     */
    public static final Integer NOT_LOCKED = 0;
    public static final Integer IS_LOCKED = 1;

    private UserConstant() {
    }
}
