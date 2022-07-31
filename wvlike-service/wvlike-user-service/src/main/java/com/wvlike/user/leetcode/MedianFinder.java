package com.wvlike.user.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据流的中位数
 * 中位数是有序列表中间的数。如果列表长度是偶数，中位数则是中间两个数的平均值。
 * 例如，
 * [2,3,4] 的中位数是 3
 * [2,3] 的中位数是 (2 + 3) / 2 = 2.5
 * 设计一个支持以下两种操作的数据结构：
 * void addNum(int num) - 从数据流中添加一个整数到数据结构中。
 * double findMedian() - 返回目前所有元素的中位数。
 * 示例：
 * addNum(1)
 * addNum(2)
 * findMedian() -> 1.5
 * addNum(3)
 * findMedian() -> 2
 * 进阶:
 * 如果数据流中所有整数都在 0 到 100 范围内，你将如何优化你的算法？
 * 如果数据流中 99% 的整数都在 0 到 100 范围内，你将如何优化你的算法？
 */

/**
 * @date: 2021/8/27
 * @author: txw
 * @description: 因为题目要输出中位数，因此只需要使得输入进去的数据按照递增顺序排列，则当数据总数为奇数时，就是其中间位置存储的数，为偶数时，就是中间位置存储的两个数和的二分之一。
 * 本文采用ArrayList来实现，插入数据尝试了两种方法，均可行，但是执行用时不是很好，仅供参考。
 * 方法一：采用二分法寻找新数据插入的位置
 * 当list为空时，直接添加数据
 * 当list非空时，将插入的数据与当前链表处于中间位置的数据比较，若小，则取左侧部分的中间位置的数据继续比较；若大，则取右边部分的中间位置的数据继续比较。直到找到合适的索引。文中代码使用递归的方法实现。
 * 方法二：采用插入排序
 * 当list为空或者添加的数据大于等于list中末尾的数时，直接添加数据
 * 否则，从list头部开始比较，若添加的数据大于索引处的数据，则索引加一，否则将添加的数据插入该索引处。
 */

public class MedianFinder {
    public List<Integer> list = new ArrayList<>();

    public MedianFinder() {
    }

    //使用二分法排序
    public void addNum(int num) {
        if (list.size() == 0) {
            list.add(0, num);
        } else {
            int index = spiltTwo(0, list.size() - 1, num);
            if (index < list.size()) {
                list.add(index, num);
            } else {
                list.add(num);
            }
        }
    }

    public int spiltTwo(int start, int end, int num) {
        int mid = (start + end) / 2;
        if (end - start > 1) {
            if (list.get(mid) > num) {
                mid = spiltTwo(start, mid, num);
            } else {
                mid = spiltTwo(mid, end, num);
            }
        } else {
            if (list.get(mid) < num) {
                mid += 1;
            }
            if (list.get(end) < num) {
                mid += 1;
            }
        }
        return mid;
    }

    //使用插入排序添加数据
    public void insertAddNum(int num) {
        if (list.size() == 0 || list.get(list.size() - 1) <= num) {
            list.add(num);
        } else {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) > num) {
                    list.add(i, num);
                    break;
                }
            }
        }
    }

    public double findMedian() {
        int mid = (list.size() - 1) / 2;
        double median;
        if (list.size() % 2 == 0) {
            median = (((double) list.get(mid) + (double) list.get(mid + 1)) / 2);
        } else {
            median = list.get(mid);
        }
        return median;
    }

}


/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */


