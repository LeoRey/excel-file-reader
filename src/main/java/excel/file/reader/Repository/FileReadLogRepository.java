package excel.file.reader.Repository;

import excel.file.reader.Entity.FileReadLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileReadLogRepository extends JpaRepository<FileReadLog, String> {
}
