package com.example.unsolito;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Calculator extends AppCompatActivity {

    // Declaración de botones
    Button btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_0,
            btn_dot, btn_ac, btn_c, btn_b1, btn_b2, btn_sin, btn_cos, btn_tan,
            btn_log, btn_ln, btn_fact, btn_x2, btn_akar, btn_inv, btn_div,
            btn_multiply, btn_minus, btn_plus, btn_equals, btn_phi, btnExit;

    // TextViews para mostrar resultados
    TextView text_small, text_big;

    // Constante PI
    String phi = "3.14159265358979";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        // Inicializar vistas
        initializeViews();

        // Configurar listeners de botones
        setupClickListeners();

        btnExit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(view.getContext(), Menu.class); // Cambia si quieres ir a otra pantalla
                startActivity(intent);
                finish();
            }
        });
    }

    private void initializeViews() {
        // Inicializar botones numéricos
        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);
        btn_3 = findViewById(R.id.btn_3);
        btn_4 = findViewById(R.id.btn_4);
        btn_5 = findViewById(R.id.btn_5);
        btn_6 = findViewById(R.id.btn_6);
        btn_7 = findViewById(R.id.btn_7);
        btn_8 = findViewById(R.id.btn_8);
        btn_9 = findViewById(R.id.btn_9);
        btn_0 = findViewById(R.id.btn_0);

        // Inicializar botones de operaciones
        btn_dot = findViewById(R.id.btn_dot);
        btn_ac = findViewById(R.id.btn_ac);
        btn_c = findViewById(R.id.btn_c);
        btn_b1 = findViewById(R.id.btn_b1);
        btn_b2 = findViewById(R.id.btn_b2);
        btn_plus = findViewById(R.id.btn_plus);
        btn_minus = findViewById(R.id.btn_minus);
        btn_multiply = findViewById(R.id.btn_multiply);
        btn_div = findViewById(R.id.btn_div);
        btn_equals = findViewById(R.id.btn_equals);

        // Inicializar botones científicos
        btn_sin = findViewById(R.id.btn_sin);
        btn_cos = findViewById(R.id.btn_cos);
        btn_tan = findViewById(R.id.btn_tan);
        btn_log = findViewById(R.id.btn_log);
        btn_ln = findViewById(R.id.btn_ln);
        btn_x2 = findViewById(R.id.btn_x2);
        btn_fact = findViewById(R.id.btn_fact);
        btn_akar = findViewById(R.id.btn_akar);
        btn_inv = findViewById(R.id.btn_inv);
        btn_phi = findViewById(R.id.btn_phi);

        btnExit = findViewById(R.id.btnExit);

        // Inicializar TextViews
        text_big = findViewById(R.id.text_big);
        text_small = findViewById(R.id.text_small);
    }

    private void setupClickListeners() {
        // Listeners para números
        setupNumberListeners();

        // Listeners para operaciones básicas
        setupBasicOperationListeners();

        // Listeners para funciones científicas
        setupScientificFunctionListeners();

        // Listeners para botones especiales
        setupSpecialButtonListeners();
    }

    private void setupNumberListeners() {
        btn_1.setOnClickListener(v -> appendToDisplay("1"));
        btn_2.setOnClickListener(v -> appendToDisplay("2"));
        btn_3.setOnClickListener(v -> appendToDisplay("3"));
        btn_4.setOnClickListener(v -> appendToDisplay("4"));
        btn_5.setOnClickListener(v -> appendToDisplay("5"));
        btn_6.setOnClickListener(v -> appendToDisplay("6"));
        btn_7.setOnClickListener(v -> appendToDisplay("7"));
        btn_8.setOnClickListener(v -> appendToDisplay("8"));
        btn_9.setOnClickListener(v -> appendToDisplay("9"));
        btn_0.setOnClickListener(v -> appendToDisplay("0"));
        btn_dot.setOnClickListener(v -> appendToDisplay("."));
    }

    private void setupBasicOperationListeners() {
        btn_plus.setOnClickListener(v -> appendToDisplay("+"));
        btn_minus.setOnClickListener(v -> appendToDisplay("-"));
        btn_multiply.setOnClickListener(v -> appendToDisplay("×"));
        btn_div.setOnClickListener(v -> appendToDisplay("÷"));
        btn_b1.setOnClickListener(v -> appendToDisplay("("));
        btn_b2.setOnClickListener(v -> appendToDisplay(")"));
    }

    private void setupScientificFunctionListeners() {
        btn_sin.setOnClickListener(v -> appendToDisplay("sin("));
        btn_cos.setOnClickListener(v -> appendToDisplay("cos("));
        btn_tan.setOnClickListener(v -> appendToDisplay("tan("));
        btn_log.setOnClickListener(v -> appendToDisplay("log("));
        btn_ln.setOnClickListener(v -> appendToDisplay("ln("));

        btn_phi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text_small.setText("π");
                appendToDisplay(phi);
            }
        });

        btn_akar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String val = text_big.getText().toString();
                if (!val.isEmpty() && !val.equals("0")) {
                    try {
                        double result = Math.sqrt(Double.parseDouble(val));
                        text_big.setText(formatResult(result));
                        text_small.setText("√" + val);
                    } catch (NumberFormatException e) {
                        text_big.setText("Error");
                    }
                }
            }
        });

        btn_x2.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                String val = text_big.getText().toString();
                if (!val.isEmpty() && !val.equals("0")) {
                    try {
                        double d = Double.parseDouble(val);
                        double square = d * d;
                        text_big.setText(formatResult(square));
                        text_small.setText(val + "²");
                    } catch (NumberFormatException e) {
                        text_big.setText("Error");
                    }
                }
            }
        });

        btn_fact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String val = text_big.getText().toString();
                if (!val.isEmpty()) {
                    try {
                        int number = Integer.parseInt(val);
                        if (number >= 0 && number <= 20) {
                            long fact = factorial(number);
                            text_big.setText(String.valueOf(fact));
                            text_small.setText(number + "!");
                        } else {
                            text_big.setText("Error");
                        }
                    } catch (NumberFormatException e) {
                        text_big.setText("Error");
                    }
                }
            }
        });

        btn_inv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String val = text_big.getText().toString();
                if (!val.isEmpty() && !val.equals("0")) {
                    try {
                        double d = Double.parseDouble(val);
                        if (d != 0) {
                            double inverse = 1.0 / d;
                            text_big.setText(formatResult(inverse));
                            text_small.setText("1/" + val);
                        } else {
                            text_big.setText("Error");
                        }
                    } catch (NumberFormatException e) {
                        text_big.setText("Error");
                    }
                }
            }
        });
    }

    private void setupSpecialButtonListeners() {
        btn_ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text_big.setText("0");
                text_small.setText("");
            }
        });

        btn_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String val = text_big.getText().toString();
                if (!val.isEmpty() && !val.equals("0")) {
                    if (val.length() == 1) {
                        text_big.setText("0");
                    } else {
                        val = val.substring(0, val.length() - 1);
                        text_big.setText(val);
                    }
                }
            }
        });

        btn_equals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String val = text_big.getText().toString();
                if (!val.isEmpty()) {
                    try {
                        String replacedStr = val.replace('÷', '/').replace('×', '*');
                        double result = eval(replacedStr);
                        text_small.setText(val);
                        text_big.setText(formatResult(result));
                    } catch (Exception e) {
                        text_big.setText("Error");
                        text_small.setText("");
                    }
                }
            }
        });
    }


    private void appendToDisplay(String value) {
        String currentText = text_big.getText().toString();
        if (currentText.equals("0") || currentText.equals("Error")) {
            text_big.setText(value);
        } else {
            text_big.setText(currentText + value);
        }
    }

    private String formatResult(double result) {
        if (result == (long) result) {
            return String.valueOf((long) result);
        } else {
            return String.format("%.10g", result);
        }
    }

    private long factorial(int n) {
        if (n <= 1) return 1;
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm();
                    else if (eat('-')) x -= parseTerm();
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor();
                    else if (eat('/')) x /= parseFactor();
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor();
                if (eat('-')) return -parseFactor();

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') {
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else if (func.equals("log")) x = Math.log10(x);
                    else if (func.equals("ln")) x = Math.log(x);
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor());
                return x;
            }
        }.parse();
    }
}