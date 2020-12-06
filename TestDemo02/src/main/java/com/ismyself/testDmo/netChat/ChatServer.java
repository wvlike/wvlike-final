//package com.ismyself.testDmo.netChat;
//
//import java.io.*;
//import java.net.ServerSocket;
//import java.net.Socket;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Scanner;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * package com.ismyself.testDmo.netChat;
// *
// * @auther txw
// * @create 2020-09-06  11:30
// * @description：
// */
//public class ChatServer {
//
//    public static void main(String[] args) throws IOException {
//        ServerSocket server = new ServerSocket(1800);
//        System.out.println("建立服务器完成");
//        Map<String , Object> map = new ConcurrentHashMap<>();
//        while (true) {
//            Socket connect = server.accept();
//            InputStream inputStream = connect.getInputStream();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//            String name = reader.readLine();
//            System.out.println( name+"进入聊天室");
//            map.put(name,connect);
//            ChatThread chatThread = new ChatThread(map);
//            chatThread.start();
//        }
//    }
//
//    static class ChatClient {
//        public static void main(String[] args) throws IOException {
//            Socket socket = new Socket("localhost", 1800);
//            boolean connected = socket.isConnected();
//            System.out.println("连接成功，connected = " + connected);
//            System.out.print("请输入昵称：");
//            Scanner sn = new Scanner(System.in);
//            String nextLine = sn.nextLine();
//            OutputStream outputStream = socket.getOutputStream();
//            outputStream.write(nextLine.getBytes("utf-8"));
//            outputStream.flush();
//            System.out.println("可以开始聊天了");
//            while (true){
//                nextLine = sn .nextLine();
//                outputStream.write(nextLine.getBytes("utf-8"));
//                outputStream.flush();
//            }
//        }
//    }
//}
