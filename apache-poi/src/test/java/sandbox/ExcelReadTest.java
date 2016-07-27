package sandbox;

import org.apache.poi.ss.usermodel.*;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static support.ResourceAccessor.excelFileWithName;

public class ExcelReadTest {

    @Test
    public void canReadOneCell() throws Exception {
        File fis = new File(excelFileWithName("prime-factors-scenarios"));
        Workbook wb = WorkbookFactory.create(fis);
        Sheet sheet = wb.getSheetAt(0);
        Row row = sheet.getRow(4);
        Cell cell = row.getCell(1);
        String text = cell.getStringCellValue();

        assertThat(text, equalTo("can decompose 2"));
    }
}
