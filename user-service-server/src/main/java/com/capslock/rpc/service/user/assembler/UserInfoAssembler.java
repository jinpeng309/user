package com.capslock.rpc.service.user.assembler;

import com.capslock.rpc.api.user.model.MobileNumber;
import com.capslock.rpc.api.user.model.User;
import com.capslock.rpc.api.user.model.UserInfo;
import com.capslock.rpc.service.user.mapper.model.UserInfoCacheData;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Component;
import rx.Observable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by alvin.
 */
@Component
public class UserInfoAssembler {

    public UserInfo assemble(final UserInfoCacheData cacheData, final User user, final boolean isInBlacklist) {
        final MobileNumber mobileNumber = new MobileNumber(user.getCountryCode(), user.getPhoneNumber());
        final UserInfo userInfo = new UserInfo(user.getUserId(), mobileNumber);
        Optional.ofNullable(cacheData).ifPresent(data -> {
                    userInfo.setAvatar(data.getAvatar());
                    userInfo.setNickname(data.getNickname());
                }
        );
        userInfo.setInBlackList(isInBlacklist);
        return userInfo;
    }

    public Observable<UserInfo> assemble(final Observable<UserInfoCacheData> cacheData, final Observable<User> user,
            final Observable<Boolean> isInBlacklist) {
        return Observable.zip(cacheData, user, isInBlacklist, (this::assemble));
    }

    public List<UserInfo> assemble(final List<UserInfoCacheData> userInfoCacheDataList, final List<User> users,
            final List<Long> blacklist) {
        final Map<Long, UserInfoCacheData> cacheDataMap = userInfoCacheDataList.stream().collect(
                Collectors.toMap(UserInfoCacheData::getUserId, Function.identity()));
        final Set<Long> blackUserIdSet = Sets.newHashSet(blacklist);
        return users.stream()
                .map(user -> {
                    return assemble(cacheDataMap.getOrDefault(user.getUserId(), null), user, blackUserIdSet.contains(user.getUserId()));
                })
                .collect(Collectors.toList());
    }

    public Observable<List<UserInfo>> assembleUserInfoList(final Observable<List<UserInfoCacheData>> userInfoCacheDataList,
            final Observable<List<User>> users, final Observable<List<Long>> blacklist) {
        return Observable.zip(userInfoCacheDataList, users, blacklist, this::assemble);
    }
}
