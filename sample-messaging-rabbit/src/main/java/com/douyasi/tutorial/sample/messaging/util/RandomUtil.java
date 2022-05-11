package com.douyasi.tutorial.sample.messaging.util;

import java.util.Random;

/**
 * RandomUtil
 * full version see `blog-web` project source code
 *
 * @author raoyc
 */
public class RandomUtil {

    private static Random random = new Random();

    /**
     * random int value between min and max
     *
     * @param min min value
     * @param max max value
     * @return int value
     */
    public static int randomInt(int min, int max) {
        return random.nextInt(max - min) + min;
    }

    /**
     * random int value between o and limit
     *
     * @param limit limit of max value
     * @return int value
     */
    public static int randomInt(int limit) {
        return random.nextInt(limit);
    }

}
