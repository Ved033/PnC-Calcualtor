import sys
import os
import subprocess
from PyQt6.QtWidgets import (QApplication, QMainWindow, QWidget, QVBoxLayout, 
                             QHBoxLayout, QLabel, QLineEdit, QPushButton, QTextEdit, QDialog)
from PyQt6.QtCore import Qt
from PyQt6.QtGui import QIcon

class CalculatorUI(QMainWindow):
    def __init__(self):
        super().__init__()
        self.initUI()
    
    def initUI(self):
        self.setWindowTitle('Multi-term Functional Calculator')
        self.resize(500, 500)
        icon_path = os.path.join(os.path.dirname(__file__), "calculator_icon.png")
        if os.path.exists(icon_path):
            self.setWindowIcon(QIcon(icon_path))
        
        # Central Widget
        central_widget = QWidget()
        self.setCentralWidget(central_widget)
        main_layout = QVBoxLayout(central_widget)
        main_layout.setSpacing(15)
        # main_layout.setWindowIcon(QIcon("calculator.png"))  # Ensure you have an icon file or adjust the path
        main_layout.setContentsMargins(20, 20, 20, 20)
        
        # Title Label
        title_label = QLabel('Multi-term Functional Calculator')
        title_label.setStyleSheet("font-size: 18px; font-weight: bold; margin-bottom: 15px;")
        main_layout.addWidget(title_label)
        
        # Expression Input Section
        expr_label = QLabel('Enter Mathematical Expression:')
        expr_label.setStyleSheet("font-weight: bold; margin-top: 10px;")
        main_layout.addWidget(expr_label)
        
        self.expression_input = QLineEdit()
        self.expression_input.setPlaceholderText('Example: 7*89-65+66-9p8/7c2*47+89+56*(7p4*6c3-90/5+45*(7*8p4))-90')
        self.expression_input.setMinimumHeight(40)
        main_layout.addWidget(self.expression_input)
        
        # Instructions
        instructions_label = QLabel('Supported Operations:')
        instructions_label.setStyleSheet("font-weight: bold; margin-top: 10px; font-size: 11px;")
        main_layout.addWidget(instructions_label)
        
        instructions_text = QLabel(
            '• Arithmetic: +, -, *, /\n'
            '• Parentheses: ( )\n'
            '• Permutation: nPr (e.g., 7p4)\n'
            '• Combination: nCr (e.g., 7c2)\n'
            '• Factorial: n! (e.g., 5!)'
        )
        instructions_text.setStyleSheet("font-size: 10px; margin-left: 10px;")
        main_layout.addWidget(instructions_text)
        
        # Calculate Expression Button
        calc_expr_btn = QPushButton('Calculate Expression')
        calc_expr_btn.setStyleSheet("background-color: #2196F3; color: white; font-weight: bold; padding: 10px;")
        calc_expr_btn.clicked.connect(self.calculate_expression)
        main_layout.addWidget(calc_expr_btn)
        
        # Separator
        separator_label = QLabel('─' * 60)
        separator_label.setStyleSheet("margin: 15px 0px;")
        main_layout.addWidget(separator_label)
        
        # (Permutation/Combination quick calculator removed)
        
        # Result Display
        result_label = QLabel('Result:')
        result_label.setStyleSheet("font-weight: bold; margin-top: 20px;")
        main_layout.addWidget(result_label)
        
        self.result_display = QTextEdit()
        self.result_display.setReadOnly(True)
        self.result_display.setMaximumHeight(150)
        main_layout.addWidget(self.result_display)

        # Know More button (hidden/disabled until Java output available)
        self.know_more_btn = QPushButton('Know More')
        self.know_more_btn.setEnabled(False)
        self.know_more_btn.clicked.connect(self.show_full_output)
        main_layout.addWidget(self.know_more_btn)
        
        # Add stretch to push content to top
        main_layout.addStretch()
        
        self.last_java_output = ''
    
    def show_full_output(self):
        dlg = QDialog(self)
        dlg.setWindowTitle('Full Java Output')
        dlg.resize(700, 450)
        layout = QVBoxLayout(dlg)
        out_text = QTextEdit()
        out_text.setReadOnly(True)
        out_text.setPlainText(self.last_java_output or 'No output available')
        layout.addWidget(out_text)
        close_btn = QPushButton('Close')
        close_btn.clicked.connect(dlg.accept)
        layout.addWidget(close_btn)
        dlg.exec()
    
    def calculate_expression(self):
        expression = self.expression_input.text().strip()
        
        if not expression:
            self.result_display.setText("Error: Please enter a mathematical expression")
            return
        
        try:
            # Remove spaces
            expression = expression.replace(" ", "")
            
            # Basic validation
            if not any(c in expression for c in ['+', '-', '*', '/', 'p', 'c', '!']):
                self.result_display.setText("Error: Expression must contain operators")
                return
            
            self.result_display.setText(f"Calculating: {expression}\n\nRunning Java calculator...")

            try:
                java_out = self.run_java_calc(expression)
                self.last_java_output = java_out
                # find explicit 'final answer' line if present
                final_lines = [l for l in java_out.splitlines() if l.strip()]
                final_ans = None
                for line in reversed(final_lines):
                    if 'final answer' in line.lower():
                        final_ans = line.split(':', 1)[-1].strip()
                        break

                if final_ans:
                    self.result_display.setText(final_ans)
                elif final_lines:
                    # show only the last non-empty line as a short result
                    self.result_display.setText(final_lines[-1])
                else:
                    self.result_display.setText('No output from Java calculator')

                # enable know-more button so user can view full output
                self.know_more_btn.setEnabled(True)
            except subprocess.CalledProcessError as e:
                self.result_display.setText(f"Java error: {e.stderr.strip()}")
            except Exception as e:
                self.result_display.setText(f"Error running Java calculator: {e}")
        
        except Exception as e:
            self.result_display.setText(f"Error: {str(e)}")

    def run_java_calc(self, expr: str) -> str:
        """Run the Java `sorter` program in this directory and return its stdout.

        Sends the expression over stdin to the Java program.
        """
        # Directory containing this script (also where compiled .class files live)
        cwd = os.path.dirname(__file__)
        cmd = ['java', '-cp', '.', 'sorter']

        # Ensure expression is a single line
        inp = expr.strip() + '\n'

        result = subprocess.run(cmd, input=inp, capture_output=True, text=True, cwd=cwd, check=True)
        return result.stdout

if __name__ == '__main__':
    app = QApplication(sys.argv)
    calculator = CalculatorUI()
    calculator.show()
    sys.exit(app.exec())

