<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="UTF-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin-product-edit.css" />
		<title>Document</title>
	</head>
	<body>
		<div class="product-edit-container">
			<div class="product-edit-header">Edit Product</div>
			<form action="/admin/product-edit" method="post">
				<div class="product-edit-form">
					<div class="form-group">
						<label for="product-name">Product Name</label>
						<input
							type="text"
							id="product-name"
							name="product-name"
							placeholder="Enter product name"
							required
						/>
					</div>
					<div class="form-group">
						<label for="product-price">Price</label>
						<input
							type="number"
							id="product-price"
							name="product-price"
							placeholder="Enter product price"
							required
						/>
					</div>
					<div class="form-group">
						<label for="product-category">Category</label>
						<select name="product-category" id="product-category" required>
							<option value="">Select category</option>
							<option value="shoes">Shoes</option>
							<option value="clothing">Clothing</option>
							<option value="accessories">Accessories</option>
						</select>
					</div>
					<div class="form-group">
						<label for="product-quantity">Quantity</label>
						<input
							type="number"
							id="product-quantity"
							name="product-quantity"
							placeholder="Enter product quantity"
							required
						/>
					</div>
					<div class="form-group">
						<label for="product-description">Product Description</label>
						<textarea
							name="product-description"
							id="product-description"
							placeholder="Enter product description"
							required
						></textarea>
					</div>
                </div>
					<div class="product-image-field">
						<div class="form-group">
							<label for="product-label">Product Image</label>
							<img class="product-image" src="" alt="" />
							<input
								type="file"
								id="product-image"
								name="product-image"
								accept="image/*"
								required
							/>
						</div>
					</div>
			</form>
            <div class="product-edit-footer">
                <button type="submit">Save</button>
            </div>
		</div>
	</body>
</html>
