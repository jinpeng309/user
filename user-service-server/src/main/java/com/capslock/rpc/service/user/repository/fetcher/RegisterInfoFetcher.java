package com.capslock.rpc.service.user.repository.fetcher;

import com.capslock.rpc.service.user.repository.mapper.RegisterInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by alvin.
 */
@Component
public class RegisterInfoFetcher {
    @Autowired
    private RegisterInfoMapper registerInfoMapper;
}
