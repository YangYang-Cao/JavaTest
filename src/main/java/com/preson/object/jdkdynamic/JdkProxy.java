package com.preson.object.jdkdynamic;

import org.junit.Test;

import java.lang.reflect.Proxy;

public class JdkProxy {

    /*
     * 动态代理
     *   在静态代理中的目标类很多时候，可以使用动态代理，避免静态代理的缺点
     *   动态代理中的类即使很多，代理类可以很少，当你修改接口中的方法时不会影响代理类
     *
     * 动态代理
     *       在程序执行过程中，使用jdk的反射机制创建代理类对象，并动态指定要代理的目标类
     *       换句话说：动态代理是一种船舰Java对象的能力，让你不要创建类，就能船舰代理类对象
     *
     *           在Java 中要创建对象
     *               1，创建类文件，Java 文件编译为class
     *               2，使用构造方法，创建类对象
     *
     *
     *  动态代理的实现：1、jdk动态代理：使用Java放书包中的类和接口实现 动态代理功能
     *                    反射包 java.lang.reflect 里面有三个类：InvocationHandler, Method , Proxy
     *                 2、CGlib动态代理（了解）:CGlib 是第三方的工具库，创建代理对象。
     *                   CGlib 的原理是继承，cglib通过几次目标类，创建他的之类，在之类中
     *                   重写父类中同名的方法，实现功能的修改
     *
     *     JDK动态代理
     *       1、反射，Method类，表示方法。类中的方法。通过Method可以执行某个方法
     *       2、jdk动态代理的实现原理
     *           反射包 Java.lang.reflect ,里面有三个类 InvocationHandler, Method , Proxy
     *           （1）、InvocationHandler接口：就一个方法invoke（）
     *                   invoke() ：表示代理对象实现的功能代码，你的代理类要完成的功能就写在invoke() 方法中
     *                   代理类完成的功能：
     *                   1、调用目标方法，执行目标方法的功能
     *                   2、功能增强，在目标方法调用时，增加功能
     *
     *
     *        方法原型：
     *           参数：object proxy:jdk创建的代理对象，无需赋值。
     *                 method method：目标类中的方法，jdk提供method对象的
     *                 object[] arge：目标类中的方法的参数，jdk提供method对象的
     *            public object invoke (object proxy ， method method ，object[] args )
     *
     *   invocationHandler 接口：表示你的代理要干什么
     *   怎么用 ：1、创建类实现接口 InvocationHandler
     *            2、重写invoke（）方法，把原来静态代理中的代理类要完成的功能，写在这个方法当中
     *
     *    method类 ：表示方法的，确切的说就是目标代理类中的方法
     *           作用：通过method可以执行某个目标类的方法，method。invoke（）；
     *                 使用方式；method.invoke() 第一个参数表示对象的要执行这个对象的方法，第二个参数表示方法执行时的参数值类型
     *                   返回值，是方法执行后的返回值
     *
     *
     *
     *
     *  3,实现动态代理的步骤
     *       1、创建接口，定义目标类要完成的功能
     *       2、创建目标类实现接口
     *       3、创建invocationHandler接口的实现类，在invoke方法中完成代理类的功能
     *           1、调用目标方法
     *           2、增强功能
     *       4、使用proxy类的静态方法，创建代理对象。并把返回值转为接口类型
     *
     *
     * */

    @Test
    public void proxyTest() {


        UsbKingFactory kingFactory = new UsbKingFactory();
        ProxyClass proxyClass = new ProxyClass(kingFactory);
        UsbSell o = (UsbSell) Proxy.newProxyInstance(kingFactory.getClass().getClassLoader(),
                kingFactory.getClass().getInterfaces(),
                proxyClass);
        float sell = o.sell(36);
        System.out.println(sell);

    }

}
