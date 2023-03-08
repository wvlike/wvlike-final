package com.wvlike.user.leetcode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @date: 2021/8/26
 * @author: txw
 * @description:
 */
public class MyLeetCodeTest {


    public static void main(String[] args) {
        MyLeetCodeTest test = new MyLeetCodeTest();
//        List<List<Integer>> combine = test.combine(5, 2);
//        System.out.println(JSON.toJSONString(combine));

//        int version = compareVersion("1.0000003.3.1", "1.003.3.2");
//        System.out.println(version);
//        System.out.println(System.currentTimeMillis());


//        List<List<Integer>> combine = test.combine(5, 2);
//        System.out.println(JSON.toJSONString(combine));
//        System.out.println(test.multiply("1322", "5555"));


//        int[] arr = new int[]{1, 3, 64, 31, 45, 5, 663, 12, 16};
//        int[] result = test.smallestK(arr, 3);
//        System.out.println(Arrays.toString(result));


//        int[] arr = new int[]{1, 96, 33, 15, 56, 100, 2, 94, 24, 72, 48};
//        Arrays.sort(arr);
//        System.out.println("index = " + test.index(arr, 11));
//        System.out.println("search = " + test.search(arr, 100));
//        List<List<Integer>> generate = test.generate(5);
//        System.out.println(JSON.toJSONString(generate));
//        test.removeElement(arr, 100);

//        int[] ints = test.sortedSquares(arr);
//        System.out.println(Arrays.toString(ints));

//        test.minSubArrayLen(200, arr);
//        int[][] ints = test.generateMatrix2(11);
//        for (int[] anInt : ints) {
//            System.out.println(Arrays.toString(anInt));
//        }
//        int[] arr = {4,8,12,16};
//        System.out.println(test.addRungs(arr, 3));
//        System.out.println(test.areNumbersAscending("1 box has 3 blue 4 red 6 green and 12 yellow marbles"));

//        List<Integer> spiralOrder = test.spiralOrder(ints);
//        System.out.println(spiralOrder);
        ListNode listNode = test.getNode(9);
        System.out.println(test.parseNode(listNode));
        ListNode swapPairs = test.swapPairs(listNode);
        System.out.println(test.parseNode(swapPairs));
    }

    /**
     * 15. 三数之和
     * https://leetcode.cn/problems/3sum/
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        // 找出a + b + c = 0
        // a = nums[i], b = nums[left], c = nums[right]
        for (int i = 0; i < nums.length; i++) {
            // 排序之后如果第一个元素已经大于零，那么无论如何组合都不可能凑成三元组，直接返回结果就可以了
            if (nums[i] > 0) {
                return result;
            }

            if (i > 0 && nums[i] == nums[i - 1]) {  // 去重a
                continue;
            }

            int left = i + 1;
            int right = nums.length - 1;
            while (right > left) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum > 0) {
                    right--;
                } else if (sum < 0) {
                    left++;
                } else {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    // 去重逻辑应该放在找到一个三元组之后，对b 和 c去重
                    while (right > left && nums[right] == nums[right - 1]) right--;
                    while (right > left && nums[left] == nums[left + 1]) left++;

                    right--;
                    left++;
                }
            }
        }
        return result;
    }


    /**
     * 383. 赎金信
     * <a href="https://leetcode.cn/problems/ransom-note/">...</a>
     *
     * @param ransomNote
     * @param magazine
     * @return
     */
    public boolean canConstruct(String ransomNote, String magazine) {
        int[] record = new int[26];
        for (char c : magazine.toCharArray()) {
            record[c - 'a'] += 1;
        }
        for (char c : ransomNote.toCharArray()) {
            record[c - 'a'] -= 1;
        }
        // 如果数组中存在负数，说明ransomNote字符串总存在magazine中没有的字符
        for (int i : record) {
            if (i < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 454. 四数相加 II
     * <a href="https://leetcode.cn/problems/4sum-ii/">...</a>
     *
     * @param nums1
     * @param nums2
     * @param nums3
     * @param nums4
     * @return
     */
    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        return 0;
    }

    /**
     * 1. 两数之和
     * <a href="https://leetcode.cn/problems/two-sum/">...</a>
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            int first = nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                int second = nums[j];
                if (first + second == target) {
                    return new int[]{first, second};
                }
            }
        }
        return new int[0];
    }

    /**
     * 202. 快乐数
     * <a href="https://leetcode.cn/problems/happy-number/">...</a>
     */
    public boolean isHappy(int n) {
        Set<Integer> record = new HashSet<>();
        while (n != 1 && !record.contains(n)) {
            record.add(n);
            n = getNextNumber(n);
        }
        return n == 1;
    }

    private int getNextNumber(int n) {
        int res = 0;
        while (n > 0) {
            int temp = n % 10;
            res += temp * temp;
            n = n / 10;
        }
        return res;
    }


    /**
     * 349. 两个数组的交集
     * <a href="https://leetcode.cn/problems/intersection-of-two-arrays/">...</a>
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public int[] intersection(int[] nums1, int[] nums2) {

        return null;

    }

    /**
     * 49. 字母异位词分组
     * https://leetcode.cn/problems/group-anagrams/
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        return new ArrayList<>(Arrays.stream(strs).collect(Collectors.groupingBy(str -> str.chars().sorted().collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString())).values());
    }

    /**
     * 242. 有效的字母异位词
     * https://leetcode.cn/problems/valid-anagram/
     * 比较两个字符串含的字母个数
     */
    public boolean isAnagram(String s, String t) {
        int[] record = new int[26];
        for (int i = 0; i < s.length(); i++) {
            record[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i < t.length(); i++) {
            record[t.charAt(i) - 'a']--;
        }
        for (int i : record) {
            if (i != 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 142. 环形链表 II
     * https://leetcode.cn/problems/linked-list-cycle-ii/
     */
    public ListNode detectCycle(ListNode head) {
        return null;
    }

    /**
     * 面试题 02.07. 链表相交
     * https://leetcode.cn/problems/intersection-of-two-linked-lists-lcci/
     *
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        // 快慢指针
        int fast = 0;
        int slow = 0;
        ListNode listNode = null;


        return listNode;
    }

    /**
     * 19. 删除链表的倒数第 N 个结点
     * https://leetcode.cn/problems/remove-nth-node-from-end-of-list/
     *
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummyNode = new ListNode(0);
        dummyNode.next = head;
        ListNode fast = head;
        ListNode slow = head;
        for (int i = n; i > 0; i--) {
            fast = fast.next;
        }
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return dummyNode.next;
    }


    public ListNode swapPairs(ListNode head) {
        ListNode dumyhead = new ListNode(-1); // 设置一个虚拟头结点
        dumyhead.next = head; // 将虚拟头结点指向head，这样方面后面做删除操作
        ListNode cur = dumyhead;
        ListNode temp; // 临时节点，保存两个节点后面的节点
        ListNode firstnode; // 临时节点，保存两个节点之中的第一个节点
        ListNode secondnode; // 临时节点，保存两个节点之中的第二个节点
        while (cur.next != null && cur.next.next != null) {
            temp = cur.next.next.next;
            firstnode = cur.next;
            secondnode = cur.next.next;
            cur.next = secondnode;       // 步骤一
            secondnode.next = firstnode; // 步骤二
            firstnode.next = temp;      // 步骤三
            cur = firstnode; // cur移动，准备下一轮交换
        }
        return dumyhead.next;
    }

    /**
     * 生成node
     *
     * @param length
     * @return
     */
    private ListNode getNode(int length) {
        ListNode listNode = new ListNode(length);
        ListNode cur = listNode;
        for (int i = length - 1; i > 0; i--) {
            cur.next = new ListNode(i);
            cur = cur.next;
        }
        return listNode;
    }

    /**
     * 变成list，方便打印
     *
     * @param node
     * @return
     */
    private List<Integer> parseNode(ListNode node) {
        List<Integer> list = new ArrayList<>();
        ListNode curr = node;
        while (curr != null) {
            list.add(curr.val);
            curr = curr.next;
        }
        return list;
    }

    /**
     * 206. 反转链表
     * https://leetcode.cn/problems/reverse-linked-list/
     */
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode cur = head;
        ListNode temp = null;
        while (cur != null) {
            temp = cur.next;// 保存下一个节点
            cur.next = prev;
            prev = cur;
            cur = temp;
        }
        return prev;
    }


    /***
     * 707. 设计链表  todo 未完成
     * https://leetcode.cn/problems/design-linked-list/
     *
     */
    class MyLinkedList {

        private ListNode head;

        private int size;

        public MyLinkedList() {
            size = 0;
            head = new ListNode(0);
        }

        public int get(int index) {
            if (index > size || index < 0) {
                return -1;
            }
            ListNode cur = head;
            for (int i = 0; i < index; i++) {
                cur = cur.next;
            }
            return cur.val;
        }

        public void addAtHead(int val) {

        }

        public void addAtTail(int val) {

        }

        public void addAtIndex(int index, int val) {

        }

        public void deleteAtIndex(int index) {

        }
    }


    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> ans = new ArrayList<>();
        int[] dx = {0, 1, 0, -1}, dy = {1, 0, -1, 0};
        int row = matrix.length, col = matrix[0].length;
        int x = 0, y = 0, mark = 0, n = row * col;
        for (int i = 0; i < n; i++) {
            ans.add(matrix[x][y]);
            matrix[x][y] = Integer.MAX_VALUE;
            int a = x + dx[mark], b = y + dy[mark];
            if (a < 0 || b < 0 || a >= row || b >= col || matrix[a][b] == Integer.MAX_VALUE) {
                mark = (mark + 1) % 4;
                a = x + dx[mark];
                b = y + dy[mark];
            }
            x = a;
            y = b;
        }
        return ans;
    }


    /**
     * 54. 螺旋矩阵
     * https://leetcode.cn/problems/spiral-matrix/
     *
     * @param matrix
     * @return
     */
    public List<Integer> spiralOrder1(int[][] matrix) {
        // 获取高、宽
        int h = matrix.length;
        int w = h == 0 ? 0 : matrix[0].length;
        List<Integer> result = new ArrayList<>();
        if (h == 0 || w == 0) {
            return result;
        }
        // 定义上下左右
        int l = 0, r = w - 1, u = 0, d = h - 1, idx = 0;
        while (idx < w * h) {
            for (int i = l; i <= r; i++) {
                result.add(matrix[u][i]);
                idx++;
            }
            u++;
            for (int i = u; i <= d; i++) {
                result.add(matrix[i][r]);
                idx++;
            }
            r--;
            for (int i = r; i >= l; i--) {
                result.add(matrix[d][i]);
                idx++;
            }
            d--;
            for (int i = d; i >= u; i--) {
                result.add(matrix[i][l]);
                idx++;
            }
            l++;
        }
        return result;
    }


    /**
     * 2042. 检查句子中的数字是否递增
     * https://leetcode.cn/problems/check-if-numbers-are-ascending-in-a-sentence/
     *
     * @param s
     * @return
     */
    public boolean areNumbersAscending(String s) {
        String[] split = s.split(" ");
        int num = 0;
        for (String str : split) {
            if (isNumber(str)) {
                if (num >= Integer.parseInt(str)) {
                    return false;
                }
                num = Integer.parseInt(str);
            }
        }
        return true;
    }

    private boolean isNumber(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) > '9' || s.charAt(i) < '0') {
                return false;
            }
        }
        return true;
    }


    /**
     * 1936. 新增的最少台阶数
     * https://leetcode.cn/problems/add-minimum-number-of-rungs/
     *
     * @param rungs
     * @param dist
     * @return
     */
    public int addRungs(int[] rungs, int dist) {
        int length = rungs.length;
        int rungNum = rungNum(rungs[0], dist);
        if (length == 1) {
            return rungNum;
        }
        for (int i = 0, j = length - 1; i < j; i++, j--) {
            if (i + 1 == j) {
                rungNum += rungNum((rungs[j] - rungs[i]), dist);
            } else {
                rungNum += rungNum((rungs[j] - rungs[j - 1]), dist);
                rungNum += rungNum((rungs[i + 1] - rungs[i]), dist);
            }
        }
        return rungNum;
    }

    public int rungNum(int gapValue, int dist) {
        if (gapValue == 0) {
            return 0;
        }
        if (gapValue % dist == 0) {
            return gapValue / dist - 1;
        } else {
            return gapValue / dist;
        }
    }

    public int[][] generateMatrix2(int n) {
        int[][] res = new int[n][n];
        int u = 0, d = n - 1, l = 0, r = n - 1, idx = 1;
        while (idx <= n * n) {
            for (int i = l; i <= r; i++) {
                res[u][i] = idx++;
            }
            u++;
            for (int i = u; i <= d; i++) {
                res[i][r] = idx++;
            }
            r--;
            for (int i = r; i >= l; i--) {
                res[d][i] = idx++;
            }
            d--;
            for (int i = d; i >= u; i--) {
                res[i][l] = idx++;
            }
            l++;
        }
        return res;
    }

    public int[][] generateMatrix1(int n) {
        int[][] res = new int[n][n];
        int up = 0, down = n - 1, left = 0, right = n - 1, index = 1;
        while (index <= n * n) {
            for (int i = left; i <= right; i++) {
                res[up][i] = index++;
            }
            up++;
            for (int i = up; i <= down; i++) {
                res[i][right] = index++;
            }
            right--;
            for (int i = right; i >= left; i--) {
                res[down][i] = index++;
            }
            down--;
            for (int i = down; i >= up; i--) {
                res[i][left] = index++;
            }
            left++;
        }
        return res;
    }


    public ListNode removeElements1(ListNode head, int val) {
        while (head != null && head.val == val) {
            head = head.next;
        }
        if (head == null) {
            return head;
        }
        ListNode pre = head;
        ListNode current = head.next;
        while (current != null) {
            if (current.val == val) {
                head.next = current.next;
            } else {
                pre = current;
            }
            current = current.next;
        }
        return head;
    }


    /**
     * 链表
     * 203. 移除链表元素
     * https://leetcode-cn.com/problems/remove-linked-list-elements/
     * 给你一个链表的头节点 head 和一个整数 val ，请你删除链表中所有满足 Node.val == val 的节点，并返回 新的头节点 。
     */
    public ListNode removeElements(ListNode head, int val) {
        ListNode dummyHead = new ListNode(0);
        dummyHead.next = head;
        ListNode temp = dummyHead;
        while (temp.next != null) {
            if (temp.next.val == val) {
                temp.next = temp.next.next;
            } else {
                temp = temp.next;
            }
        }
        return dummyHead.next;
    }

    /**
     * 59. 螺旋矩阵 II
     * https://leetcode-cn.com/problems/spiral-matrix-ii/
     * 给你一个正整数 n ，生成一个包含 1 到 n2 所有元素，且元素按顺时针顺序螺旋排列的 n x n 正方形矩阵 matrix 。
     *
     * @param n
     * @return
     */
    public int[][] generateMatrix(int n) {
        int maxNum = n * n;
        int curNum = 1;
        int[][] matrix = new int[n][n];
        int row = 0, column = 0;
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // 右下左上
        int directionIndex = 0;
        while (curNum <= maxNum) {
            matrix[row][column] = curNum;
            curNum++;
            int nextRow = row + directions[directionIndex][0], nextColumn = column + directions[directionIndex][1];
            if (nextRow < 0 || nextRow >= n || nextColumn < 0 || nextColumn >= n || matrix[nextRow][nextColumn] != 0) {
                directionIndex = (directionIndex + 1) % 4; // 顺时针旋转至下一个方向
            }
            row = row + directions[directionIndex][0];
            column = column + directions[directionIndex][1];
        }
        return matrix;
    }

    /**
     * 209. 长度最小的子数组
     * https://leetcode-cn.com/problems/minimum-size-subarray-sum/
     * 给定一个含有 n 个正整数的数组和一个正整数 target 。
     * <p>
     * 找出该数组中满足其和 ≥ target 的长度最小的 连续子数组 [numsl, numsl+1, ..., numsr-1, numsr] ，并返回其长度。如果不存在符合条件的子数组，返回 0 。
     */
    public int minSubArrayLen(int s, int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }
        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            int sum = 0;
            for (int j = i; j < n; j++) {
                sum += nums[j];
                if (sum >= s) {
                    ans = Math.min(ans, j - i + 1);
                    break;
                }
            }
        }
        return ans == Integer.MAX_VALUE ? 0 : ans;
    }

    public int minSubArrayLen1(int s, int[] nums) {
        int left = 0;
        int sum = 0;
        int result = Integer.MAX_VALUE;
        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];
            while (sum >= s) {
                result = Math.min(result, right - left + 1);
                sum -= nums[left++];
            }
        }
        return result == Integer.MAX_VALUE ? 0 : result;
    }

    /**
     * 904. 水果成篮
     * https://leetcode.cn/problems/fruit-into-baskets/
     * 滑动窗口图解 https://leetcode.cn/problems/fruit-into-baskets/solution/zhua-wa-mou-si-by-muse-77-bkd2/
     */
    public int totalFruit(int[] fruits) {
        int left = 0;
        int sum = 0;
        int result = 0;
        for (int right = 0; right < fruits.length; right++) {
            sum += fruits[right];


        }
        return result;
    }


    /**
     * 76. 最小覆盖子串
     * https://leetcode.cn/problems/minimum-window-substring/
     * 滑动窗口图解 https://leetcode.cn/problems/minimum-window-substring/solution/by-flix-1kac/
     */
    public int minWindow(String s, String t) {

        return 0;
    }


    /**
     * 977. 有序数组的平方
     * https://leetcode-cn.com/problems/squares-of-a-sorted-array/
     * 给你一个按 非递减顺序 排序的整数数组 nums，返回 每个数字的平方 组成的新数组，要求也按 非递减顺序 排序。
     *
     * @param nums
     * @return
     */
    public int[] sortedSquares(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        for (int i = 0; i <= n - 1; i++) {
            res[i] = (nums[i] * nums[i]);
        }
        Arrays.sort(res);
        return res;
    }

    public int[] sortedSquares1(int[] nums) {
        int[] res = new int[nums.length];
        int l = 0;
        int r = nums.length - 1;
        int j = nums.length - 1;
        while (l <= r) {
            if (nums[l] * nums[l] > nums[r] * nums[r]) {
                res[j--] = nums[l] * nums[l++];
            } else {
                res[j--] = nums[r] * nums[r--];
            }
        }
        return res;
    }

    /**
     * 给你一个数组 nums 和一个值 val，你需要 原地 移除所有数值等于 val 的元素，并返回移除后数组的新长度。
     * 不要使用额外的数组空间，你必须仅使用 O(1) 额外空间并 原地 修改输入数组。
     * 元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。
     * https://leetcode.cn/problems/remove-element/
     *
     * @param nums
     * @param val
     * @return
     */
    public int removeElement(int[] nums, int val) {
        // 快慢指针
        int fastIndex = 0;
        int slowIndex;
        for (slowIndex = 0; fastIndex < nums.length; fastIndex++) {
            if (nums[fastIndex] != val) {
                nums[slowIndex] = nums[fastIndex];
                slowIndex++;
            }
        }
        return slowIndex;
    }

    public int removeElement1(int[] nums, int val) {
        int left = 0;
        int right = nums.length;
        while (left < right) {
            if (nums[left] == val) {
                nums[left] = nums[right - 1];
                right--;
            } else {
                left++;
            }
        }
        return left;
    }


    /**
     * 杨辉三角：动态规划解法
     *
     * @param numRows
     * @return
     */
    public List<List<Integer>> generate1(int numRows) {
        List<List<Integer>> result = new ArrayList<>();
        int[][] dp = new int[numRows][numRows];
        dp[0][0] = 1;
        result.add(new ArrayList<Integer>() {{
            add(dp[0][0]);
        }});
        if (numRows == 1) {
            return result;
        }
        for (int i = 1; i < numRows; i++) {
            List<Integer> num = new ArrayList<>();
            dp[i][0] = 1;
            num.add(dp[i][0]);
            for (int j = 1; j < i; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i - 1][j - 1];
                num.add(dp[i][j]);
            }
            dp[i][i] = 1;
            num.add(dp[i][i]);
            result.add(num);
        }
        return result;
    }


    /**
     * 118. 杨辉三角
     * https://leetcode-cn.com/problems/pascals-triangle/
     *
     * @param numRows
     * @return
     */
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> list = new ArrayList<>();
        if (numRows < 1) {
            return list;
        }
        List<Integer> first = new ArrayList<>();
        first.add(1);
        list.add(first);
        if (numRows == 1) {
            return list;
        }
        List<Integer> nextList = first;
        for (int i = 0; i < numRows - 1; i++) {
            nextList = getNextList(nextList);
            list.add(nextList);
        }
        return list;
    }

    public List<Integer> getNextList(List<Integer> lastList) {
        int lastSize = lastList.size();
        List<Integer> nextList = new ArrayList<>();
        nextList.add(1);
        if (lastSize >= 2) {
            for (int i = 0; i < lastSize - 1; i++) {
                nextList.add(lastList.get(i) + lastList.get(i + 1));
            }
        }
        nextList.add(1);
        return nextList;
    }


    /**
     * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
     *
     * @param nums
     * @return
     */
    public int singleNumber(int[] nums) {
        int ans = 0;
        for (int num : nums) {
            ans ^= num;
        }
        return ans;
    }

    /**
     * 双指针法
     *
     * @param nums
     * @param target
     * @return
     */
    public int index(int[] nums, int target) {
        int n = nums.length;
        if (n == 0) {
            return -1;
        }
        for (int l = 0, r = n - 1; l <= r; l++, r--) {
            if (nums[l] == target) {
                return l;
            } else if (nums[r] == target) {
                return r;
            }
        }
        return -1;
    }

    /**
     * 从大到小排列 二分法
     * https://leetcode.cn/problems/binary-search/submissions/
     *
     * @param nums
     * @param target
     * @return
     */
    public int search(int[] nums, int target) {
        int l = 0;
        int r = nums.length - 1;
        while (l <= r) {
            int m = (r - l) / 2 + l;
            int mNum = nums[m];
            if (mNum == target) {
                return m;
            } else if (mNum > target) {
                r = m - 1;
            } else {
                l = m + 1;
            }
        }
        return -1;
    }

    public int search1(int[] nums, int target) {
        int l = 0;
        int r = nums.length - 1;
        while (l <= r) {
            int m = l + ((r - l) >> 1);
            if (nums[m] > target) {
                r = m;
            } else if (nums[m] < target) {
                l = m + 1;
            } else {
                return m;
            }
        }
        return -1;
    }


    /**
     * 设计一个算法，找出数组中最小的k个数。以任意顺序返回这k个数均可
     *
     * @param arr
     * @param k
     * @return
     */
    public int[] smallestK(int[] arr, int k) {
        int[] res = new int[k];
        Arrays.sort(arr);
        for (int i = 0; i < k; i++) {
            res[i] = arr[i];
        }
        return res;
    }

    /**
     * 给你一个有序数组 nums ，请你 原地 删除重复出现的元素，使每个元素 最多出现两次 ，返回删除后数组的新长度。
     * <p>
     * 不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
     *
     * @param nums
     * @return
     */
    public int removeDuplicates(int[] nums) {
        int n = nums.length;
        if (n <= 2) {
            return n;
        }
        int slow = 2, fast = 2;
        while (fast < n) {
            if (nums[slow - 2] != nums[fast]) {
                nums[slow] = nums[fast];
                ++slow;
            }
            ++fast;
        }
        return slow;
    }

    /**
     * 给定两个以字符串形式表示的非负整数 num1 和 num2，返回 num1 和 num2 的乘积，它们的乘积也表示为字符串形式。
     *
     * @param num1
     * @param num2
     * @return
     */
    public String multiply(String num1, String num2) {
        if ("0".equals(num1) || "0".equals(num2)) {
            return "0";
        }
        int n1 = num1.length();
        int n2 = num2.length();
        int[] mul = new int[n1 + n2];
        for (int i = n1 - 1; i >= 0; i--) {
            int x = num1.charAt(i) - '0';
            for (int j = n2 - 1; j >= 0; j--) {
                int y = num2.charAt(i) - '0';
                mul[i + j + 1] += x * y;
            }
        }
        for (int i = n1 + n2 - 1; i > 0; i--) {
            mul[i - 1] += mul[i] / 10;
            mul[i] %= 10;
        }
        int index = mul[0] == 0 ? 1 : 0;
        StringBuffer ans = new StringBuffer();
        while (index < n1 + n2) {
            ans.append(mul[index]);
            index++;
        }
        return ans.toString();
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    /**
     * 输入一个链表，输出该链表中倒数第k个节点。为了符合大多数人的习惯，本题从1开始计数，即链表的尾节点是倒数第1个节点。
     * 例如，一个链表有 6 个节点，从头节点开始，它们的值依次是 1、2、3、4、5、6。这个链表的倒数第 3 个节点是值为 4 的节点。
     * <p>
     * 两次遍历法
     *
     * @param head
     * @param k
     * @return
     */
    public ListNode getKthFromEnd1(ListNode head, int k) {
        int n = 0;
        ListNode node = null;
        for (node = head; node != null; node = head.next) {
            n++;
        }
        for (node = head; n > k; n--) {
            node = node.next;
        }
        return node;
    }

    /**
     * 输入一个链表，输出该链表中倒数第k个节点。为了符合大多数人的习惯，本题从1开始计数，即链表的尾节点是倒数第1个节点。
     * 例如，一个链表有 6 个节点，从头节点开始，它们的值依次是 1、2、3、4、5、6。这个链表的倒数第 3 个节点是值为 4 的节点。
     * <p>
     * 双指针法     两个指针存在位移，然后一起移动
     *
     * @param head
     * @param k
     * @return
     */
    public ListNode getKthFromEnd2(ListNode head, int k) {
        ListNode fast = head;
        ListNode slow = head;
        //先让快的走k步
        for (; k > 0; k--) {
            fast = fast.next;
        }
        //当
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }

    /**
     * 165. 比较版本号
     * https://leetcode-cn.com/problems/compare-version-numbers/
     * <p>
     * 给你两个版本号 version1 和 version2 ，请你比较它们。
     * 版本号由一个或多个修订号组成，各修订号由一个 '.' 连接。每个修订号由 多位数字 组成，可能包含 前导零 。每个版本号至少包含一个字符。修订号从左到右编号，下标从 0 开始，最左边的修订号下标为 0 ，下一个修订号下标为 1 ，以此类推。例如，2.5.33 和 0.1 都是有效的版本号。
     * 比较版本号时，请按从左到右的顺序依次比较它们的修订号。比较修订号时，只需比较 忽略任何前导零后的整数值 。也就是说，修订号 1 和修订号 001 相等 。如果版本号没有指定某个下标处的修订号，则该修订号视为 0 。例如，版本 1.0 小于版本 1.1 ，因为它们下标为 0 的修订号相同，而下标为 1 的修订号分别为 0 和 1 ，0 < 1 。
     * 返回规则如下：
     * 如果 version1 > version2 返回 1，
     * 如果 version1 < version2 返回 -1，
     * 除此之外返回 0。
     *
     * @param version1
     * @param version2
     * @return
     */
    public static int compareVersion(String version1, String version2) {
        int n = version1.length(), m = version2.length();
        int i = 0, j = 0;
        while (i < n || j < m) {
            int x = 0;
            for (; i < n && version1.charAt(i) != '.'; ++i) {
                x = x * 10 + version1.charAt(i) - '0';
            }
            ++i; // 跳过点号
            int y = 0;
            for (; j < m && version2.charAt(j) != '.'; ++j) {
                y = y * 10 + version2.charAt(j) - '0';
            }
            ++j; // 跳过点号
            if (x != y) {
                return x > y ? 1 : -1;
            }
        }
        return 0;
    }

    /**
     * 所有奇数长度子数组的和
     * 给你一个正整数数组arr，请你计算所有可能的奇数长度子数组的和。
     * 子数组定义为原数组中的一个连续子序列。
     * 请你返回arr中所有奇数长度子数组的和 。
     *
     * @param arr
     * @return
     */
    public int sumOddLengthSubarrays(int[] arr) {
        int n = arr.length;
        int[] prefixSums = new int[n + 1];
        for (int i = 0; i < n; i++) {
            prefixSums[i + 1] = prefixSums[i] + arr[i];
        }
        int sum = 0;
        for (int start = 0; start < n; start++) {
            for (int length = 1; start + length <= n; length += 2) {
                int end = start + length - 1;
                sum += prefixSums[end + 1] - prefixSums[start];
            }
        }
        return sum;
    }

    /**
     * 给定两个整数 n 和 k，返回范围 [1, n] 中所有可能的 k 个数的组合。
     * 你可以按 任何顺序 返回答案。
     * 1 <= n <= 20
     * 1 <= k <= n
     *
     * @param n
     * @param k
     * @return
     */
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> res = new ArrayList<>();
        //递归处理


        return res;
    }

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
        for (int left = 0, right = people.length - 1; left <= right; res++) {
            if (people[left] + people[right--] <= limit) {
                left++;
            }
        }
        return res;
    }


}
