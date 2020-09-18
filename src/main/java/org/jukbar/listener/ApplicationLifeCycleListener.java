package org.jukbar.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.jukbar.singleton.Configuration;
import org.jukbar.util.MailSender;

/***
 * 
 * @author Kuttubek Aidaraliev
 *
 */

public class ApplicationLifeCycleListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent arg0) {
		MailSender.destroy();
	}

	public void contextInitialized(ServletContextEvent arg0) {
		Configuration.getInstance();
	}

}
