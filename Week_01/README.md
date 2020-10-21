# 作业
***
## 作业二
自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法，此文件内容是一个 Hello.class 文件所有字节（x=255-x）处理后的文件

 ```java
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

```

## 作业三
画一张图，展示 Xmx、Xms、Xmn、Meta、DirectMemory、Xss 这些内存参数的关系
![内存模型](https://raw.githubusercontent.com/yanmeixue/JAVA-000/main/Week_01/JVM_memory_model.png)