var nume = document.querySelector('#hora').value;

toHour = 0;
toMinute = 2;
toSecond = 0;

//cuenta atras
function countDown(){

	toSecond=toSecond-1;

	if(toSecond<0){
		toSecond=59;
		toMinute=toMinute-1;
	}

	form.second.value=toSecond;
	if(toMinute<0){

		toMinute=59;
		toHour=toHour-1;
	}

	form.minute.value=toMinute;
	form.hour.value=toHour;

	if(toHour<0){

		//final
		form.second.value=0;
		form.minute.value=0;
		form.hour.value=0;
	}
	else{
	  setTimeout(countDown,1000);
	}
}
//countDown();