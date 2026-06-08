import sys
import os
import subprocess
from PyQt6.QtWidgets import (QApplication, QMainWindow, QWidget, QVBoxLayout, QGridLayout,  
                             QHBoxLayout, QLabel, QLineEdit, QPushButton, QTextEdit, QDialog, QSizePolicy, QFrame)
from PyQt6.QtCore import Qt, QSize, QPropertyAnimation, QRect, QEasingCurve
from PyQt6.QtGui import QIcon, QPixmap

class CalculatorUI(QMainWindow):
    def __init__(self):
        super().__init__()
        self.initUI()

    def create_circular_button(self, icon_path, size=30, color="rgba(0, 0, 0, 25)", prompt=""):
        # 1. Initialize the button
        btn = QPushButton()
    
        # 2. Set the icon and sizing
        btn.setIcon(QIcon(icon_path))
        btn.setIconSize(QSize(size, size))
        btn.setFixedSize(size, size)
        btn.setToolTip(prompt)

        # 3. Calculate geometry for the circle
        border_radius = size // 2

        # 4. Inject variables using a proper f-string
        btn.setStyleSheet(f"""
            QPushButton {{
                border: none;
                background-color: transparent;
                border-radius: {border_radius}px; /* Apply globally to prevent flicker */
            }}
            QPushButton:hover {{
                background-color: rgba(0, 0, 0, 15);
            }}
            QPushButton:pressed {{
                background-color: {color};
            }}
        """)
    
        return btn

    def create_separator(self):
        sep = QFrame()
        sep.setFrameShape(QFrame.Shape.HLine)
        return sep

    def initUI(self):
        self.setWindowTitle("Multi-Term Functional Calculator")
        self.setMaximumSize(550, 600)
        self.resize(500, 450) 
        icon_path = os.path.join(os.path.dirname(__file__))
        
        icon_path = r"D:\Ved Kumar\Code\Java\pncCalculator\images\calculator_icon.png"
        self.setWindowIcon(QIcon(icon_path))

        # Central Widget
        central_widget = QWidget()
        self.setCentralWidget(central_widget)
        main_layout = QGridLayout(central_widget)
        main_layout.setSpacing(10)
        main_layout.setContentsMargins(15, 15, 15, 15) # left top right bottom
        main_layout.setColumnStretch(0, 1)

        TopChildLayout = QHBoxLayout()
        TopChildLayout.setSpacing(0)
        TopChildLayout.setContentsMargins(0, 0, 0, 0)
        main_layout.addLayout(TopChildLayout, 0, 0)

        #Title Icon
        titleIconPath = r"D:\Ved Kumar\Code\Java\pncCalculator\images\calculator_title.png"
        titleIcon = QLabel()
        titleIcon.setPixmap(QPixmap(titleIconPath))
        TopChildLayout.addWidget(titleIcon, alignment=Qt.AlignmentFlag.AlignTop)

        #Title Label
        titleLabel = QLabel("Multi-Term Functional Calculator")
        titleLabel.setStyleSheet("font-size: 18px; font-weight: bold; margin-bottom: 15px;")
        titleLabel.setMaximumHeight(50)
        titleLabel.setSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Fixed)
        # titleLabel.setAlignment(Qt.AlignmentFlag.AlignLeft | Qt.AlignmentFlag.AlignTop | Qt.AlignmentFlag.AlignVCenter)
        TopChildLayout.addWidget(titleLabel, alignment=Qt.AlignmentFlag.AlignTop)

        #History Icon
        hisIconPath = r"D:\Ved Kumar\Code\Java\pncCalculator\images\history_20.png"
        hisBtn = self.create_circular_button(hisIconPath, 30, "#CBDEE6", "History")

        TopChildLayout.addWidget(hisBtn, alignment=Qt.AlignmentFlag.AlignTop)
        # # main_layout.addWidget(hisIcon, 0, 1, alignment=Qt.AlignmentFlag.AlignTop)

        # Separator Top
        hSep_top = self.create_separator()
        main_layout.addWidget(hSep_top, 1, 0)  # Top

        main_layout.setRowStretch(2,1)
        main_layout.setRowMinimumHeight(0,0)
        main_layout.setRowMinimumHeight(1,0)

        # Expression Input Section

        # Expression Label
        exprLabel = QLabel("Enter Mathematical Expression: ")
        exprLabel.setStyleSheet("font-weight: bold; margin-top: 10px")
        main_layout.addWidget(exprLabel, 2, 0)

        # Expr Layout
        exprLayout = QHBoxLayout()
        # exprLayout.addStretch()
        exprLayout.setSpacing(5)
        main_layout.addLayout(exprLayout, 3, 0)

        # Expression Edit
        self.exprInput = QLineEdit()
        self.exprInput.setPlaceholderText('Example: 7*89-9p8/7c2*47+89+56*(7p4*6c3-90/5+45*(7*8p4))-90+3^4-e^2')
        self.exprInput.setMinimumHeight(35)
        exprLayout.addWidget(self.exprInput)

        # Send Button
        sendBtn_path = r"D:\Ved Kumar\Code\Java\pncCalculator\images\send_100.png"
        sendBtn = self.create_circular_button(sendBtn_path, 40, "#B5C7CC", "Calculate")
        exprLayout.addWidget(sendBtn)
        sendBtn.clicked.connect(self.calculate_expr)


        # Instructions
        instructionsLabel = QLabel('Supported Operations:')
        instructionsLabel.setStyleSheet('font-weight: bold; margin-top: 10px; font-size: 11px')
        main_layout.addWidget(instructionsLabel, 4, 0)

        instructions_text = QLabel(
            '• Arithmetic: +, -, *, /, ^\n'
            '• Parentheses: ( )\n'
            '• Permutation: nPr (e.g., 7p4)\n'
            '• Combination: nCr (e.g., 7c2)\n'
            '• Factorial: n! (e.g., 5!)'
        )
        instructions_text.setStyleSheet("font-size: 12px; margin-left: 10px;")
        main_layout.addWidget(instructions_text, 5, 0)

        
        #Separator Bottom
        main_layout.addWidget(self.create_separator(), 6, 0)  # Bottom

        # Result

        resHLayout = QHBoxLayout()
        resHLayout.setSpacing(0)
        main_layout.addLayout(resHLayout, 7, 0)

        # Result Label
        resLabel = QLabel("Result:")
        resLabel.setStyleSheet("font-weight: bold; margin-top: 20px;")
        resHLayout.addWidget(resLabel)

        # Push UP
        pushUPBtn_path = r"D:\Ved Kumar\Code\Java\pncCalculator\images\upload_16.png"
        self.pushUPBtn = self.create_circular_button(pushUPBtn_path, 20, "#B7DCE9", "Push")
        resHLayout.addWidget(self.pushUPBtn, alignment=Qt.AlignmentFlag.AlignRight)
        self.pushUPBtn.setEnabled(False)

        # Know More
        infoBtn_path = r"D:\Ved Kumar\Code\Java\pncCalculator\images\info_16.png"
        self.infoBtn = self.create_circular_button(infoBtn_path, 30, color="#B7DCE9" ,prompt='Know More')  
        self.infoBtn.setEnabled(False)
        self.infoBtn.clicked.connect(self.show_full_output)
        resHLayout.addWidget(self.infoBtn)

        #Result Display
        self.resDisplay = QTextEdit()
        self.resDisplay.setReadOnly(True)
        self.resDisplay.setStyleSheet("font-size: 12px")
        self.resDisplay.setMaximumHeight(70)
        main_layout.addWidget(self.resDisplay, 8, 0)

        # History panel

        # Panel 
        self.hisPanel = QFrame(self)
        self.hisPanel.setObjectName("hisPanel")
        self.hisPanel.setStyleSheet("""
            QFrame#hisPanel {
                background-color: #E0E7E7;
                border-left: 1px solid #7EC1DA
            }
            QLabel, QPushButton {
                color: white;
            }
        """)
        
        self.panel_Layout = QGridLayout(self.hisPanel)
        self.panel_Layout.setContentsMargins(10, 10, 10, 0)
        #For history
        self.panelVBoxLayout = QVBoxLayout()

        # For header
        hisPanelHboxLayout= QHBoxLayout()
        self.panel_Layout.addLayout(hisPanelHboxLayout, 0 , 0, 1, 2)


        #Delete button
        deleteBtn_path = r"D:\Ved Kumar\Code\Java\pncCalculator\images\delete_16.png"
        deleteBtn = self.create_circular_button(deleteBtn_path, 20, prompt="Delete")
        hisPanelHboxLayout.addWidget(deleteBtn, alignment=Qt.AlignmentFlag.AlignTop)
        deleteBtn.clicked.connect(lambda: self.delete_btn_func())


        #History Label
        panelLabel = QLabel("History")
        panelLabel.setStyleSheet(" color: black; font-size: 14px")
        hisPanelHboxLayout.addWidget(panelLabel, alignment=Qt.AlignmentFlag.AlignTop)

        #Cross Button
        crossBtn_path = r"D:\Ved Kumar\Code\Java\pncCalculator\images\cross.png"
        crossBtn = self.create_circular_button(crossBtn_path, 20, prompt='Close')
        hisPanelHboxLayout.addWidget(crossBtn, alignment=Qt.AlignmentFlag.AlignTop)

        
        
        #Nothing Here Label
        nothingHere_path = r"D:\Ved Kumar\Code\Java\pncCalculator\images\wareHouse_128.png"
        self.nothingHere = self.create_circular_button(nothingHere_path, 128)
        self.nothingHere.setEnabled(False)
        self.panel_Layout.addWidget(self.nothingHere, 
                                    2, 0, 1, 2, 
                                    alignment=Qt.AlignmentFlag.AlignTop | Qt.AlignmentFlag.AlignHCenter)


        #To navigate size
        self.hisPanel_width = 250
        self.hisVLayout = None
        self.hisPanel.setGeometry(self.width(), 200, self.hisPanel_width, self.height())
        self.menu_visible = False 
        
        self.hisPanel.raise_()

        #Toggle 
        hisBtn.clicked.connect(self.toggle_menu)
        crossBtn.clicked.connect(self.toggle_menu)

    def resizeEvent(self, event):
        
        current_x = self.width() - self.hisPanel_width if self.menu_visible else self.width()
        self.hisPanel.setGeometry(current_x, 0, self.hisPanel_width, self.height())
        super().resizeEvent(event)
    
    def toggle_menu(self):
        """Call this function to slide the panel in and out."""
        self.menu_visible = not self.menu_visible
        start_rect = self.hisPanel.geometry()

        x_pos = self.width() - self.hisPanel_width if self.menu_visible else self.width()
        end_rect = QRect(x_pos, 0, self.hisPanel_width, self.height())

        self.hisPanel.raise_()

        # 4. Smooth animation block
        self.animation = QPropertyAnimation(self.hisPanel, b"geometry")
        self.animation.setDuration(250)
        self.animation.setStartValue(start_rect)
        self.animation.setEndValue(end_rect)
        self.animation.setEasingCurve(QEasingCurve.Type.InOutQuad)
        self.animation.start()
    
    def is_float(self, value):
        try:
            float(value)
            return True
        except ValueError:
            return False

    def calculate_expr(self):
        expr = self.exprInput.text().strip()
        fontSize_success = "font-size: 20px"
        self.resDisplay.setStyleSheet("font-size: 12px")

        print("Send Button Pressed")

        if not expr:
            self.resDisplay.setText("Error: Please enter a mathematical expression")
            print("No expr found")
            return

        try:
            # Remove Spaces
            expr = expr.replace(" ", "")
            print("Replaced ' ', '' ")

            #Basic Validation
            if not any(c in expr for c in ['+', '-', '*', '/', 'p', 'c', '!', '^', '.' ,'e']):
                print("Char validtion: pass")
                if self.is_float(expr):
                    self.resDisplay.setStyleSheet(fontSize_success)
                    self.resDisplay.setText(expr)

                    self.pushUPBtn.setEnabled(True)
                    self.trigger_pushUpBtn()
                    print("push button enabled")

                    self.update_history(hisText=expr, ansText=expr)
                    print("history updater initiated")
                    return

                else:
                    self.resDisplay.setText("Error: Expression must contain operators")
                    print("operator validity: failed")
                    return
            
            # self.resDisplay.setText(f"Calculating: {expr}\n\nRunning Java calculator...")
            print(f"Calculating: {expr}\n\nRunning Java calculator...")

            try:
                java_out = self.run_java_calc(expr)
                self.last_java_output = java_out

                # find explicit 'final answer' line if present
                final_lines = [l for l in java_out.splitlines() if l.strip()]
                final_ans = None
                
                for line in reversed(final_lines):
                    if 'final answer' in line.lower():
                        print("finding final ans")
                        final_ans = line.split(':', 1)[-1].strip()
                        break
                
                if final_ans:
                    self.resDisplay.setStyleSheet(fontSize_success)
                    self.resDisplay.setText(final_ans)
                    print("resDisplay, stylesheet: success")
                    self.update_history(hisText=expr, ansText=final_ans)
                    print("history updater initiated")

                    self.pushUPBtn.setEnabled(True)
                    self.trigger_pushUpBtn()
                    print("push button enabled")

                elif final_lines:
                    self.resDisplay.setText(final_lines[-1])
                    
                    print("elif final ans")

                else:
                    self.resDisplay.setText("No output from Java calculator")
                    print("No output from Java calculator")


                # Enable Know more btn
                self.infoBtn.setEnabled(True)
                print("info enabled")

                print("from calc_expr", type(self.resDisplay.toPlainText())) # Debugging

            
            except subprocess.CalledProcessError as e:
                print("java error", e)
                self.resDisplay.setText(f"Java error: {e.stderr.strip()}")
            except Exception as e:
                print("except block run")
                self.resDisplay.setText(f"Error running Java calculator: {e}")

        except Exception as e:
            print("setText: ", e)
            self.resDisplay.setText(f"Error: {str(e)}")

    def run_java_calc(self, express: str) -> str:
        """Run the Java `sorter` program in this directory and return its stdout.

        Sends the expression over stdin to the Java program.
        """

        cwd = os.path.dirname(__file__)

        # Compile the Java source before running to ensure the latest code is executed.
        compile_cmd = ['javac', 'sorter.java']

        subprocess.run(compile_cmd, cwd=cwd, capture_output=True, text=True, check=True)
        print(" running java...")

        cmd = ['java', '-cp', '.', 'sorter']
        print("got cmd")

        #Ensure expression is a single line
        inp = express.strip() + '\n'

        result = subprocess.run(cmd, input=inp, capture_output=True, text=True, cwd=cwd, check=True)
        print("result return: success")
        return result.stdout

    def show_full_output(self):
        print("Opened Dialog Box")
        dlg = QDialog(self)
        dlg.setWindowTitle('Full Java Output')
        dlg.resize(550, 400)
        layout = QVBoxLayout(dlg)
        out_text = QTextEdit()
        out_text.setReadOnly(True)
        out_text.setPlainText(self.last_java_output or 'No Output available')
        layout.addWidget(out_text)
        close_btn = QPushButton('Close')
        close_btn.clicked.connect(dlg.accept)
        print("Closed button")
        layout.addWidget(close_btn)
        dlg.exec()
    
    def update_history(self, hisText="", ansText=""):
        hisText = QPushButton()
        hisText.setObjectName("hisLabel")

        ansText = QPushButton()
        ansText.setObjectName("ansLabel")
        

        expr = self.exprInput.text().strip()
        ans = self.resDisplay.toPlainText()

        if self.nothingHere is not None:
            self.panel_Layout.removeWidget(self.nothingHere)
            self.nothingHere.deleteLater()
            self.nothingHere = None
            
            print("Nothing here removed: success")
            row_num = self.panel_Layout.rowCount() - 1

            if self.panelVBoxLayout is not None:
                try:
                    self.panel_Layout.addLayout(self.panelVBoxLayout, row_num+1, 0, 1, 2, alignment=Qt.AlignmentFlag.AlignRight)
                    print("panel added by not none policy: success")

                except Exception as e:
                    print("cant->", e)

            print(row_num , "not") # Debugging

        else:
            row_num = self.panel_Layout.rowCount()
            print(row_num , "in")
        
        expr = expr.replace(" ", "") # Debugging

        try:

            if self.is_float(ans):

                ansText.clicked.connect(lambda: self.update_input(str(ans)))

                ansText.setText(f"={ans}")
                hisText.setText(expr)

                hisText.setStyleSheet("""
                    QPushButton#hisLabel {
                        border: none;
                        color: black;
                        font-size: 14px;
                        background-color: transparent;
                        text-align: right;
                    }
                    QPushButton#hisLabel:hover{
                        background-color: rgba(0, 0, 0, 25);
                    }
                    QPushButton#hisLabel:pressed{
                        background-color: #9EA6A8;
                    }
                """)
                ansText.setStyleSheet("""
                    QPushButton#ansLabel {
                        border: none;
                        color: black;
                        font-size: 14px;
                        background-color: transparent;
                        text-align: right;
                    }
                    QPushButton#ansLabel:hover{
                        background-color: rgba(0, 0, 0, 25);
                    }
                    QPushButton#ansLabel:pressed{
                        background-color: #9EA6A8;
                    }
                """)

                self.panelVBoxLayout.addWidget(hisText, alignment=Qt.AlignmentFlag.AlignRight)
                self.panelVBoxLayout.addWidget(ansText, alignment=Qt.AlignmentFlag.AlignRight)

                print("his test set: success")

                print("from his: ", type(hisText.text())) # Debugging
                print("from ans: ", type(ansText.text())) # Debugging

                hisText.clicked.connect(lambda: (self.exprInput.setText(hisText.text()), 
                                        print("hisText set, expr replaced: Success"))     )


        except ValueError:
            print("his/ans text")
            pass

    def update_input(self, newInput=""):
        print("update input initiated: success")
        try:
            if self.is_float(newInput):
                self.exprInput.setText(newInput)
                print("expr replaced: success")

        except ValueError as  e:
            print("from update_input", e) # Debugging
            pass
        except Exception as e:
            print("from update_input", e) # Debugging

    def delete_btn_func(self):

        try: 
            if self.panelVBoxLayout is not None:
                print("get-into-delete: initiated")

                while self.panelVBoxLayout.count() > 0:
                    item = self.panelVBoxLayout.takeAt(0)
                    widget = item.widget()
                    self.panelVBoxLayout.removeWidget(widget)
                    if widget is not None:
                        widget.deleteLater()
                        print("Widget-delete: success")

                self.panel_Layout.removeItem(self.panelVBoxLayout)
                self.panelVBoxLayout.deleteLater()

                print("layout-delete: success")

                self.panelVBoxLayout = QVBoxLayout()

                nothingHere_path = r"D:\Ved Kumar\Code\Java\pncCalculator\images\wareHouse_128.png"
                self.nothingHere = self.create_circular_button(nothingHere_path, 128)
                self.nothingHere.setEnabled(False)
                self.panel_Layout.addWidget(self.nothingHere, 
                                            2, 0, 1, 2, 
                                            alignment=Qt.AlignmentFlag.AlignTop | Qt.AlignmentFlag.AlignHCenter)

        except Exception as e:
            print("from- self.delete_btn_func->" , e)
        
    def trigger_pushUpBtn(self):
        try:
            self.pushUPBtn.clicked.disconnect()
            
        except TypeError:
            pass

        self.pushUPBtn.clicked.connect(lambda: self.update_input(str(self.resDisplay.toPlainText())))


if __name__ == '__main__':
    app = QApplication(sys.argv)
    calculator = CalculatorUI()       
    calculator.show()
    sys.exit(app.exec())
