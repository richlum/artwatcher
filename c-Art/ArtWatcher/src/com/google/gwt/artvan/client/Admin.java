package com.google.gwt.artvan.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormHandler;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormSubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormSubmitEvent;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class Admin {

	
	
	public Panel createUploadOption() {
	    // Create a FormPanel and point it at a service.
	    final FormPanel form = new FormPanel();
	    form.setAction("/UploadArt");

	    // Because we're going to add a FileUpload widget, we'll need to set the
	    // form to use the POST method, and multipart MIME encoding.
	    form.setEncoding(FormPanel.ENCODING_MULTIPART);
	    form.setMethod(FormPanel.METHOD_POST);

	    // Create a panel to hold all of the form widgets.
	    VerticalPanel panel = new VerticalPanel();
	    form.setWidget(panel);

	   

	    // Create a FileUpload widget.
	    FileUpload upload = new FileUpload();
	    upload.setName("artXML");
	    panel.add(upload);

	    
	    // Add a 'submit' button.
	    panel.add(new Button("Submit", new ClickListener() {
	      public void onClick(Widget sender) {
	        form.submit();
	      }
	    }));

	    // Add an event handler to the form.
	    form.addFormHandler(new FormHandler() {
	      public void onSubmit(FormSubmitEvent event) {
	        // This event is fired just before the form is submitted. We can take
	        // this opportunity to perform validation.
	    	  
	      }

	      public void onSubmitComplete(FormSubmitCompleteEvent event) {
	        // When the form submission is successfully completed, this event is
	        // fired. Assuming the service returned a response of type text/html,
	        // we can get the result text here (see the FormPanel documentation for
	        // further explanation).
	        Window.alert(event.getResults());
	      }
	    });

	   return form;
	  }
}