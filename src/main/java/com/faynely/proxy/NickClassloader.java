package com.faynely.proxy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * 自定义的类加载器
 * 主要功能就是将类加载至 JVM
 *
 * @author NickFayne 2018-05-02 13:34
 */
public class NickClassloader extends  ClassLoader{

    private File classFile;

    public NickClassloader() {
        String classPath = NickClassloader.class.getResource("").getPath();
        this.classFile = new File(classPath);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try{
            String className = "." + name;
            String classAllName = NickClassloader.class.getPackage().getName() + "." + name;
            File classFile = new File(this.classFile, className.replaceAll("\\.", "/") + ".class");
            FileInputStream fis = new FileInputStream(classFile);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int len;
            while((len = fis.read(buffer)) != -1){
                baos.write(buffer, 0, len);
            }

            return defineClass(classAllName, baos.toByteArray(), 0, baos.size());
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
