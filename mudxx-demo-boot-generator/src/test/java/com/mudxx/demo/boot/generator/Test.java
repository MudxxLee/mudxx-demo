package com.mudxx.demo.boot.generator;

import org.springframework.boot.system.ApplicationHome;

import java.io.File;
import java.io.IOException;

/**
 * @author laiw
 * @date 2023/4/6 16:53
 */
public class Test {

    public static void main(String[] args) throws IOException {
        System.out.println(System.getProperty("user.dir"));
        System.out.println(new Test().getClass().getResource("/"));

        File directory = new File("");// 参数为空
        String courseFile = directory.getCanonicalPath();
        System.out.println("path2: "+ courseFile);

        ApplicationHome applicationHome = new ApplicationHome(Test.class);
        String dirPath = applicationHome.getSource().getParentFile().toString();
        System.out.println("path3: "+ dirPath);

    }

}
