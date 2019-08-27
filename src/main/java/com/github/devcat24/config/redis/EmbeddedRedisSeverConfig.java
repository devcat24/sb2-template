package com.github.devcat24.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

//@Profile("dev") // only active when profile is "dev"
@Configuration
public class EmbeddedRedisSeverConfig {

    @Value("${spring.redis.port}")
    private int redisPort;

    @Value("${spring.profiles.active:}")
    private String activeProfile;

    private RedisServer redisServer;

    @Value("${spring.redis.enable.random.port.on.profile:}")
    private String enableRandomPortOnProfile;

    @PostConstruct
    public void redisServer() throws IOException {

        String osName = System.getProperty("os.name");
        boolean isUnixCompatibleOS = osName != null && (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) ;
        if(isUnixCompatibleOS) {
            int randomPort = isRedisRunning() ? findAvailablePort() : redisPort;
            redisServer = new RedisServer(randomPort);
        }   else {
            redisServer = new RedisServer(redisPort);
        }

        /*  // -> 'mvn package' with port collision
        String [] randomPortProfiles = (enableRandomPortOnProfile != null && !enableRandomPortOnProfile.isEmpty()) ?   enableRandomPortOnProfile.split(",") : new String[]{};
        boolean inRedisRandomPortProfile = Arrays.stream(randomPortProfiles).anyMatch(this::isDevProfileMode);
        //boolean inRedisRandomPortProfile = Arrays.stream(randomPortProfiles).filter( s -> isDevProfileMode(s)).count() > 0;

        String osName = System.getProperty("os.name");
        boolean isUnixCompatibleOS = osName != null && (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) ;

        if(! inRedisRandomPortProfile) {
            redisServer = new RedisServer(redisPort);
        } else {
            if(isUnixCompatibleOS) {
                int randomPort = isRedisRunning() ? findAvailablePort() : redisPort;
                redisServer = new RedisServer(randomPort);
            }   else {
                redisServer = new RedisServer(redisPort);
            }
        }
        */

        redisServer.start();
    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }

    private boolean isDevProfileMode(String randomPortProfile){
        return (activeProfile != null) && (Arrays.stream(activeProfile.split(",")).anyMatch(s -> s.equalsIgnoreCase(randomPortProfile)));
        // return (activeProfile != null) && (Arrays.stream(activeProfile.split(",")).filter(s -> s.equalsIgnoreCase(randomPortProfile)).count() > 0);
    }

    // check whether redis server is running in the system
    private boolean isRedisRunning() throws IOException {
        return isRunning(executeGrepProcessCommand(redisPort));
    }

    // find random available port
    public int findAvailablePort() throws IOException {
        for (int port = 10000; port <= 65535; port++) {
            Process process = executeGrepProcessCommand(port);
            if (!isRunning(process)) {
                return port;
            }
        }
        throw new IllegalArgumentException("Not Found Available port: 10000 ~ 65535");
    }

    private Process executeGrepProcessCommand(int port) throws IOException {
        String command = String.format("netstat -nat | grep LISTEN|grep %d", port);
        String[] shell = {"/bin/sh", "-c", command};
        return Runtime.getRuntime().exec(shell);
    }

    private boolean isRunning(Process process) {
        String line;
        StringBuilder pidInfo = new StringBuilder();
        try (BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            while ((line = input.readLine()) != null) {
                pidInfo.append(line);
            }
        } catch (Exception ignored) {
        }
        return !pidInfo.toString().isEmpty();
    }
}