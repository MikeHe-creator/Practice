package com.mikelearner.nationaltesttool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class NationalData {
    private static final Logger log = LogManager.getLogger(NationalData.class);

    public void dataReader (){
        try {
            String Lujing = "E:/document file/Practice/NationalTestTool/2024国家公务员考试职位表zongbiao.xls";
            FileInputStream Excelfile = new FileInputStream(Lujing);
            String ExcelHouzui = Lujing.split("\\.")[1];
            Workbook workbook;
            int SheetNum;
            if (ExcelHouzui.equals("xls")) {
                workbook = new HSSFWorkbook(Excelfile);
                SheetNum = workbook.getNumberOfSheets();
            } else {
                workbook = new XSSFWorkbook(Excelfile);
                SheetNum = workbook.getNumberOfSheets();
            }

            for (int i = 0; i < SheetNum; i++) {
                Sheet sheetCentre = workbook.getSheetAt(i);
                System.out.println(sheetCentre.getSheetName());
            }
            List<List<String>> allFilteredData = new ArrayList<>();
            List<String> header = null;
            for (int i = 0; i < SheetNum; i++) {
                Sheet sheetCentre = workbook.getSheetAt(i);
                System.out.println(sheetCentre.getSheetName());
                List<List<String>> filteredData5 = GetData(sheetCentre);
                if (filteredData5 != null && !filteredData5.isEmpty()) {
                    allFilteredData.addAll(filteredData5);
                    if (header == null) {
                        header = GetHeader(sheetCentre);
                    }
                }
            }
            if (!allFilteredData.isEmpty()) {
                TurntoExcel(allFilteredData, header);
            }

        }catch (IOException e) {
            log.error("e: ", e);
        }
    }

    private List<String> GetHeader(Sheet sheet){
        Row headerRow = sheet.getRow(1);
        List<String> header = new ArrayList<>();
        for (Cell cell : headerRow) {
            header.add(cell.toString());
        }
        return header;
    }

    public List<List<String>> GetData(Sheet sheet) {
        Row headerRow = sheet.getRow(1);
        List<String> header = new ArrayList<>();
        for (Cell cell : headerRow) {
            header.add(cell.toString());
        }
        //System.out.println(header);

        List<List<String>> data = new ArrayList<>();
        for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
            Row row = sheet.getRow(rowIndex);
            List<String> rowData = new ArrayList<>();
            for (Cell cell : row) {
                rowData.add(cell.toString());
            }
            data.add(rowData);
        }
        //System.out.println(data);

        String targetHeader1 = "专业";
        int targetColumnIndex = header.indexOf(targetHeader1);
        if (targetColumnIndex == -1) {
            System.out.println("未找到栏目: " + targetHeader1);
        }
        String zhuanye1 = "物流";
        String zhuanye2="无限";
        List<List<String>> filteredData = new ArrayList<>();
        for (List<String> rowData : data) {
            if (rowData.get(targetColumnIndex).contains(zhuanye1) || rowData.get(targetColumnIndex).contains(zhuanye2)) {
                filteredData.add(rowData);
            }
        }

        String targetHeader2 = "学历";
        int targetColumnIndex2 = header.indexOf(targetHeader2);
        if (targetColumnIndex == -1) {
            System.out.println("未找到栏目: " + targetHeader1);
        }
        String xueli1="仅限硕士研究生";
        String xueli2="硕士研究生以上";
        List<List<String>> filteredData2 = new ArrayList<>();
        for (List<String> rowData : filteredData) {
            if (!rowData.get(targetColumnIndex2).contains(xueli1) || !rowData.get(targetColumnIndex2).contains(xueli2)) {
                filteredData2.add(rowData);
            }
        }
        //System.out.println(filteredData2);

        String targetHeader3 = "政治面貌";
        int targetColumnIndex3 = header.indexOf(targetHeader3);
        if (targetColumnIndex == -1) {
            System.out.println("未找到栏目: " + targetHeader1);
        }
        String party="中共党员";
        List<List<String>> filteredData3 = new ArrayList<>();
        for (List<String> rowData : filteredData2) {
            if (!rowData.get(targetColumnIndex3).contains(party)) {
                filteredData3.add(rowData);
            }
        }
        //System.out.println(filteredData3);

        String targetHeader4 = "服务基层项目工作经历";
        int targetColumnIndex4 = header.indexOf(targetHeader4);
        if (targetColumnIndex == -1) {
            System.out.println("未找到栏目: " + targetHeader1);
        }
        String Exp="大学生村官、农村义务教育阶段学校教师特设岗位计划、“三支一扶”计划、大学生志愿服务西部计划、在军队服役5年（含）以上的高校毕业生退役士兵";
        List<List<String>> filteredData4 = new ArrayList<>();
        for (List<String> rowData : filteredData3) {
            if (!rowData.get(targetColumnIndex4).contains(Exp)) {
                filteredData4.add(rowData);
            }
        }
        //System.out.println(filteredData4);

        String targetHeader5 = "备注";
        int targetColumnIndex5 = header.indexOf(targetHeader5);
        if (targetColumnIndex == -1) {
            System.out.println("未找到栏目: " + targetHeader1);
        }
        String student1="2024届高校毕业生";
        String students2="应届高校毕业生";
        List<List<String>> filteredData5 = new ArrayList<>();
        for (List<String> rowData : filteredData4) {
            if (!rowData.get(targetColumnIndex5).contains(student1) && !rowData.get(targetColumnIndex5).contains(students2)) {
                filteredData5.add(rowData);
            }
        }
        //System.out.println(filteredData4);
        if(!filteredData5.isEmpty()){
            System.out.println("本次筛选有最终结果");
        }else{
            System.out.println("本次筛选最终结果："+filteredData5);
        }

        return filteredData5;
    }

    private void TurntoExcel(List<List<String>> filteredData5, List<String> header) {
        if (filteredData5 == null || filteredData5.isEmpty() || header == null || header.isEmpty()) {
            System.out.println("数据为空，无法生成 Excel 文件");
            return;
        }

        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("筛选结果");

        Row row = sheet.createRow(0);
        for (int i = 0; i < header.size(); i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(header.get(i));
        }

        for(int rowIndex = 0; rowIndex <filteredData5.size(); rowIndex++) {
            Row row1 = sheet.createRow(rowIndex+1);
            List<String> rowData = filteredData5.get(rowIndex);
            for (int j = 0; j < rowData.size(); j++) {
                Cell cell = row1.createCell(j);
                cell.setCellValue(rowData.get(j));
            }
        }

        for (int i = 0; i < header.size(); i++) {
            sheet.autoSizeColumn(i);
        }

        String outputFilePath="E:/document file/Practice/NationalTestTool/2024国家公务员考试职位筛选表.xls";
        try (FileOutputStream fileOut = new FileOutputStream(outputFilePath)) {
            workbook.write(fileOut);
            System.out.println("文件已成功写入: " + outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
