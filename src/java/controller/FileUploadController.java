/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;

@ManagedBean(name = "fileUploadController")
public class FileUploadController {

    public void upload(FileUploadEvent event) {
        FacesMessage msg = new FacesMessage("Success! ", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        // Do what you want with the file        
        try {
            copyFile(event.getFile().getInputstream(),(event.getFile().getFileName().split("\\."))[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void copyFile(InputStream in, String extension) {
        try {

            System.out.println("File Name: " + extension);
            FacesContext fc = FacesContext.getCurrentInstance();
            String destination = fc.getExternalContext().getInitParameter("uploadDirectory");
            OutputStream out = new FileOutputStream(new File(destination +getNombreParam(fc)+"."+extension));

            System.out.println("DIR:  "  + destination);
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            in.close();
            out.flush();
            out.close();

            System.out.println("New file created!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getNombreParam(FacesContext fc) {
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        return params.get("nombre");

    }
}
