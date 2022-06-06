package com.example.clase11iweb.daos;


import com.example.clase11iweb.beans.BJob;

import java.sql.*;
import java.util.ArrayList;

public class JobDao {

    public ArrayList<BJob> listarTrabajos() {
        ArrayList<BJob> listaTrabajos = new ArrayList<>();

        String user = "root";
        String pass = "root";
        String url = "jdbc:mysql://localhost:3306/hr";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try (Connection connection = DriverManager.getConnection(url, user, pass);
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("select * from jobs");) {

            while (rs.next()) {
                BJob bJob = new BJob();
                bJob.setJobId(rs.getString(1));
                bJob.setJobTitle(rs.getString(2));
                bJob.setMinSalary(rs.getInt(3));
                bJob.setMaxSalary(rs.getInt(4));
                listaTrabajos.add(bJob);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listaTrabajos;
    }

    public void crearTrabajo(String jobId, String jobTitle, int minSalary, int maxSalary) {

        String user = "root";
        String pass = "root";
        String url = "jdbc:mysql://localhost:3306/hr";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String sql = "INSERT INTO jobs (job_id, job_title, min_salary, max_salary) VALUES (?,?,?,?)";

        try (Connection connection = DriverManager.getConnection(url, user, pass);
             PreparedStatement pstmt = connection.prepareStatement(sql);) {

            pstmt.setString(1, jobId);
            pstmt.setString(2, jobTitle);
            pstmt.setInt(3, minSalary);
            pstmt.setInt(4, maxSalary);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void borrar(String jobId) {

        String user = "root";
        String pass = "root";
        String url = "jdbc:mysql://localhost:3306/hr";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String sql = "delete from jobs where job_id = ?";

        try (Connection connection = DriverManager.getConnection(url, user, pass);
             PreparedStatement pstmt = connection.prepareStatement(sql);) {

            pstmt.setString(1, jobId);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public BJob buscarPorId(String id) {
        BJob bJob = null;

        String user = "root";
        String pass = "root";
        String url = "jdbc:mysql://localhost:3306/hr";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String sql = "select * from jobs where job_id = ?";

        try (Connection connection = DriverManager.getConnection(url, user, pass);
             PreparedStatement pstmt = connection.prepareStatement(sql);) {

            pstmt.setString(1, id);

            try (ResultSet rs = pstmt.executeQuery();) {

                if (rs.next()) {
                    bJob = new BJob();
                    bJob.setJobId(rs.getString(1));
                    bJob.setJobTitle(rs.getString(2));
                    bJob.setMinSalary(rs.getInt(3));
                    bJob.setMaxSalary(rs.getInt(4));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return bJob;
    }

    public void actualizar(BJob job) {

        String user = "root";
        String pass = "root";
        String url = "jdbc:mysql://localhost:3306/hr";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String sql = "UPDATE jobs SET job_title = ?, min_salary = ?, max_salary = ? where job_id = ?";

        try (Connection connection = DriverManager.getConnection(url, user, pass);
             PreparedStatement pstmt = connection.prepareStatement(sql);) {

            pstmt.setString(1, job.getJobTitle());
            pstmt.setInt(2, job.getMinSalary());
            pstmt.setInt(3, job.getMaxSalary());
            pstmt.setString(4, job.getJobId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public ArrayList<BJob> buscarPorIdOrTitulo(String textoBuscar) {
        ArrayList<BJob> listaTrabajos = new ArrayList<>();

        String user = "root";
        String pass = "root";
        String url = "jdbc:mysql://localhost:3306/hr";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        String sql = "select * from jobs where lower(job_id) like ? or lower(job_title) like ?";

        try (Connection connection = DriverManager.getConnection(url, user, pass);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

            preparedStatement.setString(1, "%" + textoBuscar.toLowerCase() + "%");
            preparedStatement.setString(2, "%" + textoBuscar.toLowerCase() + "%");

            try (ResultSet rs = preparedStatement.executeQuery();) {
                while (rs.next()) {
                    BJob bJob = new BJob();
                    bJob.setJobId(rs.getString(1));
                    bJob.setJobTitle(rs.getString(2));
                    bJob.setMinSalary(rs.getInt(3));
                    bJob.setMaxSalary(rs.getInt(4));
                    listaTrabajos.add(bJob);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listaTrabajos;
    }

}
