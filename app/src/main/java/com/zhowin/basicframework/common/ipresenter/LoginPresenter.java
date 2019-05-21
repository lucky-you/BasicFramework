package com.zhowin.basicframework.common.ipresenter;

import com.zhowin.basicframework.common.iview.ILoginView;
import com.zhowin.basicframework.common.mvp.BasePresenter;

/**
 * author      : Z_B
 * date       : 2019/5/21
 * function  : 登录的presenter
 */
public class LoginPresenter extends BasePresenter<ILoginView> {
    public LoginPresenter(ILoginView mvpView) {
        super(mvpView);
    }
}
