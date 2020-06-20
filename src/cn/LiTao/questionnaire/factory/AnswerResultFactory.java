package cn.LiTao.questionnaire.factory;

import cn.LiTao.questionnaire.dependent.ProjectStatus;
import cn.LiTao.questionnaire.factory.impl.ChoiceAnswerResult;
import cn.LiTao.questionnaire.factory.impl.OtherAnswerResult;
import cn.LiTao.questionnaire.pojo.AnswerResultList;
import com.fasterxml.jackson.core.JsonProcessingException;

public class AnswerResultFactory {

    public String getResultStr(String type, AnswerResultList answerResultList) throws Exception {

        if (ProjectStatus.RADIO.equals(type) || ProjectStatus.CHECKBOX.equals(type)) {
            return new ChoiceAnswerResult(answerResultList).result2Str();
        } else {
            return new OtherAnswerResult(answerResultList).result2Str();
        }

    }

}
