package com.preson.object.easyexecl;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @interfaceName DemoDateListener
 * @Description TODO
 * @Author admin
 * Date 2021/6/21 13:31
 * @Version 1.0
 */
public class DemoDateListener extends AnalysisEventListener<DemoData> {

    private static final int BATH_COUNT = 5;

    List arrayList = new ArrayList<DemoData>();



    @Override
    public void invoke(DemoData data, AnalysisContext context) {
         arrayList.add(data);
        for (Object o : arrayList) {
            System.out.println(o.toString());
        }
        if (arrayList.size() >= BATH_COUNT) {
            arrayList.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        saveData();
        System.out.println("所有数据加载完毕");
    }

    private void saveData() {

    }
}
