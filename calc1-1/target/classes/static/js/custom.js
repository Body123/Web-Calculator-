

function getHistory(){
    return document.getElementById("history class").innerHTML;
     

}

function printHistory(num){
    document.getElementById("history class").innerHTML=num;
}

function getOutput(){
    return document.getElementById("output").innerHTML;
     

}
function printOutput(num){
	/*
        if(num==""){
            document.getElementById("output").innerHTML=num;
		} else if(num.localeCompare(".")){
			document.getElementById("output").innerHTML=num;
		}
		else{
			*/
            document.getElementById("output").innerHTML=(num);
        //}
       


}
/*
function getFormattedNumber(num){
	var n = Number(num);
	var value = n.toLocaleString("en");
	return value;
}

function reverseNumberFormat(num){
	return Number(num.replace(/,/g,''));
}
*/
var operator = document.getElementsByClassName("operator");
for(var i =0;i<operator.length;i++){
	operator[i].addEventListener('click',function(){
		if(this.id=="clear"){
			printHistory("");
			printOutput("");
        }
        else if(this.id=="arrow"){
			var output=(getOutput()).toString();
			if(output){
				output= output.substr(0,output.length-1);
				printOutput(output);
			}
		}else if(this.id=="backspace"){
			printOutput("");
			
		}else{
			var output=getOutput();
			var history=getHistory();
			if(output==""&&history!=""){
				if(isNaN(history[history.length-1])){
					history= history.substr(0,history.length-1);
				}
			}
			
			if( history!=""||output!="" ){
				output= output==""?output:(output);
				history=history+output;
				
				
					history=history+this.id;
					printHistory(history);
					printOutput("");
				
			}
		}
    }
)}

var number = document.getElementsByClassName("number");
for(var i =0;i<number.length;i++){
	number[i].addEventListener('click',function(){
		
		var output=(getOutput());
		
			output=output+this.id;
			printOutput(output);
			
		
	});
}




