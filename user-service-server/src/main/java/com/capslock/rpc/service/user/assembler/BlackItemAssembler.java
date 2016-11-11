package com.capslock.rpc.service.user.assembler;

import com.capslock.rpc.api.user.model.BlackItem;
import com.capslock.rpc.service.user.repository.mapper.model.UserInfoCacheData;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by alvin.
 */
@Component
public class BlackItemAssembler {

    public BlackItem assemble(final UserInfoCacheData cacheData) {
        return new BlackItem(cacheData.getUserId(), cacheData.getNickname().orElse(""));
    }

    public List<BlackItem> assemble(final List<UserInfoCacheData> cacheDataList) {
        return cacheDataList.stream().map(this::assemble).collect(Collectors.toList());
    }
}
