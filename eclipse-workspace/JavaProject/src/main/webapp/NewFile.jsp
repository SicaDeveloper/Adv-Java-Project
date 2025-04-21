<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Profile</title>
    <meta charset="UTF-8">
    <style>
        /* Overall page style: white background with shades of grey and black accents */
        body {
            background-color: #f5f5f5;
            font-family: Arial, sans-serif;
            color: #333;
            margin: 0;
            padding: 0;
        }
        /* Container styling for a modern look */
        .container {
            width: 90%;
            max-width: 600px;
            background: #fff;
            margin: 40px auto;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 8px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }
        h1 {
            text-align: center;
            color: #000;
        }
        /* Form element styling */
        label {
            font-weight: bold;
            display: block;
            margin-top: 10px;
        }
        input[type="text"],
        input[type="email"],
        input[type="tel"],
        textarea {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        /* Buttons styling with black background */
        input[type="submit"],
        button {
            background-color: #000;
            border: none;
            color: #fff;
            padding: 10px 20px;
            margin: 10px 5px 0 0;
            border-radius: 4px;
            cursor: pointer;
        }
        input[type="submit"]:hover,
        button:hover {
            background-color: #333;
        }
        .actions {
            text-align: center;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>User Profile</h1>
        <%-- Update Profile Form: Users can edit their profile details here --%>
        <form action="updateProfile.jsp" method="post">
            <label for="firstName">First Name</label>
            <input type="text" id="firstName" name="firstName" value="<%= request.getAttribute("firstName") != null ? request.getAttribute("firstName") : "" %>" required>
            
            <label for="lastName">Last Name</label>
            <input type="text" id="lastName" name="lastName" value="<%= request.getAttribute("lastName") != null ? request.getAttribute("lastName") : "" %>" required>
            
            <label for="address">Address</label>
            <textarea id="address" name="address" rows="3" required><%= request.getAttribute("address") != null ? request.getAttribute("address") : "" %></textarea>
            
            <label for="phone">Phone Number</label>
            <input type="tel" id="phone" name="phone" value="<%= request.getAttribute("phone") != null ? request.getAttribute("phone") : "" %>" required>
            
            <div class="actions">
                <input type="submit" value="Save Changes">
            </div>
        </form>
        
        <%-- CRUD Operation Navigation: Create, View, or Delete profile --%>
        <div class="actions">
            <button type="button" onclick="window.location.href='createProfile.jsp'">Create Profile</button>
            <button type="button" onclick="window.location.href='viewProfile.jsp'">View Profile</button>
            <button type="button" onclick="window.location.href='deleteProfile.jsp'">Delete Profile</button>
        </div>
    </div>
</body>
</html>