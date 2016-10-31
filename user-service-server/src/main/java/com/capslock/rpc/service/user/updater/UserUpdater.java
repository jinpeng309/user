package com.capslock.rpc.service.user.updater;

import com.capslock.rpc.api.user.model.User;
import com.capslock.rpc.service.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rx.Observable;

import static com.capslock.rpc.service.user.util.RxDecorator.toRx;

/**
 * Created by alvin.
 */
@Component
public class UserUpdater {
    @Autowired
    private UserMapper userMapper;

    public Observable<Void> addUser(final User user) {
        return toRx(() -> userMapper.addUser(user));
    }
}
