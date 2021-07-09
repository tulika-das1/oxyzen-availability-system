<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<meta charset="utf-8">
<meta name="_csrf" content="${_csrf.token}"/>
  <meta name="_csrf_header" content="${_csrf.headerName}"/>

<title>Oxygen Management System</title>
<meta content="" name="description">

<meta content="" name="keywords">

<!-- Favicons -->
<link href="/resources/assets/img/favicon.png" rel="icon">
<link href="/resources/assets/img/apple-touch-icon.png"
	rel="apple-touch-icon">

<!-- Google Fonts -->
<link
	href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i"
	rel="stylesheet">

<!-- Vendor CSS Files -->
 <link href="/resources/assets/vendor/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="/resources/assets/vendor/bootstrap-icons/bootstrap-icons.css"
	rel="stylesheet">
<link href="/resources/assets/vendor/aos/aos.css" rel="stylesheet">
<link href="/resources/assets/vendor/remixicon/remixicon.css"
	rel="stylesheet">
<link href="/resources/assets/vendor/swiper/swiper-bundle.min.css"
	rel="stylesheet">
<link href="/resources/assets/vendor/glightbox/css/glightbox.min.css"
	rel="stylesheet">
	
<script src="/resources/assets/vendor/jquery/jquery.min.js"></script>
<!-- <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script> -->
<!-- <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script> -->
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8shuf57BaghqFfPlYxofvL8/KUEfYiJOMMV+rV" crossorigin="anonymous"></script>
	

<!-- Template Main CSS File -->
<link href="/resources/assets/css/style.css" rel="stylesheet">


<!-- =======================================================
  * Template Name: FlexStart - v1.4.0
  * Template URL: https://bootstrapmade.com/flexstart-bootstrap-startup-template/
  * Author: BootstrapMade.com
  * License: https://bootstrapmade.com/license/
  ======================================================== -->

</head>
<body>



	<!-- ======= Header ======= -->
	<header id="header" class="header fixed-top">
		<div
			class="container-fluid container-xl d-flex align-items-center justify-content-between">

			<a href="/login" class="logo d-flex align-items-center"> <img
				src="/resources/assets/img/logo.png" alt=""> <span>O2</span>
			</a>

			<nav id="navbar" class="navbar">
				<ul>
					<li><a class="nav-link scrollto active" href="#hero">Home</a></li>
					<sec:authorize access="!isAuthenticated()">
						<li><a class="nav-link scrollto" href="#login">Login</a></li>
						<li><a class="nav-link scrollto" href="#register">Sign up</a></li>
					</sec:authorize>

					<li><a class="nav-link scrollto" href="#about">About</a></li>
					<sec:authorize access="isAuthenticated()">
						<li><a href="<c:url value="/logout" />">Logout</a></li>
					</sec:authorize>


					<!-- <li class="dropdown"><a href="#"><span>Drop Down</span> <i class="bi bi-chevron-down"></i></a>
            <ul>
              <li><a href="#">Drop Down 1</a></li>
              <li class="dropdown"><a href="#"><span>Deep Drop Down</span> <i class="bi bi-chevron-right"></i></a>
                <ul>
                  <li><a href="#">Deep Drop Down 1</a></li>
                  <li><a href="#">Deep Drop Down 2</a></li>
                  <li><a href="#">Deep Drop Down 3</a></li>
                  <li><a href="#">Deep Drop Down 4</a></li>
                  <li><a href="#">Deep Drop Down 5</a></li>
                </ul>
              </li>
              <li><a href="#">Drop Down 2</a></li>
              <li><a href="#">Drop Down 3</a></li>
              <li><a href="#">Drop Down 4</a></li>
            </ul>
          </li> -->
					<li><a class="nav-link scrollto" href="#contact">Contact</a></li>
					<!-- <li><a class="getstarted scrollto" href="#about">Get Started</a></li> -->
				</ul>
				<i class="bi bi-list mobile-nav-toggle"></i>
			</nav>
			<!-- .navbar -->

		</div>
	</header>
	<!-- End Header -->
	<br />
	<br />
	<br />
	<section>

<input type = "hidden"  id = "userid" value = '<sec:authentication property="principal.id" />' >
		
		<div class="container">
			<div class="row gx-0">
				<div class="col-lg-12 d-flex flex-column justify-content-center">
					<div class="content">
						<button class="btn btn-success" data-target="#mymodal"
							data-toggle="modal">REQUEST FOR OXYGEN</button>
						<br>
						<br>
					</div>
				</div>
			</div>
			<div class="row gx-0">
				<div id="con">
					<table class="table table-bordered data-table">
						<thead>
							<tr>

								<th>Cylinder Count</th>
								<th>Request date</th>
								<th>Requested by</th>
								<th>Actions</th>


							</tr>
						</thead>
						<tbody>
							<tr>


							</tr>

						</tbody>
					</table>
				</div>

			</div>
		</div>
		<div class="modal" id="mymodal">
			<div class="modal-dialog modal-dialog-center">
				<div class="modal-content">
					<div class="modal-header">
						<h3 class="text-primary">Request For Oxygen</h3>
						<button type="button" class="close" data-dismiss="modal">
							&times;</button>
					</div>
					<div class="modal-body">


						<div class="form-group">
							<label for="number">Cylinder Count :</label> <span class="red">*
								<small></small>
							</span> <input type="number" min="1" name="" id="number" class="form-control"
								placeholder="Enter no. of cylinders" required="">
							<p id="show_error"></p>
						</div>
						<div class="form-group">
							<label for="datetime">Request Time :</label> <span class="red">*
								<small></small>
							</span> <input type="date" name="" id="datetime" class="form-control"
								placeholder="Enter the date" required="">
							<p id="show_error1"></p>
						</div>
						
						<div class="form-group">
							<label for="name">Requested By :</label> <span class="red">*
								<small></small>
							</span> <input type="text" name='' value = '<sec:authentication property="principal.displayName" />' readonly id="name" class="form-control"
								 required="">
							<p id="show_error2"></p>
						</div>

						<div class="modal-footer">
							<button id="req" class="btn btn-success" data-dismiss="modal">Request</button>
							<button class="btn btn-danger" data-dismiss="modal">Cancel</button>
						</div>

						<br>
					</div>
				</div>
			</div>
		</div>
	</section>
<script  src="/resources/assets/js/request.js"></script>
</body>