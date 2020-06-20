package cn.LiTao.questionnaire.dependent;

import cn.LiTao.questionnaire.pojo.Questionnaire;
import cn.LiTao.questionnaire.utils.SerializeUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.UUID;

//管理所有问卷的容器（单例）
@Slf4j
public class QuestionnaireContainer {

    private static QuestionnaireContainer container;
//    问卷map容器
    private HashMap<String, Questionnaire> questionnaireMap;

    private ServletContext servletContext;
//    存放序列化对象的路径
    private String questionnaireCachePath;

//    对象实例化私有 初始化map和servletContext
    private QuestionnaireContainer(ServletContext servletContext) {
        this.questionnaireMap = new HashMap<>();
        this.servletContext = servletContext;

        this.questionnaireCachePath = servletContext.getRealPath("/cache");

        log.info(this.questionnaireCachePath);
    }

//    获取容器单例
    public static QuestionnaireContainer getContainer(ServletContext servletContext) {

        if (container == null) {
            synchronized (QuestionnaireContainer.class) {
                if (container == null) {
                    container = new QuestionnaireContainer(servletContext);
                }
            }
        }
        return container;
    }

    public static QuestionnaireContainer getContainer() {
        return getContainer(null);
    }

//    根据uuid获取容器里的问卷对象，如果不存在则去目录下找找到反序列化存入容器，找不到返回null
    public Questionnaire getQuestionnaire(String uuid) {
//        根据uuid从容器中获取文件对象
        Questionnaire questionnaire = questionnaireMap.get(uuid);

//        容器中不存在
        if (questionnaire == null) {

//            拼凑对应文件路径
            String dataPath = this.questionnaireCachePath + "/" + uuid;
            try {
//                读取文件
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataPath));
                Object obj = ois.readObject();

                ois.close();

                if (obj instanceof Questionnaire) {
                    Questionnaire questionnaireObj = (Questionnaire) obj;
//                    存入容器
                    this.questionnaireMap.put(uuid, questionnaireObj);

                    log.info("从本地缓存文件找到 " + uuid + " 加入容器");
                    return questionnaireObj;
                }
                else
                    return null;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        } else return questionnaire;
    }

//    把对象加入容器并序列化
    public String addQuestionnaire(Questionnaire questionnaire) {
        String uuid = UUID.randomUUID().toString().replace("-", "");

        try {
            SerializeUtil.serializeObject(questionnaire, this.questionnaireCachePath, uuid);
            questionnaireMap.put(uuid, questionnaire);
            return uuid;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

//    更新容器中的questionnaire对象，并把旧cache覆盖
    public boolean updateQuestionnaire(Questionnaire questionnaire, String uuid) {

        try {
            SerializeUtil.serializeObject(questionnaire, this.questionnaireCachePath, uuid);
            this.questionnaireMap.remove(uuid);
            this.questionnaireMap.put(uuid, questionnaire);
            log.info(uuid + " 问卷更新成功");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

}
