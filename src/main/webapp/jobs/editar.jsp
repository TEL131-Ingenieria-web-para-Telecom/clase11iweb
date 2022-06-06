<%@page contentType="text/html" pageEncoding="UTF-8" %>
<jsp:useBean id="bJob" scope="request" type="com.example.clase11iweb.beans.BJob" />
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-0evHe/X+R7YkIZDRvuzKMRqM+OrBnVFBL6DOitfPri4tjfHxaWutUpFmBp4vmVor"
              crossorigin="anonymous">
        <title>Editar trabajo</title>
    </head>
    <body>
        <div class='container'>
            <h1 class='mb-3'>Editar trabajo</h1>
            <form method="POST" action="<%=request.getContextPath()%>/JobServlet?a=actualizar">
                <input type="hidden" name="jobId" value="<%=bJob.getJobId()%>" />
                <div class="mb-3">
                    <label for="jobTitle" class="form-label">Job Title</label>
                    <input type="text" class="form-control" name="jobTitle" id="jobTitle" value="<%=bJob.getJobTitle()%>">
                </div>
                <div class="mb-3">
                    <label for="minSalary" class="form-label">Min Salary</label>
                    <input type="text" class="form-control" name="minSalary" id="minSalary" value="<%=bJob.getMinSalary()%>">
                </div>
                <div class="mb-3">
                    <label for="maxSalary" class="form-label">Max Salary</label>
                    <input type="text" class="form-control" name="maxSalary" id="maxSalary" value="<%=bJob.getMaxSalary()%>">
                </div>
                <a href="<%=request.getContextPath()%>/JobServlet" class="btn btn-danger">Regresar</a>
                <button type="submit" class="btn btn-primary">Actualizar Trabajo</button>
            </form>
        </div>
    </body>
</html>




