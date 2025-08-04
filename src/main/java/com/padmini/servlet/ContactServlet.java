package com.padmini.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

@WebServlet("/contact")
public class ContactServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String message = request.getParameter("message");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/salondb", "root", "root");
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO contact (name, phone, email, message) VALUES (?, ?, ?, ?)");
            ps.setString(1, name);
            ps.setString(2, phone);
            ps.setString(3, email);
            ps.setString(4, message);
            ps.executeUpdate();
            con.close();

            out.println("""
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Message Sent</title>
  <style>
    body {
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100vh;
      margin: 0;
      background: #f0f0f0;
      font-family: Arial, sans-serif;
    }
    .message-box {
      background: #fff;
      padding: 40px;
      text-align: center;
      border-radius: 10px;
      box-shadow: 0 0 20px rgba(0,0,0,0.1);
    }
    .message-box h1 {
      color: #17a2b8;
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
    <h1>Message sent successfully!</h1>
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
