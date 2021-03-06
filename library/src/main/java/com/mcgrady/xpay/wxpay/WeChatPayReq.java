package com.mcgrady.xpay.wxpay;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.mcgrady.xpay.PayReqAble;
import com.mcgrady.xpay.interf.PayResultCallBack;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

/**
 * <p>微信支付请求</p>
 *
 * @author: mcgrady
 * @date: 2019/1/9
 */

public class WeChatPayReq extends PayReqAble {

    //微信支付AppID
    private String appId;
    //微信支付商户号
    private String partnerId;
    //预支付码（重要）
    private String prepayId;
    //"Sign=WXPay"
    private String packageValue;
    private String nonceStr;
    //时间戳
    private String timeStamp;
    //签名
    private String sign;

    //微信支付核心api
    private IWXAPI wxApi;

    private WeChatPayReq(Builder builder) {
        this();
        wxApi = builder.wxApi;
        payResultCallBack = builder.payListener;
        appId = builder.appId;
        partnerId = builder.partnerId;
        prepayId = builder.prepayId;
        packageValue = builder.packageValue;
        nonceStr = builder.nonceStr;
        timeStamp = builder.timeStamp;
        sign = builder.sign;
    }

    private WeChatPayReq() {
        if (wxApi != null && !TextUtils.isEmpty(appId)) {
            wxApi.registerApp(appId);
        }
    }

    @Override
    protected void pay() {
        PayReq request = new PayReq();
        request.appId = this.appId;
        request.partnerId = this.partnerId;
        request.prepayId= this.prepayId;
        request.packageValue =  !TextUtils.isEmpty(this.packageValue) ? this.packageValue : "Sign=WXPay";
        request.nonceStr= this.nonceStr;
        request.timeStamp= this.timeStamp;
        request.sign = this.sign;

        wxApi.sendReq(request);
    }

    public void handleIntent(@NonNull Intent intent, @NonNull IWXAPIEventHandler handler) {
        wxApi.handleIntent(intent, handler);
    }

    /**
     *
     * @param errCode 0    成功 展示成功页面
     *                -1   错误 可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
     *                -2   用户取消 无需处理。发生场景：用户不支付了，点击取消，返回APP。
     */
    public void onResp(int errCode) {
        if (payResultCallBack != null) {
            switch (errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    payResultCallBack.onPaySuccess(String.valueOf(errCode));
                    break;
                default:
                    payResultCallBack.onPayFailure(String.valueOf(errCode));
                    break;
            }
        }
    }

    public static final class Builder {
        private IWXAPI wxApi;
        private PayResultCallBack payListener;
        private String appId;
        private String partnerId;
        private String prepayId;
        private String packageValue;
        private String nonceStr;
        private String timeStamp;
        private String sign;

        public Builder() {
        }

        public Builder with(IWXAPI val) {
            wxApi = val;
            return this;
        }

        public Builder payCallback(PayResultCallBack val) {
            payListener = val;
            return this;
        }

        public Builder appId(String val) {
            appId = val;
            return this;
        }

        public Builder partnerId(String val) {
            partnerId = val;
            return this;
        }

        public Builder prepayId(String val) {
            prepayId = val;
            return this;
        }

        public Builder packageValue(String val) {
            packageValue = val;
            return this;
        }

        public Builder nonceStr(String val) {
            nonceStr = val;
            return this;
        }

        public Builder timeStamp(String val) {
            timeStamp = val;
            return this;
        }

        public Builder sign(String val) {
            sign = val;
            return this;
        }

        public WeChatPayReq create() {
            return new WeChatPayReq(this);
        }
    }
}
