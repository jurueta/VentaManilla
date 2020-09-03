package com.example.ventamanilla;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView totalPagar;
    private EditText cantidadManilla;
    private RadioGroup materialManilla, tipoMoneda;
    private Spinner tipoDije, materialDije;
    private final int CONVERSION_DOLAR = 3200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String [] arrayOpcionDije, arrayMaterialDije;

        totalPagar = findViewById(R.id.lblTotalPagar);
        cantidadManilla = findViewById(R.id.txtCantidadManillas);
        materialManilla = findViewById(R.id.rdbtnMaterialManilla);
        tipoMoneda = findViewById(R.id.rdbtnTipoMoneda);
        tipoDije = findViewById(R.id.sltTipoDije);
        materialDije = findViewById(R.id.sltMaterialDije);
        arrayOpcionDije = getResources().getStringArray(R.array.lista_opcion_dije);
        arrayMaterialDije = getResources().getStringArray(R.array.lista_material_dije);

        ArrayAdapter<String> adapterTipoDije = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arrayOpcionDije);
        ArrayAdapter<String> adapterMaterialDije = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arrayMaterialDije);

        tipoDije.setAdapter(adapterTipoDije);
        materialDije.setAdapter(adapterMaterialDije);
    }

    public void calculo(View v){
        double valorSeleccionado = 0, totalCOP = 0, TotalUS = 0, total;

        totalPagar.setText("");
        if (validar()) {
            int cantidad = Integer.parseInt(cantidadManilla.getText().toString());
            int opcTipo = tipoDije.getSelectedItemPosition();
            int opcMaterial = materialDije.getSelectedItemPosition();
            if (materialManilla.getCheckedRadioButtonId() == R.id.rdbtnMaterialCuero) {

                switch (opcTipo) {
                    case 1:
                        switch (opcMaterial) {
                            case 1:
                            case 2:
                                valorSeleccionado = 100;
                                break;
                            case 3:
                                valorSeleccionado = 70;
                                break;
                            case 4:
                                valorSeleccionado = 80;
                                break;
                        }
                        break;
                    case 2:
                        switch (opcMaterial) {
                            case 1:
                            case 2:
                                valorSeleccionado = 120;
                                break;
                            case 3:
                                valorSeleccionado = 90;
                                break;
                            case 4:
                                valorSeleccionado = 100;
                                break;
                        }
                        break;
                    default:
                        break;
                }
            } else {
                switch (opcTipo) {
                    case 1:
                        switch (opcMaterial) {
                            case 1:
                            case 2:
                                valorSeleccionado = 90;
                                break;
                            case 3:
                                valorSeleccionado = 50;
                                break;
                            case 4:
                                valorSeleccionado = 70;
                                break;
                        }
                        break;
                    case 2:
                        switch (opcMaterial) {
                            case 1:
                            case 2:
                                valorSeleccionado = 110;
                                break;
                            case 3:
                                valorSeleccionado = 80;
                                break;
                            case 4:
                                valorSeleccionado = 90;
                                break;
                        }
                        break;
                    default:
                        break;
                }
            }

            total = valorSeleccionado * cantidad;

            if (tipoMoneda.getCheckedRadioButtonId() == R.id.rdbtnMonedaCop) {
                total = total * CONVERSION_DOLAR;
                NumberFormat nf = NumberFormat.getInstance(new Locale("es", "CO"));
                totalPagar.setText(getText(R.string.txt_valor_total) + " " + nf.format(total) + " " + getText(R.string.txt_unidad_tipo_moneda_1));
            } else {
                totalPagar.setText(getText(R.string.txt_valor_total) + " " + total + " " + getText(R.string.txt_unidad_tipo_moneda_2));

            }
        }
    }
    public void limpiar (View V){
        totalPagar.setText("");
        cantidadManilla.setText("");
        materialManilla.clearCheck();
        tipoMoneda.clearCheck();
        tipoDije.setSelection(0);
        materialDije.setSelection(0);
    }

    public boolean validar(){

        if(cantidadManilla.getText().toString().isEmpty()){
            cantidadManilla.setError(getString(R.string.txt_faltan_datos));
            return false;
        }

        if (tipoDije.getSelectedItemPosition() == 0) {
            tipoDije.requestFocus();
            return false;
        }

        if(materialManilla.getCheckedRadioButtonId() == -1){
            materialManilla.requestFocus();
            return false;
        }

        if(tipoMoneda.getCheckedRadioButtonId() == -1){
            tipoMoneda.requestFocus();
            return false;
        }

        if (materialDije.getSelectedItemPosition() == 0) {
            materialDije.requestFocus();
            return false;
        }


        return true;
    }
}