<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Customer Profile - E-Commerce</title>
  <style>
    body {
      background: url('<%= request.getContextPath() %>/css/hoop.jpg') no-repeat center center fixed;
      background-size: cover;
      font-family: Arial, sans-serif;
      margin: 0;
      padding: 0;
      color: #333;
    }
    .profile-container {
      max-width: 900px; /* Increased width */
      margin: 40px auto;
      background-color: rgba(255, 255, 255, 0.95);
      border: 1px solid #ddd;
      border-radius: 8px;
      box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
      position: relative;
      padding: 30px; /* Added more padding */
    }
    .edit-profile-btn {
      position: absolute;
      top: 20px;
      right: 20px;
      background-color: #000;
      color: #fff;
      border: none;
      padding: 10px 16px;
      border-radius: 6px;
      cursor: pointer;
      font-size: 14px;
    }
    .edit-profile-btn:hover {
      background-color: #333;
    }
    .profile-header {
      display: flex;
      align-items: center;
      justify-content: space-between; /* Balanced space for image and text */
      padding: 20px;
      border-bottom: 1px solid #eee;
    }
    .profile-photo {
      width: 180px; /* Increased size */
      height: 180px;
      border-radius: 50%; /* Perfect circle */
      background-color: #ccc;
      background-size: cover;
      background-position: center;
      margin-right: 40px; /* More space */
      border: 5px solid #fff;
      flex-shrink: 0; /* Prevent squeezing */
    }
    .profile-info {
      flex-grow: 1; /* Gives text enough space */
    }
    .profile-info h1 {
      font-size: 26px;
      margin: 0;
      color: #000;
    }
    .profile-info p {
      font-size: 18px;
      color: #555;
      margin: 5px 0;
    }
    .profile-bio {
      margin-top: 15px;
      padding: 12px;
      background-color: #f9f9f9;
      border-radius: 8px;
      font-size: 16px;
      color: #444;
    }
    .profile-content {
      padding: 20px;
    }
    .profile-section {
      margin-bottom: 25px;
    }
    .profile-section h2 {
      font-size: 22px;
      color: #000;
      margin-bottom: 10px;
    }
  </style>
</head>
<body>
  <div class="profile-container">
    <button class="edit-profile-btn" onclick="window.location.href='editProfile.jsp'">Edit Profile</button>

    <div class="profile-header">
      <div class="profile-photo" style="background-image: url('placeholder.png');"></div>
      <div class="profile-info">
        <h1>Ishan Giri</h1>
        <p>Kakarvitta, Nepal</p>
        <div class="profile-bio">
          <p><strong>Bio:</strong> Passionate about e-commerce, technology, and innovation. Always exploring new opportunities to grow and improve!</p>
        </div>
      </div>
    </div>
    
    <div class="profile-content">
      <div class="profile-section">
        <h2>Contact Information</h2>
        <p><strong>Phone:</strong> +977 9826933353</p>
        <p><strong>Email:</strong> ishangiri@example.com</p>
        <p><strong>Address:</strong> Kakarvitta, Jhapa, Nepal</p>
      </div>
      
      <div class="profile-section">
        <h2>Basic Information</h2>
        <p><strong>Birthday:</strong> February 14, 2005</p>
        <p><strong>Gender:</strong> Male</p>
      </div>

      <div class="profile-section">
        <h2>Purchase History</h2>
        <p>Your recent orders and shopping preferences will appear here.</p>
      </div>
    </div>
  </div>
</body>
</html>
