package com.iqy.im.netty;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

public class UserChannelRel {

    private static final Map<String, Channel> CHANNEL_MAP = new HashMap<>();

    public static void put(String key, Channel channel) {
        CHANNEL_MAP.put(key, channel);
    }

    public static Channel get(String key) {
        return CHANNEL_MAP.get(key);
    }

    public static void remove(String key) {
        CHANNEL_MAP.remove(key);
    }
}
