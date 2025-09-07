package excel.file.reader.Service;

import excel.file.reader.DTO.FileReadDTO;
import excel.file.reader.DTO.UserRightsDTO;
import excel.file.reader.Entity.FileReadLog;
import excel.file.reader.Repository.FileReadLogRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

@Service
public class FileReadLogService {

    @Autowired
    UserService userService;

    @Autowired
    FileReadLogRepository fileReadLogRepository;

    public List<HashMap<String, Object>> readAndLogFile(FileReadDTO input) throws IOException {
        UserRightsDTO user = userService.searchByName(input.getUsername());
        FileReadLog dataToSave = new FileReadLog();
        dataToSave.setReadDate(new Date());
        if(user == null){
            this.fileReadLogFailed(input, "Failed - User not found!");
            return Collections.emptyList();
        } else if (!user.getIsCreate()) {
            this.fileReadLogFailed(input, "Failed - User does not have create permission!");
            return Collections.emptyList();
        }else if(!input.getFile().getOriginalFilename().endsWith(".xlsx") && !input.getFile().getOriginalFilename().endsWith(".xls")){
            this.fileReadLogFailed(input, "Failed - Please Upload Excel File (not csv or others)!");
            return Collections.emptyList();
        }else{
            List<HashMap<String, Object>> results = new ArrayList<>();

            try (Workbook workbook = new XSSFWorkbook(input.getFile().getInputStream())) {
                Sheet sheet = workbook.getSheetAt(0);

                Row headerRow = sheet.getRow(0);
                List<String> headers = IntStream.range(0, headerRow.getLastCellNum())
                        .mapToObj(i -> headerRow.getCell(i).getStringCellValue())
                        .toList();

                for (int rowIdx = 1; rowIdx <= sheet.getLastRowNum(); rowIdx++) {
                    Row row = sheet.getRow(rowIdx);
                    HashMap<String, Object> map = new HashMap<>();

                    for (int colIdx = 0; colIdx < headers.size(); colIdx++) {
                        Cell cell = row.getCell(colIdx);
                        String key = headers.get(colIdx);
                        Object value = (cell != null) ? getCellValue(cell) : "Empty";
                        map.put(key, value);
                    }

                    results.add(map);
                }
            }

            return results;
        }
    }

    public void fileReadLogFailed(FileReadDTO input, String cause){
        FileReadLog dataToSave = new FileReadLog();
        dataToSave.setReadDate(new Date());
        dataToSave.setId(UUID.randomUUID());
        dataToSave.setFileName(input.getFile().getName());
        dataToSave.setPurpose("Create");
        dataToSave.setUsername(input.getUsername());
        dataToSave.setStatus(cause);
        fileReadLogRepository.save(dataToSave);
    }

    private static Object getCellValue(Cell cell) {
        DataFormatter formatter = new DataFormatter();
        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> formatter.formatCellValue(cell);
            case BOOLEAN -> cell.getBooleanCellValue();
            default -> "Empty";
        };
    }
}
