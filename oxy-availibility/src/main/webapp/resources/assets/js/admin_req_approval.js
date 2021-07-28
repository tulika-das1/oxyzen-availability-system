var NOT_APPROVED_DISPLAY_STATUS = "NOT APPROVED";
var APPROVED_DISPLAY_STATUS = "APPROVED";
var STOCK_ADDED_DISPLAY_STATUS = "STOCK ADDED";
var REQ_CANCEL_DISPLAY_STATUS = "CANCEL";
var REQ_COMPLETE_DISPLAY_STATUS = "COMPLETE";
var NOT_APPROVED_STATUS = "NAPPR";
var APPROVED_STATUS = "APPR";
var STOCK_ADDED_STATUS = "STKADD";
var REQ_CANCEL_STATUS = "CNCL";
var REQ_STATUS_COMPLETE = "CMPLT";

$(document)
		.ready(
				function() {
					
					var token = $("meta[name='_csrf']").attr("content");
					var header = $("meta[name='_csrf_header']").attr("content");
					$(document).ajaxSend(function(e, xhr, options) {
						xhr.setRequestHeader(header, token);
					});
					
					
					$
					.ajax({
						type : "GET",
						url : '/active_request',
						contentType : "application/json",
						Accept : "application/json",
						// data : JSON.stringify(data),
						success : function(response) {
							
							var filteredData = response.filter(function (el) {
								
								
								return ( el.reqStatus===REQ_CANCEL_STATUS 
										|| el.reqStatus===NOT_APPROVED_STATUS 
										|| el.reqStatus=== APPROVED_STATUS
										|| el.reqStatus===REQ_STATUS_COMPLETE); //el.reqStatus===NOT_APPROVED_STATUS || el.reqStatus=== APPROVED_STATUS || CNCL
								  
								});
							
							
							for (let i = 0; i < filteredData.length; i++) {

								
								let date = new Date(
										filteredData[i].requestDate);

								let dd = String(date.getDate())
										.padStart(2, '0');
								let mm = String(date.getMonth() + 1)
										.padStart(2, '0'); // January
															// is 0!
								let yyyy = date.getFullYear();

								let dateStr = mm + '-' + dd + '-'
										+ yyyy;
								console.log(filteredData[i]);
								let status1 ="";
								
								if(NOT_APPROVED_STATUS === filteredData[i].reqStatus){
									status1 =NOT_APPROVED_DISPLAY_STATUS ;
									
								}else if(APPROVED_STATUS === filteredData[i].reqStatus){
									status1 = APPROVED_DISPLAY_STATUS;
									
								}else if(REQ_STATUS_COMPLETE === filteredData[i].reqStatus ){
									status1 = REQ_COMPLETE_DISPLAY_STATUS;
								}
								else{
									status1 = REQ_CANCEL_DISPLAY_STATUS;
								}
								
								
								let disablebutton =( REQ_CANCEL_STATUS === filteredData[i].reqStatus
										|| REQ_STATUS_COMPLETE ===filteredData[i].reqStatus) ? "disabled":"";
								
								
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
										+ status1
										+ "</td>"
										+"<td><button "
										+ disablebutton
										+ " class='btn btn-info btn-lg btn-edit mr-3 temp' data-target='#mymodal' data-toggle='modal' type='button'>Update</button></td>";
								
								
								$("table tbody").append(row);

							}

						},
						error : function() {
							alert("not working");
						}
					});
					
					

				});


$(".approve_request").on('click',function(){
	
	let cmt = $("#admin_req_app_rej_comment").val();
	let reqId = $("#app_rej_id").val();
	let cylinderCount = $("#app_rej_cylinder_count_id").val();
	let requestDisplayStatus = $("#req_disp_stat").val();
	if( cmt === ""){
		$("#show_error3").text('** The comment must be added.');
		$("#show_error3").css('color','red');
		$("#admin_req_app_rej_comment").focus();
		return false ;
	}
	
	let updationStatus = "";
	
	if(requestDisplayStatus===NOT_APPROVED_DISPLAY_STATUS){
		updationStatus = APPROVED_STATUS;
	}
	else if(requestDisplayStatus===APPROVED_DISPLAY_STATUS){
		updationStatus = REQ_STATUS_COMPLETE;	
	}
	
	let data = {"approvalRejectionComment":cmt,"requestId":reqId,"cylinderRequestNo":cylinderCount,"reqStatus":updationStatus};
	$("#admin_req_app_rej_comment").val("");
	
	//ajax call
	$.ajax({
		type : "PUT",
		url : '/req_updation_by_admin',
		contentType : "application/json",
		Accept : "application/json",
		data : JSON.stringify(data),
		success : function(response) {
			
			
			if(response.errorMsg!=null && response.errorMsg!=""){
				alert(response.errorMsg);
			}else{
				
				let reqIdTemp="#"+reqId+" td:eq(3)";
				let dispStatusTemp=""
				if(updationStatus ===APPROVED_STATUS){
					dispStatusTemp = APPROVED_DISPLAY_STATUS;
				}else{
					dispStatusTemp = REQ_COMPLETE_DISPLAY_STATUS;
				}	
				$(reqIdTemp).text(dispStatusTemp);
			}
			

		},
		error : function() {
			alert("not working");
		}
	});
	
});


$(".cancel_request").on('click',function(){
	
	let cmt = $("#admin_req_app_rej_comment").val();
	let reqId = $("#app_rej_id").val();
	let cylinderCount = $("#app_rej_cylinder_count_id").val();
	if( cmt === ""){
		$("#show_error3").text('** The comment must be added.');
		$("#show_error3").css('color','red');
		$("#admin_req_app_rej_comment").focus();
		return false ;
	}
	
	let data = {"approvalRejectionComment":cmt,"requestId":reqId,"cylinderRequestNo":cylinderCount,"reqStatus":REQ_CANCEL_STATUS};
	//ajax call
	$.ajax({
		type : "PUT",
		url : '/req_updation_by_admin',
		contentType : "application/json",
		Accept : "application/json",
		data : JSON.stringify(data),
		success : function(response) {
			if(response.errorMsg!=null && response.errorMsg!=""){
				alert(response.errorMsg);
			}else{
				
				let reqIdTemp="#"+reqId+" td:eq(3)";
				let dispStatusTemp=""
					
				$(reqIdTemp).text(REQ_CANCEL_DISPLAY_STATUS);
			}
			

		},
		error : function() {
			alert("not working");
		}
	});

	
	
});

$("table").on('click','button',function(){
	
	let requestId = $(this).parents('tr').attr('id');
	let cylinderReqCount = parseInt($(this).parents('tr').find('td:eq(0)').text());
	let requestDisplayStatus = $(this).parents('tr').find('td:eq(3)').text();
	$("#app_rej_cylinder_count_id").val(cylinderReqCount);
	$("#app_rej_id").val(requestId);
	$("#req_disp_stat").val(requestDisplayStatus);
	if(requestDisplayStatus ===APPROVED_DISPLAY_STATUS){
		$(".admin_comment_section label").text("Add Request Completion/Cancellation Comment:");
		$(".approve_request").text("Update");
	}else{
		$(".admin_comment_section label").text("Add Approval/Rejection Comment:");
		$(".approve_request").text("Approve");
	}
});


