/**
 * 
 */
var NOT_APPROVED_DISPLAY_STATUS = "NOT APPROVED";
var APPROVED_DISPLAY_STATUS = "APPROVED";
var STOCK_ADDED_DISPLAY_STATUS = "STOCK ADDED";
var REQUEST_CANCEL_DISPLAY_STATUS = "CANCEL";
var REQ_COMPLETE_DISPLAY_STATUS = "COMPLETE";
var NOT_APPROVED_STATUS = "NAPPR";
var APPROVED_STATUS = "APPR";
var STOCK_ADDED_STATUS = "STKADD";
var REQ_CANCEL_STATUS = "CNCL";
var REQ_COMPLETE_STATUS = "CMPLT"

$(document)
		.ready(
				function() {

					var token = $("meta[name='_csrf']").attr("content");
					var header = $("meta[name='_csrf_header']").attr("content");
					$(document).ajaxSend(function(e, xhr, options) {
						xhr.setRequestHeader(header, token);
					})
					
					$(".stock_add_comment").hide();
					$(".no_data_found_alert").hide();
					

					$
							.ajax({
								type : "GET",
								url : '/active_request',
								contentType : "application/json",
								Accept : "application/json",
								// data : JSON.stringify(data),
								success : function(response) {
									var role = $("#user_privillege").val();
									
									
									var filteredData = response.filter(function (el) {
										
										//console.log(el);
										if(role==="admin"){
											return el.reqStatus===NOT_APPROVED_STATUS ;
										}else{
											return ( 
													el.reqStatus===NOT_APPROVED_STATUS 
													|| el.reqStatus=== APPROVED_STATUS
													|| el.reqStatus===STOCK_ADDED_STATUS
													|| el.reqStatus == REQ_CANCEL_STATUS
													|| el.reqStatus == REQ_COMPLETE_STATUS
													); 
										}
										
										
										  
									});
									
									if(filteredData.length==0){
										$(".no_data_found_alert").show();
									}
									
									let availableCylinderCount = getAvailableCylinderCountCount(response);
									if(availableCylinderCount>0){
										$(".availability_count").text("Oxygen cylinder Availability count:"+availableCylinderCount);
									}else{
										$(".availability_count").text("Oxygen cylinder Availability count:"+availableCylinderCount);
									}
									
									for (let i = 0; i < filteredData.length; i++) {

										
										let date = new Date(
												filteredData[i].requestDate);

										let dd = String(date.getDate())
												.padStart(2, '0');
										let mm = String(date.getMonth() + 1)
												.padStart(2, '0'); // January
																	// is 0!
										let yyyy = date.getFullYear();

										let dateStr = mm + '-' + dd + '-'+ yyyy;
										let status = "";
										
										//TODO:
										if(NOT_APPROVED_STATUS === filteredData[i].reqStatus){
											status = NOT_APPROVED_DISPLAY_STATUS
										}else if(STOCK_ADDED_STATUS===filteredData[i].reqStatus){
											status = STOCK_ADDED_DISPLAY_STATUS
										}else if(REQ_CANCEL_STATUS===filteredData[i].reqStatus){
											status = REQUEST_CANCEL_DISPLAY_STATUS;
										}else if(REQ_COMPLETE_STATUS ===filteredData[i].reqStatus){
											status = REQ_COMPLETE_DISPLAY_STATUS;
										}else if(APPROVED_STATUS ===filteredData[i].reqStatus){
											status = APPROVED_DISPLAY_STATUS;
										}
										let disablebutton = ("NAPPR" === filteredData[i].reqStatus && "user"===role) ? ""
												: "disabled";
										var row = "<tr id="
												+ filteredData[i].requestId
												+ "><td>"
												+ filteredData[i].cylinderRequestNo
												+ "</td><td>"
												+ dateStr
												+ "</td><td>"
												+ filteredData[i].requester
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
							.click(function(e) {
										e.preventDefault();

										var number = $("#number").val();
										var datetime = $("#datetime").val();
										var name = $("#name").val();
										var name_regex = /^[a-zA-Z]+/;
										let stockIncreaseComment = $("#admin_Stock_update_comment").val();
										let stockIncreaseRequest = $("#admin_req_increse_stock_cb").val();
										
										if(typeof stockIncreaseRequest != 'undefined' ){
											
											if ($('#admin_req_increse_stock_cb').is(":checked")){
												
												stockIncreaseRequest = true;
											}else{
												stockIncreaseRequest = false;
											}
												
											
										}else{
											stockIncreaseRequest = false;
										}
										
										

										if (number == '') {
											$("#show_error").text('** The number must be filled.');
											$("#show_error").css('color', 'red');

											return false;
										} else if (number < 1) {
											$("#show_error").text('** The number must be positive.');
											$("#show_error").css('color', 'red');

											return false;
										} else if (datetime == '') {
											$("#show_error1").text('** The date must be filled.');
											$("#show_error1").css('color','red');

											return false;
										}else if(datetime != ''){
											console.log(datetime);
											//return;
											
										}
										else if (name.length == '') {
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
										
										
										if (typeof stockIncreaseComment != 'undefined'   ){
											if(stockIncreaseComment==="" && stockIncreaseRequest ==true){
												$("#show_error3").text('** The comment must be added.');
												$("#show_error3").css('color','red');
												return false;
											}
											
										}else{
											stockIncreaseComment= "";
										}
										
										/*let re = new RegExp("^(?!0*[.,]0*$|[.,]0*$|0*$)\d+[,.]?\d{0,2}$");
										if (re.test(number)) {
										    alert("Valid");
										    return;
										} else {
										    console.log("Invalid");
										    return;
										}*/

										let userid = $('#userid').val();
										let data = {
											'cylinderRequestNo' : number,
											'requestDate' : datetime,
											'requester' : name,
											'userid' : userid,
											'stockIncreaseRequest':stockIncreaseRequest,
											'stockIncreaseComment':stockIncreaseComment
											
										};//
										
										

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
														
														
														console.log(response.requestId);
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
																+ "<td>" +(stockIncreaseRequest==true ? STOCK_ADDED_DISPLAY_STATUS:
																	NOT_APPROVED_DISPLAY_STATUS) +
																		"</td>"
																+ "<td><button class='btn btn-info btn-lg btn-edit mr-3' type='button'>Edit</button>&nbsp;&nbsp;<button class='btn btn-danger btn-lg btn-delete' type='button'>Delete</button></td></tr>";
														$(" table tbody").prepend(row);

														$("#number").val('');
														$("#datetime").val('');
														$(".no_data_found_alert").hide();

													},
													error : function() {
														alert("not working");
													}
												});

									});

				});

$('body')
		.on('click',
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
		.on('click',
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


$("#admin_req_increse_stock_cb").on('click',function(){
	
	if ($('#admin_req_increse_stock_cb').is(":checked"))
	{
		$(".stock_add_comment").show();
		$(".req_or_stock_addition_by label").text("Stock Added By:");
		$(".req_or_stock_addition_dt label").text("Stock Added Time:");
	}else{
		$(".stock_add_comment").hide();
		$(".req_or_stock_addition_by label").text("Requested By:");
		$(".req_or_stock_addition_dt label").text("Request Time:");
		$("#admin_Stock_update_comment").val("");
	}
	
	
});


function getAvailableCylinderCountCount(response) {
	var approvedReqList = response.filter(function (el) {
		return ( 
				el.reqStatus=== APPROVED_STATUS
				); 
		  //el.reqStatus===STOCK_ADDED_STATUS
		});
	
	var stockAddedList = response.filter(function (el) {
		return ( 
				el.reqStatus=== STOCK_ADDED_STATUS
				); 
		  
		});
	
	let approvedCylinderCount = 0;
	let totalStockadded = 0;
	let availableCyliderInstock = 0;
	if(approvedReqList.length>0){
		for(let i = 0; i < approvedReqList.length; i++){
			approvedCylinderCount = approvedCylinderCount+ approvedReqList[i].cylinderRequestNo
		}
		
	}
	if(stockAddedList.length>0){
		for(let i = 0; i < stockAddedList.length; i++){
			totalStockadded = totalStockadded+ stockAddedList[i].cylinderRequestNo
		}
		
	}
	console.log("total item stock"+totalStockadded)
	console.log("total approved item "+approvedCylinderCount)
	availableCyliderInstock = totalStockadded-approvedCylinderCount;
	if((availableCyliderInstock)>0){
		return availableCyliderInstock;
	}else{
		return 0;
	}
	
	return 0;
}