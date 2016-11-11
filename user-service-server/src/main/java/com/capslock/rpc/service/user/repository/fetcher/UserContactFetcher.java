package com.capslock.rpc.service.user.repository.fetcher;

import com.capslock.rpc.service.user.repository.mapper.UserContactMapper;
import com.capslock.rpc.service.user.repository.mapper.model.ContactCacheData;
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rx.Observable;

/**
 * Created by alvin.
 */
@Component
public class UserContactFetcher {
    @Autowired
    private UserContactMapper userContactMapper;

    public ImmutableList<ContactCacheData> fetchUserContacts(final long ownerUid) {
        return ImmutableList.copyOf(userContactMapper.fetchContacts(ownerUid));
    }

    public Observable<ImmutableList<ContactCacheData>> fetchUserContactsAsync(final long ownerUid) {
        return Observable.fromCallable(() -> fetchUserContacts(ownerUid));
    }
}
