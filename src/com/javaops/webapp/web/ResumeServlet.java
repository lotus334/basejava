package com.javaops.webapp.web;

import com.javaops.webapp.Config;
import com.javaops.webapp.model.Resume;
import com.javaops.webapp.storage.Storage;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ResumeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-Type", "text/html; charset=UTF-8");
        response.setContentType("text/html; charset=UTF-8");
//        String name = request.getParameter("name");
//        response.getWriter().write(name == null ? "Hello Resumes!" : "Hello " + name + '!');
        PrintWriter out = response.getWriter();
        Storage storage = Config.get().getStorage();
        List<Resume> resumes = storage.getAllSorted();
        if (resumes.size() > 0) {
            out.write(String.valueOf(storage.getAllSorted().get(0)));
        } else {
            out.write("Excuse me, but storage is empty");
        }
    }
}
