package com.gso.dogreview.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.gso.dogreview.R;
import com.gso.dogreview.model.Comment;
import com.gso.dogreview.model.Dog;

public class ExelService {


	public HashMap<String,Object> getFileContent(Context context, String inputFile) {
		AssetManager am = context.getAssets();
		InputStream inputStream;
		HashMap<String, Object> result = new HashMap<String, Object>();
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

	public HashMap<String,Object> read(InputStream inputStream)
			throws IOException {
		List<String> resultSet = new ArrayList<String>();
		HashMap<String, Object> result = new HashMap<String, Object>();
		ArrayList<Dog> dogList = new ArrayList<Dog>(); 
		ArrayList<Comment> commentList = new ArrayList<Comment>();
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
					List<String> dogImageIdList = getDataColumb(sheet, 1);
					List<String> titleImageList = getDataColumb(sheet, 2);
					List<String> titleList = getDataColumb(sheet, 3);
					List<String> detailList = getDataColumb(sheet, 4);
					List<String> commentAvatarNaList = getDataColumb(sheet, 5);
					List<String> commentContenNatList = getDataColumb(sheet, 6);
					List<String> commentAvatarDogList = getDataColumb(sheet, 7);
					List<String> commentContentDogList = getDataColumb(sheet, 8);
					List<String> commentAvatarNaList2 = getDataColumb(sheet, 9);
					List<String> commentContentNaList2 = getDataColumb(sheet, 10);
					List<String> commentAvatarDogList2 = getDataColumb(sheet, 11);
					List<String> commentContentDogList2 = getDataColumb(sheet, 12);
					
					int size = idList.size();
					for (int i2 = 0; i2 < size; i2++) {
						try {
							Dog item = new Dog();
							item.setId(idList.get(i2));
							item.setName(titleList.get(i2));
							item.setDescription(detailList.get(i2));
							item.setFavourite(false);
							dogList.add(item);
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
						
						try {
							Comment item = new Comment();
							item.setDogId(idList.get(i2));
							item.setComment(commentContenNatList.get(i2));
							item.setAvatar(commentAvatarNaList.get(i2));
							
							commentList.add(item);
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
						
						try {
							Comment item = new Comment();
							item.setDogId(idList.get(i2));
							item.setComment(commentContentDogList.get(i2));
							item.setAvatar(commentAvatarDogList.get(i2));
							
							commentList.add(item);
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}		
						
						try {
							Comment item = new Comment();
							item.setDogId(idList.get(i2));
							item.setComment(commentContentNaList2.get(i2));
							item.setAvatar(commentAvatarNaList2.get(i2));
							
							commentList.add(item);
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}	
						
						try {
							Comment item = new Comment();
							item.setDogId(idList.get(i2));
							item.setComment(commentContentDogList2.get(i2));
							item.setAvatar(commentAvatarDogList2.get(i2));
							
							commentList.add(item);
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}	
					}
					
					result.put("dog_list", dogList);
					result.put("comment_list", commentList);
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
