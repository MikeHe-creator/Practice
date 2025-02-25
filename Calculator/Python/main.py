import tkinter as tk

def calculator_ui():
    windowsBody = tk.Tk()
    windowsBody.title("Calculator")
    windowsBody.geometry("400x700")
    icon = tk.PhotoImage(file="calculator.png")
    windowsBody.iconphoto(True, icon)

    screenFrame = tk.Frame(windowsBody)
    screenFrame.pack()
    screen = tk.Label(screenFrame, text="0", background="#D3D3D3", font=("Arial", 40), width="20", height="2", anchor="e")
    screen.pack(padx=15, pady=40)

    bandFrame = tk.Frame(windowsBody)
    bandFrame.pack()
    line1 = tk.Canvas(bandFrame, width=60, height=2, background="black")
    line1.grid(row=0, column=0, padx=2)
    bandName = tk.Label(bandFrame, text="BenHerbst Calculator", font=("Times New Roman", 10))
    bandName.grid(row=0, column=1, padx=2)
    line2 = tk.Canvas(bandFrame, width=60, height=2, background="black")
    line2.grid(row=0, column=2, padx=2)

    buttonFame = tk.Frame(windowsBody)
    buttonFame.pack()

    buttons = [
        ("AC", 0, 0), ("CE", 0, 1), ("%", 0, 2), ("÷", 0, 3),
        ("7", 1, 0), ("8", 1, 1), ("9", 1, 2), ("×", 1, 3),
        ("4", 2, 0), ("5", 2, 1), ("6", 2, 2), ("-", 2, 3),
        ("1", 3, 0), ("2", 3, 1), ("3", 3, 2), ("+", 3, 3),
        ("0", 4, 0), (".", 4, 1), ("=", 4, 2)
    ]
    for (text, row, col) in buttons:
        if text == "=":
            button = tk.Button(buttonFame, text=text, width=15, height=3, font=("Arial", 15),
                              command=lambda t=text, s=screen: on_button_click(t, s))
            button.grid(row=row, column=col, columnspan=2, padx=2, pady=2)
        else:
            button = tk.Button(buttonFame, text=text, width=7, height=3, font=("Arial", 15),
                              command=lambda t=text, s=screen: on_button_click(t, s))
            button.grid(row=row, column=col, padx=2, pady=2)

    return windowsBody

def on_button_click(text, screen):
    current_text = screen.cget("text")

    if text == "AC":
        screen.config(text="0")
    elif text == "CE":
        if len(current_text) == 1:
            screen.config(text="0")
        else:
            screen.config(text=current_text[:-1])
    elif text == "%":
        containYS = False
        for i in current_text:
            if i in ["+", "-", "×", "÷"]:
                containYS = True
                break

        if containYS:
            if current_text[-1] in ["+", "-", "×", "÷"]:
                screen.config(text=current_text)
            else:
                last_number = current_text.split("+")[-1].split("-")[-1].split("×")[-1].split("÷")[-1]
                new_number = str(float(last_number) / 100)
                screen.config(text=current_text[:-len(last_number)] + new_number)
        else:
            screen.config(text=str(float(current_text) / 100))
    elif text == "=":
        calculation_num(current_text,screen)
    else:
        if current_text == "0":
            screen.config(text=text)
        elif current_text[-1] == ".":
            if text.isdigit():
                screen.config(text=current_text + text)
        elif text == ".":
            last_number = ""
            for char in reversed(current_text):
                if char in ["+", "-", "×", "÷"]:
                    break
                last_number = char + last_number
            if "." not in last_number:
                screen.config(text=current_text + text)
        elif current_text[-1] in ["+", "-", "×", "÷"] and text in ["+", "-", "×", "÷"]:
            screen.config(text=current_text[:-1] + text)
        else:
            screen.config(text=current_text + text)

def calculation_num(current_text, screen):
    try:
        current_text = current_text.replace("×", "*").replace("÷", "/")
        result = str(eval(current_text))
        screen.config(text=result)
    except Exception as e:
        screen.config(text="Error")

if __name__ == "__main__":
    app = calculator_ui()
    app.mainloop()