/**
 * 
 */

$(document)
		.ready(
				function() {

					var token = $("meta[name='_csrf']").attr("content");
					var header = $("meta[name='_csrf_header']").attr("content");
					$(document).ajaxSend(function(e, xhr, options) {
						xhr.setRequestHeader(header, token);
					})

					$
							.ajax({
								type : "GET",
								url : '/active_request',
								contentType : "application/json",
								Accept : "application/json",
								// data : JSON.stringify(data),
								success : function(response) {
									
									for (let i = 0; i < response.length; i++) {

										console.log(response[i]);
										let date = new Date(
												response[i].requestDate);

										let dd = String(date.getDate())
												.padStart(2, '0');
										let mm = String(date.getMonth() + 1)
												.padStart(2, '0'); // January
																	// is 0!
										let yyyy = date.getFullYear();

										let dateStr = mm + '-' + dd + '-'
												+ yyyy;
										let status = "NAPPR" === response[i].reqStatus ? "NOT APPROVED"
												: "APPROVED";
										let disablebutton = "NAPPR" === response[i].reqStatus ? ""
												: "disabled";
										var row = "<tr id="
												+ response[i].requestId
												+ "><td>"
												+ response[i].cylinderRequestNo
												+ "</td><td>"
												+ dateStr
												+ "</td><td>"
												+ response[i].requester
												+ "</td>"
												+ "<td>"
												+ status
												+ "</td><td><button "
												+ disablebutton
												+ " class='btn btn-info btn-lg btn-edit mr-3' type='button'>Edit</button>"
												+ "&nbsp;&nbsp;<button "
												+ disablebutton
												+ " class='btn btn-danger btn-lg btn-delete' type='button'>Delete</button></td></tr>";
										$(" table tbody").append(row);

									}

								},
								error : function() {
									alert("not working");
								}
							});

					$("#req")
							.click(
									function(e) {
										e.preventDefault();

										var number = $("#number").val();
										var datetime = $("#datetime").val();
										var name = $("#name").val();
										var name_regex = /^[a-zA-Z]+/;

										if (number == '') {
											$("#show_error")
													.text(
															'** The number must be filled.');
											$("#show_error")
													.css('color', 'red');

											return false;
										} else if (number < 1) {
											$("#show_error")
													.text(
															'** The number must be positive.');
											$("#show_error")
													.css('color', 'red');

											return false;
										} else if (datetime == '') {
											$("#show_error1")
													.text(
															'** The date must be filled.');
											$("#show_error1").css('color',
													'red');

											return false;
										} else if (name.length == '') {
											$("#show_error2")
													.text(
															'** The name must be filled.');
											$("#show_error2").css('color',
													'red');

											return false;
										} else if (!name.match(name_regex)
												|| name.length == 0) {
											$("#show_error2")
													.text(
															'**For your name please use alphabet only.');
											$("#show_error2").css('color',
													'red');

											return false;
										}
										
										//
										let term = "sample1";
										let re = new RegExp("^[+]?\d+([.]\d+)?$");
										if (re.test(term)) {
										    console.log("Valid");
										} else {
										    console.log("Invalid");
										}

										let userid = $('#userid').val();
										let data = {
											'cylinderRequestNo' : number,
											'requestDate' : datetime,
											'requester' : name,
											'userid' : userid
										};//
										console.log(data);

										let genReqId = -1;

										$
												.ajax({
													type : "POST",
													url : '/generate_request',
													contentType : "application/json",
													// dataType : 'json',
													Accept : "application/json",
													data : JSON.stringify(data),
													success : function(response) {

														console
																.log(response.requestId);
														genReqId = response.requestId;
														var row = "<tr id="
																+ genReqId
																+ "><td>"
																+ number
																+ "</td><td>"
																+ datetime
																+ "</td><td>"
																+ name
																+ "</td>"
																+ "<td>Not Approved</td>"
																+ "<td><button class='btn btn-info btn-lg btn-edit mr-3' type='button'>Edit</button>&nbsp;&nbsp;<button class='btn btn-danger btn-lg btn-delete' type='button'>Delete</button></td></tr>";
														$(" table tbody")
																.append(row);

														$("#number").val('');
														$("#datetime").val('');

													},
													error : function() {
														alert("not working");
													}
												});

									});

				});

$('body')
		.on(
				'click',
				'.btn-edit',
				function() {

					var number = $(this).parents('tr').find('td:eq(0)').text();
					var datetime = $(this).parents('tr').find('td:eq(1)')
							.text();
					var name = $(this).parents('tr').find('td:eq(2)').text();

					console.log(number)
					console.log(datetime)
					console.log(name)

					$(this).parents('tr').find('td:eq(0)')
							.html(
									"<input name='edit_number' value='"
											+ number + "'>");
					//$(this).parents('tr').find('td:eq(1)').html(
					//		"<input name='edit_datetime' value='" + datetime
					//				+ "'>");
					// $(this).parents('tr').find('td:eq(2)').html("<input
					// name='edit_name' value='"+name+"'>");
					$(this)
							.parents('tr')
							.find('td:eq(4)')
							.prepend(
									"<button type='button' class='btn btn-info btn-lg btn-update mr-3'>update</button>");
					$(this).hide();
				});

$('body')
		.on(
				'click',
				'.btn-update',
				function() {

					var number = $(this).parents('tr').find(
							"input[name='edit_number']").val();
					var datetime = $(this).parents('tr').find(
							"input[name='edit_datetime']").val();
					// var
					let  reqId=$(this).parents('tr').attr("id");

					alert("before update");
					
					let data1= {"requestId":reqId,"cylinderRequestNo":number};
					console.log(data1);

					// TODO:ajax call
					
							$.ajax({
								type : "POST",
								url : '/update_request',
								contentType : "application/json",
								Accept : "application/json",
								data : JSON.stringify(data1),
								success : function(response) {

									alert("update success");

								},
								error : function() {
									alert("not working");
								}
							});

					$(this).parents('tr').find('td:eq(0)').text(number);
					$(this).parents('tr').find('td:eq(1)').text(datetime);
					// $(this).parents('tr').find('td:eq(2)').text(name);

					$(this).parents('tr').attr('number', number);
					$(this).parents('tr').attr('datetime', datetime);
					// $(this).parents('tr').attr('name',name);

					$(this).parents('tr').find('.btn-edit').show();
					$(this).parents('tr').find('.btn-update').remove();
					$(this).parents('tr').find('.btn-delete').remove();

				});

$('body').on('click', '.btn-delete', function() {

	console.log(this);
	let reqId = $(this).parents('tr').attr("id");
	// alert(reqId);
	data = {
		"requestId" : reqId
	};
	$.ajax({
		type : "DELETE",
		url : '/delete_request',
		contentType : "application/json",
		Accept : "application/json",
		data : JSON.stringify(data),
		success : function(response) {
			alert("in delte sucess");
			alert(response);

		},
		error : function() {
			alert("not working");
		}
	});

	$(this).parents('tr').remove();

});