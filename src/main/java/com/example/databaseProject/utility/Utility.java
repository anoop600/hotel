package com.example.databaseProject.utility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Utility {

	public static Integer generateId(String typeOfModel) {

		Integer count = 0;
		int index=0;
		if(typeOfModel.equals("hotel")) {
			index=0;
		}else if(typeOfModel.equals("customer")) {
			index=1;
		}
		try (FileInputStream fis = new FileInputStream("D:\\ProjectWorkspace\\DatabaseDesign2\\count.txt");
				Scanner input = new Scanner(fis);) {
			if (input.hasNextLine()) {
				String idArray[] = input.nextLine().split(",");
				count = Integer.parseInt(idArray[index]);
				count++;
				idArray[index]=count.toString();
				System.out.println("updated hotel id"+idArray[index]);
				Integer hotelId=Integer.parseInt(idArray[0]);
				Integer customerId=Integer.parseInt(idArray[1]);
				String str=hotelId.toString()+","+customerId.toString();
				System.out.println(str);
				addCountToFile(str);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return count;

	}

	public static void addCountToFile(String toWriteInFile ) throws IOException {
		FileOutputStream fos = new FileOutputStream("D:\\ProjectWorkspace\\DatabaseDesign2\\count.txt");
		System.out.println("what it is writing in file"+toWriteInFile);
		fos.write(toWriteInFile.getBytes());
		fos.close();
	}
}
