package com.padmini.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

@WebServlet("/book")
public class AppointmentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String date = request.getParameter("date");
        String time = request.getParameter("time");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/salondb", "root", "root");
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO appointment (name, phone, date, time) VALUES (?, ?, ?, ?)");
            ps.setString(1, name);
            ps.setString(2, phone);
            ps.setString(3, date);
            ps.setString(4, time);
            ps.executeUpdate();
            con.close();

            out.println("""
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Appointment Confirmed</title>
  <style>
    body {
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100vh;
      margin: 0;
      background: #f0f8ff;
      font-family: Arial, sans-serif;
    }
    .message-box {
      background: white;
      padding: 40px;
      text-align: center;
      border-radius: 10px;
      box-shadow: 0 0 20px rgba(0,0,0,0.1);
    }
    .message-box h1 {
      color: #28a745;
    }
    .btn {
      margin-top: 20px;
      padding: 10px 20px;
      background: #007bff;
      color: white;
      border: none;
      border-radius: 5px;
      text-decoration: none;
      font-size: 16px;
    }
    .btn:hover {
      background: #0056b3;
    }
  </style>
</head>
<body>
  <div class="message-box">
    <h1>Appointment booked successfully!</h1>
    <a href="index.html" class="btn">Back to Home</a>
  </div>
</body>
</html>
""");
        } catch (Exception e) {
            out.println("<h3 style='color:red;'>Something went wrong: " + e.getMessage() + "</h3>");
            e.printStackTrace(out);
        }
    }
}
