package com.capslock.rpc.service.user.updater;

import com.capslock.rpc.api.user.model.RegisterInfo;
import com.capslock.rpc.service.user.mapper.RegisterInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rx.Observable;

import static com.capslock.rpc.service.user.util.RxDecorator.toRx;

/**
 * Created by alvin.
 */
@Component
public class RegisterInfoUpdater {
    @Autowired
    private RegisterInfoMapper registerInfoMapper;

    public Observable<Void> addRegisterInfo(final RegisterInfo registerInfo) {
        return toRx(() -> registerInfoMapper.addRegisterInfo(registerInfo));
    }
}
