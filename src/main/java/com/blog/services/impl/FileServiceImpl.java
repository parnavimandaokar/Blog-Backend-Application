package com.blog.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blog.services.FileService;

@Service
public class FileServiceImpl implements FileService{

	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		//get File name
		String name=file.getOriginalFilename();
		
		
		
		//will generate random String
		String randomID=UUID.randomUUID().toString();
		String fileName1=randomID.concat(name.substring(name.lastIndexOf(".")));
		
		
		//full path till file
		//String filePath=path+ File.separator+name;
				String filePath=path+ File.separator+fileName1;
				
		//create images folder if images folder not created
		File f=new File(path);
		
		//check if folder does not exsist
		if(!f.exists()) {
			f.mkdir(); //create folder
		}
		
		//file copy
		
		Files.copy(file.getInputStream(), Paths.get(filePath));
		
	
		return fileName1;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String fullPath=path+File.separator+fileName;
		InputStream is=new FileInputStream(fullPath);
		//db logic to return inputstream
		return is;
	}

}
