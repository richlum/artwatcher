package com.google.gwt.artvan.server;





import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.gwt.user.client.ui.FileUpload;


public class UploadArtServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    private static final Logger log =
    	      Logger.getLogger(FileUpload.class.getName());
    
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
    		super.doGet(request, response);

    }
    
    @SuppressWarnings("deprecation")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
    	    throws IOException, ServletException {

    	 try {
    	      ServletFileUpload upload = new ServletFileUpload();
    	      response.setContentType("text/plain");

    	      FileItemIterator iterator = upload.getItemIterator(request);
    	      while (iterator.hasNext()) {
    	        FileItemStream item = iterator.next();
    	        InputStream stream = item.openStream();

    	        if (item.isFormField()) {
    	          log.warning("Got a form field: " + item.getFieldName());
    	        } else {
    	          log.warning("Got an uploaded file: " + item.getFieldName() +
    	                      ", name = " + item.getName());
    	          
    	          new XMLHandler(stream);
    	          response.getOutputStream().println("Upload Success!");
    	        }
    	      }
    	    } catch (Exception ex) {
    	      throw new ServletException(ex);
    	    }
    }
}