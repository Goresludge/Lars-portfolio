/*jslint node: true */
/* eslint-env node */
'use strict';

// Require express, socket.io, and vue
const express = require('express');
const app = express();
const http = require('http').Server(app);
const io = require('socket.io')(http);
const path = require('path');
const bodyParser = require('body-parser')
const cookieParser = require('cookie-parser')
const session = require('express-session')
const fs = require('fs')

var appData = {}
function loadData() {
  fs.readFile('data/data.json', function(err, data){
    if(err){
      appData ={
        users:[],
        drivers:[]
      }
    } else {
      appData = JSON.parse(data)
    }
  })
}
function saveData() {
  fs.writeFile('data/data.json', JSON.stringify(appData), function(err){
    if(err) throw err
  })
}
loadData()

// Pick arbitrary port for server
var port = 3000;
app.set('port', (process.env.PORT || port));



// init bodyParser and cookieParser
app.use(bodyParser.urlencoded({ extended: false }))
app.use(bodyParser.json())
app.use(cookieParser())

// init session
app.use(session({
  secret: 'keyboard example',
  resave: false,
  saveUninitialized: true
}))


// init template engine
app.set('view engine', 'ejs')
app.set('views', path.join(__dirname, 'views'))



// Serve static assets from public/
app.use(express.static(path.join(__dirname, 'public/')));
// Serve vue from node_modules as vue/
app.use('/vue', express.static(path.join(__dirname, '/node_modules/vue/dist/')));
// Serve leaflet from node_modules as leaflet/
app.use('/leaflet', express.static(path.join(__dirname, '/node_modules/leaflet/dist/')));
// Serve index.html directly as root page
app.get('/', function (req, res) {
  res.sendFile(path.join(__dirname, 'views/index.html'));
});
// Serve map.html as /map
app.get('/map', function (req, res) {
  res.sendFile(path.join(__dirname, 'views/map.html'));
});
// Serve dispatcher.html as /dispatcher
app.get('/dispatcher', function (req, res) {
  res.sendFile(path.join(__dirname, 'views/dispatcher.html'));
});
app.get('/admin', function (req, res) {
  res.render('admin/admin')
});






// Serve user files
app.get('/user', function(req, res) {
  res.redirect('/user/login')
})
app.get('/user/approved', function(req, res) {
  res.render('user/approved', {title:"Godkänd", action:"/user/approved", user:req.session.user})
})
app.get('/user/declined', function(req, res) {
  res.render('user/declined', {title:"Icke godkänd, gör om beställningen", action:"/user/declined", user:req.session.user})
})
app.get('/user/incorrect', function(req, res) {
  res.render('user/incorrect', {title:"Fel användarnamn eller lösenord", action:"/user/incorrect", user:req.session.user})
})
app.get('/user/confirm', function(req, res) {
  if(req.session.user)
    res.render('user/confirm', {title:"Stämmer uppgifterna?", action:"/user/confirm", user:req.session.user})
  else
    res.redirect('/user/login')
})
app.get('/user/dest', function(req, res) {
  if(req.session.user) {
    res.render('user/dest', {title:"Välj Till/Från Destination", action:"/user/dest", user:req.session.user})
  } else
    res.redirect('/user/login')
})
app.get('/user/login', function(req, res) {
  if(req.session.user) {
    res.redirect('/user/dest')
  } else {
    res.render('user/login', {title:"Logga in", action:"/user/login"})
  }
})
app.get('/user/register', function(req, res) {
  res.render('user/register', {title:"Skapa Ny Användare", action:"/user/register"})
})
app.get('/user/special', function(req, res) {
  if(req.session.user)
    res.render('user/special', {title:"Välj Specialbehov", action:"/user/special", user:req.session.user})
  else
    res.redirect('/user/login')
})
app.get('/user/time', function(req, res) {
  if(req.session.user)
    res.render('user/time', {title:"Vilken Tid Vill Du åka?", action:"/user/time", user:req.session.user})
  else
    res.redirect('/user/login')
})
app.get('/user/waiting', function(req, res) {
  if(req.session.user) {
    req.session.user.done = true
    updateUser(req.session.user)
    saveData()
    io.emit('sendAllOrders', getAllDoneUsers())
    res.render('user/waiting', {title:"Beställning mottagen", action:"/user/waiting", user:req.session.user})
  } else
    res.redirect('/user/login')
})
app.get('/user/status', function(req, res) {
  res.render('user/status', {title:'Status', action:"/user/status"})
})


app.post('/user/login', function(req, res){
  const users = appData.users
  for(var i=0; i<users.length; i++) {
    const user = users[i]
    if(req.body.username == user.username && req.body.password == user.password) {
      req.session.user = user
      break;
    }
  }
  if(req.session.user) {
    console.log('Login with '+req.session.user.username)
    res.redirect('/user/dest')
  } else {
    console.log('Username and password are incorrect')
    res.redirect('/user/incorrect')
  }

})
app.get('/user/logout', function(req, res){
  req.session.user.done = false

  updateUser(req.session.user)
  saveData()
  io.emit('sendAllOrders', getAllDoneUsers())

  delete req.session.user;
  res.redirect('/user/login');
})
app.post('/user/register', function(req, res){
  const user = {
    username: req.body.name,
    phone: req.body.phone,
    email: req.body.email,
    password: req.body.password,
    from:{lat:0, lng:0, name:" "},
    to:{lat:0, lng:0, name:" "},
    time:{
      now:true,
      t:""
    },
    special:{
      passengers:1,
      djur:false,
      fardtjanst:false,
      rullstolsbunden:false,
      other:false,
      otherData:""
    },
    done:false
  }
  if(user.password == req.body.password2) {
    appData.users.push(user)
    req.session.user = user
    saveData()
    console.log('New user created: '+user.username)
    res.redirect('/user/dest')
  }
})


app.post('/user/dest', function(req, res){
  if(req.session.user) {
    req.session.user.from = {lat:parseFloat(req.body.fromLat), lng:parseFloat(req.body.fromLng), name:req.body.from}
    req.session.user.to = {lat:parseFloat(req.body.toLat), lng:parseFloat(req.body.toLng), name:req.body.to}

    res.redirect('/user/time')
  } else {
    res.redirect('/user/login')
  }
})
app.post('/user/time', function(req, res){
  if(req.session.user) {
    req.session.user.time.now = (req.body.tid == 'now')
    if(req.body.tid == 'now') {
      var d = new Date(Date.now())
      var nowTime = ""+d.getHours()+":"+d.getMinutes()+""
      req.session.user.time.t = nowTime
    } else {
      req.session.user.time.t = req.body.time
    }
    res.redirect('/user/special')
  } else {
    res.redirect('/user/login')
  }
})
app.post('/user/special', function(req, res){
  if(req.session.user) {
    req.session.user.special.passengers = req.body.passengers
    req.session.user.special.djur = (req.body.djur=='on')
    req.session.user.special.fardtjanst = (req.body.fardtjanst=='on')
    req.session.user.special.rullstolsbunden = (req.body.rullstolsbunden=='on')
    req.session.user.special.other = (req.body.other=='on')
    req.session.user.special.otherData = req.body.otherData
    res.redirect('/user/confirm')
  } else {
    res.redirect('/user/login')
  }
})



// server driver files

app.get('/driver', function (req, res) {
  res.redirect('/driver/main')
});
app.get('/driver/main', function(req, res){
  if(req.session.driver) {
    res.render('driver/driver', {driver:req.session.driver})
  } else {
    res.redirect('/driver/login')
  }
})
app.get('/driver/login', function(req, res){
  res.render('driver/login')
})
app.get('/driver/register', function(req, res){
  res.render('driver/register', {action:'/driver/register'})
})
app.post('/driver/login', function(req, res){
  const drivers = appData.drivers
  for(var i=0; i<drivers.length; i++) {
    const driver = drivers[i]
    if(req.body.email === driver.email && req.body.password == driver.password) {
      req.session.driver = driver
      break;
    }
  }
  if(req.session.driver) {
    req.session.driver.status = 'Tillgänglig'
    req.session.driver.loggedin = true
    console.log('Login with '+req.session.driver.name)
    updateDriver(req.session.driver)
    saveData()
    io.emit('sendAllDrivers', getAllOnlineDrivers())
    res.redirect('/driver/main')
  } else {
    console.log('Username and password are incorrect')
    res.redirect('/driver/login')
  }
})
app.get('/driver/logout', function(req, res){
  if(req.session.driver) {
    req.session.driver.loggedin = false
    updateDriver(req.session.driver)
    saveData()
    io.emit('sendAllDrivers', getAllOnlineDrivers())
    delete req.session.driver;
  }
  res.redirect('/driver/login');
})
app.post('/driver/register', function(req, res){
  const driver = {
    name: req.body.name,
    password: req.body.password,
    car: req.body.car,
    location: "",
    coordinates: {x: parseFloat(req.body.lat),y:parseFloat(req.body.lng)},
    phone: req.body.phone,
    email: req.body.email,
    passengers: parseInt(req.body.passengers),
    special:{
      passengers: parseInt(req.body.passengers),
      djur:false,
      fardtjanst:false,
      rullstolsbunden:false
    },
    status: 'Tillgänglig',
    loggedin: false
  }
  appData.drivers.push(driver)
  saveData()
  console.log('New driver created: '+driver.name)
  res.redirect('/driver/login')
})



// Store data in an object to keep the global namespace clean and
// prepare for multiple instances of data if necessary
function Data() {
  this.orders = {};
}

/*
  Adds an order to to the queue
*/
Data.prototype.addOrder = function (order) {
  //Store the order in an "associative array" with orderId as key
  this.orders[order.orderId] = order;
};

Data.prototype.getAllOrders = function () {
  return this.orders;
};

var data = new Data();

io.on('connection', function (socket) {
  // Send list of orders when a client connects
  socket.emit('initialize', { orders: data.getAllOrders() });
  socket.emit('sendAllOrders', getAllDoneUsers())
  socket.emit('sendAllDrivers', getAllOnlineDrivers())
  
  // When a connected client emits an "addOrder" message
  socket.on('jobAccepted', function (data) {
        console.log('jobAccepted');
        var index = getUserIndex(data.user);
        if(index != -1){
            appData.users[index].done = false;
        }

        
        var driverIndex = getDriverIndexByEmail(data.driver);
        if(driverIndex != -1){
            appData.drivers[driverIndex].status = 'Upptagen';
        }
      //console.log(getAllDoneUsers())
      console.log('this is reached');
      io.emit('approvedOrder', null);
        socket.emit('sendAllOrders', getAllDoneUsers());
        socket.emit('sendAllDrivers', getAllOnlineDrivers());
        saveData();

    });
    socket.on('jobDeclined', function (data) {
        var index = getUserIndex(data.user);
        if(index != -1){
            appData.users[index].done = true;
        }
        var driverIndex = getDriverIndex(data.driver);
        if(driverIndex != -1){
            appData.drivers[driverIndex].status = 'TillgÃ¤nglig';
        }
        io.emit('declinedOrder', null);
        socket.emit('sendAllOrders', getAllDoneUsers());
        socket.emit('sendAllDrivers', getAllOnlineDrivers());
        saveData();

    });
    socket.on('driverMatch', function(data) {
      io.emit('driverInfo', data)
    })
  socket.on('addOrder', function (order) {
    data.addOrder(order);
    // send updated info to all connected clients, note the use of io instead of socket
    io.emit('currentQueue', { orders: data.getAllOrders() });
  });

});



var server = http.listen(app.get('port'), function () {
  console.log('Server listening on port ' + app.get('port'));
});


// filter functions

function getIndex(arr, elem) {
  for(const i in arr) {
    if(arr[i].email===elem.email) {
      return i
    }
  }
  return -1
}
function getUserIndex(user) {
  return getIndex(appData.users, user)
}
function getDriverIndex(driver) {
  return getIndex(appData.drivers, driver)
}
function getDriverIndexByEmail(email) {
  console.log(email)
  for(const i in appData.drivers) {
    if(appData.drivers[i].email===email) {
      return i
    }
  }
  return -1
}
function updateUser(user) {
  const index = getUserIndex(user)
  if(index == -1) {
    console.log('error user not found in data')
  } else {
    appData.users[index] = user
  }
}
function updateDriver(driver) {
  const index = getDriverIndex(driver)
  if(index == -1) {
    console.log('error driver not found in data')
  } else {
    appData.drivers[index] = driver
  }
}
function getAllDoneUsers() {
  const filteredUsers = []
  const users = appData.users
  for(var i in users) {
    const user = users[i]
    if(user.done) {
      filteredUsers.push(user)
    }
  }
  return filteredUsers
}
function getAllOnlineDrivers() {
  const filteredDrivers = []
  const drivers = appData.drivers
  for(var i in drivers) {
    const driver = drivers[i]
    if(driver.loggedin) {
      filteredDrivers.push(driver)
    }
  }
  return filteredDrivers
}
