package com.wvlike.user.leetcode;

import java.util.Arrays;

/**
 * @date: 2021/8/27
 * @author: txw
 * @description:
 */
public class MyLeetCodeTestReview {

    /**
     * 救生艇
     *
     * @param people
     * @param limit
     * @return
     */
    public int numRescueBoats(int[] people, int limit) {
        Arrays.sort(people);
        int res = 0;
        for (int right = people.length - 1, left = 0; left <= right; res++) {
            if (people[left] + people[right--] <= limit) {
                left++;
            }
        }
        return res;
    }

}
