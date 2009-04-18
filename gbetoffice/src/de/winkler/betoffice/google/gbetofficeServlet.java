package de.winkler.betoffice.google;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class gbetofficeServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		throws IOException {

		resp.setContentType("text/plain");
		StringBuffer sb = new StringBuffer();
		try {
			URL url = new URL("http://www.dfb.de/bliga/bundes/archiv/2008/xml/blm_e_23_08.xml");
			BufferedReader reader = new BufferedReader(new InputStreamReader(
				url.openStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				resp.getWriter().println(line);
			}
			reader.close();

		} catch (MalformedURLException e) {
			// ...
		} catch (IOException e) {
			// ...
		}

		resp.getWriter().println("Hello, world");
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
