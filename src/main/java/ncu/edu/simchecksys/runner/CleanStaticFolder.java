package ncu.edu.simchecksys.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Objects;

/**
 * @Author Ke Qiwen
 * @Date 2022/4/1 11:16
 * @Version 1.0
 * @Copyright KernelCurry
 * @description 用户存放jplag执行结果的文件夹
 */
@Component
@Slf4j
public class CleanStaticFolder implements ApplicationRunner {
    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void run(ApplicationArguments args) {
        final String folder = "static-file-folder/";
        final File root = new File(folder);
        if (!root.exists()) {
            root.mkdir();
        }
        File resource = new File(root, "resource");
        // 一次性使用，临时存放
        if (resource.exists()) {
            deleteDir(resource);
        } else {
            resource.mkdir();
        }
        File result = new File(root, "result");
        if (result.exists()) {
            deleteDir(result);
        } else {
            result.mkdir();
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void deleteDir(File root) {
        for (File dir : Objects.requireNonNull(root.listFiles())) {
            if (dir.isDirectory()) {
                for (File file : Objects.requireNonNull(dir.listFiles())) {
                    file.delete();
                }
            }
            dir.delete();
        }
    }
}

