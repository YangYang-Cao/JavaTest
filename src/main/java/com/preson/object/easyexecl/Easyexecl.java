package com.preson.object.easyexecl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.enums.WriteDirectionEnum;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import org.junit.Test;

public class Easyexecl {

    @Test
    public void simpleReads() {
        ExcelReader build = EasyExcel.read("demo.xlsx",
                DemoData.class, new DemoDateListener()).build();
        ReadSheet readSheet = EasyExcel.readSheet(0).build();

        build.read(readSheet);

        if (build != null) {
            build.finish();
        }

    }

    @Test
    public void indexOrNameReads(){
        EasyExcel.read("demo.xlsx",DemoData.class,new DemoDateListener()).sheet().doRead();
    }


    /**
     * 读多个或者全部sheet,这里注意一个sheet不能读取多次，多次读取需要重新读取文件
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>
     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoDateListener}
     * <p>
     * 3. 直接读即可
     */
    @Test
    public void repeatedRead() {
        // 读取全部sheet
        // 这里需要注意 DemoDataListener的doAfterAllAnalysed 会在每个sheet读取完毕后调用一次。然后所有sheet都会往同一个DemoDataListener里面写
        // EasyExcel.read("demo.xlsx", DemoData.class, new DemoDateListener()).doReadAll();

        // 读取部分sheet

        ExcelReader excelReader = null;
        try {
            excelReader = EasyExcel.read("demo.xlsx").build();

            // 这里为了简单 所以注册了 同样的head 和Listener 自己使用功能必须不同的Listener
            ReadSheet readSheet1 =
                    EasyExcel.readSheet(0).head(DemoData.class).registerReadListener(new DemoDateListener()).build();
            ReadSheet readSheet2 =
                    EasyExcel.readSheet(1).head(DemoData.class).registerReadListener(new DemoDateListener()).build();
            // 这里注意 一定要把sheet1 sheet2 一起传进去，不然有个问题就是03版的excel 会读取多次，浪费性能
            excelReader.read(readSheet1, readSheet2);
        } finally {
            if (excelReader != null) {
                // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
                excelReader.finish();
            }
        }
    }


    /**
     * 单租数据填充 （模板）
     */
    @Test
    public void test() {

        String string = "template.xlsx";

        ExcelWriterBuilder template = EasyExcel.write("EasyExecl-.xlsx", FillData.class).withTemplate(string);

        ExcelWriterSheetBuilder sheet = template.sheet();

        FillData fillData = new FillData();
        fillData.setName("测试");
        fillData.setAge("男");

        sheet.doFill(fillData);


    }

    /**
     * 多组数据填充 （模板） (水平填充)
     */
    @Test
    public void test1() {
        // 创建一个工作薄对象
        ExcelWriter build = EasyExcel.write("EasyExecl-.xlsx", FillData.class).withTemplate("template1.xlsx").build();

        // 创建工作表对象
        WriteSheet writeSheet = EasyExcel.writerSheet().build();

        // 组合填充时 、因为多组数据填充数据不确定 需要在填充完之后另起一行
        FillConfig fillConfig = FillConfig.builder().forceNewRow(true).build();

//        build.fill(, fillConfig,);
//        build.fill(, );

        build.finish();
    }

    /**
     * 多组数据填充 （模板） (横着填充)
     */
    @Test
    public void test2() {
        // 创建一个工作薄对象
        ExcelWriter build = EasyExcel.write("EasyExecl-.xlsx", FillData.class).withTemplate("template1.xlsx").build();

        // 创建工作表对象
        WriteSheet writeSheet = EasyExcel.writerSheet().build();

        // 组合填充时 、
        FillConfig fillConfig = FillConfig.builder().direction(WriteDirectionEnum.VERTICAL).build();


        // build.fill(, fillConfig,);


        build.finish();
    }


}
