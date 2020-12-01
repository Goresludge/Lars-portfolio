var socket = io()


// Map
var vm = new Vue({
    el: '#page',
    data: {
        map: null,
        markers: {},
        line: null,
        control:null,
        cars: []
    },
    mounted: function () {
      this.map = L.map('map').setView([51.505, -0.09], 13);
      L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
        maxZoom: 18,
      }).addTo(this.map);
        
    },
    methods: {
        updateCarPoint: function() {
            while(cars.length > 0) {
                const car = cars.pop()
                vm.map.removeLayer(car)
            }
            const drivers = filterDrivers(availDrivers)
            for(var i=0; i<drivers.length; i++) {
                const c = drivers[i].coordinates
                var pointA = new L.LatLng(c.x, c.y);
                var marker = L.marker(pointA).addTo(this.map);
                cars.push(marker)
            }
            
        },
      createMarker: function (point) {
  
          var pointA = new L.LatLng(point.lat, point.lng);
          var marker = L.marker(pointA).addTo(this.map);
          
          return marker;
      },
      createRoute: function (data) {
        //console.log("enter")
        if(data == null || data.from == null || data.to == null){
            console.log(data);

            return;
        }
        //console.log("test");
        if(vm.markers[0] && vm.markers[1]) {
          //this.map.removeLayer(this.line);
          vm.map.removeLayer(vm.markers[0]);
          vm.map.removeLayer(vm.markers[1]);          
          //console.log(this.markers);
          delete vm.markers[0];
          delete vm.markers[1];
          //console.log(this.markers);
          //console.log(this.markers[0]);
          //console.log(this.markers[1]);
        }
        //console.log(data);


        
        vm.markers[0] = vm.createMarker(data.from);
        vm.markers[1] = vm.createMarker(data.to);
        var latlngs = Array();
        var marker1 = new L.LatLng(data.from.lat, data.from.lng);
        var marker2 = new L.LatLng(data.to.lat, data.to.lng);
        latlngs.push(marker1);

        latlngs.push(marker2);
        if(this.control) {
          vm.map.removeControl(vm.control);
        }
        vm.control = L.Routing.control({
          waypoints: latlngs,
          show: false,
          waypointMode: 'snap',
          createMarker: function() {}
        }).addTo(vm.map);
    }
    }
  });
  


  function Customer(from, to, pick, name, phone, email, spec, pass, oth, id, selected) {
    this.from = from; // The this keyword refers to the object itself
    this.to = to;
    this.pickuptime = pick;
    this.name = name;
    this.phone = phone;
    this.email = email;
    this.special = spec;
    this.passengers = pass;
    this.other = oth;
    this.id = id;
}

function Driver(name, car, location, coordinates, phone, email, special, pass, status){
    this.name = name;
    this.car = car;
    this.location = location;
    this.coordinates = coordinates;
    this.phone = phone;
    this.email = email;
    this.special = special;
    this.passengers = pass;
    this.status = status;
}

//test customers
//var cust = new Customer('Centralstation', 'Glassgatan 1', '12:15', 'Terese Björk', '070-1234567', 'testmail@gmail.com', ['Djur','Färdtjänst'], 2 ,'Vill ej att förare ska prata med tjänstefolket',0);
//var cust1 = new Customer('Polacks', 'Flogstavägen 1', '12:24',  'Adam Johansson', '070-1234567', 'testmail@gmail.com', [''], 2 ,'',1);

var availCustomers = []
var serverUsers = []

//var driv1 = new Driver('Tatjana Svetlana', 'Lada Super', 'Centralstationen', {x: 1234, y: 42342},'072-1234562', 'svetlana@gmail.com', ['Färdtjänst'], 7 ,'Tillgänglig');
//var driv2 = new Driver('Johan Andersson', 'Volvo 740', 'Blåsenhus', {x: 1234, y: 42342},'072-1234562', 'svennebanan@gmail.com', ['Djur', 'Färdtjänst','Rullstol'], 5 ,'Tillgänglig');
//var driv3 = new Driver('Mgabe Magadesh', 'BMW 520', 'Fyris', {x: 1234, y: 42342}, '072-1234562', 'mgabe@gmail.com', ['Djur'], 4 ,'Upptagen');

var availDrivers = []

function orderMatch(driver, customer){

    var matchedDriver = driver;
    var matchedCustomer = customer;

}

Vue.component('vcOrder',{
    template: '<li class="order-style" v-bind:class="{active:isActive}" @click="toggle_active">\
                   <table style="margin-bottom:5px;">\
                       <tr>\
                           <td style="font-weight:bold;">{{ name }}</td><td style="text-align:right;">{{ pickuptime }}</td>\
                       </tr>\
                   </table>\
                   <table>\
                       <tr>\
                           <td style="width: 84px;">Från: </td><td>{{from}}</td>\
                       </tr>\
                       <tr>\
                           <td>Till: </td><td>{{to}}</td>\
                       </tr>\
                   </table>\
                   <div style="position:relative;" id="sec-info" v-if="isActive">\
                       <table>\
                           <tr>\
                               <td style="width: 70px;">Tele: </td><td>{{ phone }}</td>\
                           </tr>\
                           <tr>\
                               <td>Email: </td><td>{{ email }}</td>\
                           </tr>\
                           <tr>\
                               <td>S. Behov: </td><td> {{ special.toString() }}</td>\
                           </tr>\
                           <tr>\
                               <td>Passagerare: </td><td> {{ passengers }}</td>\
                           </tr>\
                           <tr>\
                               <td>Övrigt:</td><td>{{ other }}</td>\
                           </tr><br>\
                       </table>\
                       <div style="bottom:10px">\
                           <div style="float:left; width:50%;">\
                               <button class="button left" @click.stop="remove_order">Ta bort</button></div>\
                           </div>\
                           <div style="display:inline-block; width:50%;">\
                               <button class="button right" @click.stop="match_driver">Hitta chaufför</button></div>\
                           </div>\
                       </div>\
                   </div>\
               </li>',
    props: ['activeOrder', 'index', 'name', 'pickuptime', 'from', 'to', 'phone', 'email', 'special', 'passengers', 'other'],
    data: function() {
        return {
            childKey: '',
            isActive: false,
            disabled: false
        }
    },
    methods: {
        toggle_active: function() {
            this.$emit('activation-interface', this.isActive ? '' : this.childKey);

            if (this.activeOrder == this.childKey) {
                this.isActive = true
            }
            else {
                this.isActive = false
            }
        },
        remove_order: function() {
            this.$emit('remove', this.index)
        },
        match_driver: function(){
            this.$emit('match', this.index)
        }

    },
    watch: {
        activeOrder: function() {
            if (this.activeOrder == this.childKey) this.isActive = true;
            else this.isActive = false;
        }
    },
    beforeMount () {
        this.childKey = this.index
    }
})


var vmOrders = new Vue({
    el: '#columnOrders',
    data: {
        color: 'initial',
        customers: availCustomers,
        nextCustomerId: 0,
        selectedOrder: null,
        displayDrivers: false
    },
    created: function(){
        socket.on('sendAllOrders', function(data){
            console.log('sendAllOrders')
            while(availCustomers.length > 0) {
                availCustomers.pop();
            }
            while(availCustomers.length > 0) {
                serverUsers.pop();
            }
            for(var i in data) {
                vmOrders.addNewCustomer(data[i])
            }
            
        })
        socket.on('sendAllDrivers', function(data){
            console.log('sendAllDrivers')
            console.log(data)
            while(availDrivers.length > 0) {
                availDrivers.pop();
            }
            for(var i in data) {
                availDrivers.push(data[i])
            }
            
        })
    },
    methods: {

        setCurrentActive: function(childKey) {
            this.selectedOrder = childKey
            this.displayDrivers = false

            var serverUser = serverUsers[childKey]
            //console.log(serverUser)
            vm.createRoute(serverUser)
        },

        removeCustomer: function(){
            if(confirm("Vill du verkligen ta bort ordern?")){
                vmOrders.customers.splice(vmOrders.selectedOrder,1)
              }
              // TODO remove serverUser
        },
        showDrivers: function(){
            this.displayDrivers = true
        },

        addNewCustomer: function (user) {
            var specArr = [];
            if(user.special.djur){
              specArr.push('Djur');
            }
            if(user.special.fardtjanst){
              specArr.push('Färdtjänst');
            }
            if(user.special.rullstolsbunden){
              specArr.push('Rullstol');
            }
            name = user.username,
            phone = user.phone,
            email = user.email,
            from = user.from.name,
            to = user.to.name,
            pickuptime = user.time.t,
            passengers = user.special.passengers,
            special = specArr,
            other = user.special.otherData

            this.customers.push({
                id: this.nextCustomerId++,
                from: from,
                to: to,
                pickuptime: pickuptime,
                passengers: passengers,
                name: name,
                phone: phone,
                email: email,
                special: special,
                other: other
            })
            this.newCustomerText = ''

            serverUsers.push(user)
        }
    }
})


Vue.component('vcDrivers',{
    template: '<li class="order-style" v-bind:class="{active:isActive}" @click="toggle_active">\
                   <table style="margin-bottom:5px;">\
                       <tr>\
                           <td style="font-weight:bold;">{{ name }}</td><td style="text-align:right;">{{ status }}</td>\
                       </tr>\
                   </table>\
                   <table>\
                       <tr>\
                           <td style="width: 70px;">Location: </td><td>{{ location }}</td>\
                       </tr>\
                       <tr>\
                           <td>Distans: </td><td>{{ distance }}</td>\
                       </tr>\
                   </table>\
                   <div style="position:relative;" id="sec-info" v-if="isActive">\
                       <table>\
                           <tr>\
                               <td style="width: 70px;">Tele: </td><td>{{ phone }}</td>\
                           </tr>\
                           <tr>\
                               <td>Bilmodell: </td><td> {{ car }}</td>\
                           </tr>\
                           <tr>\
                               <td>Platser: </td><td> {{ passengers }}</td>\
                           </tr>\
                           <tr>\
                               <td>S.tillgång: </td><td> {{ special.toString() }}</td>\
                           </tr>\
                           </tr>\
                           <tr>\
                               <td>Telefon: </td><td> {{ phone }}</td>\
                           </tr>\
                           <tr>\
                               <td>Email: </td><td> {{ email }}</td>\
                           </tr>\
                       </table><br>\
                       <div style="bottom:10px">\
                           <div style="display:inline-block; width:50%;">\
                               <button class="button right" @click.stop="confirm_order">Bekräfta beställning</button></div>\
                           </div>\
                       </div>\
                   </div>\
               </li>',
    props: ['selectedDriver','index', 'name', 'car', 'location', 'coordinates', 'phone', 'email', 'special', 'passengers','status'],
    data: function() {
        return {
            childKey: '',
            isActive: false,
            disabled: false
        }
    },
    methods: {
        toggle_active: function() {
            this.$emit('activation-interface', this.isActive ? '' : this.childKey);

            if (this.selectedDriver == this.childKey) {
                this.isActive = true
            }
            else {
                this.isActive = false
            }
        },
        confirm_order: function() {
          this.$emit('confirm', this.index)
        }
    },
    computed: {
        distance: function() {
            return this.coordinates.x - this.coordinates.y
        }
    },
    watch: {
        selectedDriver: function() {
            if (this.selectedDriver == this.childKey) this.isActive = true;
            else this.isActive = false;
        }
    },
    beforeMount () {
        this.childKey = this.index
    }
})


var vmDrivers = new Vue({
    el: '#columnDrivers',
    data: {
        drivers: availDrivers,
        selectedDriver: null
    },
    methods: {
        setCurrentActive: function(childKey) {
            this.selectedDriver = childKey
        },
        filterDrivers: function(drivers){
          if(vmOrders.displayDrivers != false){
            customerIndex = vmOrders.selectedOrder;
            specialNeeds = vmOrders.customers[customerIndex].special;
            return drivers.filter(function (driver){
                return (driver.status=='Tillgänglig') && (availCustomers[customerIndex].passengers <= driver.passengers)
                /*
              if((specialNeeds[0] != '') && (specialNeeds.length != 0)){
              for(i = 0; i < specialNeeds.length; i++){
                return ((driver.status == 'Tillgänglig') && (vmOrders.customers[customerIndex].passengers <= driver.passengers) &&
              (driver.special.includes(specialNeeds[i])))
            }
          }
              else {
                return((driver.status == 'Tillgänglig') && (vmOrders.customers[customerIndex].passengers <= driver.passengers));

              }
              */

            })
          }
        },
        placeOrder: function(){
          driverIndex = vmDrivers.selectedDriver;
          customerIndex = vmOrders.selectedOrder;
          if(driverIndex != null && customerIndex != null){
            if(confirm("Godkänn order")){
                console.log(serverUsers[customerIndex])
                console.log(vmDrivers.drivers[driverIndex])
                socket.emit('driverMatch', {user:serverUsers[customerIndex], driver:availDrivers[driverIndex]})
                orderMatch(vmDrivers.drivers[driverIndex],vmOrders.customers[customerIndex]),
              vmDrivers.drivers[driverIndex].status = 'Upptagen',
              vmOrders.customers.splice(customerIndex,1),
              
              

              
              
              window.alert("Order lagd"),
              vmOrders.displayDrivers = false
            }
          }
          else {
            window.alert("Välj en kund till chauffören")
          }
        }
    }
})

var vmMan = new Vue({
    el: '#addManually',
    data:{
      isActive: false,
      newCustomerFrom: '',
      newCustomerTo: '',
      newCustomerPickup: '',
      newCustomerPassengers: '',
      newCustomerName: '',
      newCustomerPhone: '',
      newCustomerEmail: '',
      newCustomerSpecial: '',
      newCustomerOther: ''
    },
    methods:{
      toggle_active: function() {
          if (this.isActive == false) {
              this.isActive = true
          }
          else {
              this.isActive = false
          }
      },
      addNewCustomerManual: function() {
        from = document.getElementById("newCustomerFrom").value,
        to = document.getElementById("newCustomerTo").value,
        pickuptime = document.getElementById("newCustomerPickup").value,
        passengers = document.getElementById("newCustomerPassengers").value,
        name = document.getElementById("newCustomerName").value,
        phone = document.getElementById("newCustomerPhone").value,
        email = document.getElementById("newCustomerEmail").value,
        special = [],
        special.push(document.getElementById("newCustomerSpecial").value),
        other = document.getElementById("newCustomerOther").value
        if(from != '' && to != ''  && pickuptime != ''  && passengers != ''  && name != ''  && phone != ''){
          vmOrders.customers.push({
            id: vmOrders.nextCustomerId++,
            from: from,
            to: to,
            pickuptime: pickuptime,
            passengers: passengers,
            name: name,
            phone: phone,
            email: email,
            special: special,
            other: other
          })
          window.alert("Order tillagd");
        }
        else {
          window.alert("Skriv in alla obligatoriska fält");
        }
        
      }
    }
})


