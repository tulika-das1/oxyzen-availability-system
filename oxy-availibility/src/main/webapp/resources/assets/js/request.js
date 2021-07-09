/**
 * 
 */

$(document).ready(function(){
	
	var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
	$(document).ajaxSend(function(e, xhr, options) {
	    xhr.setRequestHeader(header, token);
	  })

    $("#req").click(function(e){
           e.preventDefault(); 
   
       var number=$("#number").val();
       var datetime=$("#datetime").val();
       var name=$("#name").val();
       var name_regex=/^[a-zA-Z]+/;
   
       if(number == ''){
           $("#show_error").text('** The number must be filled.');
           $("#show_error").css('color','red');
           
           return false;
       }
       else if(number < 1){
           $("#show_error").text('** The number must be positive.');
           $("#show_error").css('color','red');
           
           return false;
       }
       else  if(datetime == ''){
           $("#show_error1").text('** The date must be filled.');
             $("#show_error1").css('color','red');
             
             return false;
       }
       else if(name.length == ''){
           $("#show_error2").text('** The name must be filled.');
             $("#show_error2").css('color','red');
             
             return false;
       }
       else if(!name.match(name_regex) || name.length==0) {
           $("#show_error2").text('**For your name please use alphabet only.');
             $("#show_error2").css('color','red');
             
             return false;
       }
       
       let userid = $('#userid').val();
       let data = {'cylinderRequestNo':number,'requestDate':datetime,'requester':name, 'userid':userid};//
       console.log(data);
       
       
       $.ajax({
           type : "POST",
           url : '/generate_request',
           contentType: "application/json",
           Accept: "application/json", 
           data : JSON.stringify(data),
           success : function(response) {
               alert( response );
           },
           error : function() {
               alert("not working");
           }
    });
       
       var row = "<tr><td>" +number+ "</td><td>" + datetime + "</td><td>" + name 
       + "</td><td><button class='btn btn-info btn-lg btn-edit mr-3' type='button'>Edit</button>&nbsp;&nbsp;<button class='btn btn-danger btn-lg btn-delete' type='button'>Delete</button></td></tr>" ;
        $(" table tbody").append(row);
   
        $("#number").val('');
        $("#datetime").val('');
        //$("#name").val('');
   
       });
       
   });


$('body').on('click','.btn-edit',function(){


    


    var number=$(this).parents('tr').find('td:eq(0)').text();
    var datetime=$(this).parents('tr').find('td:eq(1)').text();
    var name=$(this).parents('tr').find('td:eq(2)').text();

    
   
    
    
    $(this).parents('tr').find('td:eq(0)').html("<input name='edit_number' value='"+number+"'>");
    $(this).parents('tr').find('td:eq(1)').html("<input name='edit_datetime' value='"+datetime+"'>");
    $(this).parents('tr').find('td:eq(2)').html("<input name='edit_name' value='"+name+"'>");
    $(this).parents('tr').find('td:eq(3)').prepend("<button type='button' class='btn btn-info btn-lg btn-update mr-3'>update</button>");
    $(this).hide()
});

$('body').on('click','.btn-update',function(){
    
    
    var number=$(this).parents('tr').find("input[name='edit_number']").val();
    var datetime=$(this).parents('tr').find("input[name='edit_datetime']").val();
    var name=$(this).parents('tr').find("input[name='edit_name']").val();

    
    console.log(number);
    console.log(datetime);
    console.log(name);

    
    $(this).parents('tr').find('td:eq(0)').text(number);
    $(this).parents('tr').find('td:eq(1)').text(datetime);
    $(this).parents('tr').find('td:eq(2)').text(name);

    
    $(this).parents('tr').attr('number',number);
    $(this).parents('tr').attr('datetime',datetime);
    $(this).parents('tr').attr('name',name);

    $(this).parents('tr').find('.btn-edit').show();
    $(this).parents('tr').find('.btn-update').remove();
    $(this).parents('tr').find('.btn-delete').remove();

});

$('body').on('click','.btn-delete',function(){
   
    console.log(this);
    $(this).parents('tr').remove();

});