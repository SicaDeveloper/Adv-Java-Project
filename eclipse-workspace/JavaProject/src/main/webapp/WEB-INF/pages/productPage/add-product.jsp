<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<meta charset="UTF-8">
<title>Add Product</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/add-product.css">
</head>
<body>
<div class="add-product-bg">
    <div class="add-product-main">
        <c:if test="${not empty error}">
            <div class="error-message">${error}</div>
        </c:if>
        <c:if test="${not empty validationError}">
            <div class="validation-error">${validationError}</div>
        </c:if>
        <form class="add-product-form" action="${pageContext.request.contextPath}/admin/product/add" method="post" enctype="multipart/form-data" onsubmit="return validateForm()">
            <div class="add-product-left">
                <section class="card general-info">
                <h2>Add Product</h2>
                <div class="form-group-container">
                    <div class="form-group">
                        <div class="form-item">
                            <label for="productName">Name Product</label>
                            <input type="text" id="productName" name="productName" placeholder="Product Name" required
                                   pattern="^[a-zA-Z0-9\s-]+$" title="Product name can only contain letters, numbers, spaces, and hyphens"
                                   onchange="validateProductName(this)">
                            <span class="error" id="productNameError"></span>
                        </div>
                        <div class="form-item">
                            <label for="productDescription">Description Product</label>
                            <textarea id="productDescription" name="productDescription" placeholder="Product Description" required
                                      maxlength="500" onchange="validateDescription(this)"></textarea>
                            <span class="error" id="productDescriptionError"></span>
                        </div>
                        <div class="form-item">
                            <label for="productPrice">Base Pricing</label>
                            <input type="number" id="productPrice" name="productPrice" placeholder="$0.00" required
                                   min="0" step="0.01" title="Price must be a positive number"
                                   onchange="validatePrice(this)">
                            <span class="error" id="productPriceError"></span>
                        </div>
                        <div class="form-item">
                            <label for="productQuantity">Quantity</label>
                            <input type="number" id="productQuantity" name="productQuantity" placeholder="Enter quantity" required
                                   min="1" title="Quantity must be at least 1"
                                   onchange="validateQuantity(this)">
                            <span class="error" id="productQuantityError"></span>
                        </div>
                        <div class="form-item">
                            <label for="productImage">Product Image</label>
                            <input type="file" id="productImage" name="productImageInput" accept="image/*" required
                                   onchange="validateImage(this)">
                            <span class="error" id="productImageError"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="form-item">
                            <label for="productCategory">Product Category</label>
                            <select id="productCategory" name="productCategory" required
                                    onchange="validateCategory(this)">
                                <option value="">Select a category</option>
                                <c:forEach var="category" items="${categories}">
                                    <option value="${category.id}">${category.name}</option>
                                </c:forEach>
                            </select>
                            <span class="error" id="productCategoryError"></span>
                        </div>
                    </div>
                </div>
                </section>
            </div>
            <div class="add-product-right">
                <section class="card upload-img">
                    <h2>Upload Img</h2>
                    <div class="img-preview">
                        <img id="productImagePreview" src="" alt="Product Image Preview">
                    </div>
                </section>
                <div class="form-actions">
                    <button type="submit" class="btn-primary">Add Product</button>
                </div>
            </div>
        </form>
    </div>
</div>

<script>
function validateProductName(input) {
    const errorElement = document.getElementById('productNameError');
    if (!input.value.match(/^[a-zA-Z0-9\s-]+$/)) {
        errorElement.textContent = 'Product name can only contain letters, numbers, spaces, and hyphens';
        return false;
    }
    errorElement.textContent = '';
    return true;
}

function validateDescription(input) {
    const errorElement = document.getElementById('productDescriptionError');
    if (input.value.trim().length === 0) {
        errorElement.textContent = 'Product description is required';
        return false;
    }
    if (input.value.length > 500) {
        errorElement.textContent = 'Description cannot exceed 500 characters';
        return false;
    }
    errorElement.textContent = '';
    return true;
}

function validatePrice(input) {
    const errorElement = document.getElementById('productPriceError');
    const price = parseFloat(input.value);
    if (isNaN(price) || price < 0) {
        errorElement.textContent = 'Price must be a positive number';
        return false;
    }
    errorElement.textContent = '';
    return true;
}

function validateQuantity(input) {
    const errorElement = document.getElementById('productQuantityError');
    const quantity = parseInt(input.value);
    if (isNaN(quantity) || quantity < 1) {
        errorElement.textContent = 'Quantity must be at least 1';
        return false;
    }
    errorElement.textContent = '';
    return true;
}

function validateImage(input) {
    const errorElement = document.getElementById('productImageError');
    if (input.files.length === 0) {
        errorElement.textContent = 'Product image is required';
        return false;
    }
    
    const file = input.files[0];
    const validTypes = ['image/jpeg', 'image/png', 'image/gif', 'image/avif'];
    if (!validTypes.includes(file.type)) {
        errorElement.textContent = 'Please upload a valid image file (JPEG, PNG, AVIF, or GIF)';
        return false;
    }
    
    // Preview image
    const preview = document.getElementById('productImagePreview');
    const reader = new FileReader();
    reader.onload = function(e) {
        preview.src = e.target.result;
    }
    reader.readAsDataURL(file);
    
    errorElement.textContent = '';
    return true;
}

function validateCategory(input) {
    const errorElement = document.getElementById('productCategoryError');
    if (!input.value) {
        errorElement.textContent = 'Please select a category';
        return false;
    }
    errorElement.textContent = '';
    return true;
}

function validateForm() {
    let isValid = true;
    
    // Validate all fields
    isValid = validateProductName(document.getElementById('productName')) && isValid;
    isValid = validateDescription(document.getElementById('productDescription')) && isValid;
    isValid = validatePrice(document.getElementById('productPrice')) && isValid;
    isValid = validateQuantity(document.getElementById('productQuantity')) && isValid;
    isValid = validateImage(document.getElementById('productImage')) && isValid;
    isValid = validateCategory(document.getElementById('productCategory')) && isValid;
    
    return isValid;
}
</script>
</body>
</html>