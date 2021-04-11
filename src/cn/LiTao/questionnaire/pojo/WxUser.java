package cn.LiTao.questionnaire.pojo;

import lombok.*;

/**
 * @author Devil
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class WxUser {
    private String openId;
    private String nickName;
    private int gender;
    private String language;
    private String city;
    private String province;
    private String country;
    private String avatarUrl;
}
