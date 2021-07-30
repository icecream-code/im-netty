package com.iqy.im.netty.attribute;

import io.netty.util.AttributeKey;

public interface Attributes {
    AttributeKey<String> USER_ID = AttributeKey.newInstance("user_id");
}