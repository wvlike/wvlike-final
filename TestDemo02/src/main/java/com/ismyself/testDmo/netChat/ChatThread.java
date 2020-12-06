//package com.ismyself.testDmo.netChat;
//
//import org.springframework.util.CollectionUtils;
//
//import java.io.*;
//import java.net.Socket;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.Callable;
//import java.util.concurrent.Executors;
//import java.util.concurrent.Future;
//
///**
// * package com.ismyself.testDmo.netChat;
// *
// * @auther txw
// * @create 2020-09-06  12:03
// * @description：
// */
//public class ChatThread {
//    private List<String> msgs = new ArrayList<>();
//    private Map<String, Object> sockets;
//
//    public ChatThread(Map<String, Object> map) {
//        this.sockets = map;
//    }
//
//    public void start() {
//        new Thread(()->{
//            sendMsg();
//        }).start();
//        for (Map.Entry<String, Object> entry : sockets.entrySet()) {
//            String name = entry.getKey();
//            Socket socket = (Socket) entry.getValue();
//            new Thread(() -> {
//                try {
//                    InputStream inputStream = socket.getInputStream();
//                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//                    while (bufferedReader.ready()) {
//                        String msg = bufferedReader.readLine();
//                        msgs.add(name+": "+msg);
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }).start();
//        }
//    }
//
//    private void sendMsg() throws InterruptedException {
//        while(true){
//            if(CollectionUtils.isEmpty(msgs)){
//                msgs.wait();
//            }
//
//        }
//        for (Map.Entry<String, Object> entry : sockets.entrySet()) {
//            Socket socket = (Socket) entry.getValue();
//            OutputStream outputStream = socket.getOutputStream();
//            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(outputStream));
//
//        }
//        Future<Object> task = Executors.newCachedThreadPool().submit(()->{return 1;});
//        Callable callable = task.get();
//
//    }
//}
