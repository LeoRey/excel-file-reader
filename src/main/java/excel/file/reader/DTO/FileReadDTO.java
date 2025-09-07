package excel.file.reader.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@ToString
public class FileReadDTO {
    private String id;
    private String fileName;
    private String username;
    private String purpose;
    private Date readDate;
    private MultipartFile file;
}
