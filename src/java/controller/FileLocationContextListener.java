/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author cromero
 */
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class FileLocationContextListener implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        ServletContext ctx = servletContextEvent.getServletContext();
        String path = ctx.getInitParameter("uploadDirectory");
        ctx.setAttribute("FILES_DIR", path);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        //do cleanup if needed
    }
}
