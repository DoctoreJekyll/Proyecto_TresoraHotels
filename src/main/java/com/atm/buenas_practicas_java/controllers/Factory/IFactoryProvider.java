package com.atm.buenas_practicas_java.controllers.Factory;

import java.util.List;
import java.util.Map;

public interface IFactoryProvider {
    String getTitles();
    List<String> getHeaders();
    List<Map<String,Object>> getRows();
    String getEntityName();
}
