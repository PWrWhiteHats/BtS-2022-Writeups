import tkinter as tk
from tkinter import messagebox


class Application(tk.Frame):
    xkey = b"REDACTED"
    secret = b"\x1a;\x017<#\x107\x15!\x10\x1b+\x00Q\x1fV&,'a\x07[:X\x1dJ\x07)c\x18[P\x16"

    def __init__(self,window):
        tk.Frame.__init__(self, window, height=350, width=250)
        window.title("Login v.3.8.0")
        self.welcome_label  = tk.Label(text="Enter creds my friend!", width=40,height=3)
        self.login_label    = tk.Label(text="Enter Login:")
        self.login_entry    = tk.Entry(width=40)
        self.password_label = tk.Label(text="Enter Password:")
        self.password_entry = tk.Entry(width=40, show="*")
        self.send_button    = tk.Button(text="Authenticate", width=34, command=self.authenticate)
        self.warning_label  = tk.Label(text="\nWARNING!!!\nAny  unsuccesful attempt will be logged!\nyou are going to be banned!", width=40,height=4)
    
    def pack(self):
        self.welcome_label.pack()
        self.login_label.pack()
        self.login_entry.pack()
        self.password_label.pack()
        self.password_entry.pack()
        self.send_button.pack()
        self.warning_label.pack()
    
    def authenticate(self):
        if self.login_entry.get() == "SyntaxError" and self.password_entry.get() == "@br@c@d@br@":
            messagebox.showinfo("Good to see you","Hello friend, u know how to log in, standard procedure, key in code.")
        else:
            messagebox.showinfo("Blocked","Seems like you are a bad person, IP blocked.")
        self.login_entry.delete(0,"end")
        self.password_entry.delete(0,"end")



def main():
    print("Waiting for authentication...")
    window = tk.Tk()
    window.wm_attributes('-toolwindow', 'True')
    Application(window).pack()
    window.mainloop()
    
if __name__== "__main__":
    main()
    
