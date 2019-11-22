package com.kc.common_service.util;

import java.util.Random;

public class RandomUtils {

    /**
     * 返回[min,max]区间的整数
     * */
    public static int nextInt(int min, int max){
        Random random = new Random();
        return random.nextInt(max-min+1)+min;
    }
    /**
     * 返回[0,max]区间的整数
     * */
    public static int nextInt(int max){
        return new Random().nextInt(max);
    }
}
