package com.preson.object.easyexecl;

import com.alibaba.excel.annotation.format.DateTimeFormat;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @ClassName DemoData
 * @Description TODO
 * @Author CYY
 * Date 2021/6/21 13:30
 * @Version 1.0
 */
@Data
@ToString
public class DemoData {


    private String string;


    @DateTimeFormat("yyyy年MM月dd日")

    private Date date;

    private double doubleDate;


}
