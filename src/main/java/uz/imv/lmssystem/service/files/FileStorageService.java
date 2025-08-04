package uz.imv.lmssystem.service.files;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String uploadFile(MultipartFile file ,  String destinationPath);

    String getTempLink(String fileName);
}
