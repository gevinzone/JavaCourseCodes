package com.gevinzone.jvm;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

public class XlassLoader extends ClassLoader{
    public static void main(String... args) throws Exception {
        final String className = "Hello";
        final String methodName = "hello";
        ClassLoader classLoader = new XlassLoader();
        Class<?> clazz = classLoader.loadClass(className);
        for (Method m : clazz.getDeclaredMethods()) {
            System.out.println(clazz.getSimpleName() + "." + m.getName());
        }
        Object instance = clazz.getDeclaredConstructor().newInstance();
        Method method = clazz.getMethod(methodName);
        method.invoke(instance);

    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String xlassFilePath = "Hello.xlass";
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(xlassFilePath);
        try {
            byte[] bytes = readStream(inputStream);
            return defineClass(name, bytes, 0, bytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException(name, e);
        }
    }

    private byte[] readStream(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            throw new IOException();
        }
        try {
            int length = inputStream.available();
            byte[] byteArray = new byte[length];
            inputStream.read(byteArray, 0, byteArray.length);
            return decode(byteArray);
        }  finally {
            close(inputStream);
        }
    }

    private byte[] decode(byte[] byteArray) {
        byte[] targetArray = new byte[byteArray.length];
        for (int i = 0; i < byteArray.length; i++) {
            targetArray[i] = (byte) (255 - byteArray[i]);
        }
        return targetArray;
    }

    private static void close(Closeable res) {
        if (null != res) {
            try {
                res.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
