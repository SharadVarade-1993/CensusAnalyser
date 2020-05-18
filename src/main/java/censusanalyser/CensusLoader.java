package censusanalyser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class CensusLoader {

    public  <E> Map<String,CensusDAO> loadCensusData(Class<E> censusCSVClass,String... csvFilePath) throws CensusAnalyserException {
        Map<String,CensusDAO> censusCSVMap =new HashMap<>();
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath[0]))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> csvFileIterator = csvBuilder.getCSVFileIterator(reader,censusCSVClass);
            Iterable<E> censusCSVIterable=() -> csvFileIterator;
            if(censusCSVClass.getName().equals("censusanalyser.IndiaCensusCSV")) {
                StreamSupport.stream(censusCSVIterable.spliterator(), false)
                        .map(IndiaCensusCSV.class::cast)
                        .forEach(censusCSV -> censusCSVMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            }else if(censusCSVClass.getName().equals("censusanalyser.USCensusCSV")) {
                StreamSupport.stream(censusCSVIterable.spliterator(), false)
                        .map(USCensusCSV.class::cast)
                        .forEach(censusCSV -> censusCSVMap.put(censusCSV.state, new CensusDAO(censusCSV)));
            }
            if(csvFilePath.length==1)  return censusCSVMap;
            this.loadIndianStateCode(censusCSVMap,csvFilePath[1]);
            return censusCSVMap;
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    private int loadIndianStateCode(Map<String, CensusDAO> censusCSVMap, String csvFilePath) throws CensusAnalyserException {

        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath))) {
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndiaStateCodeCSV> stateCodeCSVIterator=csvBuilder.getCSVFileIterator(reader,IndiaStateCodeCSV.class);
            Iterable<IndiaStateCodeCSV> stateCodeCSVIterable=()-> stateCodeCSVIterator;
            StreamSupport.stream(stateCodeCSVIterable.spliterator(),false)
                    .filter(stateCodeCSV -> censusCSVMap.get(stateCodeCSV.state) != null )
                    .forEach(stateCodeCSV-> censusCSVMap.get(stateCodeCSV.state).stateCode = stateCodeCSV.stateCode);
            return censusCSVMap.size();
        } catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (IllegalStateException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

}


