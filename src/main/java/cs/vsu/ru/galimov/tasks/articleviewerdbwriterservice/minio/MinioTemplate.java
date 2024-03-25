package cs.vsu.ru.galimov.tasks.articleviewerdbwriterservice.minio;

import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MinioTemplate {

    private final MinioClient minioClient;

    private final String bucketName;

    @Autowired
    public MinioTemplate(@Qualifier("minioProperties") MinioProperties properties) {
        this.minioClient = MinioClient.builder()
                .endpoint(properties.getMinioUrl())
                .credentials(properties.getAccessKey(), properties.getSecretKey())
                .build();
        this.bucketName = properties.getBucketName();
        createBucketIfNotExists(bucketName);
    }

    public void uploadFile(String objectName, InputStream inputStream) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .stream(inputStream, inputStream.available(), -1)
                .contentType("application/octet-stream")
                .build());
    }

    public InputStream downloadFile(String objectName) throws IOException, InvalidKeyException, NoSuchAlgorithmException, ServerException, InsufficientDataException, ErrorResponseException, InternalException, InvalidResponseException, XmlParserException {
        return minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .build());
    }

    public void deleteFile(String objectName) throws IOException, InvalidKeyException, NoSuchAlgorithmException, ServerException, InsufficientDataException, ErrorResponseException, InternalException, InvalidResponseException, XmlParserException {
        minioClient.removeObject(RemoveObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .build());
    }

    public List<String> getAllObjects() throws IOException, InvalidKeyException, NoSuchAlgorithmException, ServerException, InsufficientDataException, ErrorResponseException, InternalException, InvalidResponseException, XmlParserException {
        List<String> objectNames = new ArrayList<>();
        Iterable<Result<Item>> results = minioClient.listObjects(ListObjectsArgs.builder().bucket(bucketName).build());
        for (Result<Item> result : results) {
            Item item = result.get();
            objectNames.add(item.objectName());
        }
        return objectNames;
    }

    private void createBucketIfNotExists(String bucketName) {
        try {
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!bucketExists) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (Exception e) {
            System.out.println("Error in createBucketIfNotExists" + e.getMessage());
        }
    }
}
