package anno;

import java.io.File;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author laiw
 * @date 2023/7/11 11:19
 */
public class TestAnno {

    public static void main(String[] args) throws Exception {
//        //获取这个类
//        Class c = Class.forName("annotation.MyAnnotationTest01");
//        //判断类是否被@Annotation04注解
//        System.out.println(c.isAnnotationPresent(Annotation04.class));//true
//
//        //判断doSomething方法是否被@Annotation04注解
//        Method method = c.getDeclaredMethod("doSomething");
//        System.out.println(method.isAnnotationPresent(Annotation04.class));//true
//
//        //获取doSomething上的注解信息
//        //判断类是否被@Annotation04注解
//        if (c.isAnnotationPresent(Annotation04.class)){
//            //获取该注解对象(类上的)(顺便向下转型变成Annotation04类型)
//            Annotation04 myAnnotation = (Annotation04)c.getAnnotation(Annotation04.class);
//            System.out.println("类上面的注解对象：" + myAnnotation);
//            //获取该类中类上注解的value属性
//            System.out.println(myAnnotation.value());
//        }
//
//        Class stringClass = Class.forName("java.lang.String");
//        //判断String类中是否有Annotation这个注解
//        System.out.println(stringClass.isAnnotationPresent(Annotation04.class));//false

        List<Class> classList = getClassByPackage("com.mudxx.demo.boot.web.api");
        System.out.println(classList);

    }


    public static List<Class> getClassByPackage(String packageName) {
        try {
            Enumeration<URL> resources = TestAnno.class.getClassLoader().getResources(packageName.replaceAll("\\.", "/"));
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                Path path = Paths.get(url.toURI());
                List<Class> classList = new ArrayList<>();
                try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
                    for (Path p : stream) {
                        File file = p.toFile();
                        if (file.isDirectory()) {
                            continue;
                        }
                        if (file.exists()) {
                            System.out.println(packageName + "." + file.getName().replaceAll("\\.class", ""));
                            classList.add(Class.forName(packageName + "." + file.getName().replaceAll("\\.class", "")));
                        }
                    }
                }
                return classList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

