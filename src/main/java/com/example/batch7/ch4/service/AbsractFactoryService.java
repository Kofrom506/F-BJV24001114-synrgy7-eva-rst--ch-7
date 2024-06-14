package com.example.batch7.ch4.service;

import java.util.Map;

public interface AbsractFactoryService {
    Map save(Object request);

    Map update(Object request);

    Map deleted(Object request);

    Map getData();
}
