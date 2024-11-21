package com.wvlike.msg.readFile;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.util.StrUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date: 2024/9/20
 * @Author: tuxinwen
 * @Description: 去空格
 */
public class TxtFileReader {

    public static void main(String[] args) {
        String fileName = "C:\\Users\\kingdee\\Desktop\\新建文本文档.txt";

//        List<String> list = new ArrayList<>();
        FileReader fileReader = new FileReader(fileName);
        for (String line : fileReader.readLines()) {
            if (StrUtil.isNotBlank(line)) {
                System.out.println(line);
            }
        }



    }

}
