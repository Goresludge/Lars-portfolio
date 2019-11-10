var size = 16;

var oddPrint = function(output){
  for(var i = 1; i <= size; i++){
    if(i % 2 == 0){
      output += "#";
    }
    else {
      output += " ";
    }
  }
  console.log(output);
}
var evenPrint = function(output){
  for(var i = 1; i <= size; i++){
    if(i % 2 == 0){
      output += " ";
    }
    else {
      output += "#";
    }
  }
  console.log(output);
}

for(var grid = 0; grid <= 8; grid++)
  if (size == 0) {
    console.log("Nothing to show");
  }
  if(size > 0){
    for(var i = 1; i <= size; i++){
      if(i % 2 == 0){
        oddPrint("");
      }
      else{
        evenPrint("");
      }
    }
  }
