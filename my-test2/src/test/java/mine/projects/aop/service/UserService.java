package mine.projects.aop.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    public void addUser(String username) {
        System.out.println("添加用户: " + username);
    }

    public void deleteUser(String username) {
        System.out.println("删除用户: " + username);
    }
}