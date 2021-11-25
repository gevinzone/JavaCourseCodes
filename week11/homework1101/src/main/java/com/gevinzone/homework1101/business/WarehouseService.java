package com.gevinzone.homework1101.business;


import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Slf4j
public class WarehouseService {
    private final IRedisWarehouseOpt warehouseOpt;
    private final HashMap<String, String> goods = new HashMap<>(4);

    public WarehouseService(IRedisWarehouseOpt warehouseOpt, int initStock) {
        this.warehouseOpt = warehouseOpt;
        initData(String.valueOf(initStock));
    }

    private void initData(String initStock) {
        goods.put("id", "1");
        goods.put("stock", initStock);

        warehouseOpt.insertHashMap(goods.get("id"), goods);
    }

    public int multiThreadDecreaseStock(int threads, long delta) {
        for (int i = 0; i < threads; i++) {
            new Thread(() -> warehouseOpt.decreaseHashValue("1", "stock", delta)).start();
        }
        sleep(2000);
        goods.put("stock", warehouseOpt.getHashMapValue("1", "stock"));
        return Integer.parseInt(goods.get("stock"));
    }

    public int multiThreadDecreaseStock2(int threads, long delta) {
        for (int i = 0; i < threads; i++) {
            new Thread(() -> {
                int left = Integer.parseInt(goods.get("stock"));
                if (left < delta) {
                    log.info("return");
                    return;
                }
                Long result = warehouseOpt.decreaseHashValue("1", "stock", delta);
                if (result < 0) {
                    log.info("cancel");
                    warehouseOpt.increaseHashValue("1", "stock", delta);
                }
            }).start();
        }
        sleep(2000);
        goods.put("stock", warehouseOpt.getHashMapValue("1", "stock"));
        return Integer.parseInt(goods.get("stock"));
    }

    private void sleep(long mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
