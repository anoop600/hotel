$(document).ready(function() {

});
function logOut(){
    let loginParam=JSON.parse(window.localStorage.getItem("customer"));
    window.localStorage.removeItem("customer");
    alert("i logged out");
    window.location.href ="http://localhost:3000/static/homePage.html";
}
function logIn(){
    debugger;
    window.localStorage.clear();
 let logInParam=[$("#custEmailLogin").val(), $("#custPasswordLogin").val()];
// alert(JSON.parse(window.localStorage.getItem("customer")));
 $.ajax({
    type:"POST",
    dataType: 'json',
    url:"http://localhost:8092/customer/verify",
    data: JSON.stringify(logInParam),
    beforeSend : function(request) {
        request.setRequestHeader("Content-Type", "application/json");
        request.setRequestHeader("Accept", "application/json");
    },
   
    complete : function(result) {
        if(result.status==200){
            window.location.href ="http://localhost:3000/static/homePage.html";
            window.localStorage.setItem("customer",JSON.stringify(logInParam));
        }else{
            alert("Invalid email / password...please enter valid email and password");
            window.location.href ="http://localhost:3000/static/login.html";
        }
       
    }
});

   
}
function signInCustomer(){
    debugger;
    let custName=$("#custName").val();
    let custCity=$("#custCity").val();
    let custState=$("#custState").val();
    if(custName==null|| custCity==null||custState==null){
        alert("form was not properly filled");

    }
    let mob=$("#custMobile").val();
    if(mob.length!=10){
        alert("not valid mobile number");

    }
    
    if($("#custPassword").val()==$("#custRPassword").val()){
        let customer={
            customerName:custName,
            mobileNumber:mob,
            email:$("#custEmail").val(),
            password:$("#custPassword").val(),
            city:custCity,
            state:custState,
            loggedInWith:false
        };
        let logInParam=[$("#custEmail").val(),$("#custPassword").val()];
        $.ajax({
            type:"POST",
            dataType: 'json',
            url:"http://localhost:8092/customer/addCustomer",
            data: JSON.stringify(customer),
            beforeSend : function(request) {
				request.setRequestHeader("Content-Type", "application/json");
				request.setRequestHeader("Accept", "application/json");
			},
           
            complete : function(result) {
                if(result.status==200){
                    window.localStorage.setItem("customer",JSON.stringify(logInParam));
                    console.log("message : " + result.stringify);
                    window.location.href ="http://localhost:3000/static/homePage.html";
                }
                else{
                    alert("unable to create account")
                    window.location.href ="http://localhost:3000/static/signup.html";
                }
               
               
			}
        });
       
        
    }
    else{
        alert("passwords does not match")
        window.location.href ="http://localhost:3000/static/signup.html";
    }
    
}

function findHotel(){
    debugger;
    
    
    let cityName=$('#city').val();
    if(!$('#city').val()){
        alert("enter valid city name");
    }
    $.ajax({
        type : "GET",
        dataType: 'json',
        url : "http://localhost:8092/hotel/city/"+cityName,
        success : function(response) {
            var data=JSON.parse(JSON.stringify(response));
            alert(data.length);
            $(".hotelCard").remove();
            for(var i=0;i<data.length;i++){
                $('#hotels').append('<div class="card-panel hotelCard col s12 m6 blue lighten-2 "><p> Hotel Name:'+data[i].hotelName+', '+data[i].hotelCity+'</p><hr><p>Hotel has '+data[i].totalRooms+' rooms ,Per day charges are '+data[i].tariff+'</p><hr><p>Hotel is '+data[i].hotelRating+'stars</p></div>');
            }
            $("#cph").css("display","block");
            $("#cp").css("display","block");
            $("#bookHotel").css("display","block");
   
        }
    });
}
function findHotelByLowestPrice(){
    let cityName=$('#cityLP').val();
   
    if(!$('#cityLP').val()){
        alert("enter valid city name");
        return;
    }
    $.ajax({
        type : "GET",
        dataType: 'json',
        url : "http://localhost:8092/hotel/byLowestPrice/"+cityName,
        success : function(response) {
            var data=JSON.parse(JSON.stringify(response));
            alert(data.length);
            $(".hotelCard").remove();
            for(var i=0;i<data.length;i++){
                $('#hotels').append('<div class="card-panel hotelCard col s12 m6 blue lighten-2 "><p> Hotel Name:'+data[i].hotelName+', '+data[i].hotelCity+'</p><hr><p>Hotel has '+data[i].totalRooms+' rooms ,Per day charges are '+data[i].tariff+'</p><hr><p>Hotel is '+data[i].hotelRating+'</p></div>');
            }
            $("#cph").css("display","block");
            $("#cp").css("display","block");
            $("#bookHotel").css("display","block");
   
        }
    });
}
function bookHotel(){
    $("#bookHotel").css("display","none");
    $("#boookingForm").css("display","block");
}
function checkAvailability(){
    debugger;
    $("#mainForm").css("display","none");
    let loginParam= JSON.parse(window.localStorage.getItem("customer"));
    if(loginParam==null){
        alert("Sign Up / Login to book your hotel");
        window.location.href="http://localhost:3000/static/homePage.html";
        return;
    }
    if(! $("#checkIn").val()){
        alert("enter valid check in date");
        return;
    }
    if(! $("#checkOut").val()){
        alert("enter valid check out date");
        return;
    }
    if(! $("#roomCount").val()){
        alert("enter valid room count");
        return;
    }
    if(! $("#finalCity").val()){
        alert("enter valid city");
        return;
    }
    if(! $("#hotelName").val()){
        alert("enter valid hotel name");
        return;
    }
    let checkAvailParam=[$("#hotelName").val(),$("#finalCity").val(),loginParam[0],loginParam[1],$("#checkIn").val(),$("#checkOut").val(),$("#roomCount").val()];

    $.ajax({
        type : "POST",
        dataType: 'json',
        url : "http://localhost:8092/booking/verify",
        data: JSON.stringify(checkAvailParam),
        beforeSend : function(request) {
            request.setRequestHeader("Content-Type", "application/json");
            request.setRequestHeader("Accept", "application/json");
        },
        complete : function(response) {
           if(response.status==200){
               let result=confirm("rooms are available...click ok to continue for booking");
               if(result==true){
                let booking=JSON.parse(response.responseText);
                alert(booking);
                let ans=confirm("Hi "+booking.customer.customerName+" your booking from   "+booking.checkIn+" to "+booking.checkOut+"has been confirmed in "+booking.hotel.hotelName+" hotel for "+booking.hotel.totalRooms+" and charges per day per room would be "+booking.hotel.tariff+"/-");
                $.ajax({
                    type:"POST",
                    dataType: 'json',
                    url:"http://localhost:8092/booking/addBooking",
                    data: JSON.stringify(booking),
                    beforeSend : function(request) {
                        request.setRequestHeader("Content-Type", "application/json");
                        request.setRequestHeader("Accept", "application/json");
                    },
                   
                    complete : function(result) {
                        if(result.status==200){
                           alert("booking confirmed");
                           location.reload();
                        }else{
                            alert("booking is not confirmed");
                            location.reload();
                            
                        }
                       
                    }
                });
               }else{
                alert("rooms where available but you choosed to discontinue....");
                location.reload();
               }
               
           }else{
            alert("The required number of rooms are not available");
            location.reload();
           }
        }
    });


}
function goBack(){
    window.location.href="http://localhost:3000/static/homePage.html";
}

function hideShow(){
    let obj=JSON.parse(window.localStorage.getItem("customer"));
    if(obj==null){
        $(".logged").css("display","block");
        $(".signedIn").css("display","none");
        
    }else{
        $(".logged").css("display","none");
        $(".signedIn").css("display","block");
    }

}