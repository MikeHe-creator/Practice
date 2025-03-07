import tkinter,os
import pandas
from tkinter import filedialog

fames = []
current_index = 0

def opreateui():
    ntwindow=tkinter.Tk()
    ntwindow.title("李卓东国考职位筛选工具")
    ntwindow.geometry("600x500")

    welcomefame=tkinter.Frame(ntwindow)
    fames.append(welcomefame)
    welcomefame.pack()
    welcome_label=tkinter.Label(welcomefame,text="李卓东同学，欢迎使用！国考加油,祝您上岸！",font=("等线", 20))
    welcome_button=tkinter.Button(welcomefame,text="下一步",font=("等线", 10), command=nextstep)
    welcome_label.pack(padx=15, pady=100)
    welcome_button.pack(padx=15, pady=80)

    dealwithfame=tkinter.Frame(ntwindow)
    fames.append(dealwithfame)
    dealwithfame.pack()
    dealwith_label = tkinter.Label(dealwithfame, text="从官网上下载国考职位表后点击开始，开始筛选您的表格", font=("等线", 20),wraplength=400)
    dealwith_label.pack(padx=15, pady=100)
    dealwith_button=tkinter.Button(dealwithfame,text="开始",font=("等线", 10), command=lambda:dealwith_table(dealwith_button,dealwithfame,dealwith_label,ntwindow))
    dealwith_button.pack(padx=15, pady=80)

    return ntwindow

def nextstep():
    for fa in fames:
        fa.pack_forget()
    fames[current_index+1].pack()

def dealwith_table(dealwith_button, dealwithfame,dealwith_label,ntwindow):
    dealwith_button.pack_forget()
    dealwith_label.config(text="正在处理您的表格，请稍后……")
    filepath=filedialog.askopenfilename(
        title="选择文件",
        filetypes=[("Excel 文件", "*.xlsx"), ("Excel 文件", "*.xls"),("所有文件", "*.*")]
    )
    if filepath:
        try:
            excel_file = pandas.ExcelFile(filepath)
            sheet_names = excel_file.sheet_names
            all_filtered_data = []
            for sheet in sheet_names:
                sheetData=excel_file.parse(sheet,skiprows=1)

                fit_zhuanye = sheetData[sheetData["专业"].str.contains("金融|不限", na=False, regex=True)]
                fit_xueli=fit_zhuanye[~fit_zhuanye["学历"].str.contains("仅限硕士研究生|硕士研究生以上",na=False, regex=True)]
                fit_party=fit_xueli[~fit_xueli["政治面貌"].str.contains("中共党员",na=False, regex=True)]
                fit_exp=fit_party[~fit_party["服务基层项目工作经历"].str.contains("大学生村官、农村义务教育阶段学校教师特设岗位计划、“三支一扶”计划、大学生志愿服务西部计划、在军队服役5年（含）以上的高校毕业生退役士兵",na=False, regex=True)]
                fit_student=fit_exp[~fit_exp["备注"].str.contains("届高校毕业生|应届高校毕业生",na=False, regex=True)]
                print(fit_student.head())
                all_filtered_data.append(fit_student)
            final_result = pandas.concat(all_filtered_data, ignore_index=True)
            save_path = filedialog.asksaveasfilename(
                defaultextension=".xlsx",
                filetypes=[("Excel 文件", "*.xlsx")],
                title="保存筛选后的文件"
            )
            if save_path:
                with pandas.ExcelWriter(save_path, engine='xlsxwriter') as writer:
                    final_result.to_excel(writer, sheet_name="筛选表", index=False)
                print(f"筛选后的数据已保存到: {save_path}")
        except Exception as e:
            print("读取文件时出错:", e)
    dealwith_label.config(text="已处理完毕，单击完成，退出应用程序。 祝您国考考试顺利！金榜提名！上岸成功！")
    newbotton=tkinter.Button(dealwithfame,text="完成",font=("等线", 10), command=lambda:complete(ntwindow))
    newbotton.pack(padx=15, pady=80)

def complete(ntwindow):
    ntwindow.quit()
    ntwindow.destroy()

if __name__=="__main__":
    app=opreateui()
    app.mainloop()