var socket = io();
function Driver(name, password, car, location, coordinates, phone, email, special, status){
    this.name = name;
    this.password = password
    this.car = car;
    this.location = location;
    this.coordinates = coordinates;
    this.phone = phone;
    this.email = email;
    this.special = special;
    this.status = status;
}

var registered_drivers = []

function Customer(from, to, pick, drop, est, name, phone, email, spec, pass, oth, id, selected) {
    this.to = to;
    this.pickuptime = pick;
    this.dropTime = drop;
    this.estimateTime = est;
    this.name = name;
    this.phone = phone;
    this.email = email;
    this.special = spec;
    this.passengers = pass;
    this.other = oth;
    this.id = id;
    this.selected = selected;
}

var custarray = [];

var cust_index = 0;

function getEmail() {
    return document.getElementById('email').value
}

var app = new Vue({
    el: '#taxi-app',
    data: {
        loggedIn: true,
        user: '',
        password: '',
        incomingJob: '',
        currentUser: getEmail(),
        currentJob: '',
        mainButtonText: '\u25B2',
        jobExpanded: false,
        map: null,
        markers: {},
        line: null,
        control:null,
        progress: '',
        progressButtonText: 'Kund Upphämtad'
    },

    
    
    
    
    created: function () {
        //socket.on('driverInfo', function (data)
        socket.on('driverInfo', function (indata) {
            var data = indata.user
            console.log(indata.driver.email)
            console.log(this.currentUser.email)
            if(indata.driver.email === this.currentUser) {
                this.incomingJob = data;
                console.log("enter")
                if(data == null || data.from == null || data.to == null){
                    console.log(data);

                    return;
                }
                console.log("test");
                if(this.markers[0] && this.markers[1]) {
                    //this.map.removeLayer(this.line);
                    this.map.removeLayer(this.markers[0]);
                    this.map.removeLayer(this.markers[1]);          
                    //console.log(this.markers);
                    delete this.markers[0];
                    delete this.markers[1];
                }
                console.log(data);


                
                this.markers[0] = this.createMarker(data.from);
                this.markers[1] = this.createMarker(data.to);
                var latlngs = Array();
                var marker1 = new L.LatLng(data.from.lat, data.from.lng);
                var marker2 = new L.LatLng(data.to.lat, data.to.lng);
                latlngs.push(marker1);

                latlngs.push(marker2);
                if(this.control) {
                    this.map.removeControl(this.control);
                }
                this.control = L.Routing.control({
                    waypoints: latlngs,
                    show: false,
                    waypointMode: 'snap',
                    createMarker: function() {}
                }).addTo(this.map);
            }
        }.bind(this));
    },
    methods: {
        logIn: function() {
            this.currentUser = registered_drivers.filter(e => e.email == this.user)[0];
            if (this.currentUser != undefined) {
                if (this.currentUser.password == this.password) {
                    this.loggedIn = true;
                }
                else window.alert("fel lösenord");
            }
            else {
                window.alert("Användare existerar inte!");
            }
        },
        changeStatus: function() {
            if (this.currentJob == '') {
                if (this.currentUser.status == 'Upptagen') {
                    this.currentUser.status = 'Tillgänglig';
                }
                else if (this.currentUser.status == 'Tillgänglig'){
                    this.currentUser.status = 'Upptagen';
                }
            }
            else {
                window.alert("Kan inte ta paus under pågående körning!");
            }
        },
        declineOrder: function(){
            
            socket.emit('jobDeclined', {user : this.incomingJob, driver : this.currentUser});
            this.incomingJob = '';
            this.currentUser.status = 'Tillgänglig';
            this.mainButtonText = '\u25B2'
        },
        mainButtonFunc: function() {
            if (this.incomingJob != '') {
                this.currentJob = this.incomingJob;
                this.currentUser.status = 'Upptagen';
                this.incomingJob = '';
                console.log('jobAccepted')
                socket.emit('jobAccepted', {user : this.incomingJob, driver : this.currentUser});
                this.mainButtonText = '\u25B2'
            }
            else if (this.currentJob != '') {
                this.jobExpanded = !this.jobExpanded;
                if (this.jobExpanded) {
                    this.mainButtonText = '\u25BC';
                }
                else {
                    this.mainButtonText = '\u25B2';
                }

                

            }
        },
        orderFinished: function() {
            if (this.progress == '') {
                this.jobExpanded = false;
                this.progress = 'picketup';
                this.progressButtonText = 'Kund Avlämnad'
                this.mainButtonText = '\u25B2';
            }
            else {
                this.currentJob = '';
                this.currentUser.status = 'Tillgänglig';
                this.progress = '';
                this.progressButtonText = 'Kund Upphämtad';
                this.mainButtonText = '\u25B2';
            }
        },
        TESTgetJob: function() {
            this.incomingJob = custarray[cust_index % 2];
            cust_index ++;
        },
        
        createMarker: function (point) {

            var pointA = new L.LatLng(point.lat, point.lng);
            var marker = L.marker(pointA).addTo(this.map);
            return marker;
        }
    },    

    computed: {
        status: function() {
            return this.currentUser.status; 
        }
    },
    watch: {
        incomingJob: function(){
            if (this.incomingJob != '') {
                this.mainButtonText = 'Acceptera';
            }
        },
        
        
    },
    mounted: function () {
        this.map = L.map('my-map').setView([51.505, -0.09], 13);
        L.tileLayer('http://{s}.tile.osm.org/{z}/{x}/{y}.png', {
            maxZoom: 18,
        }).addTo(this.map);
    }
})
