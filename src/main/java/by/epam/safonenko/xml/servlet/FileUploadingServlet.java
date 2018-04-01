package by.epam.safonenko.xml.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

@WebServlet("/FileUploadServlet")
@MultipartConfig(fileSizeThreshold=1024*1024*10,
                maxFileSize=1024*1024*50,
                maxRequestSize=1024*1024*100)
public class FileUploadingServlet extends HttpServlet {
    private static final String UPLOAD_DIR = "uploads";
    private static final String RESPONSE_PAGE = "/pages/response.jsp";
    private static final String MESSAGE = "message";
    private static final String CONTENT_HEADER = "content-disposition";
    private static final String FILENAME = "filename";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uploadFilePath = new File(".").getCanonicalPath() + File.separator + UPLOAD_DIR;

        File fileSaveDir = new File(uploadFilePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdirs();
        }

        String fileName = null;
        for (Part part : request.getParts()) {
            fileName = getFileName(part);
            part.write(uploadFilePath + File.separator + fileName);
        }
        request.getSession().setAttribute(ServletUtil.FILE, uploadFilePath + File.separator + fileName);
        request.setAttribute(MESSAGE, fileName + " файл успешно загружен!");
        getServletContext().getRequestDispatcher(RESPONSE_PAGE).forward(request, response);
    }

    private String getFileName(Part part) {
        String content = part.getHeader(CONTENT_HEADER);
        String[] parts = content.split(";");
        for (String current : parts) {
            if (current.trim().startsWith(FILENAME)) {
                return current.substring(current.indexOf("=") + 2, current.length()-1);
            }
        }
        return "";
    }
}