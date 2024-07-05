package com.example.zhaoshichuan.learnSkills;


import org.springframework.stereotype.Component;
import com.example.zhaoshichuan.aop.MyLog;

@Component
public class MyLogTest {

    @MyLog
    public void  testTime() throws InterruptedException {
        Thread.sleep(2000);
        System.out.println(111);
    }
}
