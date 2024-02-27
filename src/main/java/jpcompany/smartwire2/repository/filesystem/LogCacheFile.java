package jpcompany.smartwire2.repository.filesystem;

import jpcompany.smartwire2.common.error.CustomException;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static jpcompany.smartwire2.common.error.ErrorCode.*;

@Component
public class LogCacheFile {
    public static final String CACHE_PATH = "cache-backup/";

    public void write(String filePath, String content) {
        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write(content);
            fileWriter.flush();
            fileWriter.close();
        } catch (FileNotFoundException fileNotFoundException) {
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            write(filePath, content);
        } catch (IOException ioException) {
            throw new CustomException(FILE_IO_WRITE_EXCEPTION, ioException);
        }
    }

    public void write(String filePath, String content, boolean isAppend) {
        try {
            FileWriter fileWriter = new FileWriter(filePath, isAppend);
            fileWriter.write(content);
            fileWriter.flush();
            fileWriter.close();
        } catch (FileNotFoundException fileNotFoundException) {
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            write(filePath, content, isAppend);
        } catch (IOException ioException) {
            throw new CustomException(FILE_IO_WRITE_EXCEPTION, ioException);
        }
    }

    public List<String> read(String filePath) {
        try{
            return Files.readAllLines(Paths.get(filePath));
        } catch (IOException ioException) {
            throw new CustomException(FILE_IO_READ_EXCEPTION, ioException);
        }
    }

    public void delete(String filePath) {
        File file = new File(filePath);
        if (file.delete()) {
            return;
        }
        throw new CustomException(FILE_IO_DELETE_EXCEPTION);
    }
}
