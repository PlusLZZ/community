package lzz.blog.community.community.service;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.GeneratePresignedUrlRequest;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Service
public class TencentCould {
    @Value("${cloud.tencent.secretId}")
    private String secretId;
    @Value("${cloud.tencent.secretKey}")
    private String secretKey;
    @Value("${cloud.tencent.bucketName}")
    private String bucketName;

    public String fileUpload(InputStream inputStream, String contentType, String originalFilename) {
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region("ap-guangzhou");
        ClientConfig clientConfig = new ClientConfig(region);
        COSClient cosClient = new COSClient(cred, clientConfig);
        String fileName;
        String[] filePaths = originalFilename.split("\\.");
        if (filePaths.length > 1) {
            fileName = UUID.randomUUID().toString() + "." + filePaths[filePaths.length - 1];
        } else {
            return null;
        }
        // 指定要上传到 COS 上对象键
        //String key = "images/002.jpg";
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, inputStream, null);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);


        GeneratePresignedUrlRequest req =
                new GeneratePresignedUrlRequest(bucketName, fileName, HttpMethodName.GET);
// 设置签名过期时间(可选), 若未进行设置, 则默认使用 ClientConfig 中的签名过期时间(1小时)
// 这里设置签名在半个小时后过期
        Date expirationDate = new Date(System.currentTimeMillis() + 10L * 365L * 24L * 60L * 60L * 1000L);
        req.setExpiration(expirationDate);
        URL url = cosClient.generatePresignedUrl(req);

        //关闭连接
        cosClient.shutdown();
        return url.toString();
    }
}
