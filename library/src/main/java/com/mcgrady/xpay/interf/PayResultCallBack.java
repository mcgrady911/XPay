package com.mcgrady.xpay.interf;

/**
 * <p>支付结果回调</p>
 *
 * @author: mcgrady
 * @date: 2019/1/9
 */

public interface PayResultCallBack {

    void onPaySuccess(String result);

    void onPayFailure(String result);

    void onPayConfirm(String result);

    void onPayCheck(String status);
}
