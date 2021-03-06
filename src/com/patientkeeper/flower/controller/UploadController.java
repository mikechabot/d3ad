package com.patientkeeper.flower.controller;

import java.io.File;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import org.apache.log4j.Logger;

import com.patientkeeper.flower.Controller;
import com.patientkeeper.flower.service.UploadService;

public class UploadController extends Controller {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected String basePath() { return ""; }	
	
	@Override
	protected void initActions() {
		addAction(null, new PostAction());
	}

	@Override
	protected Action defaultAction() {
		return new PostAction();
	}
	
	public class PostAction implements Action {
		public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
			
	        File file = null;
	        String filename = null;
	        String output = null;
	        		
	        try {
		        List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
		        for (FileItem item : items) {
		            if (!item.isFormField()) {
		            	file = new File(item.getName());
		            	filename = file.getName();
		            	file = File.createTempFile(file.getName(), ".tmp");
		            	item.write(file);
		            } else {
		            	if(item.getFieldName().equals("output")) output = item.getString();
		            }
		        }
		    } catch (FileUploadException e) {
		        throw new ServletException("Cannot parse multipart request", e);
		    }
			
	        UploadService upload = new UploadService();
	        String json = null;
	        if(output.equals("flower") || output.equals("bubble") || output.equals("stack")) {
	        	json = upload.getJson(file, true);
	        } else if(output.equals("tree") || output.equals("reingold")) {
	        	json = upload.getJson(file);
	        }  

	        request.setAttribute("json", json);
	        request.setAttribute("filename", filename);
			
			return basePath() + "/" + output + ".jsp";			
		}		
	}
}