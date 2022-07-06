package com.example.myapplication.javatest;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

/**
 * 实现socket，tcp和udp通讯
 */
public class SocketTest {
    private void testTcp(){
        try {
            //创建一个Socket对象，并指定服务端的IP及端口
            Socket socket = new Socket("192.23.25",8080);
            //获取Socket的OutputStream对象用于发送数据。
            OutputStream outputStream = socket.getOutputStream();
            //获取要传入的文件
            FileInputStream fileInputStream = new FileInputStream("a.txt");
            byte[] bytes = new byte[1024];
            int len;
            while ((len=fileInputStream.read(bytes,0,bytes.length))!=-1){
                outputStream.write(bytes,0,len);
            }
            //发送读取的数据到服务端
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void testUDP(){
        try {
            //创建DatagramSocket对象并指定一个端口号，注意，如果客户端需要接收服务器的返回数据,还需要使用这个端口号来receive，所以一定要记住
            DatagramSocket socket = new DatagramSocket(8080);
            //参数一:要接受的data参数二：data的长度
            byte[] data = new byte[2048];
            DatagramPacket packet = new DatagramPacket(data,data.length);
            socket.receive(packet);
            InetAddress address = InetAddress.getByName("192.25.23");
            //设置要发送的数据
            String str = "[asdasdads]";
            byte[] bytes = str.getBytes();
            //参数一：要发送的数据参数二：数据的长度参数三：服务端的网络地址参数四：服务器端端口号
            socket.send(new DatagramPacket(bytes,bytes.length,address,8080));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
