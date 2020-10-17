package classloader;

import java.io.*;

public class MyClassLoader extends AbstractMyClassLoader {
    private static final int base = 255;

    @Override
    protected InputStream getClassInputStream(String name) throws ClassNotFoundException {
        String path = getClass().getResource("/").getPath() + name + ".xlass";
        File file = new File(path);

        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new ClassNotFoundException("can not find class file path: " + path);
        }
    }

    @Override
    protected byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int length;
        while ((length = inputStream.read(bytes)) != -1) {
            for (int i = 0; i < length; i++) {
                /**
                 * 解码
                 */
                bytes[i] = (byte) (base - bytes[i] & 0xFF);
            }

            byteArrayOutputStream.write(bytes, 0, length);
        }

        return byteArrayOutputStream.toByteArray();
    }
}
