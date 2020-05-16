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

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfRecords = censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianStateCSV_ShouldReturnExactCount() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            int numOfStateCode = censusAnalyser.loadIndianStateCode(INDIA_STATE_CSV_FILE_PATH);
            Assert.assertEquals(37, numOfStateCode);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndianCensusData_WhenSortedOnState_ShouldReturnSortedResult() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
            IndiaCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        } catch (CensusAnalyserException e) {
        }
    }

    @Test
    public void givenIndianStateCodeData_WhenSortedStateCode_ShouldReturnSortedResult() {
        try {

            CensusAnalyser censusAnalyser = new CensusAnalyser();
            censusAnalyser.loadIndianStateCode(INDIA_CENSUS_CSV_FILE_PATH);

            String sortedCensusData = censusAnalyser.getStateWiseSortedCensusData();
            IndiaStateCodeCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndiaStateCodeCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
        } catch (CensusAnalyserException e) {

        }
    }

    @Test
    public void givenIndiaCensusData_whenCSVFileTypeIncorrect_shouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exception = ExpectedException.none();
            exception.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(INDIA_CENSUSS_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_TYPE_PROBLEM, e.type);
        }
    }


    @Test
    public void givenIndianCensusCSV_whenDelimiterIncorrect_shouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exception = ExpectedException.none();
            exception.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(INDIA_INCORRECT_DELIMITER_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);

        }
    }


    @Test
    public void givenIndianCensusCSV_whenHeaderIncorrect_shouldThrowException() {
        try{
            CensusAnalyser censusAnalyser=new CensusAnalyser();
            ExpectedException exception=ExpectedException.none();
            exception.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(INDIA_INCORRECT_HEADER_PATH);
        }catch (CensusAnalyserException e){
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

    @Test
    public void givenIndiaStateData_withWrongFile_shouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(INDIA_STATE_WRONG_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

    @Test
    public void givenIndianStateData_whenCSVFileTypeIncorrect_shouldThrowException() {
        try {
            CensusAnalyser censusAnalyser = new CensusAnalyser();
            ExpectedException exception = ExpectedException.none();
            exception.expect(CensusAnalyserException.class);
            censusAnalyser.loadIndiaCensusData(INDIA_STATE_TYPE_INCORRECT_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
        }
    }

}

