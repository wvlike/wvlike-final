package com.wvlike.test;

import org.junit.Test;
import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.subjects.BehaviorSubject;

import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @Date: 2023/1/9
 * @Author: tuxinwen
 * @Description:
 */
public class Common01Test {

    public static void main(String[] args) throws Exception {
        test01();

    }

    private static void test01() throws Exception {
        //这里完全模拟Hystrix的代码 首先发送一个窗口的空桶
        ArrayList<Long> startWith = new ArrayList<Long>() {{
            for (int i = 0; i < 10; i++) {
                add(0L);
            }
        }};
        //下面就是模拟把Long转成 ArrayList的过程startWith(0L)可以忽略 因为interval操作符开始不会
        //发送数据 等到100毫秒之后才发送第一个数据 这样会导致我们的window第一个窗口少一个数据
        //所以在这里使用startWith补齐
        Observable<ArrayList<Long>> arrayListObservable
                = Observable
                .defer(() -> Observable.interval(100, TimeUnit.MILLISECONDS).startWith(0L)
                        .window(1000, TimeUnit.MILLISECONDS)
                        .flatMap(longObservable ->
                                longObservable.reduce(new ArrayList<Long>(), (l1, l2) -> {
                                    l1.add(l2);
                                    return l1;
                                }))
                        .startWith(startWith));
        arrayListObservable.subscribe(System.out::println);
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
        //  这个集合之前就是
        //Observable.interval(100, TimeUnit.MILLISECONDS).startWith(0L).subscribe(System.out::println);
        //TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }

    @Test
    public void window1() throws Exception {

        Observable.interval(2000, TimeUnit.MILLISECONDS).startWith(0L)
                .window(2, TimeUnit.SECONDS)
                .subscribe(windowObser -> {
                    System.out.println("============");
                    windowObser.subscribe(System.out::println);
                });
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);

    }

    @Test
    public void window2() throws Exception {
        Observable.interval(1000, TimeUnit.MILLISECONDS)
                .window(2, 2)
                .subscribe(windowObs -> {
                    System.out.println("===========");
                    windowObs.subscribe(System.out::println);
                });
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }

    @Test
    public void test002() throws Exception {

        Observable.range(1, 10)
                .scan((integer, integer2) -> integer + integer2)
                .subscribe(System.out::println);
        Observable.range(1, 10)
                .reduce((integer, integer2) -> integer + integer2)
                .subscribe(System.out::println);
    }

    @Test
    public void test001() throws Exception {
        ArrayList<Long> startWith = new ArrayList<Long>();
        Observable<ArrayList<Long>> arrayListObservable
                = Observable
                .defer(() -> Observable.interval(100, TimeUnit.MILLISECONDS).startWith(0L)
                        .window(1000, TimeUnit.MILLISECONDS)
                        .flatMap(longObservable ->
                                longObservable.reduce(new ArrayList<Long>(), (l1, l2) -> {
                                    l1.add(l2);
                                    return l1;
                                })));
        arrayListObservable.share().subscribe(System.out::println);
        //这部分的代码是关键 上面的代码我们在上面的例子中已经展示
        Observable<Long> longObservable = arrayListObservable.window(10, 1)
                .flatMap(temp -> {
                    Observable<Long> scanObser = temp.scan(0L,
                            (aLong, longs) -> aLong + longs.stream()
                                    .collect(Collectors.summingLong(value -> value))).skip(10);
                    return scanObser;
                })
                .share()
                .onBackpressureDrop();
        longObservable.subscribe(System.out::println);
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }

    @Test
    public void cumulativeCounterStreamTest() throws InterruptedException {
        //1.Observable<Long>===>Observable<ArrayList>的转变
        Observable<ArrayList<Long>> bucketStream =
                Observable.interval(1000, TimeUnit.MILLISECONDS)
                        .window(2000, TimeUnit.MILLISECONDS)
                        .flatMap((Func1<Observable<Long>, Observable<ArrayList<Long>>>) longObservable -> longObservable.reduce(new ArrayList<Long>(), new Func2<ArrayList<Long>, Long, ArrayList<Long>>() {
                            @Override
                            public ArrayList<Long> call(ArrayList<Long> longs, Long aLong) {
                                longs.add(aLong);
                                return longs;
                            }
                        }));
        //2.Observable<ArrayList> ==> Observable<Long>
        Observable<Long> outputStream = bucketStream.scan(0L, (aLong, longs) -> {
            for (int i = 0; i < longs.size(); i++) {
                aLong += longs.get(i);
            }
            return aLong;
        }).skip(2);
        //3.BehaviorSubject订阅
        final BehaviorSubject subject = BehaviorSubject.create();
        outputStream.subscribe(subject);
        //4.每隔两秒输出一下最新的值
        new Thread(() -> {
            while (true) {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(subject.getValue());
            }
        }).start();
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }

    ExecutorService threadPoolExecutor = new ThreadPoolExecutor(10, Integer.MAX_VALUE,
            60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>());

    /**
     * 测试LinkedBlockingQueue
     * add：方法在添加元素的时候，若超出了度列的长度会直接抛出异常。
     * offer：方法添加元素，如果队列已满，直接返回false。
     * put：方法添加元素，如果队列已满，会阻塞直到有空间可以放。
     */
    @Test
    public void test003() throws Exception {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>(5);
        // Adding elements
        for (int i = 1; i <= 1000; i++) {
            int finalI = i;
            CompletableFuture.runAsync(() -> {
                try {
                    queue.add(finalI);
                    // take elements
                    Integer element = queue.take();
                    System.out.println(element);
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                }
            }, threadPoolExecutor);
        }
        Thread.sleep(10000);
        System.out.println("完成");
    }

    @Test
    public void test004() throws Exception {

    }

}
