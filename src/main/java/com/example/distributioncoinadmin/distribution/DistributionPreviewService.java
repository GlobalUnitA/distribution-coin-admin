package com.example.distributioncoinadmin.distribution;

import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class DistributionPreviewService {
    public List<DistributionPreviewRow> parseExcel(MultipartFile file) throws IOException {
        List<DistributionPreviewRow> rows = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())){
            Sheet sheet  = workbook.getSheetAt(0);

            for(Row row : sheet){
                if(row.getRowNum() == 0){
                    //헤더 한 줄 있다고 가정
                    continue;
                }

                Cell nameCell    = row.getCell(0);
                Cell addressCell = row.getCell(1);
                Cell amountCell  = row.getCell(2);

                if(nameCell == null && addressCell == null && amountCell == null){
                    continue;
                }

                DistributionPreviewRow dto = new DistributionPreviewRow();
                dto.setNo(row.getRowNum());
                dto.setName(getString(nameCell));
                dto.setWalletAddress(getString(addressCell));
                dto.setAmount(getDecimal(amountCell));

                rows.add(dto);
            }
        }

        return rows;
    }

    private String getString(Cell cell) {
        if (cell == null) return "";
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue().trim();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf((long) cell.getNumericCellValue());
        } else {
            return "";
        }
    }

    private BigDecimal getDecimal(Cell cell) {
        if (cell == null) return BigDecimal.ZERO;

        if (cell.getCellType() == CellType.NUMERIC) {
            return BigDecimal.valueOf(cell.getNumericCellValue());
        } else if (cell.getCellType() == CellType.STRING) {
            try {
                return new BigDecimal(cell.getStringCellValue().trim());
            } catch (NumberFormatException e) {
                return BigDecimal.ZERO;
            }
        } else {
            return BigDecimal.ZERO;
        }
    }
}
