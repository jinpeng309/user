package com.capslock.rpc.service.user.service;

import com.capslock.commons.packet.protocol.ChangedDataNotificationProtocol;
import com.capslock.rpc.api.seq.SeqGeneratorService;
import com.capslock.rpc.api.user.UserService;
import com.capslock.rpc.api.user.model.BlackItem;
import com.capslock.rpc.api.user.model.Contact;
import com.capslock.rpc.api.user.model.MobileNumber;
import com.capslock.rpc.api.user.model.RegisterInfo;
import com.capslock.rpc.api.user.model.User;
import com.capslock.rpc.api.user.model.UserInfo;
import com.capslock.rpc.service.user.assembler.BlackItemAssembler;
import com.capslock.rpc.service.user.builder.ChangedDataBuilder;
import com.capslock.rpc.service.user.extractor.ContactCacheDataExtractor;
import com.capslock.rpc.service.user.extractor.UserInfoCacheExtractor;
import com.capslock.rpc.service.user.merger.ChangeDataMerger;
import com.capslock.rpc.service.user.repository.fetcher.UserBlacklistFetcher;
import com.capslock.rpc.service.user.repository.fetcher.UserContactFetcher;
import com.capslock.rpc.service.user.repository.fetcher.UserFetcher;
import com.capslock.rpc.service.user.repository.fetcher.UserInfoChangeDataFetcher;
import com.capslock.rpc.service.user.repository.fetcher.UserInfoFetcher;
import com.capslock.rpc.service.user.repository.mapper.model.ContactCacheData;
import com.capslock.rpc.service.user.repository.mapper.model.UserInfoCacheData;
import com.capslock.rpc.service.user.repository.mapper.model.VersionedCacheData;
import com.capslock.rpc.service.user.repository.updater.RegisterInfoUpdater;
import com.capslock.rpc.service.user.repository.updater.UserBlacklistUpdater;
import com.capslock.rpc.service.user.repository.updater.UserContactUpdater;
import com.capslock.rpc.service.user.repository.updater.UserInfoChangeDataUpdater;
import com.capslock.rpc.service.user.repository.updater.UserInfoUpdater;
import com.capslock.rpc.service.user.repository.updater.UserUpdater;
import com.capslock.rpc.service.user.sender.MessageSender;
import com.weibo.api.motan.config.springsupport.annotation.MotanReferer;
import com.weibo.api.motan.config.springsupport.annotation.MotanService;
import org.springframework.beans.factory.annotation.Autowired;
import rx.Observable;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by alvin.
 */
@MotanService
public class UserServiceImpl implements UserService {
    @Autowired
    private UserInfoCacheExtractor userInfoCacheExtractor;
    @MotanReferer(basicReferer = "basicConfig", group = "seq-service-rpc")
    private SeqGeneratorService seqGeneratorService;
    @Autowired
    private UserInfoUpdater userInfoUpdater;
    @Autowired
    private UserInfoFetcher userInfoFetcher;
    @Autowired
    private UserUpdater userUpdater;
    @Autowired
    private UserFetcher userFetcher;
    @Autowired
    private UserBlacklistUpdater userBlacklistUpdater;
    @Autowired
    private UserBlacklistFetcher userBlacklistFetcher;
    @Autowired
    private RegisterInfoUpdater registerInfoUpdater;
    @Autowired
    private UserContactUpdater userContactUpdater;
    @Autowired
    private UserContactFetcher userContactFetcher;
    @Autowired
    private ContactCacheDataExtractor contactCacheDataExtractor;
    @Autowired
    private UserInfoChangeDataFetcher userInfoChangeDataFetcher;
    @Autowired
    private ChangeDataMerger changeDataMerger;
    @Autowired
    private ChangedDataBuilder changedDataBuilder;
    @Autowired
    private UserInfoChangeDataUpdater userInfoChangeDataUpdater;
    @Autowired
    private MessageSender messageSender;
    @Autowired
    private BlackItemAssembler blackItemAssembler;

    @Override
    public void addUser(final User user, final RegisterInfo registerInfo) throws IOException {
        Observable.zip(userUpdater.addUser(user), registerInfoUpdater.addRegisterInfo(registerInfo),
                userInfoUpdater.addUserInfo(userInfoCacheExtractor.extract(user)), (r1, r2, r3) -> {
                    return null;
                }).toBlocking().subscribe();
    }

    @Override
    public User fetchUser(final MobileNumber mobileNumber) {
        return userFetcher.fetchUser(mobileNumber.getCountryCode(), mobileNumber.getPhoneNumber());
    }

    @Override
    public boolean isUserExists(final MobileNumber mobileNumber) {
        return userFetcher.fetchUser(mobileNumber.getCountryCode(), mobileNumber.getPhoneNumber()) != null;
    }

    @Override
    public UserInfo fetchUserInfo(final long ownerUid, final long userId) throws IOException {
        return userInfoFetcher.fetchUserInfo(ownerUid, userId);
    }

    @Override
    public List<UserInfo> fetchUserInfoList(final long ownerUid, final List<Long> userIds) {
        return userInfoFetcher.fetchUserInfoList(ownerUid, userIds);
    }

    @Override
    public void addUserIntoBlacklist(final long ownerUid, final long userId) throws IOException {
        userBlacklistUpdater.addUserIntoBlacklist(ownerUid, userId);
        final long sequence = seqGeneratorService.generateSeq(ownerUid);
        final String rawChangeDataArray = changedDataBuilder.buildUserInfoEditBlacklistChangedDataArray(userId, true);
        userInfoChangeDataUpdater.addChangeData(ownerUid, rawChangeDataArray, sequence);
        messageSender.sendDataChangedNotification(ownerUid, sequence, ChangedDataNotificationProtocol.Type.CONTACTS_CHANGED);
    }

    @Override
    public void addUserIntoBlacklist(final long ownerUid, final List<Long> userIdList) {
        userBlacklistUpdater.addUserIntoBlacklist(ownerUid, userIdList);
        final long sequence = seqGeneratorService.generateSeq(ownerUid);
    }

    @Override
    public List<BlackItem> fetchUserBlacklist(final long ownerUid) {
        final List<Long> blacklist = userBlacklistFetcher.fetchUserBlacklist(ownerUid);
        final List<UserInfoCacheData> cacheDataList = userInfoFetcher.fetchUserInfoCacheDataList(blacklist);
        return blackItemAssembler.assemble(cacheDataList);
    }

    @Override
    public void removeUserInBlacklist(final long ownerUid, final long userId) {
        userBlacklistUpdater.removeUserInBlacklist(ownerUid, userId);
        final long sequence = seqGeneratorService.generateSeq(ownerUid);
        final String changeData = changedDataBuilder.buildUserInfoEditBlacklistChangedDataArray(userId, false);
        userInfoChangeDataUpdater.addChangeData(ownerUid, changeData, sequence);
        messageSender.sendDataChangedNotification(ownerUid, sequence, ChangedDataNotificationProtocol.Type.CONTACTS_CHANGED);
    }

    @Override
    public void removeUserInBlacklist(final long ownerUid, final List<Long> userIdList) {
    }

    @Override
    public void addContacts(final long ownerUid, final List<Contact> contacts) {
        userContactUpdater.addContacts(ownerUid, contactCacheDataExtractor.extract(contacts));
    }

    @Override
    public String fetchChangedUserInfoData(final long ownerUid, final long beginSequenceId) {
        final List<VersionedCacheData> rawChangeDataList = userInfoChangeDataFetcher.fetchChangedDataList(ownerUid, beginSequenceId);
        if (!rawChangeDataList.isEmpty()) {
            final long latestSequenceId = rawChangeDataList.get(rawChangeDataList.size() - 1).getVersion();
            return changeDataMerger.mergeUserInfoChangeData(latestSequenceId,
                    rawChangeDataList.stream().map(VersionedCacheData::getData).collect(Collectors.toList()));
        }
        return "";
    }

    @Override
    public List<UserInfo> fetchAppContacts(final long ownerUid) {
        return userContactFetcher
                .fetchUserContactsAsync(ownerUid)
                .flatMap(dataList -> userFetcher.fetchUserIdsByEncryptedPhoneNumberAsync(extractEncryptedPhoneNumber(dataList)))
                .map(userList -> fetchUserInfoList(ownerUid, userList))
                .toBlocking()
                .single();
    }

    private List<String> extractEncryptedPhoneNumber(final List<ContactCacheData> contactCacheDataList) {
        return contactCacheDataList
                .stream()
                .map(ContactCacheData::getEncryptedPhoneNumber)
                .collect(Collectors.toList());
    }
}
