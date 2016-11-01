package com.capslock.rpc.service.user.mapper;

import com.capslock.rpc.service.user.mapper.model.ContactCacheData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by alvin.
 */
@Mapper
public interface UserContactMapper {
    void addContact(final long ownerUid, final ContactCacheData contactCacheData);

    void addContacts(final long ownerUid, final List<ContactCacheData> contactCacheDataList);

    List<ContactCacheData> fetchContacts(final long ownerUid);
}
