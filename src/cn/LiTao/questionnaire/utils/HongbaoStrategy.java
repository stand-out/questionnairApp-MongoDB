package cn.LiTao.questionnaire.utils;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 分红包策略
 *
 */
public class HongbaoStrategy {
    public static final String REDIS_JC_SPLIT_SYMBOL = ":";
    private static final Random RANDOM = new Random();

    private HongbaoStrategy() {}

    public static int [] evenlyRandom(int totalAmount, int hongbaoSize) {
        if (minimumAmountCheck(totalAmount, hongbaoSize)) {
            return new int[0];
        }
        // 初始化红包，每个包至少1
        int [] resultPackage = new int[hongbaoSize];
        Arrays.fill(resultPackage, 1);
        totalAmount -= hongbaoSize;

        while (totalAmount-- > 0) {
            resultPackage[RANDOM.nextInt(resultPackage.length)]++;
        }

        return resultPackage;
    }

    public static int [] evenly(int totalAmount, int hongbaoSize) {
        int avg = totalAmount / hongbaoSize;
        int [] resultPackage = new int[hongbaoSize];

        Arrays.fill(resultPackage, avg);

        return resultPackage;
    }

    public static int [] random(int totalAmount, int hongbaoSize) {
        if (minimumAmountCheck(totalAmount, hongbaoSize)) {
            return new int[0];
        }
        // 初始化红包，每个包至少1
        int [] resultPackage = new int[hongbaoSize];

        for (int i = 0; i < hongbaoSize - 1; i++) {
            resultPackage[i] = randomCut(totalAmount, hongbaoSize - i);
            totalAmount -= resultPackage[i];
        }

        resultPackage[hongbaoSize - 1] = totalAmount;

        return resultPackage;
    }

    public static String [] toRedisSetData(int [] nums) {
        AtomicInteger count = new AtomicInteger(0);
        String [] redisSet = new String[nums.length];
        for (int i = 0; i < nums.length; i++) {
            redisSet[i] = count.incrementAndGet() + REDIS_JC_SPLIT_SYMBOL + nums[i];
        }
        return redisSet;
    }

    private static int halfRandomCut(int totalAmount, int size) {
        int evenly = totalAmount / size;
        int half = evenly / 2;
        return RANDOM.nextInt(half) + half;
    }

    private static int randomCut(int totalAmount, int size) {
        return RANDOM.nextInt(totalAmount / size) + 1;
    }

    private static boolean minimumAmountCheck(int amount, int size) {
        return size > amount;
    }
}
