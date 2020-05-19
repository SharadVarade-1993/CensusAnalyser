package censusanalyser;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CensusAnalyserTest {

    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATE_CSV_FILE_PATH = "./src/test/resources/IndiaStateCode.csv";
    private  static final String INDIA_CENSUSS_CSV_FILE_PATH="./src/test/resources/IndiaStateCensusData.csv";
    private static final String INDIA_INCORRECT_DELIMITER_PATH="./src/test/resources/IndiaStateCensusData,csv";
    private static final String INDIA_INCORRECT_HEADER_PATH="./src/test/resources/IndiaStateCensusDataHeader.csv";
    private static final String INDIA_STATE_WRONG_FILE_PATH="./src/main/resources/IndiaStateCensusData.csv";
    private static final  String INDIA_STATE_TYPE_INCORRECT_PATH="./src/test/resources/IndiaStateCensusData.pdf";
    private static final String INDIA_STATE_INCORRECT_DELIMITER_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String INDIA_CENSUS_INCORRECT_DELIMITER_PATH="./src/test/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATE_INCORRECT_HEADER_PATH="./src/test/resources/IndiaStateCensusDataHeader";
    private static final String US_CENSUS_FILE_PATH = "./src/test/resources/USCensusData.csv";;

    @Test
    public void givenIndianCensus_withCSVFile_shouldReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.India);
            int numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.India,INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.India);
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.India,WRONG_CSV_FILE_PATH,INDIA_STATE_WRONG_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.India);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.India,INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndianStateCodeData_WhenSortedStateCode_ShouldReturnSortedResult() {
        try {

            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.India);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.India,INDIA_CENSUS_CSV_FILE_PATH);

            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
            IndiaStateCodeCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaStateCodeCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        } catch (CensusAnalyserException e) {

        }
    }

    @Test
    public void givenIndiaCensusData_whenCSVFileTypeIncorrect_shouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.India);
            ExpectedException exception = ExpectedException.none();
            exception.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.India,INDIA_CENSUSS_CSV_FILE_PATH,INDIA_STATE_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_TYPE_PROBLEM, e.type);
        }
    }


    @Test
    public void givenIndianCensusCSV_whenDelimiterIncorrect_shouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.India);
            ExpectedException exception = ExpectedException.none();
            exception.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.India,INDIA_CENSUS_INCORRECT_DELIMITER_PATH,INDIA_STATE_INCORRECT_DELIMITER_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);

        }
    }


    @Test
    public void givenIndianCensusCSV_whenHeaderIncorrect_shouldThrowException() {
        try{
            CensusAnalyser censusAnalyser=new CensusAnalyser(CensusAnalyser.Country.India);
            ExpectedException exception=ExpectedException.none();
            exception.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.India,INDIA_INCORRECT_HEADER_PATH,INDIA_INCORRECT_HEADER_PATH);
        }catch (CensusAnalyserException e){
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaStateData_withWrongFile_shouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.India);
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.India,INDIA_STATE_WRONG_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianStateData_whenCSVFileTypeIncorrect_shouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.India);
            ExpectedException exception = ExpectedException.none();
            exception.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.India,INDIA_STATE_TYPE_INCORRECT_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianStateCSV_whenDelimiterIncorrect_shouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.India);
            ExpectedException exception = ExpectedException.none();
            exception.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.India,INDIA_STATE_INCORRECT_DELIMITER_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);

        }
    }

    @Test
    public void givenIndianStateCSV_whenHeaderIncorrect_shouldThrowException() {
        try{
            CensusAnalyser censusAnalyser=new CensusAnalyser(CensusAnalyser.Country.India);
            ExpectedException exception=ExpectedException.none();
            exception.expect(CensusAnalyserException.class);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.India,INDIA_STATE_INCORRECT_HEADER_PATH);
        }catch (CensusAnalyserException e){
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndianCensusData_whenSortedOnPopulation_shouldReturnNumOfStatesSorted() {

        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.India);
            int numOfSortedState=censusAnalyser.loadCensusData(CensusAnalyser.Country.India,INDIA_CENSUS_CSV_FILE_PATH);
            String sortedPopulationData = censusAnalyser.getPopulationWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedPopulationData, IndiaCensusCSV[].class);
            Assert.assertEquals(29,numOfSortedState);
        } catch (CensusAnalyserException e) { }
    }

    @Test
    public void givenIndianCensusData_whenSortedOnPopulationDensity_shouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.India);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.India,INDIA_CENSUS_CSV_FILE_PATH);
            String sortedDensityData = censusAnalyser.getPopulationDensityWiseSortedData();
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedDensityData, IndiaCensusCSV[].class);
            Assert.assertEquals(342239, censusCSV[0].densityPerSqKm);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndianCensusData_whenSortedOnArea_shouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.India);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.India,INDIA_CENSUS_CSV_FILE_PATH);
            String sortedAreaData = censusAnalyser.getPopulationAreaWiseSortedData();
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedAreaData, IndiaCensusCSV[].class);
            Assert.assertEquals(342239, censusCSV[0].areaInSqKm);
        } catch (CensusAnalyserException e) { }
    }

    @Test
    public void givenUSCensusData_shouldReturnCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser=new CensusAnalyser(CensusAnalyser.Country.US);
            int numOfRecords = censusAnalyser.loadCensusData(CensusAnalyser.Country.US,US_CENSUS_FILE_PATH);
            Assert.assertEquals(51,numOfRecords);
        } catch (CensusAnalyserException e) {}
    }

    @Test
    public void givenUSCensusData_whenSortedOnState_shouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.US,US_CENSUS_FILE_PATH);
            String sortedStateData = censusAnalyser.getStateWiseSortedCensusData();
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedStateData, USCensusCSV[].class);
            Assert.assertEquals("AK", censusCSV[0].state);
        } catch (CensusAnalyserException e) { }
    }

    @Test
    public void givenUSCensusData_whenSortedOnPopulation_shouldReturnSortedResult() {

        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.US,US_CENSUS_FILE_PATH);
            String sortedPopulationData = censusAnalyser.getPopulationWiseSortedCensusData();
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedPopulationData, USCensusCSV[].class);
            Assert.assertEquals(563626, censusCSV[0].population);
        } catch (CensusAnalyserException e) { }
    }
    @Test
    public void givenUSCensusData_whenSortedOnPopulationDensity_shouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.US,US_CENSUS_FILE_PATH);
            String sortedDensityData = censusAnalyser.getPopulationDensityWiseSortedData();
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedDensityData, USCensusCSV[].class);
            Assert.assertEquals("AK",censusCSV[0].state);
        } catch (CensusAnalyserException e) { }
    }
    @Test
    public void givenUSCensusData_whenSortedOnArea_shouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser(CensusAnalyser.Country.US);
            censusAnalyser.loadCensusData(CensusAnalyser.Country.US,US_CENSUS_FILE_PATH);
            String sortedAreaData = censusAnalyser.getPopulationAreaWiseSortedData();
            USCensusCSV[] censusCSV = new Gson().fromJson(sortedAreaData, USCensusCSV[].class);
            Assert.assertEquals("DC", censusCSV[0].state);
        } catch (CensusAnalyserException e) { }
    }

}

