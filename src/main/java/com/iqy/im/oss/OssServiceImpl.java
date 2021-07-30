package com.iqy.im.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {

    private final OssProperties ossProperties;

    private final String BASE_URL;

    public OssServiceImpl(OssProperties ossProperties) {
        this.ossProperties = ossProperties;
        BASE_URL = "https://" + ossProperties.getBucketName() + "." + ossProperties.getEndPoint() + "/";
    }

    @Override
    public String upload(String module, String filename, InputStream inputStream) {
        String timeFormat = new DateTime().toString("/yyyy/MM/dd/");
        filename = UUID.randomUUID().toString() + filename.substring(filename.lastIndexOf("."));
        String key = module + timeFormat + filename;
        return upload(key, inputStream);
    }

    @Override
    public String upload(String key, InputStream inputStream) {
        OSSClient ossClient = getOSSClient();

        String bucketName = ossProperties.getBucketName();
        if (!ossClient.doesBucketExist(bucketName)) {
            ossClient.createBucket(bucketName);
            ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
        }

        ossClient.putObject(bucketName, key, inputStream);
        ossClient.shutdown();

        return BASE_URL + key;
    }

    @Override
    public void deleteFile(String url) {
        if (StringUtils.isEmpty(url)) {
            return;
        }
        String key = url.substring(BASE_URL.length());

        OSSClient ossClient = getOSSClient();
        ossClient.deleteObject(ossProperties.getBucketName(), key);
        ossClient.shutdown();
    }

    private OSSClient getOSSClient() {
        return (OSSClient) new OSSClientBuilder().build(ossProperties.getEndPoint(),
                ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret());
    }

}
