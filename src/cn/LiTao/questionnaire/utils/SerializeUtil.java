package cn.LiTao.questionnaire.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

@Slf4j
public class SerializeUtil {

    public static <T> void serializeObject(T obj, String path, String filename) throws IOException {

        File file = new File(path);

        if (!file.exists()) {
            boolean result = file.mkdir();

            if (!result) {
                log.error("文件夹创建失败" + path);
                return;
            }
        }

        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path + "/" + filename));

        oos.writeObject(obj);
        oos.flush();
        oos.close();
    }

}
