import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MyClassLoader extends ClassLoader {
    private static final byte OFFSET = (byte) 255;

    public static void main(String[] args) throws Exception {
        Class<?> helloClass = new MyClassLoader().findClass("Hello");
        helloClass.getMethod("hello").invoke(helloClass.newInstance());
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String fileName = name + ".xlass";
        Path path = Paths.get(System.getProperty("user.dir"), "Week_01", fileName);
        byte[] bytes;
        try {
            bytes = Files.readAllBytes(path);
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) (OFFSET - bytes[i]);
            }
        } catch (IOException e) {
            throw new ClassNotFoundException("readAllBytes fail, class name: " + name);
        }
        return defineClass(name, bytes, 0, bytes.length);
    }

}
