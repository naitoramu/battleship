package model;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CSVDictReader {
    public static List<CSVRecord> parseCSV(String csvFilePath) throws IOException{
        Reader reader = new FileReader(csvFilePath);
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
            .withFirstRecordAsHeader()
            .withIgnoreHeaderCase()
            .withTrim());
        List<CSVRecord> csvRecords = csvParser.getRecords();

        return csvRecords;
    }
}
