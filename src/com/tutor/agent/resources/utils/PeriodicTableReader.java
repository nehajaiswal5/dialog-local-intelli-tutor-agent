/**
 * 
 */
package com.tutor.agent.resources.utils;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import com.tutor.agent.periodicelements.PeriodicElements.PeriodicElement;

/**
 * @author nkumari
 *
 */
public class PeriodicTableReader {

	private  HashMap<String,PeriodicElement> periodicElementByElementNameMap = null;
	private  HashMap<String,PeriodicElement> periodicElementBySymbolNameMap = null;
	private  HashMap<String,PeriodicElement> periodicElementByTypeMap = null;

	public PeriodicTableReader(){

		periodicElementByElementNameMap = new HashMap<String,PeriodicElement>();
		periodicElementBySymbolNameMap = new HashMap<String,PeriodicElement>();
		periodicElementByTypeMap = new HashMap<String,PeriodicElement>();
		readCsv();
	}
	/**
	 * @param args
	 * @return 
	 * 
	 */
	public  void readCsv(){

		String csvFile = "./TopicResources/PeriodicTableElements.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {

			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				String line1 = line;
				// use comma as separator
				PeriodicElement pe = new PeriodicElement();

				String[] elementdetail = line1.split(cvsSplitBy);


				pe.setAtomicNumber(elementdetail[0]);
				pe.setElement(elementdetail[1]);
				pe.setSymbol(elementdetail[2]);
				pe.setAtomicWeight(elementdetail[3]);
				pe.setPeriod(elementdetail[4]);
				pe.setGroup(elementdetail[5]);
				pe.setPhase(elementdetail[6]);
				pe.setMostStableCrystal(elementdetail[7]);
				pe.setType(elementdetail[8]);
				pe.setIonicRadius(elementdetail[9]);
				pe.setAtomicRadius(elementdetail[10]);
				pe.setElectronegativity(elementdetail[11]);
				pe.setFirstIonizationPotential(elementdetail[12]);
				pe.setDensity(elementdetail[13]);
				pe.setMeltingPointK(elementdetail[14]);
				pe.setBoilingPointK(elementdetail[15]);
				pe.setIsotopes(elementdetail[16]);
				pe.setDiscoverer(elementdetail[17]);
				pe.setYearofDiscovery(elementdetail[18]);
				pe.setSpecificHeatCapacity(elementdetail[19]);
				pe.setElectronConfiguration(elementdetail[20]);
				pe.setDisplayRow(elementdetail[21]);
				pe.setDisplayColumn(elementdetail[22]);

				periodicElementByElementNameMap.put(pe.getElement(),pe);
				periodicElementBySymbolNameMap.put(pe.getSymbol(), pe);
				periodicElementByTypeMap.put(pe.getType(), pe);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		
	}

	/**
	 * @param args
	 * @return map by element name
	 */
	public  HashMap<String, PeriodicElement> returnByElementMap(){
		return periodicElementByElementNameMap;
	}
	
	/**
	 * @param args
	 * @return map by symbol name
	 */
	public  HashMap<String, PeriodicElement> returnBySymbolMap(){
		return periodicElementBySymbolNameMap;
	}
	
	/**
	 * @param args
	 * @return map by type
	 */
	public  HashMap<String, PeriodicElement> returnByTypeMap(){
		return periodicElementByTypeMap;
	}

//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//		PeriodicTableReader ptr = new PeriodicTableReader();
//		//ptr.readCsv();
//		System.out.println(periodicElementByElementNameMap);
//		System.out.println(periodicElementBySymbolNameMap);
//		System.out.println(periodicElementByTypeMap);
//
//	}

}
