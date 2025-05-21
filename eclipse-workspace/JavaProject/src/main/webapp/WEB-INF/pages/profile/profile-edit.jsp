<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit Profile</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            margin: 0;
        }
        
        .edit-form-container {
            background-color: #fff;
            padding: 45px; /* Increased padding */
            border-radius: 8px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
            width: 65%; /* Expanded width */
            max-width: 750px; /* Gives more space */
            margin: 80px auto;
        }

        .edit-form-container h1 {
            text-align: center;
            font-size: 26px;
            margin-bottom: 25px;
        }

        .form-row {
            display: flex;
            justify-content: space-between;
            gap: 25px; /* More space between fields */
            margin-bottom: 20px;
        }

        .form-group {
            flex: 1;
        }

        .edit-form-container label {
            display: block;
            font-weight: bold;
            margin-bottom: 6px;
            font-size: 17px;
        }

        .form-group input,
        .form-group select {
            width: 100%; /* Uses full available space */
            padding: 8px;
            font-size: 14px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

		.form-group textarea {
		    width: 100%; /* Ensures full container width */
		    height: 200px; /* Maintains vertical space */
		    padding: 12px;
		    resize: vertical;
		    display: block;
		}
        .edit-form-container button {
            width: 100%;
            padding: 12px;
            font-size: 16px;
            border-radius: 6px;
            background-color: #333;
            color: #fff;
            border: none;
            cursor: pointer;
            font-weight: bold;
        }

        .edit-form-container button:hover {
            background-color: #555;
        }
    </style>
</head>
<body>
    <div class="edit-form-container">
        <h1>Edit Profile</h1>
        <form action="updateProfile.jsp" method="post">
            
            <div class="form-row">
                <div class="form-group">
                    <label for="name">Full Name</label>
                    <input type="text" id="name" name="name" value="Ishan Giri" required>
                </div>

                <div class="form-group">
                    <label for="phone">Phone</label>
                    <input type="tel" id="phone" name="phone" value="+977 9826933353">
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" value="ishangiri@example.com" required>
                </div>

                <div class="form-group">
                    <label for="address">Address</label>
                    <input type="text" id="address" name="address" value="Kakarvitta, Jhapa, Nepal">
                </div>
            </div>

            <div class="form-row">
                <div class="form-group">
                    <label for="birthday">Birthday</label>
                    <input type="date" id="birthday" name="birthday" value="2005-02-14">
                </div>

                <div class="form-group">
                    <label for="gender">Gender</label>
                    <select id="gender" name="gender">
                        <option value="Male" selected>Male</option>
                        <option value="Female">Female</option>
                        <option value="Other">Other</option>
                    </select>
                </div>
            </div>

            <label for="bio">Bio</label>
            <textarea id="bio" name="bio" placeholder="Tell us a little about yourself...">Passionate about e-commerce, technology, and innovation. Always exploring new opportunities to grow and improve!</textarea>

            <button type="submit">Save Changes</button>
        </form>
    </div>
</body>
</html>
