import React, {useEffect, useState} from "react";

function AddItem() {

  const [itemList, setItemList] = useState([{ itemName: "", quantity: 0, price: 0, totalPrice: 0}]);
  const [totalSum, setTotalSum] = useState('');

  useEffect(() => {
    const sessionList = JSON.parse(sessionStorage.getItem('itemList'))
    if(sessionList){
      setItemList(sessionList);
    }
  }, []);

  useEffect(() => {
    const list = [...itemList];
    displayTotalSum();
    sessionStorage.setItem('itemList', JSON.stringify(list));
  }, [itemList])

  const displayTotalSum = () => {
    const list = [...itemList];
    let tempSum = 0;
    list.forEach(element => {
       tempSum += element.totalPrice;
    });
    setTotalSum(tempSum);
    sessionStorage.setItem('totalSum',tempSum);
  }

  const handleInputChange = (event, index) => {
    if(event.target.type === "tel"){
      event.target.value = event.target.value.replace(/\D/,'');
    }
    const { name, value } = event.target;
    const list = [...itemList];
    list[index][name] = value;
    list[index].totalPrice = list[index].quantity * list[index].price;
    setItemList(list);
  };
 
  const handleRemoveClick = index => {
    const list = [...itemList];
    list.splice(index, 1);
    setItemList(list);
  };

  const handleAddClick = () => {
    setItemList([...itemList, { itemName: "", quantity: 0, price: 0, totalPrice: 0}]);
  };

  return (
    <div>
      {itemList.map((x, i) => {
        return (
          <div key={i}>
          <input
            name="itemName"
            type="text"
            maxLength="35"
            placeholder="Enter item name"
            value={x.itemName}
            onChange={event => handleInputChange(event, i)}
          />
          <input
            type="tel"
            name="quantity"
            maxLength="2"
            min="0"
            value={x.quantity}
            onChange={event => handleInputChange(event, i)}
          />
          <input
            type="tel"
            name="price"
            maxLength="5"
            min="0"
            value={x.price}
            onChange={event => handleInputChange(event, i)}
          />
          <div className="price">{x.totalPrice}</div>
          <div>
            {(itemList.length - 1 === i && itemList.length <= 7) && <button className="itemButtons" onClick={handleAddClick}>Add</button>}
            {itemList.length !== 1 && <button className="itemButtons" onClick={() => handleRemoveClick(i)}>Remove</button>}
          </div>
          </div>
        );
      })}
      <div className="totalSum">Total: {totalSum}</div>
    </div>
  );
}

export default AddItem;