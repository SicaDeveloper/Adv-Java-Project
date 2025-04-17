<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>nav</title>
    <style>
        body{
            margin: 0;
            font-family: Arial, sans-serif;
        }
        .navbar{
            display: flex;
            align-items: center;
            justify-content: space-between;
            background-color: black;
            padding: 10px 20px;
        }

        .logo img{
            height: 3vh;
            
        }
 
        .navlinks a{
            text-decoration: none;
            padding:0 10px 0 10px; 
            color: #fff;
            
        }

        

        .nav-right a{
                    text-decoration: none;
                    padding: 5px;
                    color: #fff;
                }

        .nav-icons a:hover{
            background-color: #272727;
            border-radius: 20%;
            
        }  
        
       


    </style>
</head>
<body>

    <div class="navbar">
        <div class="logo">
            <a href="#"><img src="/resource/tempest-high-resolution-logo-transparent.png" alt="Tempest"></a>
        </div>

        <div class="navlinks">
            <a href="#">Home</a>
            <a href="#">Link</a>
            <a href="#">Link</a>
            <a href="#">Link</a>
        </div>

        <div class="nav-right">
            <a href="#">Search</a>
        <span class="nav-icons">   
            <a href="#">ð</a>
            <a href="#">âï¸</a>
            <a href="#">ð¤</a>
        </span> 
        </div>

    </div>

</body>
</html>