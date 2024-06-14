package com.example.batch7.ch4.service.impl;

import com.example.batch7.ch4.service.AbsractFactoryService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Primary
public class BarangServiceImpl implements AbsractFactoryService {
    @Override
    @Qualifier("save1")
    public Map save(Object request) {
        // berbeda
        return null;
    }

    @Override
    @Qualifier("update2")
    public Map update(Object request) {
        // berbeda
        return null;
    }

    @Override
    @Qualifier("deleted3")
    public Map deleted(Object request) {
        // berbeda
        return null;
    }

    @Override
    @Qualifier("getData3")
    public Map getData() {
        return null;
    }
}
