package com.waben.stock.risk;

import org.junit.Test;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Created by yuyidi on 2017/12/3.
 * @desc
 */
public class ContainerTest {

    @Test
    public void testCopyOnWriteList() throws InterruptedException {
        final CopyOnWriteArrayList<Stock> stocks = new CopyOnWriteArrayList<>();
//        List<Stock> stocks = new ArrayList<>();
        stocks.add(new Stock("a", "1"));
        stocks.add(new Stock("b", "2"));
        stocks.add(new Stock("c", "3"));
        stocks.add(new Stock("d", "4"));
        stocks.add(new Stock("e", "5"));

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Long start = System.nanoTime();
                System.out.println("-------------");
                for (int i = 0; i < 10; i++) {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    stocks.add(new Stock("a" + i, "1" + "--" + i));
                    System.out.println("长度"+stocks.size());
                }
                System.out.println("------------"+String.valueOf(System.nanoTime()-start));
            }
        });
        Thread.sleep(2500);
        t1.start();
        for (int index=0;index<stocks.size();index++) {
//            Thread.sleep(1500);
            System.out.println(stocks.get(index).getName());
        }
    }

    class Stock {
        private String name;
        private String code;

        public Stock(String name, String code) {
            this.name = name;
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
