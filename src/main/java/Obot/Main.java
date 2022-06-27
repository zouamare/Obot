package Obot;

import javax.security.auth.login.LoginException;
import java.io.IOException;


public class Main{
    //Main은 실행의 역할만 할 수 있게 구현
    public static void main(String[] args) throws LoginException, IOException {
        ObotController obotController = new ObotController();
        obotController.run();
    }
}