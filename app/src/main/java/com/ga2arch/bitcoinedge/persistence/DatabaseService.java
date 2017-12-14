
package com.ga2arch.bitcoinedge.persistence;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class DatabaseService {
    private List<Object> data = new CopyOnWriteArrayList<>();

    public void addData(Object data) {
        this.data.add(data);
    }

    public Object getData(int index) {
        return data.get(index);
    }

    public int getSize() {
        return data.size();
    }

    public void clearData() {
        data.clear();
    }
}
