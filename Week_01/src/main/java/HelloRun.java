import classloader.MyClassLoader;

import java.lang.reflect.Method;

public class HelloRun {

    public static void main(String[] args) throws Exception {
        ClassLoader classLoader = new MyClassLoader();
        Class<?> clazz = classLoader.loadClass("Hello");
        Object obj = clazz.newInstance();
        Method method = clazz.getMethod("hello");
        method.invoke(obj);
    }
}
