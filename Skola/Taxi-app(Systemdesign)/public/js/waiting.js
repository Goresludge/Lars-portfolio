var socket = io();

var vm = new Vue({
    el: '#wait',
    created: function(){
        console.log('vue');
        socket.on('approvedOrder', function(data){
            console.log('hi');
            window.location.href = "http://localhost:3000/user/approved";
        });
        socket.on('declinedOrder', function(data){
            console.log('hi');
            window.location.href = "http://localhost:3000/user/declined";
        });
    }
});
