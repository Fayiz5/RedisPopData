package com.RedisQueuRead.RedisRead.Service;

import com.RedisQueuRead.RedisRead.Entities.MyData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class ReadData {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    public static final String key = "MYDATA";
    public static final String key2 = "MyData2";

    public void rpoptoother() {
        int numThreads = 10;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        Future<Boolean>[] futures = new Future[numThreads];

        for (int i = 0; i < numThreads; i++) {
            final int threadIndex = i;
            futures[i] = executor.submit(() -> {
                return processAndTransferData(threadIndex);
            });
        }

        executor.shutdown();

        boolean allSuccessful = true;
        for (Future<Boolean> future : futures) {
            try {
                if (!future.get()) {
                    allSuccessful = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                allSuccessful = false;
            }
        }
        if (allSuccessful)
            System.out.println("Data from "+key+" key to "+key2+" key is Success");
        else
            System.out.println("Data from "+key+" key to "+key2+" key is Failed");

        //return allSuccessful;
    }


    private boolean processAndTransferData(int threadIndex) {
        boolean success = false;
        while (true) {
            String data = redisTemplate.opsForList().leftPop(key);
            if (data != null) {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    MyData myData = objectMapper.readValue(data, MyData.class);
                    String jsonData = objectMapper.writeValueAsString(myData);
                    redisTemplate.opsForList().rightPush(key2, jsonData);
                } catch (Exception e) {
                    e.printStackTrace();
                    success = false;
                }
            } else {
                break;
            }
        }
        return success;
    }
}
