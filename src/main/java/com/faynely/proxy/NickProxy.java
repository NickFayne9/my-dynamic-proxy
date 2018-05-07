package com.faynely.proxy;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * 对应 jdk 中的 java.lang.reflect.Proxy
 *
 * @author NickFayne 2018-05-02 13:31
 */
public class NickProxy {

    private static final String _T = "\t";
    private static final String _N = "\r\n";

    public static Object newProxyInstance(NickClassloader nickClassloader, Class<?>[] interfaces, NickInvocationHandler nickInvocationHandler){

        //1. 生成代理类源码 .java(这里是重点)
        String resourceCode = generateProxyCode(interfaces);

        //2. 将代理类 .java 存至磁盘
        File javaFile = javaFileOutPut2Disk(resourceCode);

        //3. 将 .java 编译成 .class
        compileJavaClassFile(javaFile);

        //4. 将 .class 文件加载到 JVM 中

        Constructor constructor = loadClassFile2JVM(nickClassloader);

        //5. 返回代理类
        return getNewProxyInstance(constructor, nickInvocationHandler);
    }

    /**
     * 生成代理类源代码
     * @param interfaces
     * @return
     */
    private static String generateProxyCode(Class<?>[] interfaces){
        try{
            StringBuffer sb = new StringBuffer();
            sb.append("package ").append(NickProxy.class.getPackage().getName()).append(";").append(_N);
            sb.append("import com.faynely.proxy.NickProxy;").append(_N);
            sb.append("import java.lang.reflect.Method;").append(_N);

            sb.append("public class $Proxy0 implements ")
                    .append(interfaces[0].getName())
                    .append("{").append(_N);
            sb.append(_T).append("private final com.faynely.proxy.NickInvocationHandler nickInvocationHandler;").append(_N);
            sb.append(_T).append("public $Proxy0(com.faynely.proxy.NickInvocationHandler h) {").append(_N);
            sb.append(_T).append(_T).append("this.nickInvocationHandler = h;").append(_N);
            sb.append(_T).append("}").append(_N);

            for(Method m : interfaces[0].getMethods()){
                sb.append(_T).append("public ").append(m.getReturnType()).append(" ").append(m.getName()).append(" (){").append(_N);
                sb.append(_T).append(_T).append("try {").append(_N);
                sb.append(_T).append(_T).append(_T).append("Method m = ").append(interfaces[0].getName()).append(".class.getMethod(\"").append(m.getName()).append("\", new Class[]{});").append(_N);
                sb.append(_T).append(_T).append(_T).append("this.nickInvocationHandler.invoke(this, m, null);").append(_N);
                sb.append(_T).append(_T).append("} catch(Throwable e){").append(_N);
                sb.append(_T).append(_T).append(_T).append("e.printStackTrace();").append(_N);
                sb.append(_T).append(_T).append("}").append(_N);
                sb.append(_T).append("}").append(_N);
            }

            sb.append("}").append(_N);

            return sb.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将生成的 .java 源文件写入磁盘保存
     * @param srcStr
     * @return
     */
    private static File javaFileOutPut2Disk(String srcStr) {
        try {
            String filePath = NickProxy.class.getResource("").getPath();
            System.out.println(filePath);
            File f = new File(filePath + "$Proxy0.java");
            FileWriter fw = new FileWriter(f);
            fw.write(srcStr);
            fw.flush();
            fw.close();
            return f;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 编译上一步保存在磁盘中的文件
     */
    private static void compileJavaClassFile(File file) {
        try{
            JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager standardJavaFileManager = javaCompiler.getStandardFileManager(null, null, null);
            Iterable iterable = standardJavaFileManager.getJavaFileObjects(file);

            JavaCompiler.CompilationTask task = javaCompiler.getTask(null, standardJavaFileManager, null, null, null, iterable);
            task.call();
            standardJavaFileManager.close();
        } catch(Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 将 .class 文件加载到 JVM 中
     * @param nickClassloader
     * @return
     */
    private static Constructor loadClassFile2JVM(NickClassloader nickClassloader) {
        try{
            Class proxyClazz = nickClassloader.findClass("$Proxy0");
            Constructor constructor = proxyClazz.getConstructor(NickInvocationHandler.class);
            return constructor;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 实例化代理类
     * @param constructor
     * @param nickInvocationHandler
     * @return
     */
    private static Object getNewProxyInstance(Constructor constructor, NickInvocationHandler nickInvocationHandler) {
        try {
            return constructor.newInstance(nickInvocationHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
