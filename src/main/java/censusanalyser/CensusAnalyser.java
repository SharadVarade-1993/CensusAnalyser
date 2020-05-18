package censusanalyser;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAnalyser {


    List<CensusDAO> censusDAOS = null;

    public enum Country{India,US}
    Map<String, CensusDAO> censusCSVMap=null;


    public CensusAnalyser() {
        this.censusCSVMap = new HashMap<>();
    }

    public int loadCensusData(Country country,String... csvFilePath) throws CensusAnalyserException {
        censusCSVMap = new CensusLoader().loadCensusData(country,csvFilePath);
        return censusCSVMap.size();

    }




    private <E> int getCount(Iterator<E> iterator) {
        Iterable<E> csvIterable = () -> iterator;
        int numOfEnteries = (int) StreamSupport.
                stream(csvIterable.spliterator(), false).
                count();
        return numOfEnteries;
    }

    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
        if (censusCSVMap == null || censusCSVMap.size() == 0) {
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusCSVComparator = Comparator.comparing(census -> census.state);
        List<CensusDAO> censusDAOS = censusCSVMap.values().stream().collect(Collectors.toList());
        this.sort(censusDAOS, censusCSVComparator);
        String sortedStateCensus = new Gson().toJson(censusDAOS);
        return sortedStateCensus;
    }

    public String getPopulationWiseSortedCensusData() throws CensusAnalyserException {
        if (censusCSVMap == null || censusCSVMap.size() == 0) {
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusCSVComparator = Comparator.comparing(census -> census.population);
        List<CensusDAO> censusDAOS = censusCSVMap.values().stream().collect(Collectors.toList());
        this.sortByDescending(censusDAOS,censusCSVComparator);
        String sortedPopulationCensus = new Gson().toJson(this.censusDAOS);
        return sortedPopulationCensus;
    }

    private void sort(List<CensusDAO> censusDAOS,Comparator<CensusDAO> censusComparator) {
        for (int i = 0; i < censusDAOS.size() - 1; i++) {
            for (int j = 0; j < censusDAOS.size() - i - 1; j++) {
                CensusDAO census1 = censusDAOS.get(j);
                CensusDAO census2 = censusDAOS.get(j + 1);
                if (censusComparator.compare(census1, census2) > 0) {
                    censusDAOS.set(j, census2);
                    censusDAOS.set(j + 1, census1);
                }
            }
        }
    }

    public String getPopulationDensityWiseSortedData() throws CensusAnalyserException {
        if (censusCSVMap == null || censusCSVMap.size() == 0) {
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusCSVComparator = Comparator.comparing(census -> census.populationDensity);
        List<CensusDAO> censusDAOS=censusCSVMap.values().stream().collect(Collectors.toList());
        this.sortByDescending(censusDAOS,censusCSVComparator);
        String sortedDensityCensus = new Gson().toJson(censusDAOS);
        return sortedDensityCensus;
    }

    private void sortByDescending(List<CensusDAO> censusDAOS,Comparator<CensusDAO> censusComparator) {
        for (int i = 0; i < censusDAOS.size() - 1; i++) {
            for (int j = 0; j < censusDAOS.size() - i - 1; j++) {
                CensusDAO census1 = censusDAOS.get(j);
                CensusDAO census2 = censusDAOS.get(j + 1);
                if (censusComparator.compare(census1, census2) < 0) {
                    censusDAOS.set(j, census2);
                    censusDAOS.set(j + 1, census1);
                }
            }
        }
    }

    public String getPopulationAreaWiseSortedData() throws CensusAnalyserException {
        if (censusCSVMap == null || censusCSVMap.size() == 0) {
            throw new CensusAnalyserException("No Census Data", CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<CensusDAO> censusCSVComparator = Comparator.comparing(census -> census.totalArea);
        List<CensusDAO> censusDAOS=censusCSVMap.values().stream().collect(Collectors.toList());
        this.sortByDescending(censusDAOS,censusCSVComparator);
        String sortedDensityCensus = new Gson().toJson(this.censusDAOS);
        return sortedDensityCensus;
    }


}
