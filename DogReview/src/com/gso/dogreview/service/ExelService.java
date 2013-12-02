package com.gso.dogreview.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.gso.dogreview.R;
import com.gso.dogreview.model.Dog;

public class ExelService {


	public ArrayList<Dog> getFileContent(Context context, String inputFile) {
		AssetManager am = context.getAssets();
		InputStream inputStream;
		ArrayList<Dog> result = new ArrayList<Dog>();
		try {
			inputStream = am.open(inputFile);
			try {

				result = read(inputStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return result;
	}

	public ArrayList<Dog> read(InputStream inputStream)
			throws IOException {
		List<String> resultSet = new ArrayList<String>();
		ArrayList<Dog> result = new ArrayList<Dog>();
		if (inputStream != null) {
			Workbook w;
			try {
				w = Workbook.getWorkbook(inputStream);
				// Get the first sheet
				int sheetCount = w.getNumberOfSheets();
				for (int i = 0; i < sheetCount; i++) {
					Sheet sheet = w.getSheet(i);
					// Loop over column and lines
					
					List<String> idList = getDataColumb(sheet, 0);
					List<String> titleList = getDataColumb(sheet, 1);
					List<String> detailList = getDataColumb(sheet, 2);
						int size = idList.size();
						for (int i2 = 0; i2 < size; i2++) {
							Dog item = new Dog();
							item.setId(idList.get(i2));
							item.setName(titleList.get(i2));
							item.setDescription(detailList.get(i2));
							item.setFavourite(false);
							result.add(item);
						}
				}

			} catch (BiffException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			resultSet.add("File not found..!");
		}
		if (resultSet.size() == 0) {
			resultSet.add("Data not found..!");
		}
		return result;
	}

	private List<String> getDataColumb(Sheet sheet, int i) {
		// TODO Auto-generated method stub
		List<String> resultSet = new ArrayList<String>();
		for (int j = 0; j < sheet.getRows(); j++) {
			Cell cell = sheet.getCell(i, j);
			Log.d("get content ", "content " + cell.getContents());
			
			resultSet.add(cell.getContents());
			continue;
	
		}
		return resultSet;
	}

	private File createFileFromInputStream(InputStream inputStream,
			String fileName) {

		try {
			File f = new File(fileName);
			OutputStream outputStream = new FileOutputStream(f);
			byte buffer[] = new byte[1024];
			int length = 0;

			while ((length = inputStream.read(buffer)) > 0) {
				outputStream.write(buffer, 0, length);
			}

			outputStream.close();
			inputStream.close();

			return f;
		} catch (IOException e) {
			// Logging exception
			e.printStackTrace();
		}

		return null;
	}
}
