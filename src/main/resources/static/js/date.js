 const today = new Date();
 function dateFormat(today, format){
    format = format.replace("YYYY", today.getFullYear());
    format = format.replace("MM", ("0"+(today.getMonth() + 1)).slice(-2));
    format = format.replace("DD", ("0"+ today.getDate()).slice(-2));
    return format;
 }
 const data = dateFormat(today,'YYYY-MM-DD');
 //const field = document.querySelector('.date');
 const field2 = document.querySelector('.date2');

// if(field !== null) {
//    field.value = data;
//    field.setAttribute("min", data);
// }

if(field2 !== null){
    field2.value = data;
    field2.setAttribute("min", data);
}





