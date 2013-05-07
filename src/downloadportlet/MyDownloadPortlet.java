package downloadportlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.apache.commons.io.FileUtils;

public class MyDownloadPortlet extends GenericPortlet {
	private String viewPage, helpPage, editPage;

	public MyDownloadPortlet() {
		viewPage = "/view.jsp";
		helpPage = "/help.jsp";
		editPage = "/edit.jsp";
	}

	@Override
	protected void doView(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {
		String logpath = request.getPreferences().getValue("logpath",
				System.getenv("OPENSHIFT_JBOSSAS_LOG_DIR"));
		request.setAttribute("logpath", logpath);
		File logdir = new File(logpath);
		File[] files = logdir.listFiles();
		List<LogFileObject> fileObjects = new ArrayList<LogFileObject>();
		for (File file : files) {
			if (file.isFile()) {
				LogFileObject logFileObject = new LogFileObject(file);
				fileObjects.add(logFileObject);
			}
		}
		request.setAttribute("fileObjects", fileObjects);
		//
		response.setContentType(request.getResponseContentType());
		PortletRequestDispatcher dispatcher = this.getPortletContext()
				.getRequestDispatcher(viewPage);
		dispatcher.include(request, response);
	}

	@Override
	protected void doEdit(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {
		String logpath = request.getPreferences().getValue("logpath",
				System.getenv("OPENSHIFT_JBOSSAS_LOG_DIR"));
		request.setAttribute("logpath", logpath);
		//
		response.setContentType(request.getResponseContentType());
		PortletRequestDispatcher dispatcher = this.getPortletContext()
				.getRequestDispatcher(editPage);
		dispatcher.include(request, response);
	}

	@Override
	protected void doHelp(RenderRequest request, RenderResponse response)
			throws PortletException, IOException {
		// 環境変数を取得する
		Map<String, String> map = System.getenv();
		List<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>(
				map.entrySet());
		request.setAttribute("map", map);
		request.setAttribute("list", list);
		//
		response.setContentType(request.getResponseContentType());
		PortletRequestDispatcher dispatcher = this.getPortletContext()
				.getRequestDispatcher(helpPage);
		dispatcher.include(request, response);
	}

	@Override
	public void processAction(ActionRequest request, ActionResponse response)
			throws PortletException, IOException {
		String prefix = request.getParameter("prefix");
		String logpath = request.getParameter("logpath");
		PortletPreferences prefs = request.getPreferences();
		if (prefix.equals("path"))
			prefs.setValue("logpath", logpath);
		if (prefix.equals("env"))
			prefs.setValue("logpath", System.getenv(logpath));
		prefs.store();
	}

	@Override
	public void serveResource(ResourceRequest request, ResourceResponse response)
			throws PortletException, IOException {
		String filepath = request.getParameter("filepath");
		if (filepath != null) {
			File logfile = new File(filepath);
			byte[] buf = FileUtils.readFileToByteArray(logfile);
			response.setContentType("application/octet-stream");
			response.addProperty("Content-Disposition", "attachment;filename="
					+ logfile.getName());
			response.getPortletOutputStream().write(buf);
			response.getPortletOutputStream().flush();
			response.getPortletOutputStream().close();
		}
	}

}
