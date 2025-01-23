package UdemyJavaSelenium;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;

import java.io.*;
import java.util.*;

import org.openqa.selenium.chrome.ChromeDriver;

public class Multithread {

    public static void main(String[] args) throws IOException {
        List<String> urls = readUrlsFromExcel("C:\\Users\\shubham.puri\\Desktop\\TestWeb\\MulithreadInput.xlsx");
        int numberOfThreads = 2;

        // Create and start threads
        for (int i = 0; i < numberOfThreads; i++) {
            Thread thread = new Thread(new BrowserTask(urls, numberOfThreads, i));
            thread.start();
        }
    }

    public static List<String> readUrlsFromExcel(String filePath) throws IOException {
        List<String> urls = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath); Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Cell cell = row.getCell(0);
                if (cell != null) {
                    urls.add(cell.getStringCellValue());
                }
            }
        }
        return urls;
    }

    public static void writeOutputToExcel(List<Map<String, String>> data, String filePath) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Output");
            int rowNum = 0;

            Row headerRow = sheet.createRow(rowNum++);
            headerRow.createCell(0).setCellValue("URL");
            headerRow.createCell(1).setCellValue("Title");
            headerRow.createCell(2).setCellValue("Meta");

            for (Map<String, String> entry : data) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(entry.get("url"));
                row.createCell(1).setCellValue(entry.get("title"));
                row.createCell(2).setCellValue(entry.get("meta"));
            }

            try (FileOutputStream fos = new FileOutputStream(filePath)) {
                workbook.write(fos);
            }
        }
    }

    // Runnable task for each thread
    static class BrowserTask implements Runnable {

        private List<String> urls;
        private int numberOfThreads;
        private int threadIndex;

        public BrowserTask(List<String> urls, int numberOfThreads, int threadIndex) {
            this.urls = urls;
            this.numberOfThreads = numberOfThreads;
            this.threadIndex = threadIndex;
        }

        @Override
        public void run() {
            WebDriver driver = new ChromeDriver();
            List<Map<String, String>> outputData = new ArrayList<>();
            for (int i = threadIndex; i < urls.size(); i += numberOfThreads) {
                String url = urls.get(i);
                driver.get(url);
                String title = driver.getTitle();
                String meta = "";
                try {
                    meta = driver.findElement(By.xpath("//meta[@name='description']")).getAttribute("content");
                } catch (NoSuchElementException e) {

                }
                Map<String, String> data = new HashMap<>();
                data.put("url", url);
                data.put("title", title);
                data.put("meta", meta);
                outputData.add(data);
                System.out.println("Processed URL " + url + " by " + Thread.currentThread().getName());
            }
            driver.quit();
            try {
                writeOutputToExcel(outputData, "output_data_" + threadIndex + ".xlsx");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
