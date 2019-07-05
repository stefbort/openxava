package org.openxava.web.servlets;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.commons.logging.*;
import org.openxava.controller.*;
import org.openxava.util.*;
import org.openxava.view.*;
import org.openxava.web.*;


/**
 * @author Javier Paniza
 */

public class ImagesServlet extends HttpServlet {
	
	private static Log log = LogFactory.getLog(ImagesServlet.class);
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("[ImagesServlet.doGet] "); // tmp
		try {
			String propertyKey = Ids.undecorate(request.getParameter( "property" ));
			View view = getCurrentView( request, propertyKey );
			byte [] image = (byte []) view.getValue(propertyKey); 
			if (image != null) {
				response.setContentType("image");
				response.getOutputStream().write(image);
			}
		}
		catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new ServletException(XavaResources.getString("image_error"));
		}		
	}
	
	private View getCurrentView( HttpServletRequest request, String propertyKey) {  		 
		ModuleContext context = (ModuleContext) request.getSession().getAttribute("context"); 
		return (View) context.get(request, "xava_view");
	}
		
}
