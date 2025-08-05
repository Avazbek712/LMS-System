package uz.imv.lmssystem.serviceImpl.files;

import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.imv.lmssystem.service.files.FileStorageService;

import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * Created by Avazbek on 31/07/25 12:38
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class FileStorageServiceImpl implements FileStorageService {

    private final MinioClient minioClient;

    @Value("${minio.bucket.name}")
    private String bucketName;

    @Override
    @Transactional
    public String uploadFile(MultipartFile file, String destinationPath) {
        if (file.isEmpty() || Objects.isNull(file.getOriginalFilename()))
            throw new IllegalArgumentException("File not found!");


        try (InputStream inputStream = file.getInputStream()) {

            String fileExtension = getFileExtension(file.getOriginalFilename());
            String uniqueFileName = UUID.randomUUID() + "." + fileExtension;

            String objectName = destinationPath + "/" + uniqueFileName;

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(inputStream, inputStream.available(), -1)
                            .contentType(file.getContentType())
                            .build());

            return objectName;

        } catch (Exception e) {
            log.error("Error while uploading file");
            throw new IllegalArgumentException("Error while uploading file", e);
        }
    }


    @Override
    public String getTempLink(String fileName) {
        if (fileName == null || fileName.isBlank()) {
            return null;
        }

        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(fileName)
                            .expiry(1, TimeUnit.HOURS).build());
        } catch (Exception e) {
            log.error("Error while getting temp link");
            throw new IllegalArgumentException("Error while getting temp link", e);
        }

    }

    private String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}
