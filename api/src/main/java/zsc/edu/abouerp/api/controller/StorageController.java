package zsc.edu.abouerp.api.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import zsc.edu.abouerp.common.entiry.ResultBean;
import zsc.edu.abouerp.entity.domain.Storage;
import zsc.edu.abouerp.service.service.FileStorageService;
import zsc.edu.abouerp.service.service.StorageService;


/**
 * @author Abouerp
 */
@RestController
@RequestMapping("/api/storage")
public class StorageController {

    private final StorageService storageService;
    private final FileStorageService fileStorageService;

    public StorageController(
            StorageService storageService,
            FileStorageService fileStorageService) {
        this.storageService = storageService;
        this.fileStorageService = fileStorageService;
    }

    @PostMapping
    public ResultBean<String> save(@RequestParam MultipartFile file) {
        String md5 = fileStorageService.upload(file);
        storageService.save(md5, file);
        return new ResultBean<>(md5);
    }

    @GetMapping(value = "/preview/{id}")
    public ResponseEntity<Resource> preview(@PathVariable String id) throws Exception{
        Storage storage = storageService.findByMD5(id);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, storage.getContentType())
                .body(fileStorageService.findByHash(storage.getMd5()));
    }
}
