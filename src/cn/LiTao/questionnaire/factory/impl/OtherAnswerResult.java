package cn.LiTao.questionnaire.factory.impl;

import cn.LiTao.questionnaire.factory.AnswerResult;
import cn.LiTao.questionnaire.pojo.AnswerResultList;
import cn.LiTao.questionnaire.utils.AnswerResultListFilter;
import cn.LiTao.questionnaire.utils.JsonUtil;

import java.util.List;
import java.util.Map;

public class OtherAnswerResult extends AnswerResult {
    public OtherAnswerResult(AnswerResultList answerDataList) {
        super(answerDataList);
    }

    @Override
    public String result2Str() throws Exception {

        List<Map<String, String>> list = AnswerResultListFilter.resultPack(this.answerDataList.getList());

        return JsonUtil.objectToString(list);
    }
}
