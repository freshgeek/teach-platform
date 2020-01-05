package org.ck.teach.teachplatform;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
class TeachPlatformApplicationTests {

    @Test
    void contextLoads() {
    }

    public static void main(String[] args) {

        List<String> list = new ArrayList<>();

        for (int i = 0; i < 10000; i++) {
            list.add(String.valueOf(Math.random()));
        }

        long start = System.nanoTime();

        for (int i = 0; i < list.size(); i++) {
            list.get(i);
        }

        long l = System.nanoTime() - start;
        System.out.printf("%dms", l / 1000);
        start = System.nanoTime();

        int i = 0;
        for (String s : list) {
            i = 1;
        }
        l = System.nanoTime() - start;
        System.out.printf("%dms", l / 1000);

    }
}
