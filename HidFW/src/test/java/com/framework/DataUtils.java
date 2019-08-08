package com.framework;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataUtils {
	private static FormulaEvaluator evaluator;
	private static Hashtable<String, List<Object>> datatable = new Hashtable<>();

	public static Object[][] getParams(String scenario, String testname) {
		String key = String.format("%s**%s", scenario, testname);
		if (datatable.containsKey(key))
			return getParamFromList(datatable.get(key));
		
		List<Object> paramArr = new ArrayList<Object>();
		
		try {
			@SuppressWarnings("resource")
			XSSFWorkbook workbook = new XSSFWorkbook(Paths.get("Datatables", scenario + ".xlsx").toFile());
			XSSFSheet sheet = workbook.getSheet("test");
			evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			Iterator<Row> rows = sheet.rowIterator();
			while (rows.hasNext()) {
				Row row = rows.next();
				if (row.getCell(0).getStringCellValue().equalsIgnoreCase("TD_ID")) continue;
				
				String params ="";
				
				for(Cell cell:row) {
					if(cell.getColumnIndex()<3) continue;
					if (getCellValue(row.getCell(0)).equalsIgnoreCase(testname)
							&& StringUtils.substring(getCellValue(row.getCell(1)), 0, 1).equalsIgnoreCase("Y"))
						params+=getCellValue(cell)+"**";
				}
				params = StringUtils.removeEnd(params, "**");
				if (params.length() > 0)
					paramArr.add(new Param(params));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		datatable.put(key, paramArr);
		return getParamFromList(datatable.get(key));
	}

	private static String getCellValue(Cell cell) {
		CellValue cellValue = evaluator.evaluate(cell);
		String val = "";
		switch (cellValue.getCellTypeEnum()) {
		case STRING:
			val = cellValue.getStringValue().trim();
			break;
		case BLANK:
			val = "";
			break;
		case BOOLEAN:
			val = cellValue.getBooleanValue() ? "true" : "false";
			break;
		case ERROR:
			val = "";
			break;
		case NUMERIC:
			val = String.valueOf(cellValue.getNumberValue());
			break;
		case _NONE:
			val = "";
			break;
		case FORMULA:
			val = "";
			break;
		}
		return val;

	}
	
	private static  Object[][] getParamFromList(List<Object> list){
		Object[][] param =new Object[list.size()][2];
		
		for(int i=0;i<list.size();i++) {
			param[i][0]=list.get(i);
			param[i][1]=i+1;
		}
		
		return param;
	}

}
