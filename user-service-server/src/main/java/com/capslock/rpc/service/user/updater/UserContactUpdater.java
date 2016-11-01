package com.capslock.rpc.service.user.updater;

import com.capslock.rpc.service.user.mapper.UserContactMapper;
import com.capslock.rpc.service.user.mapper.model.ContactCacheData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rx.Observable;

import java.util.List;

import static com.capslock.rpc.service.user.util.RxDecorator.toRx;

/**
 * Created by alvin.
 */
@Component
public class UserContactUpdater {
    @Autowired
    private UserContactMapper userContactMapper;

    public void addContact(final long ownerUid, final ContactCacheData contactCacheData) {
        userContactMapper.addContact(ownerUid, contactCacheData);
    }

    public Observable<Void> addContactAsync(final long ownerUid, final ContactCacheData contactCacheData) {
        return toRx(() -> addContact(ownerUid, contactCacheData));
    }

    public void addContacts(final long ownerUid, final List<ContactCacheData> contactCacheDataList) {
        userContactMapper.addContacts(ownerUid, contactCacheDataList);
    }

    public Observable<Void> addContactsAsync(final long ownerUid, final List<ContactCacheData> contactCacheDataList) {
        return toRx(() -> addContacts(ownerUid, contactCacheDataList));
    }
}
