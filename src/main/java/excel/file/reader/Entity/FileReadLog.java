package excel.file.reader.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity(
        name = "file_read_log"
)
public class FileReadLog {
    @Id
    @Column(name = "id")
    private UUID id;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "username")
    private String username;
    @Column(name = "purpose")
    private String purpose;
    @Column(name = "status")
    private String status;
    @Column(name = "read_date")
    private Date readDate;
}
