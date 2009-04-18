package de.winkler.betoffice.google;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

@SuppressWarnings("serial")
public class gbetofficeServlet extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
		throws IOException {
		resp.setContentType("text/html");

		String year = req.getParameter("year").toString();
		String round = req.getParameter("round").toString();

		resp.getWriter().println("<html><body>");
		resp.getWriter().println(
			"Searching for : " + year + ", " + round + "<br/>");

		// http://www.dfb.de/bliga/bundes/archiv/2008/xml/blm_e_23_08.xml

		StringBuffer sb = new StringBuffer(
			"http://www.dfb.de/bliga/bundes/archiv/");
		sb.append(year).append("/xml/blm_e_").append(round).append("_").append(
			year.substring(2, 4)).append(".xml");
		resp.getWriter().println(sb.toString() + "<br/><br/>");
		try {
			URL url = new URL(sb.toString());
			BufferedReader reader = new BufferedReader(new InputStreamReader(
				url.openStream()));

			StringBuffer xml = new StringBuffer();
			String line;
			while ((line = reader.readLine()) != null) {
				xml.append(line);
			}
			Document document = DocumentHelper.parseText(xml.toString());
			List termine = document.selectNodes("//ergx/termin");
			int index = 1;
			for (Object termin : termine) {
				Element terminNode = (Element) termin;
				resp.getWriter().println("Termin " + index + " : "+ terminNode.element("datum").getText() + "<br/>");
				resp.getWriter().println("Heim:" + terminNode.element("teama").getText() + "<br/>");
				resp.getWriter().println("Gast:" + terminNode.element("teamb").getText() + "<br/>");
				resp.getWriter().println("Ergebnis:" + terminNode.element("punkte_a").getText() + ":" + terminNode.element("punkte_b").getText() + "<br/>");
				resp.getWriter().println("<br/>");
				index++;
			}

			resp.getWriter().println();
			resp.getWriter().println("</body></html>");
			reader.close();

		} catch (MalformedURLException ex) {
			throw new RuntimeException(ex);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		} catch (DocumentException ex) {
			throw new RuntimeException(ex);
		}
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws IOException {

		resp.setContentType("text/plain");

		String year = req.getAttribute("year").toString();
		String round = req.getAttribute("round").toString();

		resp.getWriter().println("<html><body>");
		resp.getWriter().println(
			"Searching for : " + year + ", " + round + "<br/>");

		StringBuffer sb = new StringBuffer();
		try {
			URL url = new URL(
				"http://www.dfb.de/bliga/bundes/archiv/2008/xml/blm_e_23_08.xml");
			BufferedReader reader = new BufferedReader(new InputStreamReader(
				url.openStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				resp.getWriter().println(line);
			}
			resp.getWriter().println();
			resp.getWriter().println("</body></html>");
			reader.close();

		} catch (MalformedURLException ex) {
			throw new RuntimeException(ex);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
}

/*
 * Die Daten vom DFB bekommst Du über
 * http://www.dfb.de/bliga/bundes/archiv/<jjjj>/xml/blm_e_<st>_<jj>.xml wobei
 * <jjjj> die Saison mit 4 Stellen, <jj> die Saison mit 2 Stellen und <st> der
 * Spieltag mit 2 Stellen ist, z.B. z.B.
 * http://www.dfb.de/bliga/bundes/archiv/2004/xml/blm_e_01_04.xml Zu einzelnen
 * Spieltagen bekommst Du Infos über
 * http://www.dfb.de/bliga/bundes/archiv/<jjjj>/xml/bl1m_s_<spielid>_<jj>.xml
 * wobei die <spielid> in den XMLs des Spieltags zu finden sind, allerdings in
 * einem etwas anderen Format... z.B.
 * http://www.dfb.de/bliga/bundes/archiv/2004/xml/bl1m_s_2678_04.xml
 */
