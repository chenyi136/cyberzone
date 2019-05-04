package com.safecode.cyberzone.global;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by xuq on 2018/7/4.
 */
//@AutoConfigureMockMvc
//@RunWith(SpringRunner.class)
//@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
public class SwaggerOfflineDoc {

    private static String snippetDir = "target/generated-snippets";
    private static String outputDir = "target/asciidoc";

    //
    public void getoffDoc() {
        // 这个outputDir必须和插件里面<generated></generated>标签配置一致
        try {
//           new MockMvc().perform(get("localhost:7000/v2/api-docs").accept(MediaType.APPLICATION_JSON))
//                    .andDo(SwaggerResultHandler.outputDirectory(outputDir).build())
//                    .andExpect(status().isOk())
//                    .andReturn();
            //读取url中的json文件
            StringBuilder json = new StringBuilder();
            URL urlObject = new URL("/v2/api-docs");
            URLConnection uc = urlObject.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();

//            Swagger2MarkupConverter.from(outputDir + "/swagger.json")
//                    .withPathsGroupedBy(GroupBy.TAGS)// 按tag排序
//                    .withMarkupLanguage(MarkupLanguage.ASCIIDOC)// 格式
//                    .withExamples(snippetDir)
//                    .build()
//                    .intoFolder(outputDir);// 输出
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
