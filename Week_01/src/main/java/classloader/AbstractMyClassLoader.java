package classloader;

import java.io.IOException;
import java.io.InputStream;

public abstract class AbstractMyClassLoader extends ClassLoader {

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (name == null) {
            throw new IllegalArgumentException("class name can not be null");
        }

        /**
         * 已加载的直接返回
         */
        Class<?> clazz = findLoadedClass(name);
        if (clazz != null) {
            return clazz;
        }

        try (InputStream inputStream = getClassInputStream(name)) {
            byte[] bytes = getBytes(inputStream);
            return defineClass(name, bytes, 0, bytes.length);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            /**
             * 本地找不到class文件时尝试双亲委托加载
             */
            return super.loadClass(name);
        }
    }

    /**
     * 获取class文件输入流
     *
     * @param name
     * @return
     * @throws ClassNotFoundException class文件找不到时抛出ClassNotFoundException
     */
    protected abstract InputStream getClassInputStream(String name) throws ClassNotFoundException;

    /**
     * 转换成对应的字节码
     * @param inputStream
     * @return
     * @throws IOException
     */
    protected abstract byte[] getBytes(InputStream inputStream) throws IOException;
}
