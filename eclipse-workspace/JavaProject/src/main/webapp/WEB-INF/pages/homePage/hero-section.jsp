<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Tempest|Home</title>
  <style>
  
    * {
      margin: 0;
      padding: 0;
      
    }

    .hero {
      font-family: "Poppins", Helvetica, sans-serif;
      background-image: url("${pageContext.request.contextPath}/resource/jordon image1.jpg");
      background-repeat: no-repeat;
      background-size: cover;
      filter:brightness(90%);
      height: 90vh;
      
    }


    .content {
      position: relative;
      z-index: 1;
      width: 350px;          
      padding: 130px;         
      color: #fff;
      border-radius: 10px;   
    }

    .content h1 {
      font-size: 48px;       
      margin-bottom: 24px;
    }

    .content p {
      font-size: 18px;       
      margin-bottom: 32px;
      line-height: 1.7;
    }

    .content button {
      padding: 14px 28px;   
      font-size: 16px;
      font-weight: bold;
      border: none;
      border-radius: 6px;
      cursor: pointer;
    }

  </style>

</head>
<body>
  
  <div class="hero">
   <div class="content">
      <h1>The future of sports is here</h1>
      <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
      <a href="${pageContext.request.contextPath}/login">
      <button >Discover Us</button>
      </a>
    </div>
  </div>
</body>
</html>