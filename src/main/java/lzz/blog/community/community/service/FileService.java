package lzz.blog.community.community.service;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.UUID;

@Service
public class FileService {

    // 1 初始化用户身份信息（secretId, secretKey）。
    private String secretId = "AKID79yZu1oXy32mIhC0rSqzub2EEzUkbY7l";
    private String secretKey = "hAC0YfbJlxub3GbnkSwBCzWouEKAm7E5";
    private COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
    // 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
// clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
    Region region = new Region("ap-guangzhou");
    ClientConfig clientConfig = new ClientConfig(region);


    public String fileUpload(InputStream inputStream, String contentType, String originalFilename) {
        clientConfig.setHttpProtocol(HttpProtocol.http);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);

        String fileName;
        String[] filePaths = originalFilename.split("\\.");
        if (filePaths.length > 1) {
            fileName = UUID.randomUUID().toString() + "." + filePaths[filePaths.length - 1];
        } else {
            return null;
        }

     /*   //创建存储桶
        String bucket = "lzzblog-1300861162"; //存储桶名称，格式：BucketName-APPID
        CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucket);
        // 设置 bucket 的权限为 Private(私有读写), 其他可选有公有读私有写, 公有读写
        createBucketRequest.setCannedAcl(CannedAccessControlList.Private);
        try{
            Bucket bucketResult = cosClient.createBucket(createBucketRequest);
        } catch (CosServiceException serverException) {
            serverException.printStackTrace();
        } catch (CosClientException clientException) {
            clientException.printStackTrace();
        }*/

        //查看所有存储桶的名称
        /*List<Bucket> buckets = cosClient.listBuckets();
        for (Bucket bucketElement : buckets) {
            String bucketName = bucketElement.getName();
            String bucketLocation = bucketElement.getLocation();
            System.out.println(bucketName+bucketLocation+"大哥");
        }*/

        //上传文件
        // 指定要上传的文件
        //File localFile = new File("C:\\Users\\Administrator\\Pictures\\test.jpg");
        // 指定要上传到的存储桶
        String bucketName = "lzzblog-1300861162";
        // 指定要上传到 COS 上对象键
        //String key = "images/002.jpg";
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, inputStream, null);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

        //关闭连接
        cosClient.shutdown();
        return fileName;
    }

    public void viewFile() {

        clientConfig.setHttpProtocol(HttpProtocol.http);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);

        // Bucket的命名格式为 BucketName-APPID ，此处填写的存储桶名称必须为此格式
        String bucketName = "lzzblog-1300861162";
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
        // 设置bucket名称
        listObjectsRequest.setBucketName(bucketName);
        // prefix表示列出的object的key以prefix开始
        listObjectsRequest.setPrefix("images/");
        // deliter表示分隔符, 设置为/表示列出当前目录下的object, 设置为空表示列出所有的object
        listObjectsRequest.setDelimiter("/");
        // 设置最大遍历出多少个对象, 一次listobject最大支持1000
        listObjectsRequest.setMaxKeys(1000);
        ObjectListing objectListing = null;
        do {
            try {
                objectListing = cosClient.listObjects(listObjectsRequest);
            } catch (CosServiceException e) {
                e.printStackTrace();
                return;
            } catch (CosClientException e) {
                e.printStackTrace();
                return;
            }
            // common prefix表示表示被delimiter截断的路径, 如delimter设置为/, common prefix则表示所有子目录的路径
            List<String> commonPrefixs = objectListing.getCommonPrefixes();

            // object summary表示所有列出的object列表
            List<COSObjectSummary> cosObjectSummaries = objectListing.getObjectSummaries();
            for (COSObjectSummary cosObjectSummary : cosObjectSummaries) {
                // 文件的路径key
                String key = cosObjectSummary.getKey();
                // 文件的etag
                String etag = cosObjectSummary.getETag();
                // 文件的长度
                long fileSize = cosObjectSummary.getSize();
                // 文件的存储类型
                String storageClasses = cosObjectSummary.getStorageClass();
                System.out.println(key + "  " + etag + "  " + fileSize + "  " + storageClasses);
            }

            String nextMarker = objectListing.getNextMarker();
            listObjectsRequest.setMarker(nextMarker);
        } while (objectListing.isTruncated());


    }

    public void fileUrl() {
        clientConfig.setHttpProtocol(HttpProtocol.http);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);
        String bucketName = "lzzblog-1300861162";

        String key = "images/001.jpg";
        GeneratePresignedUrlRequest req =
                new GeneratePresignedUrlRequest(bucketName, key, HttpMethodName.GET);
        URL url = cosClient.generatePresignedUrl(req);
        System.out.println(url.toString());
    }


}
