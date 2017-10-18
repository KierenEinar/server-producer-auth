package com.kangning.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by kieren on 17/10/16.
 */
@EnableDiscoveryClient
@SpringBootApplication
public class App {
    public static void main (String args[]) {
        SpringApplication.run(App.class,args);
    }
}
