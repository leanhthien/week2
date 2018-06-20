package com.internship.thien.nytimesnews.search.model;

import java.util.Map;

public interface DataRepository {

    void getDataFromNetWork(DataListener listener, Map query);

}
