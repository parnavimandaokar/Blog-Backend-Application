package com.blog.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;


public interface FileService {
	//method to upload data on particular path and get the data in file
		 String uploadImage(String path,MultipartFile file) throws IOException;
		 
		 InputStream getResource(String path,String fileName) throws FileNotFoundException;
	}