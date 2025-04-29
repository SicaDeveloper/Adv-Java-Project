<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<c:set var="loggedInUser" value="${sessionScope.user}" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Dashboard</title>
<link rel="stylesheet" type="text/css" href="${contextPath}/css/dashboard.css" />
</head>
<body>

    <div class="container">
        <div class="sidebar">
            <div class="profile">
                <img src="${contextPath}/uploads/${loggedInUser.profilePicture}" alt="Profile Picture">
                <p>${loggedInUser.username}</p>
                <form action="${contextPath}/uploadProfilePic" method="post" enctype="multipart/form-data">
                    <input type="file" name="profilePic" required>
                    <button type="submit">Upload</button>
                </form>
            </div>
            <ul class="nav">
                <li><a href="#"><span class="icon">🏠</span> Dashboard</a></li>
                <li><a href="#"><span class="icon">📊</span> Order List</a></li>
                <li><a href="#"><span class="icon">💳</span> Students</a></li>
            </ul>
            <div class="upgrade">Upgrade to Pro</div>
        </div>

        <div class="content">
            <div class="header">
                <div class="info-box">
                    <h3>Total Students</h3>
                    <p>3,000</p>
                </div>
                <div class="info-box">
                    <h3>Computing</h3>
                    <p>3000</p>
                </div>
                <div class="info-box">
                    <h3>Multimedia</h3>
                    <p>1000</p>
                </div>
                <div class="info-box">
                    <h3>Networking</h3>
                    <p>1000</p>
                </div>
            </div>

            <div class="card">
                <div>
                    <h2>Welcome, ${loggedInUser.username}!</h2>
                    <p>School Management Dashboard</p>
                    <br /> <br />
                    <p>Manage your school's data efficiently and effortlessly.</p>
                </div>
                <img src="${contextPath}/resources/images/system/college.jpg" alt="college">
            </div>

            <div class="card">
                <div>
                    <h2>Reach for the Stars</h2>
                    <p>Keep pushing forward, stay dedicated, and embrace challenges.</p>
                </div>
            </div>

            <div class="table-container">
                <h3>Student Information</h3>
                <table>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Module</th>
                            <th>Email</th>
                            <th>Number</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="student" items="${studentList}">
                            <tr>
                                <td>${student.id}</td>
                                <td>${student.firstName} ${student.lastName}</td>
                                <td>${student.program.name}</td>
                                <td>${student.email}</td>
                                <td>${student.number}</td>
                                <td>
                                    <form action="${contextPath}/dashboard" method="post" style="display: inline;">
                                        <input type="hidden" name="studentId" value="${student.id}">
                                        <input type="hidden" name="action" value="edit">
                                        <button class="action-btn" type="submit">
                                            <img src="${contextPath}/resources/images/icons/edit.png" alt="Edit" title="Edit" />
                                        </button>
                                    </form>
                                    <form action="${contextPath}/dashboard" method="post" style="display: inline;">
                                        <input type="hidden" name="studentId" value="${student.id}">
                                        <input type="hidden" name="action" value="delete">
                                        <button class="action-btn" type="submit">
                                            <img src="${contextPath}/resources/images/icons/delete.png" alt="Delete" title="Delete" />
                                        </button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

</body>
</html>