package mine.jdk.debug.protobuf.simple;

import mine.jdk.debug.protobuf.proto.User;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author zero
 * @description Protobuf 最简单的使用示例
 * @date 2025-12-04
 */
public class ProtobufDemo {

    public static void main(String[] args) {
        // 1. 创建一个 User 对象
        User user = User.newBuilder()
                .setId(1)
                .setName("张三")
                .setEmail("zhangsan@example.com")
                .setAge(25)
                .build();

        System.out.println("=== 创建 User 对象 ===");
        System.out.println("ID: " + user.getId());
        System.out.println("姓名: " + user.getName());
        System.out.println("邮箱: " + user.getEmail());
        System.out.println("年龄: " + user.getAge());

        // 2. 序列化为字节数组
        byte[] serializedData = user.toByteArray();
        System.out.println("\n=== 序列化 ===");
        System.out.println("序列化后的字节数组长度: " + serializedData.length + " 字节");

        // 3. 从字节数组反序列化
        try {
            User deserializedUser = User.parseFrom(serializedData);
            System.out.println("\n=== 反序列化 ===");
            System.out.println("ID: " + deserializedUser.getId());
            System.out.println("姓名: " + deserializedUser.getName());
            System.out.println("邮箱: " + deserializedUser.getEmail());
            System.out.println("年龄: " + deserializedUser.getAge());

            // 4. 验证序列化和反序列化是否一致
            System.out.println("\n=== 验证 ===");
            System.out.println("序列化和反序列化是否一致: " + user.equals(deserializedUser));
        } catch (IOException e) {
            System.err.println("反序列化失败: " + e.getMessage());
        }

        // 5. 序列化到文件
        String fileName = "user.bin";
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            user.writeTo(fos);
            fos.close();
            System.out.println("\n=== 写入文件 ===");
            System.out.println("已写入文件: " + fileName);

            // 6. 从文件读取
            FileInputStream fis = new FileInputStream(fileName);
            User userFromFile = User.parseFrom(fis);
            fis.close();
            System.out.println("\n=== 从文件读取 ===");
            System.out.println("ID: " + userFromFile.getId());
            System.out.println("姓名: " + userFromFile.getName());
            System.out.println("邮箱: " + userFromFile.getEmail());
            System.out.println("年龄: " + userFromFile.getAge());
        } catch (IOException e) {
            System.err.println("文件操作失败: " + e.getMessage());
        }
    }
}

