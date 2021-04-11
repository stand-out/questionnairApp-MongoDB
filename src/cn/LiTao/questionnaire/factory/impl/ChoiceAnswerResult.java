package cn.LiTao.questionnaire.factory.impl;

import cn.LiTao.questionnaire.factory.AnswerResult;
import cn.LiTao.questionnaire.pojo.AnswerResultList;
import cn.LiTao.questionnaire.utils.AnswerResultListFilter;
import cn.LiTao.questionnaire.utils.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Map;

public class ChoiceAnswerResult extends AnswerResult {
    public ChoiceAnswerResult(AnswerResultList answerDataList) {
        super(answerDataList);
    }

    @Override
    public String result2Str() throws JsonProcessingException {

        Map<String, Integer> statistical = AnswerResultListFilter.statistical(this.answerDataList.getList());

        return JsonUtil.objectToString(statistical);
    }
}
