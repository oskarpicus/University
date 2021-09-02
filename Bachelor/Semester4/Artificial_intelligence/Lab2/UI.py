from tkinter import *
from tkinter.filedialog import askopenfilename
from Constants import *
from Writer import Writer


class UI:
    def __init__(self):
        self.__window = Tk()
        self.__buttonChooseFile = Button(self.__window, text="Choose file", command=self.__chooseFile)
        self.__buttonCompute = Button(self.__window, text="Compute", command=self.__compute)
        self.__textBox = Text(self.__window, wrap=WORD, width=30, height=10)
        self.__init()

    def run(self):
        self.__window.mainloop()

    def __init(self):
        self.__window.title("iMap")
        row = 0
        font = Constants.FONT + " " + Constants.SIZE + " " + Constants.FONT_WEIGHT
        Label(self.__window, text="Select a file", font=font).grid(row=row, column=0, padx=Constants.PADDING,
                                      pady=Constants.PADDING)
        row += 1
        self.__buttonChooseFile.grid(row=row, column=0, padx=Constants.PADDING,
                                      pady=Constants.PADDING)
        row += 1
        self.__buttonCompute.grid(row=row, column=0, padx=Constants.PADDING,
                                   pady=Constants.PADDING)
        row += 1
        self.__textBox.grid(row=row, column=0)

    def __chooseFile(self):
        self.__writer = Writer(askopenfilename())
        self.__clearTextBox()
        self.__textBox.insert(END, Constants.SELECTED_MESSAGE+self.__writer.filename)

    def __compute(self):
        self.__clearTextBox()
        self.__textBox.insert(END, Constants.RESULT_MESSAGE + self.__writer.solutionFilename())
        self.__writer.compute()

    def __clearTextBox(self):
        self.__textBox.delete(0.0, END)
