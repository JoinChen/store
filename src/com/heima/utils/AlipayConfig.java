package com.heima.utils;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */
public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

        // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
        public static String app_id = "2016080200146504";

        // 商户私钥，您的PKCS8格式RSA2私钥
        public static String merchant_private_key = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCDmE8G3mJw5fXqHQF+22CGsvSGpHkNyUCjX1QuOmRKJQ5bVDynCxnyscLiTg5x7kzkBOhilkUmWeEM1bQiKqqIYbu22GYLKTD62q4w5ztiasZ9g18HPmRFHHj5bcEPpfyYQeNyE9TS5bpUO4uUIt7npP1e1ZbBfVJWqlJ/kGZMiwMG9P7HV9ccUfGpA2fKc0YPD6bH7bcK6WcdYcxL6hghm+Vsimqnk+omIYxWSkQCaq56g39/CUSgjYB1GAepvRDeVtIAhR8ryPETURfpsllf9+2WfDC1ujuB7nVdfxxx+UFRq+oUQXK5YDy+qwBk0w5FeMfgI8OeoDA1Ca5wIesPAgMBAAECggEBAIH8DXuMaRJ5o+oHkrWavoLu+6iPh999PnLhtAYhuXSfm8nMsEiUJ6JZwlXFonEZYSwOSPkw08cpGqqR4Oeq213xo/jdUhaOLoprPYqirT7ul3fdLx25nFPD5x8CMaRiYdqHosHJIvza3K1dT+cYdrO7fmaRdeNUBeQDQ5Ars1DAyIz78nluhd0SKZCEm3eaVVhPXkUqsHRGPEk5Sj4qzfi2cCIk/s1OUsPU+m2DKSZczdEYaudb85NMwX2qB1uZf2v6t/qKjWN5cbTx6t48EkE8uCi40vbgJDqZ8/JABSPJAqc8tRhYQhiqmT6iJ8BEhwQMjgCVGaL9HbUX6v3k80ECgYEAvTWY+AY5emd8WeYJXmV2Nfm42v/DJ5KMsnuHxFWrnjkmpSxS6vMNfaIpC0sGnI+hnK+0mYZNu1zDeb/oE/FIBl44ZxS5MdYLNcvm8DVt5vUPFfHHYybB6z53XAEuWG7KbXr1ME3RvT4aDqA+SsnT7zunBqPZ3A7Rro8LmRYEKa8CgYEAsgw7vUXSlU5V23QZD3rRkQowMWqi9I2ttolZy0DV0U1jpVHemp1sAs0LZuoECLmhybRaZ3kngCP9MgVKEGaQ9NLRB9N6TgQ41UVaE5UoKnBjAXwvB03zxfP2KF4RR/xPK5kOceiE/6dto6wBTSa/G3YM1ATskk/7DFGrn0DIjKECgYEAtNe4mD7iyxKEMSyEAdw6Kwa+31qbfYQ9xA37yvUTLgxz2mVN71BVsaG/VxLjg9poXmk11ZMfUpgsIX8B46W28Jc3k5akF3T8i/OSjD6VB61sOM45g5Q0vpKa5K4gTNe/1PozxXJrqFPl9drFDxhu33aFgvwyq3jigtQoAq6fVfMCgYAqg7NMGb0T+9WShmFpidk6ueUSF7V7kc0WFiwD1aJ4zPLrSeZOoyJkdNOxKlX2Q2U3hcJEjPB6r51I8WxjCCq62xAgv0WoFWgAvQOqQ2aI47cwZpfQNbPs47k2oGed+chpz61cU7pWVYNfnrXc11Cw3PppMhfPRBvdMC874rtAoQKBgQCdhtl99N7d2MxkheLXHvNqP8g7Bsa9hnZEzWXzhalfIvRGurXzq89F8dGYxCOBuXSo9N3oDcrYirapwcZLm7mUGQNZ6hHzbkeKM1vo9qWVYD/6wXg4QrCkZmx+iTxIr3yOH4uFV5irDyb2MQz5VNo7xKHBWZ4bn3/XjURBFELUjQ==";
        // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
        public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA05IAUkL2O0rZys94o2P1gKOi/9sCnm/mxNel7TkZZ4XEg9QhB9VW6FteOGanAaCqpv1liK5mp2ftllgMmuz/tI+P+uPnXKcgHZ4zk44kOw9wlcz4Vjp+bpqeAl7LnQJrUUVL31zYG7m+CkVSKG6epMfAXTh6gRtRUSs6vxBJik4koWZ/I4pkdE5coqfygfcOTezohhB0p+8I+RtJLrj3wKVSrM/iESrRcfkvBYefsF8yzcIhF1GvkWkmM6hZfKZrWuYFInr1ZkwCizfQZrChQj/ZCf0vcpY87TdL3ptOKicldT2OR/Aln/Q9t7zC3P4laoEWbca3uDTR14j2h+elswIDAQAB";
        // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
        public static String notify_url = "http://localhost:8080/notify_url.jsp";

        // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
        public static String return_url = "http://localhost:8080/return_url.jsp";

        // 签名方式
        public static String sign_type = "RSA2";

        // 字符编码格式
        public static String charset = "utf-8";

        // 支付宝网关
        public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

        // 支付宝网关
        public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

        /**
         * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
         * @param sWord 要写入日志里的文本内容
         */
        public static void logResult(String sWord) {
            FileWriter writer = null;
            try {
                writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
                writer.write(sWord);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
}
