package com.iqy.im.oss;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class OssProperties {

    private String endPoint;

    private String accessKeyId;

    private String accessKeySecret;

    private String bucketName;
}
