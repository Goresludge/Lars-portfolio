
export function ValidInputs (list){

  let errorMsg = "";

  console.log(list);


  if(list[3] === null){
    list[3] = [];
    errorMsg += "Items are missing or are uncomplete\n";
  }


  let containsItems = true;
  list[3].forEach(element => {
    const itemList = Object.values(element)
    console.log(itemList);
    if(!(itemList[0].trim() && itemList[1].toString().trim() && itemList[2].toString().trim())){
      containsItems = false;
    }
  });

  if(!list[0] || !list[0].trim()){
    errorMsg += "Name is missing\n";
  }
  if(!list[1] || !list[1].trim()){
    errorMsg += "Adress is missing\n";
  }
  if(!list[2] || !list[2].trim()){
    errorMsg += "City is missing\n";
  }
  if(!containsItems){
    errorMsg += "Items are missing or are uncomplete\n";
  }
  if(!list[5]){
    sessionStorage.setItem('status','Outstanding');
  }
  if(!list[6]){
    errorMsg += "No date is selected\n";
  }

  return errorMsg;

}

export default ValidInputs;