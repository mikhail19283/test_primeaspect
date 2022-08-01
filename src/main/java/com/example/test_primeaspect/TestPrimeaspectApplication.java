package com.example.test_primeaspect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class TestPrimeaspectApplication {

    private static final String LOCAL_DIRECTORY = "/tmp/countries/flags/";
    private static final String FORMAT_SVG = ".svg";
    private static final String FORMAT_PNG = ".png";

    public static void main(String[] args) {
        SpringApplication.run(TestPrimeaspectApplication.class, args);
        StringBuilder url = new StringBuilder("https://restcountries.com/v2/alpha?codes=");
        for (String s : args) {
            url.append(s).append(",");
        }
        String fullUrl = url.substring(0, url.length() - 1);

        RestTemplate restTemplate = new RestTemplate();
        Flags[] flags = restTemplate.getForObject(fullUrl, Flags[].class);

        ExecutorService executorService = Executors.newFixedThreadPool(flags.length);

        Arrays.stream(flags).forEach(flag -> executorService.submit(() -> {
            try(InputStream png = new URL(flag.getFlags().getPng()).openStream();
                InputStream svg = new URL(flag.getFlags().getSvg()).openStream()
            ){
                Files.copy(png, Paths.get(LOCAL_DIRECTORY + flag.getName() + FORMAT_PNG));
                Files.copy(svg, Paths.get(LOCAL_DIRECTORY + flag.getName() + FORMAT_SVG));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));

    }

}
