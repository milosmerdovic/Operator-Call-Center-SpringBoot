package net.beotel.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelHelper {
	

	public List<XSSFRow> parseXLSFile(File xlsFile, Map<String, Integer> columnMap){
		List<XSSFRow> rowList = null;
		if (columnMap != null) {
			try {
				InputStream is = new FileInputStream(xlsFile);
				XSSFWorkbook wb = new XSSFWorkbook(is);
				if(wb!=null){
					XSSFSheet sheet = wb.getSheetAt(0);
					if(sheet != null){
						XSSFRow headerRow = sheet.getRow(0);
						mapColumnNameToIndex(headerRow, columnMap); //mapira nazive zaglavlja i njihove indekse
						int firstDataRow = sheet.getFirstRowNum()+1;
						int lastDataRow = sheet.getLastRowNum();
						//log.info("Broj redova u fajlu: "+ (lastDataRow- firstDataRow));
						rowList = new ArrayList<XSSFRow>();
						for(int i=firstDataRow;i<=lastDataRow;i++){
							XSSFRow row = sheet.getRow(i);
							rowList.add(row);
						}
					}
				}
			} catch (FileNotFoundException e) {
				//log.warn("Doslo je do greske prilikom uploada fajla!!!");
				//log.warn("Greska: "+ e.getCause());
			} catch (IOException e) {
				//log.warn("Doslo je do greske prilikom uploada fajla!!!");
				//log.warn("Greska: "+ e.getCause());
			}			
			if(rowList == null || rowList.size()==0) {
				//log.warn("Neuspelo parsiranje fajla. Pojavio se problem pri parsiranju fajla na redove.");
			}
			//log.info("Ukupan broj parsiranih redova u fajlu: "+ (rowList != null? rowList.size():0));
		} else {
			//log.error("Parametar columnMap prosledjujemo po referenci, mora biti instanciran.");
		}
		return rowList;
	}
	
	public Map<String, Integer> mapColumnNameToIndex(XSSFRow headerRow, Map<String, Integer> columnMap){
		if(columnMap==null) {
			columnMap = new HashMap<>();
		}
		int minColIx = headerRow.getFirstCellNum();
		int maxColIx = headerRow.getLastCellNum();
		for(int i=minColIx; i<maxColIx; i++){
			XSSFCell cell = headerRow.getCell(i);
			if(cell==null){
				continue;
			}
			String strValue = cell.getStringCellValue();
			//log.info("Pakujemo u mapu key: "+ strValue+", value: "+i);
			columnMap.put(strValue, i);
		}

		return columnMap;
	}
}
