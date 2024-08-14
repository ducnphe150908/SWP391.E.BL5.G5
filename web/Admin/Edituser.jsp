<%-- 
    Document   : Adduser
    Created on : Jun 13, 2024, 9:54:57 PM
    Author     : pc
--%>
<%@page import="model.UserDetail,java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.Base64" %>

<% UserDetail basicUserDetail = (UserDetail) request.getAttribute("basicDetail"); 
   int userID = (int) request.getAttribute("userID"); 
%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="icon" href="favicon.png">
        <title>JSP Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/js/bootstrap.bundle.min.js" rel="stylesheet"><!-- comment -->
        <link href="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js" rel="stylesheet">
        <style>
            body {

                background-color: rgb(102, 179, 255);
            }

            .form-control:focus {
                box-shadow: none;

                background-color: rgba(255, 255, 255, 0.5);
            }

            .profile-button {
                background: rgb(99, 39, 120);
                box-shadow: none;
                border: none
            }

            .profile-button:hover {
                background: #682773
            }

            .profile-button:focus {
                background: #682773;
                box-shadow: none
            }

            .profile-button:active {
                background: #682773;
                box-shadow: none
            }

            .back:hover {
                color: #682773;
                cursor: pointer
            }

            .labels {
                font-size: 11px
            }

            .add-experience:hover {
                background: #BA68C8;
                color: #fff;
                cursor: pointer;
                border: solid 1px #BA68C8
            }
            .file-input-wrapper {
                position: relative;
                overflow: hidden;
                display: inline-block;
                margin-top: 10px;
            }
            .file-input-wrapper input[type="file"] {
                position: absolute;
                top: 0;
                right: 0;
                margin: 0;
                padding: 0;
                font-size: 20px;
                cursor: pointer;
                opacity: 0;
                filter: alpha(opacity=0);
            }
            .file-input-wrapper button {
                background-color: #007bff;
                border: none;
                color: white;
                padding: 10px 20px;
                font-size: 16px;
                cursor: pointer;
                border-radius: 4px;
                font-weight: normal;
            }
            .file-input-wrapper button:hover {
                background-color: #0056b3;
            }
            .file-input-info {
                font-size: 14px;
                color: #6c757d;
                margin-top: 5px;
            }
            .error-message {
                color: red;
                margin-top: 5px;
                display: none;
            }
        </style>
       
    </head>
    <body>
        <div class="container rounded bg-white mt-5 mb-5">
            <div class="row">
                <div class="col-md-3 border-right">
                    <div class="d-flex flex-column align-items-center text-center p-3 py-5">  
                        <form action="uploadImage" enctype="multipart/form-data" id="imageForm" method="post">
                            <% String base64Image = basicUserDetail.getUserAvatar(); %>
                            <img src="data:image/jpg;base64, <%= base64Image %>" alt="Owner" class="rounded-circle" width="150">
                            <div class="file-input-wrapper">
                                <button type="button" class="btn btn-primary">Chọn ảnh</button>
                                <input type="file" name="img" class="form-control" id="imgInput">
                                <div class="file-input-info">Kích thước ảnh tối đa 1mb, định dạng .jpg</div>
                            </div>
                            <div class="error-message" id="errorMessage">File không hợp lệ hoặc quá 1MB.</div>
                            <input type="hidden" name="userID" value="<%= userID %>">
                        </form>
                    </div>
                </div>
                <div class="col-md-5 border-right">
                    <div class="p-3 py-5">
                        <div class="d-flex justify-content-between align-items-center mb-3">
                            <h4 class="text-right">Profile Settings</h4>
                        </div>

                        <form action="Edituser" method="post"> 
                            <div class="row mt-2">
                                <label class="labels">Name</label>
                                <input type="text" name="name" class="form-control" placeholder="Name" value="<%= basicUserDetail.getUserName() %>">
                            </div>
                            <div class="row mt-3">
                                <div class="col-md-12">
                                    <label class="labels">Gender</label>
                                    <input type="text" name="gender" class="form-control" placeholder="enter female/male" value="<%= basicUserDetail.getUserGender() %>">
                                </div>
                                <div class="col-md-12">
                                    <label class="labels">Date of birth</label>
                                    <input type="date" name="dob" class="form-control" placeholder="enter your date of birth" value="<%= basicUserDetail.getUserBirth() %>">
                                </div>
                                <div class="col-md-12">
                                    <label class="labels">Address</label>
                                    <input type="text" name="address" class="form-control" placeholder="enter address line " value="<%= basicUserDetail.getUserAddress() %>">
                                </div>
                                <div class="col-md-12">
                                    <label class="labels">Phone</label>
                                    <input type="text" name="phone" class="form-control" placeholder="enter your phone" value="<%= basicUserDetail.getUserPhone() %>">
                                </div>

                            </div>
                            <div class="mt-5 text-center">
                                <button class="btn btn-primary profile-button" type="submit">Save Profile</button>
                            </div>
                        </form>         

                    </div>

                </div>

            </div>
        </div>
    </div>
</div>
<script>
    function previewImage(event) {
        var reader = new FileReader();
        reader.onload = function () {
            var output = document.getElementById('profileImage');
            output.src = reader.result;
        };
        reader.readAsDataURL(event.target.files[0]);
    }
</script>
<script>
    function previewImage(event) {
        var reader = new FileReader();
        reader.onload = function () {
            var output = document.getElementById('imagePreview');
            output.style.backgroundImage = 'url(' + reader.result + ')';
        }
        reader.readAsDataURL(event.target.files[0]);
    }
</script>
<script>
    const imgInput = document.getElementById('imgInput');
    const btn = document.querySelector('.file-input-wrapper button');
    const errorMessage = document.getElementById('errorMessage');
    const form = document.getElementById('imageForm');
    const currentImage = document.getElementById('currentImage');

    imgInput.addEventListener('change', function () {
        const file = imgInput.files[0];
        const validImageTypes = ['image/jpeg', 'image/png', 'image/gif'];

        if (file && validImageTypes.includes(file.type) && file.size <= 1048576) {
            btn.textContent = file.name;
            errorMessage.style.display = 'none';
            form.submit();
        } else {
            errorMessage.style.display = 'block';
            imgInput.value = '';
            btn.textContent = 'Chọn ảnh';
        }
    });
</script>
</body>
</html>
