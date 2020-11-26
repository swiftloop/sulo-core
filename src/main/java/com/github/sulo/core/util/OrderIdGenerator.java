package com.github.sulo.core.util;

/**
 * @author sorata 2020-10-10:10:38
 * <p>
 * 订单号生成器
 */
public abstract class OrderIdGenerator {


    /**
     * 自增序列的占位
     */
    private static final int RAND_BIT = 12;
    /**
     * 用户id的占位
     */
    private static final int UID_BIT = 10;
    /**
     * 时间占位
     */
    private static final int TIME_BIT = RAND_BIT + UID_BIT;
    /**
     * 自增数的最大值
     */
    private static final long MAX_RAND = ~(-1L << RAND_BIT);
    /**
     * 用户id最大数
     */
    private static final long MAX_UID = ~(-1L << UID_BIT);
    private static long lastTime = -1L;
    private static long flag = 0;


    /**
     * 0-{时间：41}-{uid: 10}-{自增: 12}
     *
     * @param uid 用户ID
     * @return 唯一iD
     */
    public synchronized static long gen(long uid) {
        //1997/07/01
        long start = 867686400000L;
        long time = System.currentTimeMillis();
        if (time == lastTime) {
            flag = (flag + 1) & MAX_RAND;
            if (flag == 0) {
                while (time == lastTime) {
                    time = System.currentTimeMillis();
                }
            }
        } else {
            flag = 0;
        }
        lastTime = time;
        return ((time - start) << TIME_BIT) | ((uid & MAX_UID) << RAND_BIT) | flag;
    }


}
