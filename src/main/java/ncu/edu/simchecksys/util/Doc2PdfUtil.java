package ncu.edu.simchecksys.util;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;
import lombok.extern.slf4j.Slf4j;
import ncu.edu.simchecksys.constant.OtherConstant;

import java.io.File;
import java.net.ConnectException;

/**
 * @Author Ke Qiwen
 * @Date 2022/3/31 23:43
 * @Version 1.0
 * @Copyright KernelCurry
 */
@Slf4j
public class Doc2PdfUtil {

    private String openOfficeHost;
    private Integer openOfficePort;

    public Doc2PdfUtil() {
    }

    public Doc2PdfUtil(String openOfficeHost, Integer openOfficePort) {
        this.openOfficeHost = openOfficeHost;
        this.openOfficePort = openOfficePort;
    }


    /**
     * doc转pdf
     * @return pdf文件路径
     */
    public File doc2Pdf(StringBuffer fileName, String name) throws ConnectException {
        File docFile = new File(fileName.toString());
        if (name.endsWith("doc")){
            name = name.replaceAll("doc","pdf");
        }else {
            name = name.replaceAll("docx","pdf");
        }
        File officeFile = new File(OtherConstant.REALPATH ,"pdf临时文件夹");
        if (!officeFile.exists()){
            officeFile.mkdir();
        }
        File pdfFile = new File(OtherConstant.REALPATH ,"pdf临时文件夹" + File.separator + name);
        if (docFile.exists()) {
            if (!pdfFile.exists()) {
                OpenOfficeConnection connection = new SocketOpenOfficeConnection(openOfficeHost, openOfficePort);
                try {
                    connection.connect();
                    DocumentConverter converter = new StreamOpenOfficeDocumentConverter(connection);
                    //最核心的操作，doc转pdf
                    converter.convert(docFile, pdfFile);
                    connection.disconnect();
                    log.info("****pdf转换成功，PDF输出：" + pdfFile.getPath() + "****");
                } catch (java.net.ConnectException e) {
                    log.info("****pdf转换异常，openoffice服务未启动！****");
                    e.printStackTrace();
                    throw e;
                } catch (com.artofsolving.jodconverter.openoffice.connection.OpenOfficeException e) {
                    System.out.println("****pdf转换器异常，读取转换文件失败****");
                    e.printStackTrace();
                    throw e;
                } catch (Exception e) {
                    e.printStackTrace();
                    throw e;
                }
            }
        } else {
            log.info("****pdf转换异常，需要转换的doc文档不存在，无法转换****");
        }
        return pdfFile;
    }
}