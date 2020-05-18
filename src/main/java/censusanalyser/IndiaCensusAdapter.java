package censusanalyser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class IndiaCensusAdapter extends CensuaAdapter {

    @Override
    public Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException {
        Map<String,CensusDAO> censusCsvMap=super.loadCensusData(IndiaCensusCSV.class,csvFilePath[0]);
        this.loadIndianStateCode(censusCsvMap,csvFilePath[1]);
        return censusCsvMap;
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


