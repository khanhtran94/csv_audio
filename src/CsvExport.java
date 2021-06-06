import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvExport {
    public static final String CSV_FILE_NAME = "test.csv";

    public static void main(String[] args) throws IOException {

        // default all fields are enclosed in double quotes
        // default separator is a comma

        Map<String, Integer> result = getWordOnFile("content.txt");
        List<String[]> csvData = createCsvDataSimple(result);


        try (CSVWriter writer = new CSVWriter(new FileWriter("test.csv"))) {
            writer.writeAll(csvData);
        }
    }

    private static Map<String, Integer> getWordOnFile(String fileName) {
        List<String[]> result = null;
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {

            //xu ly ky tu double trong file txt
            result = stream.map(x -> x.replace("”", " ").replace("“"," ").split(" ")).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, Integer> wordFrequeny = new HashMap<>();
        result.forEach(line -> {
            for (String word : line) {
                if (wordFrequeny.containsKey(word)) {
                    wordFrequeny.put(word, wordFrequeny.get(word) + 1);
                } else {
                    wordFrequeny.put(word, 1);
                }
            }
        });

        return wordFrequeny;
    }

    private static List<String[]> createCsvDataSimple(Map<String, Integer> wordFrequency) {
        String[] header = {"word", "name"};


        List<String[]> list = new ArrayList<>();
        list.add(header);
        wordFrequency.forEach((word, frequency) ->
        {
            String[] lineCsv = {word, frequency.toString()};
            list.add(lineCsv);
        });

        return list;
    }

}
