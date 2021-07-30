package com.iqy.im.oss;

import java.io.InputStream;

public interface OssService {

    String upload(String module, String filename, InputStream inputStream);

    String upload(String key, InputStream inputStream);

    void deleteFile(String url);
}
