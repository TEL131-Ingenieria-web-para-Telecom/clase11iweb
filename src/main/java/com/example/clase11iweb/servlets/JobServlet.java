package com.example.clase11iweb.servlets;

import com.example.clase11iweb.beans.BJob;
import com.example.clase11iweb.daos.JobDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "JobServlet", value = "/JobServlet")
public class JobServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("a") == null ? "listar" : request.getParameter("a");
        JobDao jobDao = new JobDao();

        switch (action) {
            case "listar" -> {
                request.setAttribute("listaTrabajos", jobDao.listarTrabajos());

                RequestDispatcher requestDispatcher = request.getRequestDispatcher("jobs/lista.jsp");
                requestDispatcher.forward(request, response);
            }
            case "crear" -> {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher("jobs/nuevo.jsp");
                requestDispatcher.forward(request, response);
            }
            case "borrar" -> {
                String id = request.getParameter("id");
                jobDao.borrar(id);
                response.sendRedirect(request.getContextPath() + "/JobServlet");
            }
            case "editar" -> {
                String id = request.getParameter("id");
                BJob bJob = jobDao.buscarPorId(id);
                if (bJob != null) {
                    request.setAttribute("bJob", bJob);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("jobs/editar.jsp");
                    requestDispatcher.forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/JobServlet");
                }
            }
        }


    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("a") == null ? "listar" : request.getParameter("a");
        JobDao jobDao = new JobDao();

        switch (action) {
            case "guardar" -> {
                String jobId = request.getParameter("jobId");
                String jobTitle = request.getParameter("jobTitle");
                String minSalaryStr = request.getParameter("minSalary");
                String maxSalaryStr = request.getParameter("maxSalary");

                try {
                    int minSalary = Integer.parseInt(minSalaryStr);
                    int maxSalary = Integer.parseInt(maxSalaryStr);

                    jobDao.crearTrabajo(jobId, jobTitle, minSalary, maxSalary);

                    response.sendRedirect(request.getContextPath() + "/JobServlet");

                } catch (NumberFormatException e) {
                    System.out.println("error al parsear");
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("jobs/nuevo.jsp");
                    requestDispatcher.forward(request, response);
                }

            }
            case "actualizar" -> {
                BJob bJob = leerParametrosRequest(request);
                jobDao.actualizar(bJob);
                response.sendRedirect(request.getContextPath() + "/JobServlet");
            }
            case "buscar" -> {
                String textoBuscar = request.getParameter("textoBuscar");
                request.setAttribute("textoBuscar",textoBuscar);
                request.setAttribute("listaTrabajos", jobDao.buscarPorIdOrTitulo(textoBuscar));

                RequestDispatcher requestDispatcher = request.getRequestDispatcher("jobs/lista.jsp");
                requestDispatcher.forward(request, response);
            }
        }
    }

    public BJob leerParametrosRequest(HttpServletRequest request) {
        String jobId = request.getParameter("jobId");
        String jobTitle = request.getParameter("jobTitle");
        String minSalaryStr = request.getParameter("minSalary");
        String maxSalaryStr = request.getParameter("maxSalary");

        BJob bJob = new BJob();
        bJob.setJobId(jobId);
        bJob.setJobTitle(jobTitle);
        bJob.setMinSalary(Integer.parseInt(minSalaryStr));
        bJob.setMaxSalary(Integer.parseInt(maxSalaryStr));
        return bJob;
    }

}
