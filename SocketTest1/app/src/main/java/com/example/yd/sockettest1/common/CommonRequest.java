package com.example.yd.sockettest1.common;

import com.example.yd.sockettest1.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by 张YD on 2017/8/21.
 */

public class CommonRequest {
    /**
     * 请求码，类似于接口号（在本文中用Servlet做服务器时暂时用不到）
     */
    private String requestCode;
    /**
     * 请求参数
     * （说明：这里只用一个简单map类封装请求参数，对于请求报文需要上送一个数组的复杂情况需要自己再加一个ArrayList类型的成员变量来实现）
     */
    private HashMap<String, String> requestParam;

    public CommonRequest() {
        requestCode = "";
        requestParam = new HashMap<>();
    }

    /**
     * 设置请求代码，即接口号，在本例中暂时未用到
     */
    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    /**
     * 为请求报文设置参数
     * @param paramKey 参数名
     * @param paramValue 参数值
     */
    public void addRequestParam(String paramKey, String paramValue) {
        requestParam.put(paramKey, paramValue);
    }

    /**
     * 将请求报文体组装成json形式的字符串，以便进行网络发送
     * @return 请求报文的json字符串
     */
    public String getJsonStr() {
        // 由于Android源码自带的JSon功能不够强大（没有直接从Bean转到JSonObject的API），为了不引入第三方资源这里我们只能手动拼装一下啦
        JSONObject object = new JSONObject();
        JSONObject param = new JSONObject(requestParam);
        try {
            // 下边2个"requestCode"、"requestParam"是和服务器约定好的请求体字段名称，在本文接下来的服务端代码会说到
            object.put("requestCode", requestCode);
            object.put("requestParam", param);
        } catch (JSONException e) {
            LogUtil.logErr("请求报文组装异常：" + e.getMessage());
        }
        // 打印原始请求报文
        LogUtil.logRequest(object.toString());
        return  String.valueOf(object);
    }
}
