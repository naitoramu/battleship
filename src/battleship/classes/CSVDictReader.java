package battleship.classes;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CSVDictReader {

    private List <CSVRecord> labelsList;

    public CSVDictReader(String csvFilePath) throws IOException {
        laodCSVFile(csvFilePath);
    }

    private void laodCSVFile(String csvFilePath) throws IOException {
        Reader reader = new FileReader(csvFilePath);
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
            .withFirstRecordAsHeader());
        labelsList = csvParser.getRecords();
    }

    public CSVRecord getLabelByName(String name) {
        for(CSVRecord record : labelsList) {
            if (record.get("Name").equals(name)) {
                System.out.println(record);
                return record;
            }
        }
        return null;
    }

    public ArrayList<String> getAvailableLanguages() {
        List<String> availableLangsList = labelsList.get(0).getParser().getHeaderNames();
        ArrayList<String> availableLangsArrayList = new ArrayList<String>(availableLangsList);
        availableLangsArrayList.remove(0);
        
        return availableLangsArrayList;
    }

    
}
