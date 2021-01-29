export function GetData(){

  const namePerson = sessionStorage.getItem('namePerson') || '';
  const adress = sessionStorage.getItem('adress') || '';
  const city = sessionStorage.getItem('city') || '';
  const items = JSON.parse(sessionStorage.getItem('itemList'));
  const notes = sessionStorage.getItem('notes') || '';
  const status = sessionStorage.getItem('status') || 'Outstanding';
  const date = sessionStorage.getItem('date') || '';

  const list = [];

  list.push(namePerson,adress,city,items,notes,status,date);

  return list;

}

export default GetData;
