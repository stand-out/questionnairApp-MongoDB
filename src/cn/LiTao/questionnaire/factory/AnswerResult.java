package cn.LiTao.questionnaire.factory;

import cn.LiTao.questionnaire.pojo.AnswerResultList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public abstract class AnswerResult {

    protected AnswerResultList answerDataList;

    public AnswerResult(AnswerResultList answerDataList) {
        this.answerDataList = answerDataList;
    }

    public abstract String result2Str() throws Exception;

}
