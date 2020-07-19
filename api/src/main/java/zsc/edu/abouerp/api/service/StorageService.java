package zsc.edu.abouerp.api.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import zsc.edu.abouerp.api.domain.Storage;
import zsc.edu.abouerp.api.repository.StorageRepository;

/**
 * @author Abouerp
 */
@Service
@Log4j2
public class StorageService {

    private final StorageRepository storageRepository;

    public StorageService(StorageRepository storageRepository) {
        this.storageRepository = storageRepository;
    }

    @Transactional
    public String save(String sha1, MultipartFile file) {
        Storage storage = new Storage();
        storage = storage
                .setSha1(sha1)
                .setContentType(file.getContentType())
                .setOriginalFilename(file.getOriginalFilename());
        log.info("stroage = {}", storage);
        storageRepository.save(storage);
        return null;
    }

    public Storage findBySHA1(String id) {
        Storage storage = storageRepository.findBySha1(id).orElse(null);
        if (storage == null) {
            return null;
        } else {
            return storage;
        }
    }
}
