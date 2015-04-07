package edu.upenn.cis455.mapreduce.master;

import java.io.*;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.*;
import javax.servlet.http.*;

public class MasterServlet extends HttpServlet {

	static final long serialVersionUID = 455555001;
	HashMap<String, String[]> workerInfo = new HashMap<String, String[]>();
	HashMap<String, Long> lastWorkerUpdate = new HashMap<String, Long>();
	Date currentDate = new Date();

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws java.io.IOException {
		// code from original file
//		response.setContentType("text/html");
//		PrintWriter out = response.getWriter();
//		out.println("<html><head><title>Master</title></head>");
//		out.println("<body>Hi, I am the master!</body></html>");

		String reqPath = request.getPathInfo();
		System.out.println(reqPath);
		// parsing info sent by worker
		if (reqPath.equalsIgnoreCase("/workerstatus")) {
			String[] workerUpdate = request.getQueryString().split("&");
			// if worker's provided information isn't adequate
			if (workerUpdate.length != 5) {
				return;
			}

			// updating or adding the worker's information
			String portNo = workerUpdate[0].split("=")[1];
			if (!workerInfo.containsKey(portNo)) {
				workerInfo.put(portNo, workerUpdate);
				lastWorkerUpdate.put(portNo, currentDate.getTime());
			} else {
				workerInfo.remove(portNo);
				workerInfo.put(portNo, workerUpdate);
				lastWorkerUpdate.put(portNo, currentDate.getTime());
			}
		
		// sending interface to user
		} else if (reqPath.equalsIgnoreCase("/status")) {
			
			// add form to submit MapReduce jobs
			appendFile(response, "workspace/HW2/WebContent/WEB-INF/status1.html");
			
			// add details about currently running workers
			
			// end status page
			appendFile(response, "workspace/HW2/WebContent/WEB-INF/status2.html");
		}
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		String reqPath = request.getPathInfo();
		
		// form submitted by user
		if (reqPath.equalsIgnoreCase("/status")) {
			
		}
	}
	
	/**
	 * Appends a named file to the given HttpServletResponse object
	 * @param response - response to append the file to
	 * @param filename - name of the file to append to the HttpServletResponse
	 */
	private void appendFile(HttpServletResponse response, String filename) {
		// Displaying home interface
		File index = new File(filename);
		FileInputStream fileInput = null;
		try {
			fileInput = new FileInputStream(index);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int content;
		try {
			while ((content = fileInput.read()) != -1) {
				response.getOutputStream().write(content);
			}
			fileInput.close(); // Closing the file stream
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}

//		  String portNo = workerUpdate[0].split("=")[1];
//		  String status = workerUpdate[1].split("=")[1];
//		  String job = workerUpdate[2].split("=")[1];
//		  String keysRead = workerUpdate[3].split("=")[1];
//		  String keysWritten = workerUpdate[4].split("=")[1];

  
