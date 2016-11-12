package com.hbs.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class UnitTest {

	public static void main(String[] args) {
		String one = "नमस्ते";
		String two = null;
		
		File f = new File("C:\\users\\anshulmathur\\Desktop\\Hindi.txt");
		try {
			//FileInputStream in = new FileInputStream(f);
			BufferedReader b = new BufferedReader(new FileReader(f));
			String line = null;
			while((line = b.readLine())!=null){
				System.out.println(" "+line+"l");
//				if(line.equals(one)){
//					System.out.println("yes");
//				
//				}else{
//					System.out.println("no");
//				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
