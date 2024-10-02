package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
@Profile("test")
public class InfoServiceTest implements InfoService{

    Logger logger = LoggerFactory.getLogger(InfoServiceProduction.class);
    @Value("${server.port}")
    private Integer port;

    @Override
    public Integer getPort() {
        logger.debug("Was invoked method for get port");
        return port;
    }

    @Override
    public String getTimeMethod() {
        logger.debug("Was invoked method for get time");

        //first
        long startTime = System.currentTimeMillis();
        int sum = Stream.iterate(1, a -> a + 1)
                .limit(100_000_000)
                .reduce(0, (a, b) -> a + b);
        long endTime = System.currentTimeMillis();
        logger.debug(String.valueOf(endTime - startTime));

        //second
        startTime = System.currentTimeMillis();
        int sum2 = 0;
        for (int i = 0; i <= 100_000_000; i++) {
            sum2 += i;
        }
        endTime = System.currentTimeMillis();
        logger.debug(String.valueOf(endTime - startTime));

        //Third
        startTime = System.currentTimeMillis();
        int sum3 = Stream.iterate(1, a -> a + 1)
                .parallel()
                .limit(100_000_000)
                .reduce(0, (a, b) -> a + b);
        endTime = System.currentTimeMillis();
        logger.debug(String.valueOf(endTime - startTime));

        return "first: " + sum + "\n" +
                "second: " + sum2 + "\n" +
                "third: " + sum3 ;
    }
}
