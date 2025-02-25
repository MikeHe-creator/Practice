"use client"
import "./globals.css"
import {useRef} from "react";

export default function Page(){
    const screenTxt= useRef(null);

    const JPdianji=(event)=>{
        const clickedChar = event.target.textContent;
        let currentContent=screenTxt.current.textContent;
        const containsOperator = /[+\-×÷]/.test(currentContent);
        const jjcc=["+","-","×","÷"];
        const shuziJp=["0","1","2","3","4","5","6","7","8","9","."];
        const lastOperatorIndex = Math.max(currentContent.lastIndexOf('+'),
            currentContent.lastIndexOf('-'), currentContent.lastIndexOf('×'), currentContent.lastIndexOf('÷'));
        if(clickedChar==='AC'){
            screenTxt.current.textContent="0";
        }else if(clickedChar==='CE'){
            const currentContent = screenTxt.current.textContent;
            if (currentContent.length === 1) {
                screenTxt.current.textContent = "0";
            } else {
                screenTxt.current.textContent = currentContent.slice(0, -1);
            }
        }else if(clickedChar==='%'){
            if (containsOperator) {
                if (lastOperatorIndex === currentContent.length - 1) {
                    return;
                }
                const lastNumber = currentContent.slice(lastOperatorIndex + 1);
                const percentValue = parseFloat(lastNumber) / 100;
                screenTxt.current.textContent = currentContent.slice(0, lastOperatorIndex + 1) + percentValue;
            } else {
                const percentValue = parseFloat(currentContent) / 100;
                screenTxt.current.textContent = percentValue.toString();
            }
        }else if(shuziJp.includes(clickedChar)||jjcc.includes(clickedChar)){
            if (currentContent === "0" && clickedChar !== ".") {
                screenTxt.current.textContent = clickedChar;
            } else if (lastOperatorIndex === currentContent.length - 1 && jjcc.includes(clickedChar)) {
                screenTxt.current.textContent = currentContent.slice(0, -1) + clickedChar;
            } else if (currentContent.endsWith(".") && clickedChar === ".") {
                screenTxt.current.textContent = currentContent;
            } else {
                screenTxt.current.textContent = currentContent + clickedChar;
            }
        }else{
            if(currentContent==="÷0"){
                screenTxt.current.textContent="Error";
            }else{
                const CalculateNum=currentContent.replace("×","*").replace("÷","/")
                screenTxt.current.textContent=formatResult(eval(CalculateNum));
            }
        }
    }

    function formatResult(result) {
        let resultStr = result.toString();
        if (resultStr.includes('.')) {
            resultStr = resultStr.replace(/0+$/, '');
            if (resultStr.endsWith('.')) {
                resultStr = resultStr.slice(0, -1);
            }
        }
        return resultStr;
    }


    return(
        <>
            <div className={"flex justify-center items-center w-screen h-screen"}>
                <div className={"w-[350px] h-[660px] border-[5px] border-amber-600 rounded-2xl border-solid flex flex-col justify-center p-5"}>
                    <div id={"screenTxt"} className={'w-[300px] h-[90px] border-[3px] border-blue-200 rounded-2xl bg-blue-200 flex justify-end items-end'}>
                        <p className={"text-2xl"} ref={screenTxt}>0</p>
                    </div>
                    <div className={"flex flex-row"}>
                        <div className={"w-[53px] h-0 border-[2px] border-gray-500 m-2"}></div>
                        <div>BenHerbst Calculator</div>
                        <div className={"w-[53px] h-0 border-[2px] border-gray-500 m-2"}></div>
                    </div>
                    <div className={"flex flex-wrap gap-2 w-[312px] mt-20"} onClick={JPdianji}>
                        <button className={"w-[70px] h-[70px] bg-orange-300 rounded-2xl text-2xl"}>AC</button>
                        <button className={"w-[70px] h-[70px] bg-orange-300 rounded-2xl text-2xl"}>CE</button>
                        <button className={"w-[70px] h-[70px] bg-orange-300 rounded-2xl text-2xl"}>%</button>
                        <button className={"w-[70px] h-[70px] bg-orange-300 rounded-2xl text-2xl"}>÷</button>

                        <button className={"w-[70px] h-[70px] bg-green-300 rounded-2xl text-2xl"}>7</button>
                        <button className={"w-[70px] h-[70px] bg-green-300 rounded-2xl text-2xl"}>8</button>
                        <button className={"w-[70px] h-[70px] bg-green-300 rounded-2xl text-2xl"}>9</button>
                        <button className={"w-[70px] h-[70px] bg-orange-300 rounded-2xl text-2xl"}>×</button>

                        <button className={"w-[70px] h-[70px] bg-green-300 rounded-2xl text-2xl"}>4</button>
                        <button className={"w-[70px] h-[70px] bg-green-300 rounded-2xl text-2xl"}>5</button>
                        <button className={"w-[70px] h-[70px] bg-green-300 rounded-2xl text-2xl"}>6</button>
                        <button className={"w-[70px] h-[70px] bg-orange-300 rounded-2xl text-2xl"}>-</button>

                        <button className={"w-[70px] h-[70px] bg-green-300 rounded-2xl text-2xl"}>1</button>
                        <button className={"w-[70px] h-[70px] bg-green-300 rounded-2xl text-2xl"}>2</button>
                        <button className={"w-[70px] h-[70px] bg-green-300 rounded-2xl text-2xl"}>3</button>
                        <button className={"w-[70px] h-[70px] bg-orange-300 rounded-2xl text-2xl"}>+</button>

                        <button className={"w-[70px] h-[70px] bg-green-300 rounded-2xl text-2xl"}>0</button>
                        <button className={"w-[70px] h-[70px] bg-green-300 rounded-2xl text-2xl"}>.</button>
                        <button className={"w-[150px] h-[70px] bg-red-300 rounded-2xl text-2xl"}>=</button>
                    </div>
                </div>
            </div>
        </>
    )
}