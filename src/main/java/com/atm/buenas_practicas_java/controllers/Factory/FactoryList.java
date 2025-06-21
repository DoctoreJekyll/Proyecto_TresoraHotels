package com.atm.buenas_practicas_java.controllers.Factory;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class FactoryList {

    private final Map<String, IFactoryProvider> factoryListMap;

    public FactoryList(List<IFactoryProvider> factoryList) {
        this.factoryListMap = factoryList.stream().collect(Collectors.toMap(IFactoryProvider::getEntityName, Function.identity()));
    }

    public Optional<IFactoryProvider> getFactory(String entityName) {
        return Optional.ofNullable(factoryListMap.get(entityName));
    }
}
