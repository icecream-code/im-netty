package com.iqy.im;

import com.iqy.im.netty.ImServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class NettyCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        new ImServer().start(8082);
    }
}
