package com.capslock.rpc.service.user.fetcher;

import com.capslock.rpc.service.user.mapper.RegisterInfoMapper;
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
