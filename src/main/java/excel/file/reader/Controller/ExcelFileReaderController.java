package excel.file.reader.Controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import excel.file.reader.DTO.FileReadDTO;
import excel.file.reader.Service.FileReadLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/excel")
@JsonIgnoreProperties
public class ExcelFileReaderController {
    @Autowired
    private FileReadLogService fileReadLogService;

    @PostMapping("/read")
    public ResponseEntity<?> readFile(@ModelAttribute FileReadDTO input) throws IOException {
        try {
            List<HashMap<String, Object>> results = fileReadLogService.readAndLogFile(input);

            if (results.isEmpty()) {
                return ResponseEntity
                        .badRequest()
                        .body("Failed to process file. Please see log for details");
            }

            return ResponseEntity.ok(results);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing file: " + e.getMessage());
        }
    }
}
