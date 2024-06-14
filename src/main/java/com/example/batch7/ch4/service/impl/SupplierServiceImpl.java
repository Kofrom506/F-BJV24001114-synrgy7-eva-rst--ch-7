package com.example.batch7.ch4.service.impl;

import com.example.batch7.ch4.service.AbsractFactoryService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SupplierServiceImpl implements AbsractFactoryService {
    @Override
    public Map save(Object request) {
        // berbeda
        return null;
    }

    @Override
    public Map update(Object request) {
        // berbeda
        return null;
    }

    @Override
    public Map deleted(Object request) {
        // berbeda
        return null;
    }

    @Override
    public Map getData() {
        return null;
    }
}
