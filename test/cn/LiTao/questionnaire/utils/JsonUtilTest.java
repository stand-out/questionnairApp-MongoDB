package cn.LiTao.questionnaire.utils;

import cn.LiTao.questionnaire.pojo.AnswerData;
import cn.LiTao.questionnaire.pojo.Project;
import cn.LiTao.questionnaire.pojo.Questionnaire;
import cn.LiTao.questionnaire.pojo.ResponseBean;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtilTest {

    @Test
    public void test() throws JsonProcessingException {
        ResponseBean responseBean = new ResponseBean();
        HashMap<String, List> hashMap = new HashMap<>();
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("123");

        hashMap.put("user", arrayList);

        responseBean.setCode(1);
        responseBean.setMsg("Sms send error...");
//        responseBean.setData(hashMap);

        System.out.println(JsonUtil.ObjectToString(responseBean));;
    }

    @Test
    public void str2objTest() {
        String json = "{\"dataUuid\":\"31de240042ac409bae872195d41ac334\",\"id\":11,\"lastModifyTime\":1576487835000,\"projectName\":\"问卷标题\",\"projectStatus\":\"未发布\",\"projectType\":\"问卷\",\"questionnaire\":{\"title\":\"问卷标题\",\"describe\":\"感谢您能抽出几分钟时间来参加本次答题，现在我们就马上开始吧！\",\"questionList\":[{\"type\":\"radio\",\"title\":\"请选择下面一项\",\"questionCardClass\":\"questionCardChoice\",\"notNull\":true,\"optionList\":[\"选项1\",\"选项2\"]},{\"type\":\"checkbox\",\"title\":\"请选择下面多项\",\"questionCardClass\":\"questionCardChoice\",\"notNull\":true,\"optionList\":[\"选项1\",\"选项2\"]},{\"type\":\"fill-in\",\"title\":\"请填写下面内容\",\"questionCardClass\":\"questionCardFillIn\",\"notNull\":true,\"optionList\":[\"问题1\"]}],\"endDescribe\":\"您已完成本次问卷，感谢您的帮助与支持\",\"password\":\"\",\"ipCountLimit\":-1}}";
        String json2 = "{\"dataUuid\":\"31de240042ac409bae872195d41ac334\",\"id\":11,\"lastModifyTime\":\"2019-12-16T07:13:00.043Z\",\"projectName\":\"问卷标题\",\"projectStatus\":\"未发布\",\"projectType\":\"问卷\",\"questionnaire\":{\"title\":\"问卷标题\",\"describe\":\"感谢您能抽出几分钟时间来参加本次答题，现在我们就马上开始吧！\",\"questionList\":[],\"endDescribe\":\"您已完成本次问卷，感谢您的帮助与支持\",\"password\":\"\",\"ipCountLimit\":-1}}";
        Project project = JsonUtil.jsonToObject(json, Project.class);

        assert project != null;
        Questionnaire questionnaire = project.getQuestionnaire();

        System.out.println(project);

        System.out.println(questionnaire.getQuestionList());
    }

    @Test
    public void answerStr2Obj() throws JsonProcessingException {
        String json = "[{\"id\":\"7ext1sod6do0\",\"resultMap\":{\"java\":\"java\"}},{\"id\":\"6r7z7msqjh0\",\"resultMap\":{\"Idea\":\"Idea\"}},{\"id\":\"7gsnwjm4kq80\",\"resultMap\":{\"问题1\":\"y\"}},{\"id\":\"37d139cmye00\",\"resultMap\":{\"1+1\":\"2\",\"1*1\":\"1\",\"1/1\":\"1\"}},{\"id\":\"3q0jz0tr4xy0\",\"resultMap\":{\"问题1\":2}},{\"id\":\"6jt6rsgkawg0\",\"resultMap\":{}}]";
        //        ArrayList arrayList = JsonUtil.jsonToObject(json, ArrayList.class);
//
//        for (Object o : arrayList) {
//            String subString = o.toString().replace("=", ":");
////            JsonNode node = JsonUtil.getNode(subString);
//            System.out.println(subString);
//        }
//
//        String json = "{\"id\":\"7ext1sod6do0\", \"7ext1sod6do0\":2}";
//        JsonNode node = JsonUtil.getNode(json);
//
//        for (int i = 0; i < node.size(); i++) {
//            String subJson = node.get(i).toString();
//            AnswerData answerData = JsonUtil.jsonToObject(subJson, AnswerData.class);
//            System.out.println(answerData);
//        }
        List<AnswerData> answerDataList = JsonUtil.jsonToList(json, AnswerData.class);

//        for (AnswerData answerData : answerDataList) {
//            System.out.println(answerData);
//        }
        System.out.println(answerDataList);

    }

}
