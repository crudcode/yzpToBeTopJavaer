package com.yzp.toBeTopJavaer._3进阶篇.设计模式.单例模式.单例与序列化的那些事儿;

import java.io.*;

public class SerializableDemo1 {
    //为了便于理解，忽略关闭流操作及删除文件操作。真正编码时千万不要忘记
    //Exception直接抛出
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //Write Obj to file
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("temFile"));
        oos.writeObject(Singleton.getSingleton());
        //Read Obj from file
        File file= new File("tempFile");
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));

        Singleton newInstance = (Singleton) ois.readObject();
        //判断是否同一个对象  false
        System.out.println(newInstance == Singleton.getSingleton());
    }
}
