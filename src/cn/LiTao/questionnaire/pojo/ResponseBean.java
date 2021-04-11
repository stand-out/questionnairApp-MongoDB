package cn.LiTao.questionnaire.pojo;

import cn.LiTao.questionnaire.utils.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

public class ResponseBean<D> {

    private int code;
    private D data;
    private String msg;

    public ResponseBean(int code, D data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public static ResponseBean<String> SUCCESS() {
        return new ResponseBean<>(0, "SUCCESS", "SUCCESS");
    }

    public ResponseBean() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ResponseBean{" +
                "code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }

    public String toJson() throws JsonProcessingException {
        return JsonUtil.objectToString(this);
    }
}
