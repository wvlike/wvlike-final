package com.wvlike.test;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.metric.HystrixCommandExecutionStarted;
import com.wvlike.user.dto.UserInfoDTO;
import com.wvlike.user.dto.UserSimpleInfoDTO;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StopWatch;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subjects.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Date: 2023/1/9
 * @Author: tuxinwen
 * @Description:
 */
public class CommonTest {

    public static void main(String[] args) throws InterruptedException {
//        test00();

//        test01();

//        test02();

//        test03();

//        test04();

//        test05();

//        test06();

//        test07();

//        test08();

//        test09();

//        test10();

        test12();

    }

    public static void test12() {
        Date capitalGrantTime = new Date(1763518457000L);
        Date approvalAcceptTime = DateUtil.parse("2025-11-18 11:10:00");
        Date grantTime = DateUtil.parse("2025-11-18 11:18:00");
        Long currentApplyId = 123L;
        Long notifyApplyId = 456L;
        Integer code;
        if (DateUtil.between(capitalGrantTime, approvalAcceptTime, DateUnit.DAY) > 30) {
            code = 0;
        }
        if (currentApplyId.equals(notifyApplyId)) {
            code = 1;
        }
        if (grantTime != null && DateUtil.betweenDay(grantTime, capitalGrantTime, true) == 0) {
            code = 1;
        }
        code = 0;

        System.out.println(code);

        System.out.println(111111111111L);
        System.out.println(DateUtil.betweenDay(grantTime, capitalGrantTime, true) == 0);
        System.out.println(DateUtil.between(grantTime, capitalGrantTime, DateUnit.DAY) == 0);

    }

    public static void test11() {
        int a = 1 * 3;
        int b = 7;
        int c = 3 * 3;
        System.out.println(a & 1);
        System.out.println(b & 1);
        System.out.println(c & 1);
        System.out.println(100 >>> 1);
    }

    public static void test10() {
        UserInfoDTO userInfo1 = UserInfoDTO.builder()
                .name("张三")
                .address("南山区")
                .isChange(true)
                .build();

        UserSimpleInfoDTO userInfo2 = UserSimpleInfoDTO.builder()
                .name("李四")
                .address("宝安区")
                .age(11)
                .build();

        BeanUtils.copyProperties(userInfo1, userInfo2);
        System.out.println(JSON.toJSONString(userInfo2));
    }

    public static void test09() {
        StopWatch stopWatch = new StopWatch("testtestsetse");

        stopWatch.start("121346546");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {

        }
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

    public static void test08() {
        Subject<HystrixCommandExecutionStarted, HystrixCommandExecutionStarted> writeOnlySubject = new SerializedSubject<>(PublishSubject.create());
        Observable<HystrixCommandExecutionStarted> readOnlyStream = writeOnlySubject.share();
        System.out.println(111111);

    }

    public static void test07() {
        Observable<Long> observableA = Observable.interval(300, TimeUnit.MILLISECONDS);
        observableA.takeUntil(e -> e == 5).subscribe(System.out::println);
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void test06() {
        Observable<Long> observableA = Observable.interval(300, TimeUnit.MILLISECONDS);
        Observable<Long> observableB = Observable.interval(1200, TimeUnit.MILLISECONDS);

        observableA.takeUntil(observableB)
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        System.exit(0);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        System.out.println(aLong);
                    }
                });

        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void test05() {
        Observable<Integer> observable = Observable.just(1, 2, 3, 4, 5);
        observable.subscribe(System.out::println);
        observable.map(i -> Observable.from(Lists.newArrayList(i)))
                .subscribe(objectObservable -> objectObservable.subscribe(e -> {
                    System.out.println(666);
                    System.out.println(e);
                }));
        observable.flatMap(i -> Observable.from(Lists.newArrayList(i)))
                .subscribe(e -> {
                    System.out.println(777);
                    System.out.println(e);
                });

    }


    private static void test04() throws InterruptedException {
        ReplaySubject<Long> objectReplaySubject = ReplaySubject.create();
        //创建一个原始的发射器
        Observable<Long> just = Observable.interval(1, TimeUnit.SECONDS);
        //使用ReplaySubject 来订阅 原始的发射器
        Subscription subscribe = just.subscribe(objectReplaySubject);
        //订阅 ReplaySubject 就像我们直接订阅just 一样。
        objectReplaySubject.subscribe(integer -> System.out.println(integer));
        TimeUnit.SECONDS.sleep(10);
        //可以通过 ReplaySubject 订阅的结果来取消 订阅ReplaySubject的 订阅者
        subscribe.unsubscribe();
        //10s后 重新订阅ReplaySubject
        objectReplaySubject.subscribe(integer -> System.out.println("====" + integer));
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }


    public static void test03() throws InterruptedException {
        //1.Observable<Long>===>Observable<ArrayList>的转变
        Observable<ArrayList<Long>> bucketStream =
                Observable.interval(1000, TimeUnit.MILLISECONDS)
                        .window(2000, TimeUnit.MILLISECONDS)
                        .flatMap(longObservable -> longObservable.reduce(new ArrayList<>(), (longs, aLong) -> {
                            longs.add(aLong);
                            return longs;
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


    private static void test02() throws InterruptedException {
        ArrayList<Long> startWith = new ArrayList<Long>() {{
            for (int i = 0; i < 5; i++) {
                add(0L);
            }
        }};
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

        //这部分的代码是关键 上面的代码我们在上面的例子中已经展示
        Observable<Long> longObservable = arrayListObservable.window(10, 1)
                .flatMap(temp -> temp.scan(0L, (aLong, longs) -> aLong + (Long) longs.stream().mapToLong(value -> value).sum()).skip(10))
                .share()
                .onBackpressureDrop();
        longObservable.subscribe(System.out::println);
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }

    private static void test01() throws InterruptedException {
        //这里完全模拟Hystrix的代码 首先发送一个窗口的空桶
        ArrayList<Long> startWith = new ArrayList<Long>() {{
            for (int i = 0; i < 10; i++) {
                add(1L);
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
                                    System.out.println(l1 + "--" + l2);
                                    return l1;
                                }))
                        .startWith(startWith));
        arrayListObservable.subscribe(System.out::println);
        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
    }

    private static void test00() throws InterruptedException {

        Observable.interval(100, TimeUnit.MILLISECONDS)
                .window(1, TimeUnit.SECONDS)
                .subscribe(windowObser -> {
                    System.out.println("============");
                    windowObser.subscribe(System.out::println);
                });


//        Observable<Long> arrayListObservable
//                = Observable
//                .defer(() -> Observable.interval(100, TimeUnit.MILLISECONDS).startWith(0L)
//                        .window(1000, TimeUnit.MILLISECONDS)
//                        .flatMap(longObservable -> longObservable.doOnEach(e -> {
//                            System.out.println(e.getValue());
//                            System.out.println("+++++++++++++++++");
//                                })
//                        )
//                );
//        arrayListObservable.subscribe(System.out::println);

        TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);

//        Observable.range(0, 10)
//                .scan((integer, integer2) -> {
//                    System.out.println("integer = " + integer);
//                    System.out.println("integer2 = " + integer2);
//                    return integer2;
//                })
//                .subscribe(System.out::println);
//        System.out.println("===========================");
//        Observable.range(0, 10)
//                .reduce((integer, integer2) -> {
//                    System.out.println("integer = " + integer);
//                    System.out.println("integer2 = " + integer2);
//                    return integer2;
//                })
//                .subscribe(System.out::println);
    }


}
