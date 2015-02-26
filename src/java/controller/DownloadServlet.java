/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.WildcardFileFilter;

/**
 *
 * @author cromero
 */
public class DownloadServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {

            String fileName = request.getParameter("nombre");
            if (fileName == null || fileName.equals("")) {
                throw new ServletException("File Name can't be null or empty");
            }

            
            //ServletContext ctx = getServletContext();

            String destination = (String) request.getServletContext().getAttribute("FILES_DIR");
            //"E:/tmp/";//FacesContext.getCurrentInstance().getExternalContext().getInitParameter("uploadDirectory");

            
            File dir = new File(destination + File.separator);
            FileFilter fileFilter = new WildcardFileFilter(fileName+".*");
            File[] files = dir.listFiles(fileFilter);
            for(int i = 0; i < files.length; i++) {
                System.out.println("Archivo 1: " + files[i].getName());
                System.out.println("Archivo 1.1: " + FilenameUtils.getBaseName(files[i].getName()));
                if(FilenameUtils.getBaseName(files[i].getName()).compareTo(fileName) == 0){
                    fileName = files[i].getName();
                    break;
                }
            }
            
            
            System.out.println("Archivo 2 :" + fileName);
            File file = new File(destination + File.separator + fileName);

            if (!file.exists()) {
                throw new ServletException("File doesn't exists on server.");
            }
            System.out.println("HOLA6");
            System.out.println("File location on server:" + file.getAbsolutePath());

            System.out.println("HOLA7");
            InputStream fis = new FileInputStream(file);
            ServletOutputStream os = response.getOutputStream();
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setHeader("Content-Disposition", "attachment;filename="+fileName);
            response.setContentType("application/pdf");
            response.setContentLength((int) file.length());
            System.out.println("HOLA8");
            byte[] bufferData = new byte[1024];
            int read = 0;
            while ((read = fis.read(bufferData)) != -1) {
                os.write(bufferData, 0, read);
            }
            os.flush();
            os.close();
            fis.close();

        } finally {

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
