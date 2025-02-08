package UdemyJavaSelenium;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Multithread {

    public static void main(String[] args) throws IOException {
        List<String> urls = readUrlsFromExcel("C:\\Users\\shubham.puri\\Desktop\\TestWeb\\MulithreadInput.xlsx");
        int numberOfThreads = 5;
        List<Map<String, String>> outputData = new ArrayList<>();

        // Create and start threads
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < numberOfThreads; i++) {
            Thread thread = new Thread(new BrowserTask(urls, numberOfThreads, i, outputData));
            threads.add(thread);
            thread.start();
        }

        // Wait for all threads to finish
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Write output data to Excel
        writeOutputToExcel(outputData, "output_data.xlsx");
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


    static class BrowserTask implements Runnable {

        private List<String> urls;
        private int numberOfThreads;
        private int threadIndex;
        private List<Map<String, String>> outputData;

        public BrowserTask(List<String> urls, int numberOfThreads, int threadIndex, List<Map<String, String>> outputData) {
            this.urls = urls;
            this.numberOfThreads = numberOfThreads;
            this.threadIndex = threadIndex;
            this.outputData = outputData;
        }

        @Override
        public void run() {
            WebDriver driver = new ChromeDriver();
            for (int i = threadIndex; i < urls.size(); i += numberOfThreads) {
                String url = urls.get(i);
                driver.get(url);
                String title = driver.getTitle();
                String meta = "";
                try {
                    meta = driver.findElement(By.xpath("//meta[@name='description']")).getAttribute("content");
                } catch (NoSuchElementException e) {
                    // Handle exception
                }
                Map<String, String> data = new HashMap<>();
                data.put("url", url);
                data.put("title", title);
                data.put("meta", meta);
                synchronized (outputData) {
                    outputData.add(data);
                }
                System.out.println("Processed URL " + url + " by " + Thread.currentThread().getName());
            }
            driver.quit();
        }
    }
}
