package zsc.edu.abouerp.api.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import zsc.edu.abouerp.api.config.StorageProperties;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Abouerp
 */
@Log4j2
@Service
public class FileStorageService {
    private final Path rootLocation;

    public FileStorageService(StorageProperties storageProperties) {
        this.rootLocation = Paths.get(storageProperties.getLocation());
    }

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
        }
        log.info("file storage path = {}", rootLocation.toString());
    }

    public String upload(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (file.isEmpty()) {
            return null;
//            throw new StorageException(String.format("Failed to store empty file, %s", originalFilename));
        }

        if (originalFilename != null && originalFilename.contains("..")) {
            // This is a security check
//            throw new StorageException(String.format("Cannot store file with relative path outside current directory, %s", originalFilename));
            return null;
        }
        //拿二进制文件生成一个唯一的名称
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            if (originalFilename != null && !originalFilename.isEmpty()) {
                digest.update(originalFilename.getBytes());
            }
            Path temp = Files.createTempFile("temp-", null);
            try (
                    InputStream in = file.getInputStream();
                    OutputStream out = Files.newOutputStream(temp)
            ) {
                int n;
                byte[] buf = new byte[8192];

                while (-1 != (n = in.read(buf, 0, buf.length))) {
                    digest.update(buf, 0, n);
                    out.write(buf, 0, n);
                }

                String sha1 = String.format("%032X", new BigInteger(1, digest.digest()));
                log.info("sh1 = {}" + sha1);

                Path dest = rootLocation.resolve(sha1);
                if (dest.toFile().exists()) {
                    return sha1;
                }
                Files.move(temp, dest);
                return sha1;
            } catch (IOException e) {
//                throw new StorageException("Failed to store file " + originalFilename, e);
            }
        } catch (IOException | NoSuchAlgorithmException e) {
//            throw new StorageException("Failed to create temp file ", e);
        }
        return null;
    }

    public Resource findByHash(String hash) {
        try {
            Resource resource = new UrlResource(rootLocation.resolve(hash).toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                return null;
//                throw new StorageFileNotFoundException("Could not read file: " + hash);
            }
        } catch (MalformedURLException e) {
//            throw new StorageFileNotFoundException("Could not read file: " + hash, e);
        }
        return null;
    }
}
