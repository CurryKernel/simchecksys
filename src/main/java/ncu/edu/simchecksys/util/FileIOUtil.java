package ncu.edu.simchecksys.util;

import java.io.*;

/**
 * @Author Ke Qiwen
 * @Date 2022/3/31 23:44
 * @Version 1.0
 * @Copyright KernelCurry
 */
public class FileIOUtil {
    //将str以指定编码方式，写入文件
    public static void saveFile(File outfile, String str, String encode) {
        BufferedWriter fr = null;
        try {
            fr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outfile, true), encode));
            fr.write(str);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}

