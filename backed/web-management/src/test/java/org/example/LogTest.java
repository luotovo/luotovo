package org.example;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class LogTest {

    private static final Logger log = LoggerFactory.getLogger(LogTest.class);

    @Test
    public void testLog() {
        log.debug("开始计算...");

        int[] nums = {1, 5, 3, 2, 1, 4, 5, 4, 6, 7, 4, 34, 2, 23};
        int sum = Arrays.stream(nums).sum();

        log.info("数组元素和为: " + sum);
        log.debug("结束计算...");

        log.trace("trace...");
        log.warn( "warn...");
        log.error("error...");
    }
}

