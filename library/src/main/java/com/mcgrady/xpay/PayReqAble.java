package com.mcgrady.xpay;


import com.mcgrady.xpay.interf.PayResultCallBack;

/**
 * <p>支付请求</p>
 *
 * @author: mcgrady
 * @date: 2019/1/9
 */

public abstract class PayReqAble {

    /**
     * 支付回调
     */
    protected PayResultCallBack payResultCallBack;

    /**
     * 发起支付
     */
    protected abstract void pay();

    protected PayResultCallBack getPayResultCallBack() {
        return payResultCallBack;
    }
}
