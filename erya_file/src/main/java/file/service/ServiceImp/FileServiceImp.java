package file.service.ServiceImp;

import com.github.tobato.fastdfs.service.FastFileStorageClient;
import file.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class FileServiceImp implements FileService {
    private FastFileStorageClient fastFileStorageClient;

    public FileServiceImp(FastFileStorageClient fastFileStorageClient) {
        this.fastFileStorageClient = fastFileStorageClient;
    }

    @Override
    public void upload(MultipartFile multipartFile) {
        InputStream inputStream = null;
        try {
            inputStream = multipartFile.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String originalFilename = multipartFile.getOriginalFilename();
        assert originalFilename != null;
        fastFileStorageClient.uploadFile(inputStream,
                multipartFile.getSize(),
                originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase()
                , null);
    }
}
