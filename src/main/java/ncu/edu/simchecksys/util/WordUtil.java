package ncu.edu.simchecksys.util;

/**
 * @Author Ke Qiwen
 * @Date 2022/3/29 19:02
 * @Version 1.0
 * @Copyright KernelCurry
 * word文档操作工具
 */

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.ooxml.extractor.POIXMLTextExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

import java.io.FileInputStream;

@Slf4j
public class WordUtil {

    private static final String DOC = "doc";
    private static final String DOCX = "docx";

    /**
     * 读取word文档到String 之中
     * @param path
     * @return String
     */
    public static String readWord(String path) {
        String buffer = "";
        try {
            // DOC文件
            if (path.endsWith(DOC)) {
                FileInputStream is = new FileInputStream(path);
                WordExtractor ex = new WordExtractor(is);
                buffer = ex.getText();
                is.close();
            } else if (path.endsWith(DOCX)) {		//DOCX文件
                OPCPackage opcPackage = POIXMLDocument.openPackage(path);
                POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
                buffer = extractor.getText();
                opcPackage.close();
            } else {
                log.debug("此文件不是word文件！");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //new
        return buffer;
    }

}

