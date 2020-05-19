package censusanalyser;

public class CensusDAO {
    public String state;
    public String stateCode;
    public String stateId;
    public int population;
    public double totalArea;
    public double populationDensity;


    public CensusDAO(IndiaCensusCSV indiaCensusCSV) {
        state = indiaCensusCSV.state;
        totalArea =indiaCensusCSV.areaInSqKm;
        populationDensity = indiaCensusCSV.densityPerSqKm;
        population=indiaCensusCSV.population;
        stateCode=indiaCensusCSV.stateCode;
    }

    public CensusDAO(USCensusCSV censusCSV) {
        state = censusCSV.state;
        stateCode=censusCSV.stateId;
        totalArea =censusCSV.totalArea;
        population=censusCSV.population;
        populationDensity=censusCSV.populationDensity;

    }
    public Object getCensusDTO(CensusAnalyser.Country country) {
        if(country.equals(CensusAnalyser.Country.US))
            return  new USCensusCSV(state,stateCode,population,populationDensity,totalArea);

        return new IndiaCensusCSV(state,population,(int)populationDensity,(int)totalArea);

    }
}