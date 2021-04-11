package cn.LiTao.questionnaire.utils;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;

/**
 * @author Devil
 */

@Slf4j
public class AseUtil {
    public static JsonNode decrypt(byte [] content, byte [] keyByte, byte [] ivByte) {
        keyByte = fill(keyByte, 16);

        try {
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
            byte[] resultByte = cipher.doFinal(content);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");

                return JsonUtil.getNode(result);
            }
        } catch (Exception ex) {
            log.warn("微信用户信息解密失败", ex);
        }
        return null;
    }

    private static byte [] fill(byte [] bytes, int base) {
        if (bytes.length < base) {
            byte[] temp = new byte[base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(bytes, 0, temp, 0, bytes.length);
            return temp;
        }
        return bytes;
    }

}
