package zsc.edu.abouerp.service.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import zsc.edu.abouerp.entity.domain.Storage;
import zsc.edu.abouerp.service.exception.StorageFileNotFoundException;
import zsc.edu.abouerp.service.repository.StorageRepository;

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
    public void save(String md5, MultipartFile file) {
        Storage storage = new Storage();
        storage = storage
                .setMd5(md5)
                .setContentType(file.getContentType())
                .setOriginalFilename(file.getOriginalFilename())
                .setCount(storage.getCount() + 1);
        log.info("stroage = {}", storage);
        storageRepository.save(storage);
    }

    public Storage findByMD5(String id) {
        log.info("----------------------------md5 = {}" + id);
        return storageRepository.findByMd5(id).orElseThrow(StorageFileNotFoundException::new);
    }

    public void deleteById(Integer id) {
        storageRepository.deleteById(id);
    }

    public Storage save(Storage storage) {
        return storageRepository.save(storage);
    }


}
