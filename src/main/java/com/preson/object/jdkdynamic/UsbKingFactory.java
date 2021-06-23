package com.preson.object.jdkdynamic;

/**
 * @ClassName UsbKingFactory
 * @Description TODO
 * @Author CYY
 * Date 2020/7/10 10:44
 * @Version 1.0
 */
public class UsbKingFactory implements UsbSell {

    @Override
    public float sell(int a) {

        System.out.println("目标类执行了方法");
        return 85.0f;
    }

}
