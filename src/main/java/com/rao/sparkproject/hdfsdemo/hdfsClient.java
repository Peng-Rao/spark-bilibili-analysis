package com.rao.sparkproject.hdfsdemo;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class hdfsClient {
    @Test
    public void testCopyFromLocalFile() throws IOException, InterruptedException, URISyntaxException {

        // 1 获取文件系统
        Configuration configuration = new Configuration();
        FileSystem fs = FileSystem.get(new URI("hdfs://localhost:8020"), configuration, "raopend");

        // 2 上传文件
        String[] files = new String[]{"data/0a3164f5-e82a-4b85-a9fa-7b351c6dd826", "data/93e21029-efd4-4754-ad03-424d1b5f3c5e"};
        for (String file : files) {
            fs.copyFromLocalFile(new Path(file), new Path("/data/"));
        }

        // 3 关闭资源
        fs.close();
    }
}
