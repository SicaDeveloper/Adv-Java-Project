<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>footer</title>
    <style>

        body{
            margin:0;
            padding: 0;
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            
        }

        .footer{
            background-color: ;
            text-align: center;
            background-color: #fff;
            width: 100%;
            position: fixed;
            bottom: 0;
            
            padding-top: 80px;

        }

        .usefull-links{
            
            display: flex;
            justify-content: space-between;
            align-items: center;
            font-size: 16px;
            font-weight:bold;
            
            margin: 0 auto;
            max-width: 60%;
        }

        .usefull-links a{
            text-decoration: none;
            color: #000;
            margin: 0 15px;
            text-align: center;
        }

        .trade{
            font-weight: 600;
            padding: 0 40px;
            font-size: 24px;
        }

        .footer-icons img{
            height: 3.2vh;
            margin: 0 10px;
            padding: 5px;
            
        }

        .footer-icons img:hover{
            background-color: #f0f0f0;
            border-radius: 20%;
        }

        .footer-icons{
            margin: 20px 0;
            display: flex;
            justify-content: center;
            align-items: center;
            
            
        }

        .line{
            width: 65%;
            height: 1.2px; 
            background-color: #000;
            margin: 30px auto; 
        }

        .copyright{
            color: #000;
            font-size: 14px;
        }
    </style>
</head>
<body>
    
    <footer class="footer">
        <div class="usefull-links">

            <a href="#">Product</a>
            <a href="#">Feature</a>
            <a href="#">Resource</a>
            <a href="#" class="trade">Trade</a>
            <a href="#">About</a>
            <a href="#">Blog</a>
            <a href="#">Support</a>

        </div>

        <div class="line"></div>

        <div class="footer-icons">
            <a href="#"><img src="/resource/instagram_2111463.png" alt="Instagram"></a>
            <a href="#"><img src="/resource/twitter_5969020.png" alt="X"></a>
            <a href="#"><img src="/resource/facebook_5968764.png" alt="Facebook"></a>
            <a href="#"><img src="/resource/youtube_4494485.png" alt="Youtube"></a>
            <a href="#"><img src="/resource/whatsapp_733585.png" alt="Whatsapp"></a>
        </div>
        <div class="copyright">
            <p>&copy; 2010 &ndash; 2020 Privacy &ndash; Terms</p>
        </div>
    </footer>

</body>
</html>